import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class PercolationTest6 {
  private Percolation percolation;

  @Before
  public void before() {
    percolation = new Percolation(6);
    percolation.open(1, 6);
    percolation.open(2, 6);
    percolation.open(3, 6);
    percolation.open(4, 6);
    percolation.open(5, 6);
    percolation.open(5, 5);
    percolation.open(4, 4);
    percolation.open(3, 4);
    percolation.open(2, 4);
    percolation.open(2, 3);
    percolation.open(2, 2);
    percolation.open(2, 1);
    percolation.open(3, 1);
    percolation.open(4, 1);
    percolation.open(5, 1);
    percolation.open(5, 2);
    percolation.open(6, 2);
    percolation.open(5, 4);
  }

  @Test
  public void test2() {
    assertTrue(percolation.percolates());
    assertTrue(percolation.isFull(1, 6));
  }

}
