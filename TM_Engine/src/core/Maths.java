package core;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class Maths {

//	public static Matrix4f createTransformationMatrix(Vector3f translation, float rx, float ry, float rz, float scale) {
//		Matrix4f matrix = new Matrix4f();
//		matrix.setIdentity();
//		Matrix4f.translate(translation, matrix, matrix);
//		Matrix4f.rotate((float) Math.toRadians(rx), new Vector3f(1, 0, 0), matrix, matrix);
//		Matrix4f.rotate((float) Math.toRadians(ry), new Vector3f(0, 1, 0), matrix, matrix);
//		Matrix4f.rotate((float) Math.toRadians(rz), new Vector3f(0, 0, 1), matrix, matrix);
//		Matrix4f.scale(new Vector3f(scale, scale, scale), matrix, matrix);
//
//		return matrix;
//	}
	
	public static Matrix4f createTransformationMatrix(Vector3f translation, Vector3f rotation, Vector3f scale) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(translation, matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rotation.x), new Vector3f(1,0,0), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rotation.y), new Vector3f(0,1,0), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rotation.z), new Vector3f(0,0,1), matrix, matrix);
		Matrix4f.scale(new Vector3f(scale.x, scale.y, scale.z), matrix, matrix);
		return matrix;
	}
	
	 public static Matrix4f createProjectionMatrix(float FOV, float zNEAR, float zFAR) {
		Matrix4f res = new Matrix4f();
		float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
		float yScale = (float) (1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio;
		float xScale = yScale / aspectRatio;
		float frustumLenght = zFAR - zNEAR;

		res = new Matrix4f();
		res.m00 = xScale;
		res.m11 = yScale;
		res.m22 = -((zFAR + zNEAR) / frustumLenght);
		res.m23 = -1;
		res.m32 = -((2 * zNEAR * zFAR) / frustumLenght);
		res.m33 = 0;

		return res;
	}
	
	public static Matrix4f createViewMatrix(Camera cam) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		
		Matrix4f.rotate((float) Math.toRadians(cam.getRotation().x), new Vector3f(1,0,0), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(cam.getRotation().y), new Vector3f(0,1,0), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(cam.getRotation().z), new Vector3f(0,0,1), matrix, matrix);
		
		Vector3f cameraPos = cam.getPosition();
		Vector3f negativecameraPos = new Vector3f(-cameraPos.x, -cameraPos.y, -cameraPos.z);
		
		Matrix4f.translate(negativecameraPos, matrix, matrix);
		
		return matrix;
	}
	
	public static float barryCentric(Vector3f p1, Vector3f p2, Vector3f p3, Vector2f pos) {
		float det = (p2.z - p3.z) * (p1.x - p3.x) + (p3.x - p2.x) * (p1.z - p3.z);
		float l1 = ((p2.z - p3.z) * (pos.x - p3.x) + (p3.x - p2.x) * (pos.y - p3.z)) / det;
		float l2 = ((p3.z - p1.z) * (pos.x - p3.x) + (p1.x - p3.x) * (pos.y - p3.z)) / det;
		float l3 = 1.0f - l1 - l2;
		return l1 * p1.y + l2 * p2.y + l3 * p3.y;
	}

}
