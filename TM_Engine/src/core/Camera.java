package core;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	
	private Vector3f position;
	private Vector3f rotation;

	public Camera(Vector3f position, Vector3f rotation) {
		this.rotation = rotation;
		this.position = position;
	}
	
	public Camera() {
		this.rotation = new Vector3f(0,0,0);
		this.position = new Vector3f(0,0,0);
	}
	
	public void move(){
		if(Keyboard.isKeyDown(Keyboard.KEY_A)){
			position.x-=0.10f;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_D)){
			position.x+=0.1f;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			position.z-=0.10f;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			position.z+=0.1f;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_Q)){
			position.y-=0.1f;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_E)){
			position.y+=0.1f;
		}
		
	}
	
	public Vector3f getPosition() {
		return position;
	}
	
	public Vector3f getRotation() {
		return rotation;
	}
	
	public void setPosition(Vector3f position) {
		this.position = position;
	}
	
	public void setRotation(Vector3f rotation) {
		this.rotation = rotation;
	}

}
