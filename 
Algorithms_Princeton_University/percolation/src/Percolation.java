import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
  private final int n;
  private final boolean[] sites;
  private int numberOfOpenSites;
  private final WeightedQuickUnionUF unUF;

  public Percolation(int n) {
    if (n < 1) {
      throw new IllegalArgumentException("Percolation dimension n should be >= 1");
    }
    this.n = n;
    sites = new boolean[n * n + 1];
    unUF = new WeightedQuickUnionUF(n * n + 2);
  }

  public void open(int row, int col) {
    int i = index(row, col);
    if (!sites[i]) {
      sites[i] = true;
      numberOfOpenSites++;
    }

    if (row == 1) {
      unUF.union(0, index(row, col));
    }
    if (row == n) {
      unUF.union(index(row, col), n * n + 1);
    }
    if (row > 1 && sites[index(row - 1, col)]) {
      unUF.union(index(row, col), index(row - 1, col));
    }
    if (row < n && sites[index(row + 1, col)]) {
      unUF.union(index(row, col), index(row + 1, col));
    }
    if (col > 1 && sites[index(row, col - 1)]) {
      unUF.union(index(row, col), index(row, col - 1));
    }
    if (col < n && sites[index(row, col + 1)]) {
      unUF.union(index(row, col), index(row, col + 1));
    }
  }

  public boolean isOpen(int row, int col) {
    int i = index(row, col);
    return sites[i];
  }

  public boolean isFull(int row, int col) {
    return unUF.connected(0, index(row, col));
  }

  public int numberOfOpenSites() {
    return numberOfOpenSites;
  }

  public boolean percolates() {
    return unUF.connected(0, n * n  + 1);
  }

  private void validate(int row, int col) {
    if (row < 1 || row > n || col < 1 || col > n) {
      throw new IllegalArgumentException("Row and column numbers should be in range from 1 to " + n);
    }
  }

  private int index(int row, int col) {
    validate(row, col);
    return (row - 1) * n + col;
  }
}
