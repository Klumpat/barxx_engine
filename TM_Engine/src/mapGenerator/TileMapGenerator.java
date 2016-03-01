package mapGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import org.lwjgl.util.vector.Vector2f;

public class TileMapGenerator {

	public final int width;
	public final int height;

	private int[] data;

	public final static int AIR = 0;
	public final static int STONE = 1;
	public final static int TEMP_ROOM = 2;
	public final static int CONNECTION = 3;
	public final static int WALL = 4;
	public final static int INVIS_WALL = 5;
	public final static int LEFT_WALL = 6;
	public final static int RIGHT_WALL = 7;
	public final static int TOP_LEFT_CORNER = 8;
	public final static int TOP_RIGHT_CORNER = 9;
	public final static int ROOM = 0xff;

	public List<Room> rooms = new ArrayList<Room>();
	public List<SquaredRoom> sRooms = new ArrayList<SquaredRoom>();
	public List<Vector2f> connections = new ArrayList<Vector2f>();

	private Random random;

	public TileMapGenerator(int width, int height) {
		this.width = width;
		this.height = height;
		this.data = new int[width * height];

		// TODO implement seeding
		random = new Random();
	}
	


	public int[] generateCave() {
		System.out.println("generating Cavemap");
		long start = System.nanoTime();
		setStartingValue(STONE);
		randomize(43);
		clearSingleFragments();
		smoothMap(4, 4, 4);
		clearSingleFragments();
		declareRooms(32, 99999999);
		connectRooms();
		setOutline();

		/*
		 * TODO check everywall if its neighbour is a connection, if yes, remove
		 * the wall then check all connection if its neighbour is a stone, make
		 * it a wall
		 * 
		 * find a way to store connections
		 * 
		 * and try to connect ALL rooms
		 * 
		 */

		long end = System.nanoTime();
		float took = (float) (end - start) / 1000000.0f;
		System.out.println("Map generated in: " + took + "ms");
		return data;
	}

	public void addAllConnections() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int index = x + y * width;
				if (data[index] == CONNECTION)
					connections.add(new Vector2f(x, y));
			}
		}
	}

	public int[] generateDungeon() {
		reset();
		setStartingValue(STONE);

		Stack<SquaredRoom> tempRooms = new Stack<SquaredRoom>();

		int roomcount = 150;

		for (int i = 0; i < roomcount; i++) {
			int w = random.nextInt(10) + 10;
			int h = random.nextInt(10) + 10;
			int x = random.nextInt(width - 20);
			int y = random.nextInt(height - 20);
			sRooms.add(new SquaredRoom(w, h, new Vector2f(x, y), ROOM + i, this));
		}

		for (SquaredRoom room : sRooms) {

			for (Vector2f vec : room.coords) {
				data[(int) (vec.x + vec.y * width)] = AIR;
			}

		}

		setOutline();
		declareRooms(10, 1600);
		connectRooms();
		addAllConnections();
		outlineConnectionsWith(WALL);

		return data;
	}

	private void outlineConnectionsWith(int value) throws IndexOutOfBoundsException {

		for (Vector2f vec : connections) {

//			if (getSurroundingValueCount((int) vec.x, (int) vec.y, value) == 0) {
//				continue;
//			}

			if (getData()[(int) ((vec.x + 1) + vec.y * width)] == TileMapGenerator.STONE) {
				getData()[(int) ((vec.x + 1) + vec.y * width)] = value;
				//connections.add(new Vector2(vec.x + 1, vec.y));
			}
			if (getData()[(int) ((vec.x - 1) + vec.y * width)] == TileMapGenerator.STONE) {
				getData()[(int) ((vec.x - 1) + vec.y * width)] = value;
				//connections.add(new Vector2(vec.x - 1, vec.y));
			}

			if (getData()[(int) (vec.x + (vec.y - 1) * width)] == TileMapGenerator.STONE) {
				getData()[(int) (vec.x + (vec.y - 1) * width)] = value;
				//connections.add(new Vector2(vec.x, vec.y - 1));
			}

			if (getData()[(int) (vec.x + (vec.y + 1) * width)] == TileMapGenerator.STONE) {
				getData()[(int) (vec.x + (vec.y + 1) * width)] = value;
				//connections.add(new Vector2(vec.x, vec.y + 1));
			}

		}

	}
	
