import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.LinkedList;

public class Solver {
  private LinkedList<Board> solution;

  // find a solution to the initial board (using the A* algorithm)
  public Solver(Board initial) {
    if (initial == null) {
      throw new IllegalArgumentException();
    }

    MinPQ<SearchNode> pq = new MinPQ<>();
    pq.insert(new SearchNode(initial, null));

    MinPQ<SearchNode> pqTwin = new MinPQ<>();
    pqTwin.insert(new SearchNode(initial.twin(), null));
    while (!pq.isEmpty() && !pqTwin.isEmpty()) {
      SearchNode node = pqTwin.delMin();
      if (node.board.isGoal()) {
        break;
      }
      node.addNeighborsTo(pqTwin);

      node = pq.delMin();
      if (node.board.isGoal()) {
        solution = new LinkedList<>();
        while (node != null) {
          solution.addFirst(node.board);
          node = node.parent;
        }
        break;
      }
      node.addNeighborsTo(pq);
    }
  }

  public boolean isSolvable() {
    return solution != null;
  }

  // min number of moves to solve initial board; -1 if unsolvable
  public int moves() {
    return solution == null ? -1 : solution.size() - 1;
  }

  // sequence of boards in a shortest solution; null if unsolvable
  public Iterable<Board> solution() {
    return solution;
  }

  public static void main(String[] args) {
    In in = new In(args[0]);
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

    // print solution to standard output
    if (!solver.isSolvable()) {
      StdOut.println("No solution possible");
    } else {
      StdOut.println("Minimum number of moves = " + solver.moves());
      for (Board board : solver.solution()) {
        StdOut.println(board);
      }
    }
  }

  private static class SearchNode implements Comparable<SearchNode> {
    private final SearchNode parent;
    private final Board board;
    private final int moves;
    private final int priority;

    SearchNode(Board board, SearchNode parent) {
      this.board = board;
      this.parent = parent;
      moves = parent  == null ? 0 : parent.moves + 1;
      priority = moves + board.manhattan();
    }

    void addNeighborsTo(MinPQ<SearchNode> pq) {
      Iterable<Board> neighbors = board.neighbors();
      for (Board neighbor : neighbors) {
        if (parent == null || !parent.board.equals(neighbor)) {
          pq.insert(new SearchNode(neighbor, this));
        }
      }
    }

    @Override
    public int compareTo(SearchNode node) {
      return priority - node.priority;
    }
  }
}
