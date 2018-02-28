import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class PercolationStatsTest {
  @Test
  public void test_true() {
    PercolationStats percolationStats = new PercolationStats(200, 100);
    assertEquals(0.592993499, percolationStats.mean(), .001);
  }

  @Test
  public void test_false() {
    PercolationStats percolationStats = new PercolationStats(200, 100);
    assertNotEquals(0.592993499, percolationStats.mean(), .0001);
  }
}
