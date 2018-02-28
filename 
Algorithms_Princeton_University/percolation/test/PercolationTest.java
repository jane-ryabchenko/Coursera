import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PercolationTest {
  private Percolation percolation;

  @Before
  public void before() {
    percolation = new Percolation(5);
    percolation.open(1, 1);
    percolation.open(1, 2);
    percolation.open(1, 4);
    percolation.open(2, 4);
    percolation.open(3, 2);
    percolation.open(3, 4);
    percolation.open(3, 5);
    percolation.open(4, 1);
    percolation.open(4, 3);
    percolation.open(5, 1);
    percolation.open(5, 2);
    percolation.open(5, 4);
    percolation.open(5, 5);
  }

  @Test
  public void test_initial() {
    assertEquals(13, percolation.numberOfOpenSites());
  }

//  @Test
//  public void test_percolate1() {
//    percolation = new Percolation(1);
//    percolation.open(1, 1);
//    assertTrue(percolation.percolates());
//  }

  @Test
  public void test_false() {
    assertFalse(percolation.percolates());
    assertTrue(percolation.isFull(3, 5));
    assertFalse(percolation.isFull(4, 3));
  }

  @Test
  public void test_true() {
    assertFalse(percolation.isFull(3, 3));
    assertFalse(percolation.isFull(5, 3));

    percolation.open(3, 3);
    percolation.open(5, 3);

    assertTrue(percolation.percolates());
    assertTrue(percolation.isFull(3, 3));
    assertTrue(percolation.isFull(4, 3));
  }

  @Test
  public void test_true2() {
    percolation.open(4, 5);
    assertTrue(percolation.percolates());
  }

  @Test
  public void test_true3() {
    percolation.open(3, 3);
    percolation.open(4, 2);
    assertTrue(percolation.percolates());
  }
}
