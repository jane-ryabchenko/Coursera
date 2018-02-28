import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PercolationTest8 {
  private Percolation percolation;

  @Before
  public void before() {
    percolation = new Percolation(8);
    percolation.open(1, 3);
    percolation.open(2, 6);
    percolation.open(3, 3);
    percolation.open(4, 6);
    percolation.open(3, 2);
    percolation.open(5, 6);
    percolation.open(2, 5);
    percolation.open(7, 5);
    percolation.open(4, 7);
    percolation.open(3, 1);
    percolation.open(7, 8);
    percolation.open(2, 7);
    percolation.open(2, 1);
    percolation.open(4, 3);
    percolation.open(7, 1);
    percolation.open(6, 8);
    percolation.open(1, 4);
    percolation.open(2, 8);
    percolation.open(5, 2);
    percolation.open(5, 4);
    percolation.open(7, 7);
    percolation.open(4, 4);
    percolation.open(1, 5);
    percolation.open(2, 4);
    percolation.open(7, 6);
    percolation.open(3, 6);
    percolation.open(3, 7);
    percolation.open(5, 3);
    percolation.open(8, 6);
    percolation.open(6, 2);
    percolation.open(7, 3);
    percolation.open(4, 8);
  }

  @Test
  public void test1() {
    assertFalse(percolation.percolates());
    assertTrue(percolation.isFull(1, 3));
  }

  @Test
  public void test2() {
    percolation.open(6, 7);
    percolation.open(5, 7);
    assertTrue(percolation.percolates());
    assertTrue(percolation.isFull(1, 3));
  }
}