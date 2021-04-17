public class OffByN implements CharacterComparator {
    private int i;

    /** Constructor*/
    public OffByN(int N) {
        i = N;
    }

    @Override
    public boolean equalChars(char x, char y) {
        if (x - y == i || x - y == -i) {
            return true;
        }
        return false;
    }
}
