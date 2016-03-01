package render;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import core.Camera;
import core.GameObject;
import core.Light;
import core.Maths;

public class MasterRenderer {

	private static final float FOV = 70;
	private static final float zNEAR = 0.1f;
	private static final float zFAR = 1000;

	private static final float RED = 0.0f;
	private static final float GREEN = 0.0f;
	private static final float BLUE = 0.0f;

	private Matrix4f projectionMatrix;

	private GameObjectRenderer renderer;
	private StaticShader shader = new StaticShader();

	private Map<TexturedMesh, List<GameObject>> entities = new HashMap<TexturedMesh, List<GameObject>>();

	public MasterRenderer() {
		enableCulling();
		projectionMatrix = Maths.createProjectionMatrix(FOV, zNEAR, zFAR);
		renderer = new GameObjectRenderer(shader, projectionMatrix);
		
	}

	public void render(Light light, Camera camera) {
		prepare();

		// entity rendering
		enableCulling();
		shader.start();
		shader.loadSkycolor(new Vector3f(RED, GREEN, BLUE));
		shader.loadLight(light);
		shader.loadViewMatrix(camera);
		renderer.render(entities);
		shader.stop();

		entities.clear();
	}

	public static void enableCulling() {
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
	}

	public static void disableCulling() {
		GL11.glDisable(GL11.GL_CULL_FACE);
	}

	public void prepare() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClearColor(RED, GREEN, BLUE, 1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}

	public void processGameObjects(GameObject entity) {
		TexturedMesh entityModel = entity.getModel();
		List<GameObject> batch = entities.get(entityModel);

		if (batch != null) {
			batch.add(entity);
		} else {
			List<GameObject> newBatch = new ArrayList<GameObject>();
			newBatch.add(entity);
			entities.put(entityModel, newBatch);
		}

	}

	public Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}

	public void cleanUp() {
		shader.cleanUp();
	}

}
