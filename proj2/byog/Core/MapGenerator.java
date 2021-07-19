package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;

public class MapGenerator {
    private static final int N = 20; // 20 attempts from room generation
    private static ArrayList<Room> allRooms = new ArrayList<Room>();

    /** Generate several random rooms with Nth attempts, store generated
     * rooms in an ArrayList, at the end sort it with their X axis value. */
    private static void fillWithRandomRooms(TETile[][] tiles,Random RANDOM) {
        for (int i = 0; i <= N; i++) {
            Room r = randomRoom(tiles, RANDOM);
            if (!roomCheck(tiles, r)) {
                continue;
            }
            buildRoom(tiles, r);
            allRooms.add(r);
        }
        Collections.sort(allRooms);
        /*
        for (Room r : allRooms) {
            System.out.println(r);
        }
         */
    }

    /** Generate a random room. */
    private static Room randomRoom(TETile[][] tiles, Random RANDOM) {
        int WIDTH = tiles.length;
        int HEIGHT = tiles[0].length;
        // Room: x, y == [2, WIDTH / HEIGHT - 2]
        int x = 2 + RANDOM.nextInt(WIDTH - 4);
        int y = 2 + RANDOM.nextInt(HEIGHT - 4);
        Position bl = new Position(x, y);
        // width / height == 5 / 7 / 9
        int width = (3 + RANDOM.nextInt(3)) * 2 - 1;
        int height = (3 + RANDOM.nextInt(3)) * 2 - 1;
        Room randomRoom = new Room(bl, width, height);
        return randomRoom;
    }
    /** Check the generated room available or not. */
    private static boolean roomCheck(TETile[][] tiles, Room r) {
        int WIDTH = tiles.length;
        int HEIGHT = tiles[0].length;
        int x = r.getBottomLeftX();
        int y = r.getBottomLeftY();
        int width = r.getWidth();
        int height = r.getHeight();
        // 1. check if room exceeds the range for x, y [2, WIDTH / HEIGHT - 2]
        if (x + width + 1 >= WIDTH - 2 || y + height + 1 >= HEIGHT - 2) {
            return false;
        }
        // 2. check if room overlapped with another room
        for (int i = x; i <= x + width + 1; i++) {
            for (int j = y; j <= y + height + 1; j++) {
                if (tiles[i][j] == Tileset.WALL || tiles[i][j] == Tileset.GRASS) {
                    return false;
                }
            }
        }
        return true;
    }

    /** Build the generated room on tiles without wall */
    private static void buildRoom(TETile[][] tiles, Room r) {
        int x = r.getBottomLeftX();
        int y = r.getBottomLeftY();
        int width = r.getWidth();
        int height = r.getHeight();
        for (int i = x; i <= x + width; i++) {
            for (int j = y; j <= y + height; j++) {
                tiles[i][j] = Tileset.FLOOR;
            }
        }
    }

    /** Connect 2 nearby rooms (on x axis) */
    private static void connectRooms(TETile[][] tiles, Random RANDOM) {
        for (int i = 0; i < allRooms.size() - 1; i++) {
            Position p1 = randomPointRoom(allRooms.get(i), RANDOM);
            Position p2 = randomPointRoom(allRooms.get(i + 1), RANDOM);
            Hallway h = connectWithHallWay(p1, p2, RANDOM);
            buildRoom(tiles, h.getHorizontalRoom());
            buildRoom(tiles, h.getVerticalRoom());
        }
    }

    /** Find a random position in a selected room */
    private static Position randomPointRoom(Room r, Random RANDOM) {
        int x = r.getBottomLeftX();
        int y = r.getBottomLeftY();
        int width = r.getWidth();
        int height = r.getHeight();
        int xRandom = x + 1 + RANDOM.nextInt(width - 1);
        int yRandom = y + 1 + RANDOM.nextInt(height - 1);
        Position randomPoint = new Position(xRandom, yRandom);
        return randomPoint;
    }

    /** Generate a hallway to connect 2 selected rooms based on 2 random position */
    private static Hallway connectWithHallWay(Position p1, Position p2, Random RANDOM) {
        int x = Math.abs(p1.x - p2.x);
        int y = Math.abs(p1.y - p2.y);
        Position interPoint = new Position(p2.x, p1.y);
        int hallwaySize = 2 + RANDOM.nextInt(2);
        if (p1.y == p2.y && p1.x < p2.x) {
            Room hRoom = new Room(p1, x, hallwaySize);
            Room vRoom = new Room(p2, 0, 0);
            Hallway h = new Hallway(hRoom, vRoom);
            return h;
        }
        if (p1.y == p2.y && p1.x > p2.x) {
            Room hRoom = new Room(p2, x, hallwaySize);
            Room vRoom = new Room(p1, 0, 0);
            Hallway h = new Hallway(hRoom, vRoom);
            return h;
        }
        if (p1.x == p2.x && p1.y < p2.y) {
            Room hRoom = new Room(p1, 0, 0);
            Room vRoom = new Room(p1, hallwaySize, y);
            Hallway h = new Hallway(hRoom, vRoom);
            return h;
        }
        if (p1.x == p2.x && p1.y > p2.y) {
            Room hRoom = new Room(p2, 0, 0);
            Room vRoom = new Room(p2, hallwaySize, y);
            Hallway h = new Hallway(hRoom, vRoom);
            return h;
        }

        if (p1.x < p2.x && p1.y < p2.y) {
            Room hRoom = new Room(p1, x, hallwaySize);
            Room vRoom = new Room(interPoint, hallwaySize, y);
            Hallway h = new Hallway(hRoom, vRoom);
            return h;
        } else if  (p1.x > p2.x && p1.y < p2.y) {
            Room hRoom = new Room(interPoint, x, hallwaySize);
            Room vRoom = new Room(interPoint, hallwaySize, y);
            Hallway h = new Hallway(hRoom, vRoom);
            return h;
        } else if (p1.x < p2.x && p1.y > p2.y) {
            Room hRoom = new Room(p1, x, hallwaySize);
            Room vRoom = new Room(p2, hallwaySize, y);
            Hallway h = new Hallway(hRoom, vRoom);
            return h;
        } else {
            Room hRoom = new Room(p2, hallwaySize, y);
            Room vRoom = new Room(interPoint, x, hallwaySize);
            Hallway h = new Hallway(hRoom, vRoom);
            return h;
        }
    }

