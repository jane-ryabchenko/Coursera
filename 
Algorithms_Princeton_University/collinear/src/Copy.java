public class Copy {
  //import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//public class FastCollinearPoints {
//  private ArrayList<LineSegment> segment = new ArrayList<>();
//
//  public FastCollinearPoints(Point[] points) {
//    checkArguments(points);
//    if (points.length < 4) {
//      return;
//    }
//
//    List<Point> pts = new ArrayList<>();
//    List<Double> slopeList = new ArrayList<>();
//    Arrays.sort(points);
//    Point[] basePoints = Arrays.copyOf(points, points.length);
//    for (Point base : basePoints) {
//      Double slope = null;
//      Arrays.sort(points, base.slopeOrder());
//
//      for (int i = 0; i < points.length; i++) {
//        Point p = points[i];
//        Double s = base.slopeTo(p);
//
//        if (!s.equals(slope) || i == points.length - 1) {
//          if (pts.size() >= 3) {
//            pts.add(base);
//
//            Collections.sort(pts);
//            segment.add(new LineSegment(pts.get(0), pts.get(pts.size() - 1)));
//          }
//          pts.clear();
//        }
//
//        if (s > Double.NEGATIVE_INFINITY && s < Double.POSITIVE_INFINITY) {
//          pts.add(p);
//        }
//        slope = s;
//      }
//    }
//  }
//
//  public int numberOfSegments() {
//    return segment.size();
//  }
//
//  public LineSegment[] segments() {
//    return segment.toArray(new LineSegment[segment.size()]);
//  }
//
//  private void checkArguments(Point[] points) {
//    if (points == null) {
//      throw new IllegalArgumentException();
//    }
//
//    Point zero = new Point(0, 0);
//    Arrays.sort(points, zero.slopeOrder());
//  }
//}

}
