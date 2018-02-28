import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;

public class KdTree1 {
  private static final double XMIN = 0.0;
  private static final double XMAX = 1.0;
  private static final double YMIN = 0.0;
  private static final double YMAX = 1.0;

  private  int size;

  private Node root;

  // construct an empty tree of points
  public KdTree1() {
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
    root = insert(root, p, XMIN, YMIN, XMAX, YMAX, 0);
  }

  private int cmp(Point2D a, Point2D b, int level) {
    if (level % 2 == 0) {
      // Compare x-coordinates
      int cmpResult = new Double(a.x()).compareTo(new Double(b.x()));

      if (cmpResult == 0) {
        return new Double(a.y()).compareTo(new Double(b.y()));
      } else {
        return cmpResult;
      }
    } else {
      // Compare y-coordinates
      int cmpResult = new Double(a.y()).compareTo(new Double(b.y()));

      if (cmpResult == 0) {
        return new Double(a.x()).compareTo(new Double(b.x()));
      } else {
        return cmpResult;
      }
    }
  }

  private Node insert(Node x, Point2D value, double xmin, double ymin, double xmax, double ymax, int level) {
    if (x == null) {
      size++;
      return new Node(value, new RectHV(xmin, ymin, xmax, ymax));
    };

    int cmp = cmp(value, x.point, level);

    if (cmp < 0) {
      if (level % 2 == 0) {
        x.left = insert(x.left, value, xmin, ymin, x.point.x(), ymax, level + 1);
      } else {
        x.left = insert(x.left, value, xmin, ymin, xmax, x.point.y(), level + 1);
      }
    } else if (cmp > 0) {
      if (level % 2 == 0) {
        x.right = insert(x.right, value, x.point.x(), ymin, xmax, ymax, level + 1);
      } else {
        x.right = insert(x.right, value, xmin, x.point.y(), xmax, ymax, level + 1);
      }
    }

    return x;
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
  public Iterable<Point2D> range(RectHV rect) {
    Queue<Point2D> queue = new Queue<Point2D>();

    rangeAdd(root, rect, queue);

    return queue;
  }

  private void rangeAdd(Node x, RectHV rect, Queue<Point2D> queue) {
    if (x != null && rect.intersects(x.rect)) {
      if (rect.contains(x.point)) {
        queue.enqueue(x.point);
      }
      rangeAdd(x.left, rect, queue);
      rangeAdd(x.right, rect, queue);
    }
  }



  public Point2D nearest(Point2D p) {
    if (p == null) {
      throw new IllegalArgumentException();
    }
    return nearest(root, p, null);
  }

  private Point2D nearest(Node x, Point2D point, Point2D min) {
    if (x != null) {
      if (min == null) {
        min = x.point;
      }

      // If the current min point is closer to query than the current point
      if (min.distanceSquaredTo(point)
          >= x.rect.distanceSquaredTo(point)) {
        if (x.point.distanceSquaredTo(point) < min.distanceSquaredTo(point)) {
          min = x.point;
        }

        // Check in which order should we iterate
        if (x.right != null && x.right.rect.contains(point)) {
          min = nearest(x.right, point, min);
          min = nearest(x.left, point, min);
        } else {
          min = nearest(x.left, point, min);
          min = nearest(x.right, point, min);
        }
      }
    }
    return min;
  }

  private void draw(Node n) {
    if (n == null) {
      return;
    }
    n.point.draw();
    draw(n.left);
    draw(n.right);
  }

  private static class Node {
    private final Point2D point;
    private RectHV rect;
    private Node left;
    private Node right;


    public Node(Point2D p, RectHV inRect) {
      point = p;
      rect = inRect;
      left = null;
      right = null;
    }
  }
}