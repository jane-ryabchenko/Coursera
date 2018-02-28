import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BurrowsWheeler {
  // apply Burrows-Wheeler transform, reading from standard input and writing to standard output
  public static void transform() {
    String s = BinaryStdIn.readString();
    CircularSuffixArray array = new CircularSuffixArray(s);
    for (int i = 0; i < array.length(); i++) {
      int suffix = array.index(i);
      if (suffix == 0) {
        BinaryStdOut.write(i);
        break;
      }
    }
    for (int i = 0; i < array.length(); i++) {
      byte out = (byte) s.charAt((s.length() - 1 + array.index(i)) % s.length());
      BinaryStdOut.write(out);
    }
    BinaryStdIn.close();
    BinaryStdOut.close();
  }

  // apply Burrows-Wheeler inverse transform, reading from standard input and writing to standard output
  public static void inverseTransform() {
    int first = BinaryStdIn.readInt();
    Map<Character, List<Integer>> indexes = new HashMap<>();
    int count = 0;
    while (!BinaryStdIn.isEmpty()) {
      byte b = BinaryStdIn.readByte();
      char c = (char) b;
      List<Integer> idx = indexes.get(c);
      if (idx == null) {
        idx = new ArrayList<>();
        indexes.put(c, idx);
      }
      idx.add(count++);
    }

    char[] f = new char[count];
    int[] next = new int[count];
    int j = 0;
    byte b = 0;
    do {
      List<Integer> idx = indexes.get((char) b);
      if (idx != null) {
        for (int ind : idx) {
          next[j] = ind;
          f[j] = (char) b;
          j++;
        }
      }
      b++;
    } while (b != 0);

    j = first;
    for (int i = 0; i < count; i++) {
      BinaryStdOut.write((byte) f[j]);
      j = next[j];
    }

    BinaryStdIn.close();
    BinaryStdOut.close();
  }

  // if args[0] is '-', apply Burrows-Wheeler transform
  // if args[0] is '+', apply Burrows-Wheeler inverse transform
  public static void main(String[] args) {
    if (args == null || args.length == 0) {
      throw new IllegalArgumentException();
    }
    if ("-".equals(args[0])) {
      transform();
    } else if ("+".equals(args[0])) {
      inverseTransform();
    } else {
      throw new IllegalArgumentException();
    }
  }
}
