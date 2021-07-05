package byog.Core;

public class Hallway {
    private Room horizontalRoom;
    private Room verticalRoom;

    public Hallway(Room h, Room v) {
        horizontalRoom = h;
        verticalRoom = v;
    }

    public Room getHorizontalRoom() {
        return horizontalRoom;
    }

    public Room getVerticalRoom() {
        return verticalRoom;
    }
}