//	private void addRandomRooms(int minWidth, int minHeight, int maxWidth, int maxHeight, int attempts) {
//
//		List<Bounds> bounds = new ArrayList<Bounds>();
//
//		for (int i = 0; i < attempts; i++) {
//
//			int w = random.nextInt(maxWidth - minWidth) + minWidth;
//			int h = random.nextInt(maxHeight - minHeight) + minHeight;
//			int xx = random.nextInt(width) - w;
//			int yy = random.nextInt(height) - h;
//
//			if (xx < 1 || yy < 1 || xx >= width - 1 || yy >= height - 1 || xx + w >= width - 1
//					|| yy + h >= height - 1) {
//				continue;
//			}
//
//			Bounds current = new Bounds(xx, yy, w, h);
//
//			boolean moveOn = false;
//
//			System.out.println("checking intersections");
//			for (Bounds b : bounds) {
//				if (b.intersects(b)) {
//					bounds.add(current);
//					break;
//				}
//			}
//
//			if (!moveOn)
//				continue;
//
//			for (int y = yy; yy < yy + h; yy++) {
//				for (int x = xx; xx < xx + w; xx++) {
//
//					if (data[x + y * w] == STONE)
//						data[x + y * w] = AIR;
//
//				}
//			}
//
//		}
//		System.out.println("Bounds added: " + bounds.size());
//
//	}

