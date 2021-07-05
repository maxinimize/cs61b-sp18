package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;

public class MapGenerator {
    public static final int WIDTH = 80;
    public static final int HEIGHT = 40;
    private static final long SEED = 287013;
    private static final Random RANDOM = new Random(SEED);
    private static final int N = 20;
    public static ArrayList<Room> allRooms = new ArrayList<Room>();

    public static void fillWithRandomRooms(TETile[][] tiles) {
        for (int i = 0; i <= N; i++) {
            Room r = randomRoom(tiles);
            if (!roomCheck(tiles, r)) {
                continue;
            }
            buildRoom(tiles, r);
            allRooms.add(r);
        }
        Collections.sort(allRooms);
        for (Room r : allRooms) {
            System.out.println(r);
        }
    }

    private static Room randomRoom(TETile[][] tiles) {
        int x = 2 + RANDOM.nextInt(WIDTH - 2);
        int y = 2 + RANDOM.nextInt(HEIGHT - 2);
        Position bl = new Position(x, y);
        // width / height = 5 / 7 / 9
        int width = (3 + RANDOM.nextInt(3)) * 2 - 1;
        int height = (3 + RANDOM.nextInt(3)) * 2 - 1;
        Room randomRoom = new Room(bl, width, height);
        return randomRoom;
    }

    private static boolean roomCheck(TETile[][] tiles, Room r) {
        int x = r.getBottomLeftX();
        int y = r.getBottomLeftY();
        int width = r.getWidth();
        int height = r.getHeight();
        // 1. check if room exceeds the tiles
        if (x + width + 1 >= WIDTH - 1 || y + height + 1 >= HEIGHT - 1) {
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

    private static void buildRoom(TETile[][] tiles, Room r) {
        int x = r.getBottomLeftX();
        int y = r.getBottomLeftY();
        int width = r.getWidth();
        int height = r.getHeight();
        for (int i = x; i <= x + width; i++) {
            for (int j = y; j <= y + height; j++) {
                /*
                if (tiles[i][j] == Tileset.FLOOR) {
                    continue;
                }
                if (i == x || i == x + width || j == y || j == y + height) {
                    tiles[i][j] = Tileset.WALL;
                } else {
                    tiles[i][j] = Tileset.FLOOR;
                }
                 */
                tiles[i][j] = Tileset.FLOOR;
            }
        }
    }

    public static void connectRooms(TETile[][] tiles) {
        for (int i = 0; i < allRooms.size() - 1; i++) {
            Position p1 = randomPointRoom(allRooms.get(i));
            Position p2 = randomPointRoom(allRooms.get(i + 1));
            Hallway h = connectWithHallWay(p1, p2);
            buildRoom(tiles, h.getHorizontalRoom());
            buildRoom(tiles, h.getVerticalRoom());
        }
    }

    private static Position randomPointRoom(Room r) {
        int x = r.getBottomLeftX();
        int y = r.getBottomLeftY();
        int width = r.getWidth();
        int height = r.getHeight();
        int xRandom = x + 1 + RANDOM.nextInt(width - 1);

        int yRandom = y + 1 + RANDOM.nextInt(height - 1);
        Position randomPoint = new Position(xRandom, yRandom);
        System.out.println("(" + xRandom + "," + yRandom + ")");
        return randomPoint;
    }

    private static Hallway connectWithHallWay(Position p1, Position p2) {
        int x = Math.abs(p1.x - p2.x);
        int y = Math.abs(p1.y - p2.y);
        Position interPoint = new Position(p2.x, p1.y);
        int hallwaySize = 2;
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

    public static void fillWithWallTiles(TETile[][] tiles) {
        int height = tiles[0].length;
        int width = tiles.length;
        for (int x = 1; x < width - 1; x += 1) {
            for (int y = 1; y < height - 1; y += 1) {
                if (tiles[x][y] == null) {
                    if (tiles[x + 1][y + 1] == Tileset.FLOOR || tiles[x + 1][y] == Tileset.FLOOR || tiles[x + 1][y - 1] == Tileset.FLOOR || tiles[x][y - 1] == Tileset.FLOOR || tiles[x - 1][y - 1] == Tileset.FLOOR || tiles[x - 1][y] == Tileset.FLOOR || tiles[x - 1][y + 1] == Tileset.FLOOR || tiles[x][y + 1] == Tileset.FLOOR) {
                        tiles[x][y] = Tileset.WALL;
                    }
                }
            }
        }
    }

    public static void fillWithBlankTiles(TETile[][] tiles) {
        int height = tiles[0].length;
        int width = tiles.length;
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                if (tiles[x][y] == null) {
                    tiles[x][y] = Tileset.NOTHING;
                }
            }
        }
    }
}
