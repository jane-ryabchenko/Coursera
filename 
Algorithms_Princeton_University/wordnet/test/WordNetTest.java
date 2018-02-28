import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class WordNetTest {
  @Test
  public void testInput() {
    WordNet wordNet = new WordNet("synsets.txt", "hypernyms.txt");
    assertThat(wordNet.isNoun("24-karat_gold")).isTrue();
    assertThat(wordNet.isNoun("pure_gold")).isTrue();
    assertThat(wordNet.isNoun("silumin")).isFalse();
    assertThat(wordNet.sap("22-karat_gold", "18-karat_gold")).isEqualTo("alloy metal");
    assertThat(wordNet.distance("22-karat_gold", "18-karat_gold")).isEqualTo(2);
    assertThat(wordNet.distance("22-karat_gold", "alloy")).isEqualTo(1);
    assertThat(wordNet.distance("22-karat_gold", "22-karat_gold")).isEqualTo(0);
    assertThat(wordNet.distance("hero", "Sphaerobolaceae")).isEqualTo(12);
  }

  @Test
  public void test8() {
    WordNet wordNet = new WordNet("synsets8.txt", "hypernyms8ManyAncestors.txt");
    assertThat(wordNet.distance("a", "b")).isEqualTo(1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInputCycle() {
    new WordNet("synsets3.txt", "hypernyms3InvalidCycle.txt");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInputCycle6() {
    new WordNet("synsets6.txt", "hypernyms6InvalidCycle+Path.txt");
  }
}