//	private int[] generateRooms(int minWidth, int minHeight, int maxWidth, int maxHeight, int attempts) {
//
//		List<Bounds> bounds = new ArrayList<Bounds>();
//		List<SquaredRoom> alive = new ArrayList<SquaredRoom>();
//
//		int failed = 0;
//		for (int i = 0; i < attempts; i++) {
//
//			int w = random.nextInt(maxWidth - minWidth) + minWidth;
//			int h = random.nextInt(maxHeight - minHeight) + minHeight;
//			int x = random.nextInt(width) - w;
//			int y = random.nextInt(height) - h;
//
//			if (x < 1 || y < 1 || x >= width - 1 || y >= height - 1) {
//				failed++;
//				continue;
//			}
//
//			Bounds currentBounds = new Bounds(x, y, w, h);
//
//		}
//
//		return data;
//	}

	public void setSeed(String seed) {
		random.setSeed(seed.hashCode());
	}

	public int getRoomCount() {
		return rooms.size();
	}

	public int getAverageRoomSize() {
		int res = 0;

		for (Room r : rooms) {
			res += r.coords.size();
		}

		return res;
	}

	public void reset() {
		rooms.clear();
		sRooms.clear();
		connections.clear();
	}

	public List<Room> getRooms() {
		return rooms;
	}

	@Deprecated
	public int[] generateMaze() {
		System.out.println("generating Cavemap");

		long start = System.nanoTime();

		setStartingValue(STONE);

		long end = System.nanoTime();
		float took = (float) (end - start) / 1000000.0f;
		System.out.println("Map generated in: " + took + "ms");

		return data;
	}

	private void connectRooms() {
		System.out.println("Connecting Rooms...");

		// TODO there is a bug, the first OR the last WALL will not be broken...

		int bestDistance = 0;
		Vector2f bestTileA = new Vector2f();
		Vector2f bestTileB = new Vector2f();
		Room bestRoomA = new Room();
		Room bestRoomB = new Room();
		boolean possibleConnectionFound = false;

		int connections = 0;

		for (Room roomA : rooms) {
			possibleConnectionFound = false;

			for (Room roomB : rooms) {
				if (roomA == roomB) {
					continue;
				}
				if (roomA.isConnected(roomB)) {
					possibleConnectionFound = false;
					break;
				}

				for (int tileIndexA = 0; tileIndexA < roomA.coords.size(); tileIndexA++) {
					for (int tileIndexB = 0; tileIndexB < roomB.coords.size(); tileIndexB++) {
						Vector2f tileA = roomA.coords.get(tileIndexA);
						Vector2f tileB = roomB.coords.get(tileIndexB);
						int distanceBetweenRooms = (int) (Math.pow(tileA.x - tileB.x, 2)
								+ Math.pow(tileA.y - tileB.y, 2));

						if (distanceBetweenRooms <= bestDistance || !possibleConnectionFound) {
							bestDistance = distanceBetweenRooms;
							possibleConnectionFound = true;
							bestTileA = tileA;
							bestTileB = tileB;
							bestRoomA = roomA;
							bestRoomB = roomB;
						}
					}
				}
			}
			if (possibleConnectionFound) {
				Room.connectRooms(bestRoomA, bestRoomB);
				drawLine(bestTileA, bestTileB, CONNECTION);
				connections++;
			}
		}
		System.out.println("RoomCount: " + rooms.size());
		System.out.println("Rooms connected: " + connections);

	}

	private void drawLine(Vector2f start, Vector2f end, int col) {

		int x0 = (int) start.x;
		int x1 = (int) end.x;
		int y0 = (int) start.y;
		int y1 = (int) end.y;

		int w = x1 - x0;
		int h = y1 - y0;

		int dx0 = 0;
		int dy0 = 0;
		int dx1 = 0;
		int dy1 = 0;

		if (w < 0)
			dx0 = -1;
		else if (w > 0)
			dx0 = 1;
		if (h < 0)
			dy0 = -1;
		else if (h > 0)
			dy0 = 1;
		if (w < 0)
			dx1 = -1;
		else if (w > 0)
			dx1 = 1;

		int longest = Math.abs(w);
		int shortest = Math.abs(h);

		if (!(longest > shortest)) {
			longest = Math.abs(h);
			shortest = Math.abs(w);
			if (h < 0)
				dy1 = -1;
			else if (h > 0)
				dy1 = 1;
			dx1 = 0;
		}
		int numerator = longest >> 1;
		for (int i = 0; i <= longest; i++) {

			/*
			 * FIXME this is so ugly... needed to add this to draw thick lines..
			 * find an other way...
			 */
			data[x0 + y0 * width] = col;
			data[(x0 + 1) + (y0 + 1) * width] = col;
			data[(x0 - 1) + (y0 - 1) * width] = col;
			data[(x0 - 1) + (y0 + 1) * width] = col;
			data[(x0 + 1) + (y0 - 1) * width] = col;
			data[(x0) + (y0 - 1) * width] = col;
			data[(x0 - 1) + (y0) * width] = col;
			data[(x0) + (y0 + 1) * width] = col;
			data[(x0 + 1) + (y0) * width] = col;
			numerator += shortest;
			if (!(numerator < longest)) {
				numerator -= longest;
				x0 += dx0;
				y0 += dy0;
			} else {
				x0 += dx1;
				y0 += dy1;
			}
		}

	}

	private void drawLIne(Vector2f start, Vector2f end) {

		float x0 = start.x;
		float x1 = end.x;
		float y0 = start.y;
		float y1 = end.y;

		float dx = Math.abs(x0 - x1);
		float sx = x0 < x1 ? 1 : -1;
		float dy = Math.abs(y0 - y1);
		float sy = y0 < y1 ? 1 : -1;
		float err = dx + dy;
		float e;

		while (x0 != x1 && y0 != y1) {

			if (data[(int) (x0 + y0 * width)] == STONE || data[(int) (x0 + y0 * width)] == WALL) {
				data[(int) (x0 + y0 * width)] = CONNECTION;
			}

			e = 2 * err;
			if (e > dy) {
				err += dy;
				x0 += sx;
			}

		}
		while (y0 != y1) {
			if (data[(int) (x0 + y0 * width)] == STONE || data[(int) (x0 + y0 * width)] == WALL) {
				data[(int) (x0 + y0 * width)] = CONNECTION;
			}

			e = 2 * err;

			if (e > dx) {
				err += dx;
				y0 += sy;
			}

		}

	}

	private void declareRooms(int minSize, int maxSize) {

		int removed = 0;
		int added = 0;
		System.out.println("declaring Rooms");
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (data[x + y * width] == AIR)
					data[x + y * width] = TEMP_ROOM;
			}
		}

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {

				if (data[x + y * width] == TEMP_ROOM) {
					Room room = new Room(ROOM + x, this);
					room.add(new Vector2f(x, y));
					room.expand(x, y);
					x = 0;
					y = 0;
					if (room.coords.size() >= minSize && room.coords.size() <= maxSize) {
						System.out.println("Added room with size: " + room.coords.size());
						room.setOutline();
						rooms.add(room);
						added++;
					} else {
						System.out.println("Removed room with size: " + room.coords.size());
						room.discard();
						removed++;
					}
				}
			}
		}

		System.out.println("Rooms added: " + added + " Rooms removed: " + removed);
		System.out.println("RoomListSize: " + rooms.size());

	}

	private void setStartingValue(int value) {
		Arrays.fill(data, value);
	}

	private void randomize(int wallchance) {
		System.out.println("randomize Data");
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (y == 0 || y == height - 1 || x == 0 || x == width - 1)
					data[x + y * width] = STONE;
				else
					data[x + y * width] = random.nextInt(99) + 1 < wallchance ? STONE : AIR;

			}
		}
	}

	private void smoothMap(int times, int stoneValue, int airValue) {

		for (int i = 0; i < times; i++) {
			System.out.println("smoothing map the " + (i + 1) + ". time");

			for (int x = 0; x < width; x++) {

				for (int y = 0; y < height; y++) {
					int neighbourWallTiles = getSurroundingValueCount(x, y, STONE);

					if (neighbourWallTiles > stoneValue)
						data[x + y * width] = STONE;
					else if (neighbourWallTiles < airValue)
						data[x + y * width] = AIR;

				}
			}
		}

	}

	public void clearSingleFragments() {
		System.out.println("deleting single walls");

		for (int x = 0; x < width; x++) {

			for (int y = 0; y < height; y++) {
				if (data[x + y * width] == AIR) {
					int neighbourWallTiles = getSurroundingValueCount(x, y, STONE);
					if (neighbourWallTiles == 8)
						data[x + y * width] = STONE;
				}

				if (data[x + y * width] == STONE) {
					int neighbourWallTiles = getSurroundingValueCount(x, y, AIR);
					if (neighbourWallTiles == 8)
						data[x + y * width] = AIR;
				}

			}
		}

	}

	private int getSurroundingValueCount(int gridX, int gridY, int value) {
		int wallCount = 0;
		for (int neighbourX = gridX - 1; neighbourX <= gridX + 1; neighbourX++) {
			for (int neighbourY = gridY - 1; neighbourY <= gridY + 1; neighbourY++) {
				if (neighbourX >= 0 && neighbourX < width && neighbourY >= 0 && neighbourY < height) {
					if (neighbourX != gridX || neighbourY != gridY) {
						if (data[neighbourX + neighbourY * width] == value) {
							wallCount += 1;// data[neighbourX + neighbourY *
											// width];
						}
					}
				} else {
					wallCount++;
				}
			}
		}

		return wallCount;
	}

	private void setOutline() {
		System.out.println("set the Outline");
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (y == 0 || y == height - 1 || x == 0 || x == width - 1)
					data[x + y * width] = STONE;
				else
					continue;

			}
		}
	}

	public int[] getData() {
		return data;
	}

}
