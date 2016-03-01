package core;

import org.lwjgl.util.vector.Vector3f;

import render.MeshTexture;
import render.TexturedMesh;

public class Pane extends GameObject{
	
	protected static TexturedMesh test = new TexturedMesh(OBJLoader.loadObjModel("block_n"),
			new MeshTexture(Loader.loadTexture("justBLUE")));

	public Pane(Vector3f position, Vector3f rotation, Vector3f scale) {
		super(test, position, rotation, scale);
	}

}
