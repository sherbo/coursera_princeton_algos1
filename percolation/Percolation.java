/******************************************************************************
 *  Name: Sherban Drulea
 *  Date: April 2, 2019
 *  Description: Coursera Assignment 1
 *****************************************************************************/

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF unionFind;
    private WeightedQuickUnionUF backwashFind;
    private boolean[] sites;
    private final int gridSize, virtualTopIndex, virtualBottomIndex;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be greater than zero.");
        }

        int numNodes = n * n + 2;
        unionFind = new WeightedQuickUnionUF(numNodes);
        backwashFind = new WeightedQuickUnionUF(numNodes);
        sites = new boolean[n * n];
        gridSize = n;
        virtualTopIndex = n * n;
        virtualBottomIndex = n * n + 1;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        verifyColAndRowInRange(row, col);
        openSite(row, col);
        connectToVirtualTopSite(row, col);
        connectAdjacents(row, col);
        connectToVirtualBottomSite(row, col);
    }

    private int rowColtoIndex(int row, int col) {
        return (row - 1) * gridSize + (col - 1);
    }

    private void verifyColAndRowInRange(int row, int col) {
        if (row < 1 || row > gridSize || col < 1 || col > gridSize) {
            throw new IllegalArgumentException("Row and/or column out of range.");
        }
    }

    private void openSite(int row, int col) {
        sites[rowColtoIndex(row, col)] = true;
    }

    private void union(int row, int col) {
        unionFind.union(row, col);
        backwashFind.union(row, col);
    }

    private void connectAdjacents(int row, int col) {
        connectTopSite(row, col);
        connectBottomSite(row, col);
        connectLeftSite(row, col);
        connectRightSite(row, col);
    }

    private void connectTopSite(int row, int col) {
        if (row > 1 && isOpen(row - 1, col)) {
            union(rowColtoIndex(row - 1, col), rowColtoIndex(row, col));
        }
    }

    private void connectBottomSite(int row, int col) {
        if (row < gridSize && isOpen(row + 1, col)) {
            union(rowColtoIndex(row + 1, col), rowColtoIndex(row, col));
        }
    }

    private void connectLeftSite(int row, int col) {
        if (col > 1 && isOpen(row, col - 1)) {
            union(rowColtoIndex(row, col - 1), rowColtoIndex(row, col));
        }
    }

    private void connectRightSite(int row, int col) {
        if (col < gridSize && isOpen(row, col + 1)) {
            union(rowColtoIndex(row, col + 1), rowColtoIndex(row, col));
        }
    }

    private void connectToVirtualTopSite(int row, int col) {
        if (row == 1) {
            union(virtualTopIndex, rowColtoIndex(row, col));
        }
    }

    private void connectToVirtualBottomSite(int row, int col) {
        if (row == gridSize) {
            backwashFind.union(virtualBottomIndex, rowColtoIndex(row, col));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        verifyColAndRowInRange(row, col);
        return sites[rowColtoIndex(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        verifyColAndRowInRange(row, col);
        return unionFind.connected(rowColtoIndex(row, col), virtualTopIndex);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int open = 0;
        for (int i = 0; i < sites.length; i++) {
            if (sites[i]) {
                open++;
            }
        }

        return open;
    }

    // does the system percolate?
    public boolean percolates() {
        return backwashFind.connected(virtualBottomIndex, virtualTopIndex);
    }

    private void print(){
        for (int i = 1; i < gridSize + 1; ++i) {
            for (int j = 1; j < gridSize + 1; ++j) {
                System.out.print(this.isFull(i, j) ? "1 " : "0 ");
            }
            System.out.println();
        }
    }

    // test client (optional)
    public static void main(String[] args) {
        int N = 5;
        Percolation p = new Percolation(N);
        p.print();
        System.out.println("Number of open sites: " + Integer.toString(p.numberOfOpenSites()));
        System.out.println("Percolates: " + Boolean.toString(p.percolates()));
        System.out.println();
        System.out.println();

        for (int i = 1; i <= N; ++i) {
            p.open(i, i);
            if (i < N) p.open(i, i + 1);
        }

        p.open(N, 1);
        p.open(N - 1, 2);
        p.open(N - 1, 1);
        p.print();
        System.out.println("Number of open sites: " + Integer.toString(p.numberOfOpenSites()));
        System.out.println("Percolates: " + Boolean.toString(p.percolates()));
        System.out.println();
        System.out.println();

        for (int i = 1; i <= N; ++i) {
            for (int j = 1; j < N + 1; ++j) {
                p.open(i, j);
            }
        }
        p.print();
        System.out.println("Number of open sites: " + Integer.toString(p.numberOfOpenSites()));
        System.out.println("Percolates: " + Boolean.toString(p.percolates()));
    }
}
