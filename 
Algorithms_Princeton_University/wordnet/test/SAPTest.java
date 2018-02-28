import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class SAPTest {
  @Test
  public void test5_4() {
    verifySAP("digraph1.txt", 5, 4, 2, 1);
  }

  @Test
  public void test5_5() {
    verifySAP("digraph1.txt", 5, 5, 0, 5);
  }

  @Test
  public void test9_12() {
    verifySAP("digraph1.txt", 9, 12, 3, 5);
  }

  @Test
  public void test7_2() {
    verifySAP("digraph1.txt", 7, 2, 4, 0);
  }

  @Test
  public void test1_6() {
    verifySAP("digraph1.txt", 1, 6, -1, -1);
  }

  @Test
  public void test71732_6890() {
    verifySAP("digraph-wordnet.txt", 71732, 6890, 19, 60600);
  }

  @Test
  public void test6_6() {
    verifySAP("digraph-wordnet.txt", 6, 6, 0, 6);
  }

  @Test
  public void test22_8() {
    verifySAP("digraph-wordnet.txt", 22, 8, 1, 6);
  }

  @Test
  public void test10_10() {
    verifySAP("digraph-wordnet.txt", 10, 10, 0, 10);
  }

  @Test
  public void test4_2() {
    verifySAP("digraph-wordnet.txt", 4, 2, 1, 2);
  }

  @Test
  public void test0_4() {
    verifySAP("digraph9.txt", 0, 4, 3, 2);
  }

  @Test
  public void test7_10() {
    verifySAP("digraph3.txt", 7, 10, 3, 10);
  }

  @Test
  public void test13_7() {
    verifySAP("digraph3.txt", 13, 7, 6, 8);
  }

  private void verifySAP(String file, int v, int w, int length, int ancestor) {
    In in = new In(file);
    Digraph graph = new Digraph(in);
    SAP sap = new SAP(graph);
    assertThat(sap.length(v, w)).isEqualTo(length);
    assertThat(sap.ancestor(v, w)).isEqualTo(ancestor);
  }
}