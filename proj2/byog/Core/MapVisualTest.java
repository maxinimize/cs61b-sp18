package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;

import java.util.Random;


public class MapVisualTest {
    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(80, 40);

        TETile[][] randomTiles = new TETile[80][40];

        long SEED = 287013;
        Random RANDOM = new Random(SEED);

        MapGenerator.WorldGenerator(randomTiles,RANDOM);

        ter.renderFrame(randomTiles);
    }
}
