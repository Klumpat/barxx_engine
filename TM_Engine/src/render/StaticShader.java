package render;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import core.Camera;
import core.Light;
import core.Maths;

public class StaticShader extends Shader{
	
private static final int MAX_LIGHTS = 4;
	
	private static final String VERTEX_FILE = "res/shaders/vertexShader.vs";
	private static final String FRAGMENT_FILE = "res/shaders/fragmentShader.fs";
	
	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_lightPosition;
	private int location_lightColor;
	@SuppressWarnings("unused")
	private int location_attenuation;
	private int location_lightPositions[];
	private int location_lightColors[];
	private int location_attenuations[];
	private int location_shineDamper;
	private int location_reflectivity;
	private int location_fakeLighting;
	private int location_skycolor;

	public StaticShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttribs() {
		super.bindAttribs(0, "position");
		super.bindAttribs(1, "textureCoords");
		super.bindAttribs(2, "normal");
	}

	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		location_reflectivity = super.getUniformLocation("reflectivity");
		location_shineDamper = super.getUniformLocation("shineDamper");
		location_fakeLighting = super.getUniformLocation("fakeLighting");
		location_skycolor = super.getUniformLocation("skycolor");
		location_lightPosition = super.getUniformLocation("lightPosition");
		location_attenuation = super.getUniformLocation("attenuation");
		location_lightColor = super.getUniformLocation("lightColor");
		
		location_lightPositions = new int[MAX_LIGHTS];
		location_lightColors = new int[MAX_LIGHTS];
		location_attenuations = new int[MAX_LIGHTS];
		
		for(int i = 0; i<MAX_LIGHTS; i++){
			location_lightPositions[i] = super.getUniformLocation("lightPosition[" + i + "]");
			location_lightColors[i] = super.getUniformLocation("lightColor[" + i + "]");
			location_attenuations[i] = super.getUniformLocation("attenuation[" + i + "]");
			
		}
	}
	
	public void loadShineVariables(float damper, float reflectivity){
		super.loadFloat(location_reflectivity, reflectivity);
		super.loadFloat(location_shineDamper, damper);
	}
	
	public void loadTransformationMatrix(Matrix4f matrix){
		super.loadMatrix(location_transformationMatrix, matrix);
	}
	
//	public void loadLights(List<Light> lights){
//		for(int i = 0; i<MAX_LIGHTS; i++){
//			
//			if(i<lights.size()){
//				super.loadVector(location_lightPosition[i], lights.get(i).getPosition());
//				super.loadVector(location_lightColor[i], lights.get(i).getColor());
//				super.loadVector(location_attenuation[i], lights.get(i).getAttenuation());
//			} else {
//				super.loadVector(location_lightPosition[i], new Vector3f(0,0,0));
//				super.loadVector(location_lightColor[i], new Vector3f(0,0,0));
//				super.loadVector(location_attenuation[i], new Vector3f(1,0,0));
//			}
//			
//		}
//	}
	
	public void loadProjectionMatrix(Matrix4f matrix){
		super.loadMatrix(location_projectionMatrix, matrix);
	}
	
	public void loadViewMatrix(Camera camera){
		Matrix4f matrix = Maths.createViewMatrix(camera);
		super.loadMatrix(location_viewMatrix, matrix);
	}
	
	public void loadFakeLighting(boolean useFake){
		loadBoolean(location_fakeLighting, useFake);
	}
	
	public void loadSkycolor(Vector3f color) {
		loadVector(location_skycolor, color);
	}

	public void loadLight(Light light) {
		super.loadVector(location_lightPosition, light.getPosition());
		super.loadVector(location_lightColor, light.getColor());
		super.loadVector(location_attenuation, light.getColor());
	}

}
