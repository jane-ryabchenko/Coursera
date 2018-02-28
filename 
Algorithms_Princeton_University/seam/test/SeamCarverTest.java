import edu.princeton.cs.algs4.Picture;
import org.junit.Test;


import static com.google.common.truth.Truth.assertThat;

public class SeamCarverTest {
  @Test
  public void test_4x6() {
    SeamCarver seamCarver = new SeamCarver(new Picture("4x6.png"));
    assertThat(seamCarver.findHorizontalSeam()).asList().containsExactly(1, 2, 1, 0).inOrder();
    assertThat(seamCarver.findVerticalSeam()).asList().containsExactly(1, 2, 1, 1, 2, 1).inOrder();
  }

  @Test
  public void test_6x5() {
    SeamCarver seamCarver = new SeamCarver(new Picture("6x5.png"));
    int[] hSeam = seamCarver.findHorizontalSeam();
    assertThat(hSeam).asList().containsExactly(1, 2, 1, 2, 1, 0).inOrder();
    int[] vSeam = seamCarver.findVerticalSeam();
    assertThat(vSeam).asList().containsExactly(3, 4, 3, 2, 1).inOrder();
  }

  @Test
  public void test_6x5_remove() {
    SeamCarver seamCarver = new SeamCarver(new Picture("6x5.png"));
    int[] seam = seamCarver.findHorizontalSeam();
    seamCarver.removeHorizontalSeam(seam);
    seam = seamCarver.findVerticalSeam();
    seamCarver.removeVerticalSeam(seam);
  }
}
