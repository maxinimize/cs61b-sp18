package byog.Core;

public class Room implements Comparable<Room> {
    private final int width;
    private final int height;
    private final Position bottomLeft;

    public Room() {
        width = 0;
        height = 0;
        bottomLeft = new Position();
    }

    public Room(Position bl, int w, int h) {
        width = w;
        height = h;
        bottomLeft = bl;
    }

    public int getBottomLeftX() {
        return bottomLeft.x;
    }

    public int getBottomLeftY() {
        return bottomLeft.y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public int compareTo(Room r) {
        return ((this.getBottomLeftX() < r.getBottomLeftX()) ? -1 : 1);
    }

    @Override
    public String toString() {
        return "X: " + this.bottomLeft.x + ", Y: " + this.bottomLeft.y + ", Width: " + this.width + ", Height:" + this.height;
    }
}
