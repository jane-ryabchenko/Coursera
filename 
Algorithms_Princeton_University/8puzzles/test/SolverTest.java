import com.google.common.collect.Iterables;
import edu.princeton.cs.algs4.In;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class SolverTest {
  @Test
  public void testSolver_succeed() {
    verifySolver("puzzle04.txt", 4);
  }

  @Test
  public void testSolver_failed() {
    verifySolver("puzzle3x3-unsolvable.txt", -1);
  }

  private void verifySolver(String fileName, int moves) {
    In in = new In(fileName);
    int n = in.readInt();
    int[][] blocks = new int[n][n];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        blocks[i][j] = in.readInt();
      }
    }
    Board initial = new Board(blocks);

    // solve the puzzle
    Solver solver = new Solver(initial);
    Iterable<Board> solution = solver.solution();
    assertThat(solver.moves()).isEqualTo(moves);
    if (moves >= 0) {
      assertThat(solver.isSolvable()).isTrue();
      assertThat(solution).isNotNull();
      assertThat(solution).hasSize(moves + 1);
      assertThat(Iterables.getLast(solution).isGoal()).isTrue();
    } else {
      assertThat(solver.isSolvable()).isFalse();
      assertThat(solution).isNull();
    }
  }
}
