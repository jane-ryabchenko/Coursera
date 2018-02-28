import org.junit.Test;


import static com.google.common.truth.Truth.assertThat;

public class CircularSuffixArrayTest {
  @Test
  public void test() {
    CircularSuffixArray array = new CircularSuffixArray("ABRACADABRA!");
    assertThat(array.length()).isEqualTo(12);

    assertThat(array.index(0)).isEqualTo(11);
    assertThat(array.index(1)).isEqualTo(10);
    assertThat(array.index(2)).isEqualTo(7);
    assertThat(array.index(3)).isEqualTo(0);
    assertThat(array.index(4)).isEqualTo(3);
    assertThat(array.index(5)).isEqualTo(5);
    assertThat(array.index(6)).isEqualTo(8);
    assertThat(array.index(7)).isEqualTo(1);
    assertThat(array.index(8)).isEqualTo(4);
    assertThat(array.index(9)).isEqualTo(6);
    assertThat(array.index(10)).isEqualTo(9);
    assertThat(array.index(11)).isEqualTo(2);
  }
}
