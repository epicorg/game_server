package game.map.generation;

import game.map.Item;
import game.map.MapConstructor;
import game.map.MapDimension;
import game.map.MapObject;
import game.map.Texture;
import game.map.utils.MapConst;
import game.map.utils.MapDefault;
import game.map.utils.MapGeometric;
import game.map.utils.MapPosition;
import game.map.utils.MapRandom;
import game.model.PlayerStatus;

import java.util.ArrayList;

/**
 * Generate a maze by construct a default (random) grid and by open random
 * doors.
 * 
 * @author Noris
 * @date 2015/04/23
 */

public class GridMapGenerator implements MapGenerator {

	/*
	 * The size of every wall of the map.
	 */
	private static final double WALL_SIZE = 0.2;

	/*
	 * Minimum distance between objects (trees, spawn points, win points...).
	 */
	private static final double GRID_TOLERANCE = MapConst.PLAYER_SIZE * 2;

	/*
	 * Maximum and minimum number of doors for every wall.
	 */
	private static final int MIN_DOORS = 1;
	private static final int MAX_DOORS = 5;

	private MapConstructor mapConstructor;
	private MapDimension mapSize;
	private int numberOfPlayers;

	private ArrayList<MapDimension> spawnPoints;
	private ArrayList<MapDimension> doors;
	private MapDimension winPoint;

	private ArrayList<MapDimension> wallSegments;
	private ArrayList<MapDimension> wallsPositions;
	private ArrayList<MapDimension> wallsSizes;

	public GridMapGenerator(MapDimension mapSize, int numberOfPlayers) {

		this.mapSize = mapSize;
		mapConstructor = new MapConstructor();
		spawnPoints = new ArrayList<MapDimension>();
		doors = new ArrayList<MapDimension>();
		this.numberOfPlayers = numberOfPlayers;

		wallSegments = new ArrayList<MapDimension>();
		wallsPositions = new ArrayList<MapDimension>();
		wallsSizes = new ArrayList<MapDimension>();
	}

	@Override
	public MapConstructor generateMap() {

		mapConstructor.setMapSize(mapSize);
		MapDefault.constructBorders(mapConstructor, mapSize, Texture.HEDGE4);

		generateGrid();
		generateVerticalWall();

		generateSpawnPoints();
		generateWin();

		return mapConstructor;
	}

	// private void generateSpawnPoints() {
	//
	// MapDimension mapSizeWithTolerance = new MapDimension(mapSize.getWidth() -
	// WALL_SIZE
	// - PLAYER_SIZE / 2, mapSize.getHeight(), mapSize.getWidth() - WALL_SIZE
	// - PLAYER_SIZE / 2);
	//
	// for (int i = 0; i < numberOfPlayers; i++) {
	// MapDimension tmp = MapPosition.getRandomSpawnPoint(mapSizeWithTolerance);
	// spawnPoints.add(tmp);
	// mapConstructor.addSpawnPoint(new PlayerStatus(tmp, tmp));
	//
	// // TODO DEBUG
	// System.out.println("Player Position: " + tmp);
	// }
	//
	// }

	private void generateSpawnPoints() {

		MapDimension mapSizeWithTolerance = new MapDimension(mapSize.getWidth() - WALL_SIZE
				- MapConst.PLAYER_SIZE / 2, mapSize.getHeight(), mapSize.getWidth() - WALL_SIZE
				- MapConst.PLAYER_SIZE / 2);

		for (int i = 0; i < numberOfPlayers; i++) {

			MapDimension tmp = MapPosition.getRandomSpawnPoint(mapSizeWithTolerance);

			boolean isOK = true;

			for (int j = 0; j < wallsPositions.size(); j++) {

				ArrayList<MapDimension> points = MapGeometric.getWallPoints(wallsPositions.get(j),
						wallsSizes.get(j));

				if (MapGeometric.isCircleOnSegment(tmp, MapConst.PLAYER_SIZE / 2, points.get(0),
						points.get(1))) {
					isOK = false;
					break;
				}
			}

			if (isOK) {
				mapConstructor.addSpawnPoint(new PlayerStatus(tmp, tmp));
				spawnPoints.add(tmp);
			}

			// TODO DEBUG
			System.out.println("Player Position: " + tmp);
		}

	}

	// private void generateWin() {
	//
	// do {
	//
	// MapDimension mapSizeWithTolerance = new MapDimension(mapSize.getWidth() -
	// WALL_SIZE
	// - PLAYER_SIZE, mapSize.getHeight(), mapSize.getWidth() - WALL_SIZE
	// - PLAYER_SIZE);
	//
	// winPoint = MapPosition.getRandomPosition(mapSizeWithTolerance);
	//
	// // TODO DEBUG
	// System.out.println("Win Position: " + winPoint);
	//
	// } while (MapGeometric.checkIfUsed(winPoint, spawnPoints, PLAYER_SIZE *
	// 3));
	//
	// mapConstructor.addWinPoint(new MapObject(Item.VASE, Texture.CERAMIC1,
	// winPoint,
	// new MapDimension(0.5, 1, 0)));
	//
	// }

