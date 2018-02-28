import edu.princeton.cs.algs4.Picture;
import java.awt.Color;


public class SeamCarver {
  private Picture picture;
  private int width;
  private int height;
  private double[] pictureEnergy;

  public SeamCarver(Picture pic) {
    if (pic == null) {
      throw  new IllegalArgumentException();
    }
    loadPicture(new Picture(pic));
  }

  private void loadPicture(Picture pic) {
    picture = pic;
    width = picture.width();
    height = picture.height();
    pictureEnergy = new double[width * height];
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        pictureEnergy[y * width + x] = energy(x, y);
      }
    }
  }

  // current picture
  public Picture picture() {
    return new Picture(picture);
  }

  // width  of current picture
  public int width() {
    return width;
  }

  // height of current picture
  public int height() {
    return height;
  }

  // energy of pixel at column x and row y
  public double energy(int x, int y) {
    if (x < 0 || y < 0 || x >= width || y >= height) {
      throw new IllegalArgumentException();
    }
    if (x == 0 || x == width - 1 || y == 0 || y == height - 1) {
      return 1000;
    }
    double dx = gradient(picture.get(x - 1, y), picture.get(x + 1, y));
    double dy = gradient(picture.get(x, y - 1), picture.get(x, y + 1));
    return Math.sqrt(dx + dy);
  }

  // sequence of indices for horizontal seam
  public int[] findHorizontalSeam() {
    double[] distTo = new double[width * height];
    for (int i = 0; i < width * height; i++) {
      if (i % width != 0) {
        distTo[i] = Double.MAX_VALUE;
      }
    }

    int[] edgeTo = new int[width * height];
    for (int x = 0; x < width - 1; x++) {
      for (int y = 0; y < height; y++) {
        if (y > 0) {
          relaxEdge(x, y, x + 1, y - 1, distTo, edgeTo);
        }
        relaxEdge(x, y, x + 1, y, distTo, edgeTo);
        if (y < height - 1) {
          relaxEdge(x, y, x + 1, y + 1, distTo, edgeTo);
        }
      }
    }

    double minDist = Double.MAX_VALUE;
    int pos = 0;
    for (int y = 0; y < height; y++) {
      int i = width - 1 + y * width;
      if (distTo[i] < minDist) {
        minDist = distTo[i];
        pos = i;
      }
    }

    int[] seam = new int[width];
    seam[width - 1] = pos / width;
    for (int i = width - 2; i >= 0; i--) {
      pos = edgeTo[pos];
      seam[i] = pos / width;
    }

    return seam;
  }

  // sequence of indices for vertical seam
  public int[] findVerticalSeam() {
    double[] distTo = new double[width * height];
    for (int i = width; i < width * height; i++) {
      distTo[i] = Double.MAX_VALUE;
    }

    int[] edgeTo = new int[width * height];
    for (int y = 0; y < height - 1; y++) {
      for (int x = 0; x < width; x++) {
        if (x > 0) {
          relaxEdge(x, y, x - 1, y + 1, distTo, edgeTo);
        }
        relaxEdge(x, y, x, y + 1, distTo, edgeTo);
        if (x < width - 1) {
          relaxEdge(x, y, x + 1, y + 1, distTo, edgeTo);
        }
      }
    }

    double minDist = Double.MAX_VALUE;
    int pos = 0;
    for (int x = 0; x < width; x++) {
      int i = x + (height - 1) * width;
      if (distTo[i] < minDist) {
        minDist = distTo[i];
        pos = i;
      }
    }

    int[] seam = new int[height];
    seam[height - 1] = pos % width;
    for (int i = height - 2; i >= 0; i--) {
      pos = edgeTo[pos];
      seam[i] = pos % width;
    }

    return seam;
  }

  public void removeHorizontalSeam(int[] seam) {
    if (seam == null || seam.length != width || height <= 1) {
      throw new IllegalArgumentException();
    }
    Picture newPicture = new Picture(width, height - 1);
    for (int x = 0; x < width; x++) {
      if (seam[x] < 0 || seam[x] >= height || (x > 0 && Math.abs(seam[x] - seam[x - 1]) > 1)) {
        throw new IllegalArgumentException();
      }
      int r = 0;
      for (int y = 0; y < height; y++) {
        if (y != seam[x]) {
          newPicture.set(x, r, picture.get(x, y));
          r++;
        }
      }
    }
    loadPicture(newPicture);
  }

  public void removeVerticalSeam(int[] seam) {
    if (seam == null || seam.length != height || width <= 1) {
      throw new IllegalArgumentException();
    }
    Picture newPicture = new Picture(width - 1, height);
    for (int y = 0; y < height; y++) {
      if (seam[y] < 0 || seam[y] >= width || (y > 0 && Math.abs(seam[y] - seam[y - 1]) > 1)) {
        throw new IllegalArgumentException();
      }
      int c = 0;
      for (int x = 0; x < width; x++) {
        if (x != seam[y]) {
          newPicture.set(c, y, picture.get(x, y));
          c++;
        }
      }
    }
    loadPicture(newPicture);
  }

  private double gradient(Color p1, Color p2) {
    int red = p1.getRed() - p2.getRed();
    int green = p1.getGreen() - p2.getGreen();
    int blue = p1.getBlue() - p2.getBlue();
    return red * red + green * green + blue * blue;
  }

  private void relaxEdge(int x1, int y1, int x2, int y2, double[] distTo, int[] edgeTo) {
    double newDistTo = distTo[x1 + y1 * width] + pictureEnergy[x2 + y2 * width];
    if (newDistTo < distTo[x2 + y2 * width]) {
      distTo[x2 + y2 * width] = newDistTo;
      edgeTo[x2 + y2 * width] = x1 + y1 * width;
    }
  }
}

