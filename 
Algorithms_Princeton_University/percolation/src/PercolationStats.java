import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
  private final double mean;
  private final double stddev;
  private final double confidenceLo;
  private final double confidenceHi;

  public PercolationStats(int n, int trials) {
    if (n < 1 || trials < 1) {
      throw new IllegalArgumentException(
          "Dimension of percolation matrix and number of trials should be greater than 0.");
    }
    double[] p = new double[trials];
    for (int i = 0; i < trials; i++) {
      Percolation percolation = new Percolation(n);
      while (!percolation.percolates()) {
        int ind = StdRandom.uniform(n * n);
        percolation.open(ind / n + 1, ind % n + 1);
      }
      p[i] = ((double) percolation.numberOfOpenSites()) / (n * n);
    }
    mean = StdStats.mean(p);
    stddev = StdStats.stddev(p);
    double delta = 1.96 * stddev() / Math.sqrt(trials);
    confidenceLo = mean - delta;
    confidenceHi = mean + delta;
  }

  public double mean() {
    return mean;
  }

  public double stddev() {
    return stddev;
  }

  public double confidenceLo() {
    return confidenceLo;
  }

  public double confidenceHi() {
    return confidenceHi;
  }

  public static void main(String[] args) {
    int n = Integer.parseInt(args[0]);
    int t = Integer.parseInt(args[1]);
    PercolationStats percolationStats = new PercolationStats(n, t);
    StdOut.printf("mean                    = %f\n", percolationStats.mean());
    StdOut.printf("stddev                  = %f\n", percolationStats.stddev());
    StdOut.printf("95%% confidence interval = [%f, %f]\n", percolationStats.confidenceLo(),
        percolationStats.confidenceHi());
  }
}
