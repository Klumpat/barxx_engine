package core;

import render.DisplayManager;
import render.MasterRenderer;

public class CoreEngine {

	private MasterRenderer renderer;
	private Loader loader;
	private Game game;

	private boolean running;

	public CoreEngine() {
		DisplayManager.createDisplay(1200, 768, "BarxxEngine");
		renderer = new MasterRenderer();
		loader = new Loader();
		game = new Game();

		running = false;

	}

	public void start() {
		System.out.println("start");
		if (running)
			return;

		running = true;
		run();

	}

	public void stop() {
		System.out.println("stop");
		if (!running)
			return;

		running = false;

	}

	public void run() {
		System.out.println("run");

		final double tickTime = 1.0 / 60.0;

		long lastTime = System.nanoTime();
		long secTimer = System.currentTimeMillis();

		double delta = 0.0;

		int frames = 0;
		int updates = 0;

		while (running) {
			long currentTime = System.nanoTime();
			delta += (double) (currentTime - lastTime) / 1000000000.0;
			lastTime = currentTime;

			boolean render = true;

			if (DisplayManager.isDisplayCloseRequested())
				stop();

			while (delta >= tickTime) {
				delta -= tickTime;
				update();
				updates++;
				render = true;

				if (System.currentTimeMillis() - secTimer >= 1000) {
					secTimer += 1000;
					System.out.println("FPS: " + frames + " TPS: " + updates);
					frames = 0;
					updates = 0;
				}

			}

			if (render) {
				render();
				frames++;
			}

		}

		cleanUp();

	}

	private void cleanUp() {
		game.cleanUp();
		Loader.cleanUp();
		renderer.cleanUp();
		DisplayManager.destroyDisplay();
	}

	public void update() {
		game.update();
	}

	public void render() {
		game.render(renderer);
		DisplayManager.updateDisplay();
	}

	public static void main(String[] args) {

		CoreEngine core = new CoreEngine();
		core.start();

	}

}
