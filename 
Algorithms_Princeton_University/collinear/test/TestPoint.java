import org.junit.Test;

import java.util.Comparator;

import static org.junit.Assert.assertEquals;

public class TestPoint {
  @Test
  public void testSlopeToNeg() {
    Point p1 = new Point(1, 1);
    Point p2 = new Point(1, 1);
    assertEquals(Double.NEGATIVE_INFINITY, p1.slopeTo(p2), 0.01);
  }

  @Test
  public void testSlopeToPos() {
    Point p1 = new Point(1, 1);
    Point p2 = new Point(1, 5);
    assertEquals(Double.POSITIVE_INFINITY, p1.slopeTo(p2), 0.01);
  }

  @Test
  public void testSlopeToZero() {
    Point p1 = new Point(1, 1);
    Point p2 = new Point(5, 1);
    assertEquals(0.0, p1.slopeTo(p2), 0.01);
  }

  @Test
  public void testSlopeTo() {
    Point p1 = new Point(1, 1);
    Point p2 = new Point(2, 3);
    assertEquals(2.0, p1.slopeTo(p2), 0.01);
  }

  @Test
  public void testCopareToEq() {
    Point p1 = new Point(1, 1);
    Point p2 = new Point(1, 1);
    assertEquals(0, p1.compareTo(p2));
  }

  @Test
  public void testCompareToLess() {
    Point p1 = new Point(1, 1);
    Point p2 = new Point(2, 5);
    assertEquals(-1, p1.compareTo(p2));
  }

  @Test
  public void testCompareToGr() {
    Point p1 = new Point(2, 3);
    Point p2 = new Point(1, 1);
    assertEquals(1, p1.compareTo(p2));
  }

  @Test
  public void testComparatorZero() {
    Point defPoint = new Point(0, 0);
    Point p1 = new Point(1, 1);
    Point p2 = new Point(1, 1);
    Comparator<Point> pointComparator = defPoint.slopeOrder();
    assertEquals(0, pointComparator.compare(p1, p2));
  }

  @Test
  public void testComparatorLess() {
    Point defPoint = new Point(0, 0);
    Point p1 = new Point(1, 1);
    Point p2 = new Point(2, 3);
    Comparator<Point> pointComparator = defPoint.slopeOrder();
    assertEquals(-1, pointComparator.compare(p1, p2));
  }

  @Test
  public void testComparatorGr() {
    Point defPoint = new Point(0, 0);
    Point p1 = new Point(2, 3);
    Point p2 = new Point(1, 1);
    Comparator<Point> pointComparator = defPoint.slopeOrder();
    assertEquals(1, pointComparator.compare(p1, p2));
  }

}
