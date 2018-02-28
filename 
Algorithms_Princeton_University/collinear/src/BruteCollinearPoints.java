import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
  private final ArrayList<LineSegment> segment = new ArrayList<>();

  public BruteCollinearPoints(Point[] points) {
    checkArguments(points);
    Arrays.sort(points);
    for (int p = 0; p < points.length - 3; p++) {
      for (int q = p + 1; q < points.length - 2; q++) {
        for (int r = q + 1; r < points.length - 1; r++) {
          for (int s = r + 1; s < points.length; s++) {
            if (Double.compare(points[p].slopeTo(points[q]), points[p].slopeTo(points[r])) == 0 &&
            Double.compare(points[p].slopeTo(points[q]), points[p].slopeTo(points[s])) == 0) {
              segment.add(new LineSegment(points[p], points[s]));
            }
          }
        }
      }
    }
  }

  public int numberOfSegments() {
    return segment.size();
  }

  public LineSegment[] segments() {
    return segment.toArray(new LineSegment[segment.size()]);
  }

  private void checkArguments(Point[] points) {
    if (points.length == 0) {
      throw new IllegalArgumentException();
    }
    for (int i = 0; i < points.length - 1; i++) {
      for (int j = i + 1; j < points.length; j++) {
        if (points[i].compareTo(points[j]) == 0 || points[i] == null || points[j] == null) {
          throw new IllegalArgumentException();
        }
      }
    }
  }
}
