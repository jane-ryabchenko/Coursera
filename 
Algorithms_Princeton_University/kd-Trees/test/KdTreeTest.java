import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class KdTreeTest {
  @Test
  public void test() {
    In in = new In("input10.txt");
    KdTree kdtree = new KdTree();
    while (!in.isEmpty()) {
      double x = in.readDouble();
      double y = in.readDouble();
      Point2D p = new Point2D(x, y);
      kdtree.insert(p);
    }
    assertThat(kdtree.size()).isEqualTo(10);
    assertThat(kdtree.contains(new Point2D(0.564, 0.413))).isTrue();
  }

  @Test
  public void testRange1() {
    KdTree kdtree = new KdTree();
    kdtree.insert(new Point2D(0.125, 0.75));
    kdtree.insert(new Point2D(0.375, 0.875));
    kdtree.insert(new Point2D(0.625, 0.25));
    kdtree.insert(new Point2D(0.25, 0.5));
    kdtree.insert(new Point2D(0.5, 0.0));

    assertThat(kdtree.size()).isEqualTo(5);
    assertThat(kdtree.range(new RectHV(0.0, 0.375, 0.875, 0.625))).containsExactly(new Point2D(0.25, 0.5));
  }

  @Test
  public void testRange2() {
    KdTree kdtree = new KdTree();
    kdtree.insert(new Point2D(0.5, 0.1875));
    kdtree.insert(new Point2D(0.875, 0.8125));
    kdtree.insert(new Point2D(0.625, 1.0));
    kdtree.insert(new Point2D(0.75, 0.75));
    kdtree.insert(new Point2D(0.8125, 0.625));
    kdtree.insert(new Point2D(0.375, 0.875));
    kdtree.insert(new Point2D(0.0625, 0.3125));
    kdtree.insert(new Point2D(0.9375, 0.25));
    kdtree.insert(new Point2D(0.5625, 0.125));
    kdtree.insert(new Point2D(0.6875, 0.6875));

    assertThat(kdtree.size()).isEqualTo(10);
    assertThat(kdtree.range(new RectHV(0.1875, 0.5625, 1.0, 0.9375))).containsExactly(
        new Point2D(0.375, 0.875),
        new Point2D(0.875, 0.8125),
        new Point2D(0.75, 0.75),
        new Point2D(0.6875, 0.6875),
        new Point2D(0.8125, 0.625));
  }

  @Test
  public void testNearest1() {
    KdTree kdtree = new KdTree();
    kdtree.insert(new Point2D(0.7, 0.2));
    kdtree.insert(new Point2D(0.5, 0.4));
    kdtree.insert(new Point2D(0.2, 0.3));
    kdtree.insert(new Point2D(0.4, 0.7));
    kdtree.insert(new Point2D(0.9, 0.6));

    assertThat(kdtree.size()).isEqualTo(5);
    assertThat(kdtree.nearest(new Point2D(0.528, 0.872))).isEqualTo(new Point2D(0.4, 0.7));
  }

  @Test
  public void testNearest2() {
    KdTree kdtree = new KdTree();
    kdtree.insert(new Point2D(0.372, 0.497));
    kdtree.insert(new Point2D(0.564, 0.413));
    kdtree.insert(new Point2D(0.226, 0.577));
    kdtree.insert(new Point2D(0.144, 0.179));
    kdtree.insert(new Point2D(0.083, 0.51));
    kdtree.insert(new Point2D(0.32, 0.708));
    kdtree.insert(new Point2D(0.417, 0.362));
    kdtree.insert(new Point2D(0.862, 0.825));
    kdtree.insert(new Point2D(0.785, 0.725));
    kdtree.insert(new Point2D(0.499, 0.208));

    assertThat(kdtree.size()).isEqualTo(10);
    assertThat(kdtree.nearest(new Point2D(0.193, 0.961))).isEqualTo(new Point2D(0.32, 0.708));
  }

  @Test
  public void testNearest3() {
    KdTree kdtree = new KdTree();
    kdtree.insert(new Point2D(0.372, 0.497));
    kdtree.insert(new Point2D(0.564, 0.413));
    kdtree.insert(new Point2D(0.226, 0.577));
    kdtree.insert(new Point2D(0.144, 0.179));
    kdtree.insert(new Point2D(0.083, 0.51));
    kdtree.insert(new Point2D(0.32, 0.708));
    kdtree.insert(new Point2D(0.417, 0.362));
    kdtree.insert(new Point2D(0.862, 0.825));
    kdtree.insert(new Point2D(0.785, 0.725));
    kdtree.insert(new Point2D(0.499, 0.208));

    assertThat(kdtree.size()).isEqualTo(10);
    assertThat(kdtree.nearest(new Point2D(0.696, 0.985))).isEqualTo(new Point2D(0.862, 0.825));
  }

  @Test
  public void testNearest4() {
    KdTree kdtree = new KdTree();
    kdtree.insert(new Point2D(0.7, 0.2));
    kdtree.insert(new Point2D(0.5, 0.4));
    kdtree.insert(new Point2D(0.2, 0.3));
    kdtree.insert(new Point2D(0.4, 0.7));
    kdtree.insert(new Point2D(0.9, 0.6));

    assertThat(kdtree.size()).isEqualTo(5);
    assertThat(kdtree.nearest(new Point2D(0.205, 0.498))).isEqualTo(new Point2D(0.2, 0.3));
  }

  @Test
  public void testNearest5() {
    KdTree kdtree = new KdTree();
    kdtree.insert(new Point2D(0.7, 0.2));
    kdtree.insert(new Point2D(0.5, 0.4));
    kdtree.insert(new Point2D(0.2, 0.3));
    kdtree.insert(new Point2D(0.4, 0.7));
    kdtree.insert(new Point2D(0.9, 0.6));

    assertThat(kdtree.size()).isEqualTo(5);
    assertThat(kdtree.nearest(new Point2D(0.32, 1))).isEqualTo(new Point2D(0.4, 0.7));
  }
}
