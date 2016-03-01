package core;

import org.lwjgl.util.vector.Vector3f;

import render.MeshTexture;
import render.TexturedMesh;

public class Block extends GameObject {

	protected static TexturedMesh crate = new TexturedMesh(OBJLoader.loadObjModel("block"),
			new MeshTexture(Loader.loadTexture("RoughBlockWall")));
	
	protected static TexturedMesh dragon = new TexturedMesh(OBJLoader.loadObjModel("dragon"),
			new MeshTexture(Loader.loadTexture("justred")));

	
	public Block(Vector3f position, Vector3f rotation, Vector3f scale) {
		super(dragon, position, rotation, scale);
	}

}
