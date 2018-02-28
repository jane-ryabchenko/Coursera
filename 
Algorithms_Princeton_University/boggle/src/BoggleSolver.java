import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BoggleSolver {
  // Initializes the data structure using the given array of strings as the dictionary.
  // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)

  private static class Node {
    char c;
    boolean w;
    Node[] children;

    Node(char c) {
      this.c = c;
      children = new Node[26];
    }

    Node addChild(char c) {
      if (children[c - 'A'] == null) {
        children[c - 'A'] = new Node(c);
      }
      return children[c - 'A'];
    }

    Node getChild(char c) {
      return children[c - 'A'];
    }

    private static void addWord(Node root, String w) {
      Node n = root;
      for (int i = 0; i < w.length(); i++) {
        char c = w.charAt(i);
        n = n.addChild(c);
        if (i == w.length() - 1) {
          n.w = true;
        } else if (c == 'Q' && w.charAt(i + 1) == 'U') {
          i++;
        }
      }
    }
  }

  private Node root;

  public BoggleSolver(String[] dictionary) {
    root = new Node(' ');
    for (String w : dictionary) {
      if (w.length() >= 3) {
        Node.addWord(root, w);
      }
    }
  }

  // Returns the set of all valid words in the given Boggle board, as an Iterable.
  public Iterable<String> getAllValidWords(BoggleBoard board) {
    Set<String> result = new HashSet<>();
    for (int i = 0; i < board.rows(); i++) {
      for (int j = 0; j < board.cols(); j++) {
        List<StringBuilder> words = search(board, i, j, root, new boolean[board.rows()][board.cols()]);
        if (words != null) {
          for (StringBuilder word : words) {
             result.add(word.reverse().toString());
          }
        }
      }
    }
    return result;
  }

  // Returns the score of the given word if it is in the dictionary, zero otherwise.
  // (You can assume the word contains only the uppercase letters A through Z.)
  public int scoreOf(String word) {
    if (word.length() <= 2) {
      return 0;
    }
    if (word.length() <= 4) {
      return 1;
    }
    if (word.length() == 5) {
      return 2;
    }
    if (word.length() == 6) {
      return 3;
    }
    if (word.length() == 7) {
      return 5;
    } else {
      return 11;
    }
  }

  private List<StringBuilder> search(BoggleBoard board, int i, int j, Node n, boolean[][] visited) {
    if (i < 0 || i == board.rows() || j < 0 || j == board.cols()) {
      return null;
    }

    if (visited[i][j]) {
      return null;
    }

    visited[i][j] = true;
    Node c = n.getChild(board.getLetter(i, j));
    if (c == null) {
      visited[i][j] = false;
      return null;
    }

    List<StringBuilder> words = null;
    if (c.w) {
      words = new ArrayList<>();
      words.add(new StringBuilder());
    }
    words = append(words, search(board, i - 1, j - 1, c, visited));
    words = append(words, search(board, i - 1, j, c, visited));
    words = append(words, search(board, i - 1, j + 1, c, visited));
    words = append(words, search(board, i, j + 1, c, visited));
    words = append(words, search(board, i + 1, j + 1, c, visited));
    words = append(words, search(board, i + 1, j, c, visited));
    words = append(words, search(board, i + 1, j - 1, c, visited));
    words = append(words, search(board, i, j - 1, c, visited));
    visited[i][j] = false;

    if (words != null) {
      for (StringBuilder word : words) {
        if (c.c == 'Q') {
          word.append('U');
        }
        word.append(c.c);
      }
    }

    return words;
  }

  private List<StringBuilder> append(List<StringBuilder> appendTo, List<StringBuilder> target) {
    if (appendTo == null) {
      return target;
    }
    if (target != null) {
      appendTo.addAll(target);
    }
    return appendTo;
  }

  public static void main(String[] args) {
    In in = new In("dictionary-algs4.txt");
    String[] dictionary = in.readAllStrings();
    BoggleSolver solver = new BoggleSolver(dictionary);
    BoggleBoard board = new BoggleBoard("board4x4.txt");
    int score = 0;
    for (String word : solver.getAllValidWords(board)) {
      StdOut.println(word);
      score += solver.scoreOf(word);
    }
    StdOut.println("Score = " + score);
  }

}
