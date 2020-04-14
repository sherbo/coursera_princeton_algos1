/******************************************************************************
 *  Name: Sherban Drulea
 *  Date: April 2, 2019
 *  Description: Coursera Assignment 1
 *****************************************************************************/


import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

// Estimates percolation threshold for an N-by-N percolation system.
public class PercolationStats {

    // instance variable to store result values from simulations
    private double[] simulationValues;

    // Performs T independent experiments (Monte Carlo simulations) on an
    // N-by-N grid.
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) {               // N and T are receive in test client as command-line arguments
            throw new java.lang.IllegalArgumentException("N and T should be greater than or equal to 1.");
        }
        this.simulationValues = new double[T];


        for (int i = 0; i < T; i++) {
            Percolation process = new Percolation(N);
            int openSites = 0;
            while (!process.percolates()) {
                int row = StdRandom.uniform(1, N + 1);
                int col = StdRandom.uniform(1, N + 1);

                if (!process.isOpen(row, col)) {
                    process.open(row, col);
                    openSites++;
                }
            }
            this.simulationValues[i] = (double) openSites / (double) (N * N);   // stores the ratio at which percolation is successful
        }
    }

    // Returns sample mean of percolation threshold.
    public double mean() {
        return StdStats.mean(this.simulationValues);
    }

    // Returns sample standard deviation of percolation threshold.
    public double stddev() {
        if (this.simulationValues.length == 1) {
            return Double.NaN;
        }

        return StdStats.stddev(this.simulationValues);
    }

    // Returns low endpoint of the 95% confidence interval.
    public double confidenceLo() {
        double mean = this.mean();
        double deviation = this.stddev();
        double numOfSimulations = (double) this.simulationValues.length;
        double loLimit = mean - (1.96 * deviation / Math.sqrt(numOfSimulations));

        return loLimit;
    }

    // Returns high endpoint of the 95% confidence interval.
    public double confidenceHi() {
        double mean = this.mean();
        double deviation = this.stddev();
        double numOfSimulations = (double) this.simulationValues.length;
        double hiLimit = mean + (1.96 * deviation / Math.sqrt(numOfSimulations));

        return hiLimit;
    }

    // Test client
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(N, T);
        StdOut.printf("mean                     = %f\n", stats.mean());
        StdOut.printf("stddev                   = %f\n", stats.stddev());
        StdOut.printf("%s confidence interval  = [%f, %f]\n", "95%",
                      stats.confidenceLo(), stats.confidenceHi());
    }
}


