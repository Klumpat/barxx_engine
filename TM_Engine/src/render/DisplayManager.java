package render;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

public class DisplayManager {

	public static void createDisplay(int width, int height, String title) {

		ContextAttribs attribs = new ContextAttribs(3,2).withForwardCompatible(true).withProfileCore(true);

		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setTitle(title);
			Display.create(new PixelFormat(), attribs);
			Keyboard.create();
			Mouse.create();
			//Display.setVSyncEnabled(true);
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());

	}
	
	
	public static void updateDisplay(){
		//Display.sync(60);
		Display.update();
	}
	
	public static void destroyDisplay(){
		Display.destroy();
	}
	
	public static boolean isDisplayCloseRequested(){
		return Display.isCloseRequested();
	}

}
