import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.DirectedDFS;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WordNet {
  private List<String> synsets = new ArrayList<>();
  private Map<String, List<Integer>> words = new HashMap<>();
  private SAP sap;

  // constructor takes the name of the two input files
  public WordNet(String synsetsFile, String hypernymsFile) {
    if (synsetsFile == null || hypernymsFile == null) {
      throw new java.lang.IllegalArgumentException();
    }

    In in = new In(synsetsFile);
    String line;
    Set<Integer> sources = new HashSet<>();
    while ((line = in.readLine()) != null) {
      String syn = line.split(",")[1];
      for (String w : syn.split(" ")) {
        List<Integer> vertices = words.get(w);
        if (vertices == null) {
          vertices = new ArrayList<>();
          words.put(w, vertices);
        }
        vertices.add(synsets.size());
      }
      sources.add(synsets.size());
      synsets.add(syn);
    }
    in.close();

    Digraph graph = new Digraph(synsets.size());

    in = new In(hypernymsFile);
    while ((line = in.readLine()) != null) {
      String[] vertices = line.split(",");
      int v = Integer.parseInt(vertices[0]);
      if (vertices.length > 1) {
        sources.remove(v);
      }
      for (int i = 1; i < vertices.length; i++) {
        int w = Integer.parseInt(vertices[i]);
        graph.addEdge(v, w);
      }
    }
    in.close();

    DirectedCycle cycle = new DirectedCycle(graph);
    if (cycle.hasCycle()) {
      throw new IllegalArgumentException();
    }

    Integer root = null;
    for (int source : sources) {
      DirectedDFS directedDFS = new DirectedDFS(graph.reverse(), source);
      if (directedDFS.count() == graph.V()) {
        if (root == null) {
          root = source;
        } else {
          throw new IllegalArgumentException();
        }
      }
    }
    if (root == null) {
      throw new IllegalArgumentException();
    }

    sap = new SAP(graph);
  }

  // returns all WordNet nouns
  public Iterable<String> nouns() {
    return words.keySet();
  }

  // is the word a WordNet noun?
  public boolean isNoun(String word) {
    if (word == null) {
      throw new IllegalArgumentException();
    }
    return words.containsKey(word);
  }

  // distance between nounA and nounB (defined below)
  public int distance(String nounA, String nounB) {
    return sap.length(words.get(nounA), words.get(nounB));
  }

  // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
  // in a shortest ancestral path (defined below)
  public String sap(String nounA, String nounB) {
    int ancestor = sap.ancestor(words.get(nounA), words.get(nounB));
    return synsets.get(ancestor);
  }
}
