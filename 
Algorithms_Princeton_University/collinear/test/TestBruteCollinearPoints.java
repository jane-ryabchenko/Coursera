import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestBruteCollinearPoints {

  @Test
  public void testCollinearPointstrue() {
    Point[] points = new Point[]{new Point(1, 2), new Point(2, 4), new Point(2, 2),
                                 new Point(3, 6), new Point(4, 8), new Point(3, 3)};
    BruteCollinearPoints bruteCollinearPoints = new BruteCollinearPoints(points);
    assertEquals(1, bruteCollinearPoints.numberOfSegments());
  }

  @Test
  public void test8() {
    Point[] points = new Point[] {
        new Point(10000, 0),
        new Point(0, 10000),
        new Point(3000, 7000),
        new Point(7000, 3000),
        new Point(20000, 21000),
        new Point(3000, 4000),
        new Point(14000, 15000),
        new Point(6000, 7000)
    };
    BruteCollinearPoints bruteCollinearPoints = new BruteCollinearPoints(points);
    assertEquals(2, bruteCollinearPoints.numberOfSegments());
  }
}
