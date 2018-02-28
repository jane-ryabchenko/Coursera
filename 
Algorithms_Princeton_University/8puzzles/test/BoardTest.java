import edu.princeton.cs.algs4.In;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class BoardTest {
  @Test
  public void testDimension() {
    verifyDimension("puzzle3x3-04.txt", 4, 4);
    verifyDimension("puzzle4x4-08.txt", 8, 8);
  }

  private void verifyDimension(String fileName, int h, int m) {
    In in = new In(fileName);
    int n = in.readInt();
    int[][] blocks = new int[n][n];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        blocks[i][j] = in.readInt();
      }
    }
    Board board = new Board(blocks);
    assertThat(board.dimension()).isEqualTo(n);
    assertThat(board.hamming()).named("Hamming").isEqualTo(h);
    assertThat(board.manhattan()).named("Manhattan").isEqualTo(m);
    Board twin = board.twin();
    assertThat(twin.hamming() - board.hamming()).named("Wrong twin hamming:\n %s\n%s", board, twin).isAnyOf(-2, 2);
    assertThat(twin.manhattan() - board.manhattan()).named("Wrong twin manhattan:\n %s\n%s", board, twin).isAnyOf(-2, 2);
  }
}
