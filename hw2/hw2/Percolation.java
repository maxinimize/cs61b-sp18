package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int [][] grid;
    private WeightedQuickUnionUF unionGrid2VSite; // N * N + 2 elements
    private WeightedQuickUnionUF unionGrid1VSite; // N * N + 1 elements, no bottom sites
    private final int[] dx = {-1, 1, 0, 0}, dy = {0, 0, -1, 1}; // neighbour orthogonal sites helper
    private int N;
    private int openSitesNumber;

    /** create N-by-N grid, with all sites initially blocked
     */
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        grid = new int[N][N];
        unionGrid2VSite = new WeightedQuickUnionUF(N * N + 2);
        unionGrid1VSite = new WeightedQuickUnionUF(N * N + 1);
        this.N = N;
        openSitesNumber = 0;

    }

    /** Range for row and col is [0, N - 1], otherwise throw a java.lang.IndexOutOfBoundsException
     */
    private void validate(int row, int col) {
        if (row >= N || col >= N || row < 0 || col < 0) {
            throw new IndexOutOfBoundsException();
        }
    }

    /** Convert (row, col) cell to ith cell
     */
    private int xyTo1D(int row, int col) {
        return row * N + col;
    }

    /** open the site (row, col) if it is not open already
     */
    public void open(int row, int col) {
        validate(row, col);
        // check if it's open or not
        if (grid[row][col] != 1) {
            grid[row][col] = 1;
            // if it's in the top row, connect it with virtual top sites (N * N)
            if (row == 0) {
                unionGrid2VSite.union(N * N, xyTo1D(row, col));
                unionGrid1VSite.union(N * N, xyTo1D(row, col));
            }
            // if it's in the bottom row, connect it with virtual bottom sites (N * N + 1)
            if (row == N - 1) {
                unionGrid2VSite.union(N * N + 1, xyTo1D(row, col));
            }
            // connect it with neighbour open sites
            unionOrthogonalOpenSites(unionGrid2VSite, row, col);
            unionOrthogonalOpenSites(unionGrid1VSite, row, col);
            openSitesNumber++;
        }

    }

    /** Check if neighbour sites are open or not, connect them if it's open
     */
    private void unionOrthogonalOpenSites(WeightedQuickUnionUF unionGrid, int row, int col) {
        for (int i = 0; i < 4; i++) {
            if (row + dx[i] >= 0 && row + dx[i] < N && col + dy[i] >= 0 && col + dy[i] < N) {
                if (grid[row + dx[i]][col + dy[i]] == 1) {
                    unionGrid.union(xyTo1D(row + dx[i], col + dy[i]), xyTo1D(row, col));
                }
            }

        }
    }

    /** is the site (row, col) open?
     */
    public boolean isOpen(int row, int col) {
        return grid[row][col] == 1;
    }

    /** / is the site (row, col) full?
     */
    public boolean isFull(int row, int col) {
        return unionGrid1VSite.connected(N * N, xyTo1D(row, col));
    }

    /** number of open sites
     */
    public int numberOfOpenSites() {
        return openSitesNumber;
    }

    /** does the system percolate?
     */
    public boolean percolates() {
        return unionGrid2VSite.connected(N * N, N * N + 1);
    }

    public static void main(String[] args) {

    }
}
