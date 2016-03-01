package core;

import org.lwjgl.util.vector.Vector3f;

import render.TexturedMesh;

public abstract class GameObject {

	protected TexturedMesh model;
	protected Vector3f position;
	protected Vector3f rotation;
	protected Vector3f scale;

	public GameObject(TexturedMesh mesh, Vector3f position, Vector3f rotation, Vector3f scale) {
		this.model = mesh;
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;

	}
	
	public void increaseRotation(Vector3f amt){
		rotation.x += amt.x;
		rotation.y += amt.y;
		rotation.z += amt.z;
	}
	
	public void increasePosition(Vector3f amt){
		position.x += amt.x;
		position.y += amt.y;
		position.z += amt.z;
	}

	public TexturedMesh getModel() {
		return model;
	}

	public Vector3f getPosition() {
		return position;
	}

	public Vector3f getRotation() {
		return rotation;
	}

	public Vector3f getScale() {
		return scale;
	}

	public void setMesh(TexturedMesh mesh) {
		this.model = mesh;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public void setRotation(Vector3f rotation) {
		this.rotation = rotation;
	}

	public void setScale(Vector3f scale) {
		this.scale = scale;
	}

}
