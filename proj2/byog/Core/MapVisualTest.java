package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;


public class MapVisualTest {
    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(MapGenerator.WIDTH, MapGenerator.HEIGHT);

        TETile[][] randomTiles = new TETile[MapGenerator.WIDTH][MapGenerator.HEIGHT];

        MapGenerator.fillWithRandomRooms(randomTiles);
        MapGenerator.connectRooms(randomTiles);
        MapGenerator.fillWithWallTiles(randomTiles);
        MapGenerator.fillWithBlankTiles(randomTiles);

        ter.renderFrame(randomTiles);
    }
}
