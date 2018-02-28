import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class FastCollinearPoints {
  private ArrayList<LineSegment> segment = new ArrayList<>();
  private HashMap<Double, ArrayList<Point>> slopePoints = new HashMap<Double, ArrayList<Point>>();

  public FastCollinearPoints(Point[] points) {
    checkArguments(points);
    if (points.length < 4) {
      return;
    }
    Arrays.sort(points);
    Point[] basePoints = Arrays.copyOf(points, points.length);
    Arrays.sort(basePoints);
    for (int i = 0; i < points.length - 3; i++) {
      Point[] temp = Arrays.copyOfRange(basePoints, i, points.length);
      Arrays.sort(temp, temp[0].slopeOrder());
      int first = 1, last = 2;
      double slope;
      while (last < temp.length) {
        slope = temp[0].slopeTo(temp[first]);
        while (last < temp.length && Double.compare(slope, temp[0].slopeTo(temp[last])) == 0) {
          last++;
        }
        if (last - first >= 3) {
          if (addPointToMap(slope, temp, last - 1))
            segment.add(new LineSegment(temp[0], temp[last - 1]));
        }
        first = last;
        last++;
      }
    }
  }

  private boolean addPointToMap(double slope, Point[] temp, int end) {
    ArrayList<Point> listPoint = slopePoints.get(slope);
    if (listPoint == null) {
      listPoint = new ArrayList<>();
      listPoint.add(temp[end]);
      slopePoints.put(slope, listPoint);
      return true;
    } else if (listPoint.contains(temp[end])) {
      return false;
    }
    else {
      listPoint.add(listPoint.size(), temp[end]);
      slopePoints.put(slope, listPoint);
      return true;
    }
  }

  public int numberOfSegments() {
    return segment.size();
  }

  public LineSegment[] segments() {
    return segment.toArray(new LineSegment[segment.size()]);
  }

  private void checkArguments(Point[] points) {
    if (points == null) {
      throw new IllegalArgumentException();
    }

    Point zero = new Point(0, 0);
    Arrays.sort(points, zero.slopeOrder());
  }
}
