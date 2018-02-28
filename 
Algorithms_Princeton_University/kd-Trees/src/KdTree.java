import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.List;

public class KdTree {
  private  int size;

  private Node root;

  // construct an empty tree of points
  public KdTree() {
    size = 0;
  }


  public boolean isEmpty() {
    return size == 0;
  }

  public int size() {
    return size;
  }

  public void insert(Point2D p) {
    if (p == null) {
      throw new IllegalArgumentException();
    }

    Node node = new Node(p);
    if (root == null) {
      root = node;
      size++;
    } else {
      Node n = root;
      int level = 0;
      while (true) {
        if (n.point.equals(p)) {
          break;
        }
        if ((level % 2 == 0 && p.x() < n.point.x()) || (level % 2 != 0 && p.y() < n.point.y())) {
          if (n.left == null) {
            n.left = node;
            size++;
            break;
          }
          n = n.left;
        } else {
          if (n.right == null) {
            n.right = node;
            size++;
            break;
          }
          n = n.right;
        }
        level++;
      }
    }
  }

  public boolean contains(Point2D p) {
    if (p == null) {
      throw new IllegalArgumentException();
    }

    Node n = root;
    int level = 0;
    while (n != null) {
      if (n.point.equals(p)) {
        return true;
      }
      if ((level % 2 == 0 && p.x() < n.point.x()) || (level % 2 != 0 && p.y() < n.point.y())) {
        n = n.left;
      } else {
        n = n.right;
      }
      level++;
    }
    return false;
  }

  // draw all points to standard draw
  public void draw() {
    draw(root);
  }

  // all points that are inside the rectangle (or on the boundary)
  public Iterable<Point2D> range(RectHV rect) {
    if (rect == null) {
      throw new IllegalArgumentException();
    }
    List<Point2D> points = new ArrayList<>();
    search(rect, root, 0, points);
    return points;
  }

  public Point2D nearest(Point2D p) {
    if (p == null) {
      throw new IllegalArgumentException();
    }
    Point2D[] points = new Point2D[] {root.point};
    search(p, root, 0, points, new double[] {p.distanceTo(root.point)});
    return points[0];
  }

  private void draw(Node n) {
    if (n == null) {
      return;
    }
    n.point.draw();
    draw(n.left);
    draw(n.right);
  }

  private void search(RectHV rect, Node n, int level, List<Point2D> points) {
    if (n == null) {
      return;
    }
    if (rect.contains(n.point)) {
      points.add(n.point);
    }
    if ((level % 2 == 0 && n.point.x() <= rect.xmax()) || (level % 2 != 0 && n.point.y() <= rect.ymax())) {
      search(rect, n.right, level + 1, points);
    }
    if ((level % 2 == 0 && n.point.x() > rect.xmin()) || (level % 2 != 0 && n.point.y() > rect.ymin())) {
      search(rect, n.left, level + 1, points);
    }
  }

  private void search(Point2D query, Node n, int level, Point2D[] points, double[] minDist) {
    if (n == null) {
      return;
    }

    double xmin = query.x() - minDist[0];
    double xmax = query.x() + minDist[0];
    double ymin = query.y() - minDist[0];
    double ymax = query.y() + minDist[0];

    if (n.point.x() >= xmin && n.point.x() <= xmax && n.point.y() >= ymin && n.point.y() <= ymax) {
      double newDist = query.distanceTo(n.point);
      if (minDist[0] > newDist) {
        points[0] = n.point;
        minDist[0] = newDist;

        xmin = query.x() - minDist[0];
        xmax = query.x() + minDist[0];
        ymin = query.y() - minDist[0];
        ymax = query.y() + minDist[0];
      }
    }

    if ((level % 2 == 0 && n.point.x() <= xmax) || (level % 2 != 0 && n.point.y() <= ymax)) {
      search(query, n.right, level + 1, points, minDist);
    }
    if ((level % 2 == 0 && n.point.x() > xmin) || (level % 2 != 0 && n.point.y() > ymin)) {
      search(query, n.left, level + 1, points, minDist);
    }
  }

  private static class Node {
    private final Point2D point;
    private Node left;
    private Node right;

    public Node(Point2D p) {
      point = p;
    }
  }
}