package core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.util.vector.Vector3f;

import mapGenerator.TileMapGenerator;
import render.MasterRenderer;
import render.Mesh;
import render.MeshTexture;
import render.TexturedMesh;
import terrain.Terrain;

public class Game {
	
	Camera cam = new Camera();
	Light light = new Light(cam.getPosition(), new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(1.0f, 0.1f, 0.02f));
	
	TileMapGenerator gen;
	int[] mapdata;
	
	List<GameObject> objects = new ArrayList<GameObject>();
	public Game(){
		
		//cam.setRotation(new Vector3f(90,0,0));
		//cam.setPosition(new Vector3f(32,300,-32));
		
		gen = new TileMapGenerator(64, 64);
		mapdata = new int[64 * 64];
		
		mapdata = gen.generateCave();
		
		light.setPosition(new Vector3f(0,1000,-50));


//		Random random = new Random();
//		for (int y = 0; y < 64; y++) {
//			for(int x = 0; x<64;x++){
//				
//				if(mapdata[x + y * 64] == TileMapGenerator.STONE){
//					objects.add(new Block(new Vector3f(x,0,-y), new Vector3f(0,0,0), new Vector3f(1,1,1),false));
//				} else {
//					objects.add(new Block(new Vector3f(x,-1,-y), new Vector3f(0,0,0), new Vector3f(1,1,1)));
//				}
//				
//			}
//
//		}
		
//		objects.add(new Block(new Vector3f(0,-3,-25), new Vector3f(0,0,0), new Vector3f(1,1,1)));
//		objects.get(0).getModel().getTexture().setHasReflectivity(true);;
//		objects.get(0).getModel().getTexture().setReflectivity(1.0f);
//		objects.get(0).getModel().getTexture().setShineDamper(15.0f);
		
		
	}
	
	public void render(MasterRenderer renderer){
		
		for (GameObject go : objects) {

			renderer.processGameObjects(go);
		}
		
		
		renderer.render(light, cam);
	}
	
	public void update(){
		cam.move();
		//objects.get(0).increaseRotation(new Vector3f(0.0f,0.5f,0.0f));
		
	}
	
	public void cleanUp(){
		
	}

}
