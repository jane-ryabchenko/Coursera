import java.util.ArrayList;
import java.util.List;

public class Board {
  private final int[][] board;
  private final int n;
  private final int blankRow;
  private final int blankCol;
  private int hamming;
  private int manhattan;

  public Board(int[][] blocks) {
    if (blocks == null || blocks.length < 2 || blocks.length > 128 || blocks.length != blocks[0].length) {
      throw new IllegalArgumentException();
    }
    n = blocks.length;
    board = new int[n][n];
    int row = -1;
    int col = -1;
    for (int i = 0; i < blocks.length; i++) {
      for (int j = 0; j < blocks.length; j++) {
        if (blocks[i][j] == 0) {
          if (row >= 0) {
            throw new IllegalArgumentException();
          }
          row = i;
          col = j;
        }
        if (blocks[i][j] < 0 || blocks[i][j] >= n * n) {
          throw new IllegalArgumentException();
        }
        board[i][j] = blocks[i][j];
        if ((i < n - 1 || j < n - 1) && board[i][j] != i * n + j + 1) {
          hamming++;
        }
        manhattan += distance(i, j);
      }
    }

    if (row < 0) {
      throw new IllegalArgumentException();
    }
    blankRow = row;
    blankCol = col;
  }

  public int dimension() {
    return n;
  }

  public int hamming() {
    return hamming;
  }

  public int manhattan() {
    return manhattan;
  }

  public boolean isGoal() {
    if (this.blankRow != n - 1 && this.blankCol != n - 1) {
      return false;
    }
    else {
      return (hamming() == 0);
    }
  }

  public Board twin() {
    int[][] copy;
    if (blankRow > 0) {
      copy = exchange(0, 0, 0, 1);
    } else {
      copy = exchange(1, 0, 1, 1);
    }
    return new Board(copy);
  }

  public Iterable<Board> neighbors() {
    List<Board> list = new ArrayList<>();
    if (blankRow > 0) {
      list.add(new Board(exchange(blankRow, blankCol, blankRow - 1, blankCol)));
    }
    if (blankRow < n - 1) {
      list.add(new Board(exchange(blankRow, blankCol, blankRow + 1, blankCol)));
    }
    if (blankCol > 0) {
      list.add(new Board(exchange(blankRow, blankCol, blankRow, blankCol - 1)));
    }
    if (blankCol < n - 1) {
      list.add(new Board(exchange(blankRow, blankCol, blankRow, blankCol + 1)));
    }
    return list;
  }

  public boolean equals(Object y)   {
    if (y == null || y.getClass() != this.getClass()) {
      return false;
    }
    Board b = (Board) y;
    if (n != b.dimension()) {
      return false;
    }
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (board[i][j] != b.board[i][j]) {
          return false;
        }
      }
    }
    return true;
  }

  public String toString() {
    StringBuilder s = new StringBuilder();
    s.append("\n").append(n).append("\n");
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        s.append(String.format(" %d ", board[i][j]));
      }
      s.append("\n");
    }
    return s.toString();
  }

  private int distance(int i, int j) {
    if (board[i][j] == 0) {
      return 0;
    }
    int row = (board[i][j] - 1) / n;
    int col = (board[i][j] - 1) % n;
    return Math.abs(row - i) + Math.abs(col - j);
  }

  private int[][] exchange(int row1, int col1, int row2, int col2) {
    int[][] copy = new int[n][n];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        copy[i][j] = board[i][j];
      }
    }
    copy[row1][col1] = board[row2][col2];
    copy[row2][col2] = board[row1][col1];
    return copy;
  }
}
