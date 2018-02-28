import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class PointSETTest {
  @Test
  public void test() {
    In in = new In("input10.txt");
    PointSET pointSET = new PointSET();
    while (!in.isEmpty()) {
      double x = in.readDouble();
      double y = in.readDouble();
      Point2D p = new Point2D(x, y);
      pointSET.insert(p);
    }
    assertThat(pointSET.size()).isEqualTo(10);
    assertThat(pointSET.contains(new Point2D(0.564, 0.413))).isTrue();
    assertThat(pointSET.nearest(new Point2D(0.415, 0.360))).isEqualTo(new Point2D(0.417, 0.362));
  }
}
