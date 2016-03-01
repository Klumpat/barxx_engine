package render;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import core.GameObject;
import core.Maths;

public class GameObjectRenderer {

	private StaticShader shader;

	public GameObjectRenderer(StaticShader shader, Matrix4f projectionMatrix) {
		this.shader = shader;

		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}

	public void render(Map<TexturedMesh, List<GameObject>> entities) {
		for (TexturedMesh model : entities.keySet()) {
			prepareTexturedModel(model);
			List<GameObject> batch = entities.get(model);
			for (GameObject entity : batch) {
				prepareInstances(entity);

				// render here:
				GL11.glDrawElements(GL11.GL_TRIANGLES, model.getMesh().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);

			}

			unbindTexturedModel();
		}
	}

	private void prepareTexturedModel(TexturedMesh model) {
		Mesh mesh = model.getMesh();

		GL30.glBindVertexArray(mesh.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);

		MeshTexture texture = model.getTexture();
		if(texture.isHasTransparency()){
			MasterRenderer.disableCulling();
		}
		
		if(texture.isHasReflectivity()){
			shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
		}
		
		

		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
	}

	private void unbindTexturedModel() {
		MasterRenderer.enableCulling();
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}

	private void prepareInstances(GameObject entity) {
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotation(),
				entity.getScale());
		shader.loadTransformationMatrix(transformationMatrix);
	}

	@Deprecated
	public void render(GameObject entity) {
		TexturedMesh model = entity.getModel();
		Mesh rawModel = model.getMesh();

		GL30.glBindVertexArray(rawModel.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);

		Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotation(),
				entity.getScale());
		shader.loadTransformationMatrix(transformationMatrix);

		MeshTexture texture = model.getTexture();

		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
		GL11.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}
}