    /** Build wall after rooms are all connected */
    private static void fillWithWallTiles(TETile[][] tiles) {
        int WIDTH = tiles.length;
        int HEIGHT = tiles[0].length;
        // Range for checking if it should be a WALL or not [1, WIDTH / HEIGHT -1]
        for (int x = 1; x < WIDTH - 1; x += 1) {
            for (int y = 1; y < HEIGHT - 1; y += 1) {
                if (tiles[x][y] == null) {
                    if (tiles[x + 1][y + 1] == Tileset.FLOOR || tiles[x + 1][y] == Tileset.FLOOR || tiles[x + 1][y - 1] == Tileset.FLOOR || tiles[x][y - 1] == Tileset.FLOOR || tiles[x - 1][y - 1] == Tileset.FLOOR || tiles[x - 1][y] == Tileset.FLOOR || tiles[x - 1][y + 1] == Tileset.FLOOR || tiles[x][y + 1] == Tileset.FLOOR) {
                        tiles[x][y] = Tileset.WALL;
                    }
                }
            }
        }
    }

    /** Leave all the other place as NOTHING */
    private static void fillWithBlankTiles(TETile[][] tiles) {
        int WIDTH = tiles.length;
        int HEIGHT = tiles[0].length;
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                if (tiles[x][y] == null) {
                    tiles[x][y] = Tileset.NOTHING;
                }
            }
        }
    }

    /** Remove walls inside Rooms */
    private static void optimizeWallTiles(TETile[][] tiles) {
        int WIDTH = tiles.length;
        int HEIGHT = tiles[0].length;
        // Range for checking [1, WIDTH / HEIGHT -1]
        for (int x = 1; x < WIDTH - 1; x += 1) {
            for (int y = 1; y < HEIGHT - 1; y += 1) {
                // Remove Dead Walls within Floors
                if (tiles[x][y] == Tileset.WALL) {
                    if (tiles[x + 1][y + 1] == Tileset.NOTHING || tiles[x + 1][y] == Tileset.NOTHING || tiles[x + 1][y - 1] == Tileset.NOTHING || tiles[x][y - 1] == Tileset.NOTHING || tiles[x - 1][y - 1] == Tileset.NOTHING || tiles[x - 1][y] == Tileset.NOTHING || tiles[x - 1][y + 1] == Tileset.NOTHING || tiles[x][y + 1] == Tileset.NOTHING) {
                        continue;
                    }
                    tiles[x][y] = Tileset.FLOOR;
                }
            }
        }
    }

    /** Find a random place for player and door */
    private static void fillWithDoorAndPlayer(TETile[][] tiles, Random RANDOM) {
        int WIDTH = tiles.length;
        int HEIGHT = tiles[0].length;
        while (true) {
            // Range for a Random floor [2, WIDTH / HEIGHT - 2]
            int xFloor = 2 + RANDOM.nextInt(WIDTH - 4);
            int yFloor = 2 + RANDOM.nextInt(HEIGHT - 4);
            if (tiles[xFloor][yFloor] == Tileset.FLOOR) {
                tiles[xFloor][yFloor] = Tileset.PLAYER;
                break;
            }
        }
        while (true) {
            // Range for a Random WALL [1, WIDTH / HEIGHT - 1]
            int xDoor = 1 + RANDOM.nextInt(WIDTH - 2);
            int yDoor = 1 + RANDOM.nextInt(HEIGHT - 2);
            if (tiles[xDoor][yDoor] == Tileset.WALL) {
                if ((tiles[xDoor + 1][yDoor] == Tileset.NOTHING && tiles[xDoor - 1][yDoor] == Tileset.FLOOR) || (tiles[xDoor][yDoor - 1] == Tileset.NOTHING && tiles[xDoor][yDoor + 1] == Tileset.FLOOR)|| (tiles[xDoor - 1][yDoor] == Tileset.NOTHING && tiles[xDoor + 1][yDoor] == Tileset.FLOOR) || (tiles[xDoor][yDoor + 1] == Tileset.NOTHING && tiles[xDoor][yDoor - 1] == Tileset.FLOOR)) {
                    tiles[xDoor][yDoor] = Tileset.LOCKED_DOOR;
                    break;
                }
            }
        }

    }

    /** Generate a random world function */
    public static void WorldGenerator(TETile[][] tiles, Random RANDOM) {
        fillWithRandomRooms(tiles,RANDOM);
        connectRooms(tiles, RANDOM);
        fillWithWallTiles(tiles);
        fillWithBlankTiles(tiles);
        optimizeWallTiles(tiles);
        fillWithDoorAndPlayer(tiles, RANDOM);
    }
}