	private void generateWin() {

		MapDimension mapSizeWithTolerance = new MapDimension(mapSize.getWidth() - WALL_SIZE
				- MapConst.PLAYER_SIZE / 2, mapSize.getHeight(), mapSize.getWidth() - WALL_SIZE
				- MapConst.PLAYER_SIZE / 2);

		boolean isOK;

		do {

			isOK = true;

			winPoint = MapPosition.getRandomPosition(mapSizeWithTolerance);

			for (int j = 0; j < wallsPositions.size(); j++) {

				ArrayList<MapDimension> points = MapGeometric.getWallPoints(wallsPositions.get(j),
						wallsSizes.get(j));

				if (MapGeometric.isCircleOnSegment(winPoint, MapConst.PLAYER_SIZE / 2,
						points.get(0), points.get(1))) {
					isOK = false;
					break;
				}
			}

			if (MapGeometric.checkIfUsed(winPoint, spawnPoints, MapConst.PLAYER_SIZE * 3))
				isOK = false;

		} while (!isOK);

		mapConstructor.addWinPoint(new MapObject(Item.VASE, Texture.CERAMIC1, winPoint,
				new MapDimension(0.5, 1, 0)));

		// TODO DEBUG
		System.out.println("Win Position: " + winPoint);

	}

	/*
	 * It generates a grid of walls in the total size of the map. Every wall go
	 * from one end of the map to the other, and it's distance from the closest
	 * walls is always equal to GRID_TOLERANCE. Than the number of these walls
	 * is equal to the length of the map divided by GRID_TOLERANCE. After the
	 * creation of a wall, it is split into N+1 segment, where N is the (random)
	 * number of the doors. Every segment has the same length.
	 */
	private void generateGrid() {

		double newLenght = -mapSize.getLength();

		int loop = (int) Math.round(mapSize.getLength() * 2 / GRID_TOLERANCE) + 1;

		int random = 0;

		for (int i = 0; i < loop; i++) {

			MapDimension position = new MapDimension(newLenght, -1, 0);

			MapDimension size = new MapDimension(WALL_SIZE, 2, mapSize.getWidth() * 2);

			wallsPositions.add(position);
			wallsSizes.add(size);

			random = MapRandom.getRandomInt(MIN_DOORS, MAX_DOORS, random);

			ArrayList<MapDimension> segments = generateDoors(position, size, random);

			for (int j = 0; j < segments.size() - 1; j++) {

				if (!(i == 0 || i == loop - 1)) {
					mapConstructor.addMapObject(new MapObject(Item.WALL, Texture.HEDGE4, segments
							.get(j), segments.get(segments.size() - 1)));

					if (j != 0 && j != segments.size() - 2)
						wallSegments.add(segments.get(j));
				}
			}

			newLenght += GRID_TOLERANCE;

		}
	}

	/*
	 * It split a wall into num+1 segments, where num is the number of the
	 * doors. Every segment is on the same line of the original wall. The doors
	 * aperture is equal to PLAYER_SIZE.
	 */
	private ArrayList<MapDimension> generateDoors(MapDimension position, MapDimension size, int num) {

		ArrayList<MapDimension> walls = new ArrayList<MapDimension>();

		ArrayList<MapDimension> points = MapGeometric.getWallPointsOnLength(position, size);

		MapDimension p1 = points.get(0);
		MapDimension p2 = points.get(1);

		double totalSize = MapGeometric.getDistance(p1, p2);
		double dividedSize = (totalSize - MapConst.PLAYER_SIZE * num) / (num + 1);

		if (p1.getWidth() == p2.getWidth()) {

			double l1 = p1.getLength();
			double l2 = p2.getLength();

			double length = l1 < l2 ? l1 : l2;

			for (int i = 0; i < num + 1; i++) {

				length += (dividedSize / 2);
				walls.add(new MapDimension(position.getWidth(), position.getHeight(), length));
				length += (dividedSize / 2 + MapConst.PLAYER_SIZE);

				/*
				 * Add the center of every generated segment to an array. This
				 * array was used in generateVerticalWall method to not generate
				 * a wall inside a door.
				 */
				doors.add(new MapDimension(position.getWidth(), position.getHeight(), length
						- (MapConst.PLAYER_SIZE / 2)));
			}

			walls.add(new MapDimension(size.getWidth(), size.getHeight(), dividedSize));

		}

		else if (p1.getLength() == p2.getLength()) {

			double w1 = p1.getWidth();
			double w2 = p2.getWidth();

			double width = w1 < w2 ? w1 : w2;

			for (int i = 0; i < num + 1; i++) {

				width += (dividedSize / 2);
				walls.add(new MapDimension(width, position.getHeight(), position.getLength()));
				width += (dividedSize / 2 + MapConst.PLAYER_SIZE);

				doors.add(new MapDimension(width - (MapConst.PLAYER_SIZE / 2),
						position.getHeight(), position.getLength()));
			}

			walls.add(new MapDimension(dividedSize, size.getHeight(), size.getLength()));

		}

		return walls;
	}

	/*
	 * It generate the walls in the opposite direction of the generateGrid
	 * method. Every wall starts from the center of a wall of the grid.
	 */
	private void generateVerticalWall() {

		for (MapDimension w : wallSegments) {

			MapDimension position = new MapDimension(w.getWidth() + GRID_TOLERANCE / 2,
					w.getHeight(), w.getLength());

			MapDimension size = new MapDimension(GRID_TOLERANCE, 2, WALL_SIZE);

			ArrayList<MapDimension> points = MapGeometric.getWallPoints(position, size);

			if (!MapGeometric.checkIfUsed(points.get(0), doors, 0.5)
					&& !MapGeometric.checkIfUsed(points.get(1), doors, 0.5)) {

				mapConstructor
						.addMapObject(new MapObject(Item.WALL, Texture.HEDGE4, position, size));

				wallsPositions.add(position);
				wallsSizes.add(size);
			}
		}

	}

}
