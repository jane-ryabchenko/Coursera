import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class FastCollinearPointsTest {
  @Test
  public void testInput6() {
    Point[] points = new Point[] {
        new Point(19000, 10000),
        new Point(18000, 10000),
        new Point(32000, 10000),
        new Point(21000, 10000),
        new Point(1234, 5678),
        new Point(14000, 10000)
    };

    FastCollinearPoints instance = new FastCollinearPoints(points);
    assertThat(instance.numberOfSegments()).isEqualTo(1);
    LineSegment[] segments = instance.segments();
    assertThat(segments).isNotNull();
    assertThat(segments).hasLength(1);
    assertThat(segments).asList().doesNotContain(null);
    assertThat(segments[0].toString()).isEqualTo("(14000, 10000) -> (32000, 10000)");
  }

  @Test
  public void testInput8() {
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

    FastCollinearPoints instance = new FastCollinearPoints(points);
    assertThat(instance.numberOfSegments()).isEqualTo(2);
    LineSegment[] segments = instance.segments();
    assertThat(segments).isNotNull();
    assertThat(segments).hasLength(2);
    assertThat(segments).asList().doesNotContain(null);
    assertThat(segments[1].toString()).isEqualTo("(3000, 4000) -> (20000, 21000)");
    assertThat(segments[0].toString()).isEqualTo("(0, 10000) -> (10000, 0)");
  }
}
