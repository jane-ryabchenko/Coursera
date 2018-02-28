import edu.princeton.cs.algs4.In;
import org.junit.Before;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class OutcastTest {
  private Outcast outcast;

  @Before
  public void before() {
    WordNet wordNet = new WordNet("synsets.txt", "hypernyms.txt");
    outcast = new Outcast(wordNet);
  }

  @Test
  public void outcastTest5() {
    assertThat(findOutcast("outcast5.txt")).isEqualTo("table");
  }

  @Test
  public void outcastTest8() {
    assertThat(findOutcast("outcast8.txt")).isEqualTo("bed");
  }

  @Test
  public void outcastTest11() {
    assertThat(findOutcast("outcast11.txt")).isEqualTo("potato");
  }

  private String findOutcast(String outcastFile) {
    In in = new In(outcastFile);
    String[] nouns = in.readAllStrings();
    return outcast.outcast(nouns);
  }
}
