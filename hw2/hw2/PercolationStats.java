package hw2;
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {

    private int T;
    private double[] threshold;
    private Percolation test;

    /** perform T independent experiments on an N-by-N grid
     */
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        this.T = T;
        threshold = new double[T];
        for (int i = 0; i < T; i++) {
            test = pf.make(N);
            while (!test.percolates()) {
                int row = StdRandom.uniform(N);
                int col = StdRandom.uniform(N);
                test.open(row, col);
            }
            threshold[i] = (double) test.numberOfOpenSites() / (N * N);
        }
    }

    /** sample mean of percolation threshold
     */
    public double mean() {
        return StdStats.mean(threshold);
    }

    /** sample standard deviation of percolation threshold
     */
    public double stddev() {
        return StdStats.stddev(threshold);
    }

    /** low endpoint of 95% confidence interval
     */
    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.sqrt(T);
    }

    /** high endpoint of 95% confidence interval
     */
    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.sqrt(T);
    }

}
