import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;
import java.util.Comparator;

public class CircularSuffixArray {
  private final Integer[] suffixes;

  // circular suffix array of s
  public CircularSuffixArray(String s) {
    if (s == null) {
      throw new IllegalArgumentException();
    }
    suffixes = new Integer[s.length()];
    for (int i = 0; i < s.length(); i++) {
      suffixes[i] = i;
    }
    Arrays.sort(suffixes, new Comparator<Integer>() {
      @Override
      public int compare(Integer s1, Integer s2) {
        int length = length();
        for (int i = 0; i < length; i++) {
          int i1 = (i + s1) % length;
          int i2 = (i + s2) % length;
          int dif = s.charAt(i1) - s.charAt(i2);
          if (dif != 0) {
            return dif;
          }
        }
        return 0;
      }
    });
  }

  // length of s
  public int length() {
    return suffixes.length;
  }

  // returns index of ith sorted suffix
  public int index(int i) {
    if (i < 0 || i >= length()) {
      throw new IllegalArgumentException();
    }
    return suffixes[i];
  }

  // unit testing (required)
  public static void main(String[] args) {
    CircularSuffixArray array = new CircularSuffixArray("ABRACADABRA!");
    for (int i = 0; i < array.length(); i++) {
      StdOut.println(array.index(i));
    }
  }
}
