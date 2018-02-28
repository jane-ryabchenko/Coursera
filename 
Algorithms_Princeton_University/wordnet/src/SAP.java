import edu.princeton.cs.algs4.Digraph;

import java.util.LinkedList;
import java.util.Queue;

public class SAP {
  private Digraph graph;

  // constructor takes a digraph (not necessarily a DAG)
  public SAP(Digraph g) {
    if (g == null) {
      throw new IllegalArgumentException();
    }
    graph = new Digraph(g);
  }

  // length of shortest ancestral path between v and w; -1 if no such path
  public int length(int v, int w) {
    int[] c = findClosestAncestor(v, w);
    return c == null ? -1 : c[1];
  }

  // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
  public int ancestor(int v, int w) {
    int[] c = findClosestAncestor(v, w);
    return c == null ? -1 : c[0];
  }

  // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
  public int length(Iterable<Integer> v, Iterable<Integer> w) {
    int[] c = findClosestAncestor(v, w);
    return c == null ? -1 : c[1];
  }

  // a common ancestor that participates in shortest ancestral path; -1 if no such path
  public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
    int[] c = findClosestAncestor(v, w);
    return c == null ? -1 : c[0];
  }

  private int[] findClosestAncestor(int v, int w) {
    if (v < 0 || v >= graph.V() || w < 0 || w >= graph.V()) {
      throw new IllegalArgumentException();
    }
    Queue<Integer> queueV = new LinkedList<>();
    Queue<Integer> queueW = new LinkedList<>();
    queueV.add(v);
    queueW.add(w);
    return findClosestAncestor(queueV, queueW);
  }

  private int[] findClosestAncestor(Iterable<Integer> listV, Iterable<Integer> listW) {
    if (listV == null || listW == null) {
      throw new IllegalArgumentException();
    }
    Queue<Integer> queueV = new LinkedList<>();
    Queue<Integer> queueW = new LinkedList<>();
    for (Integer v : listV) {
      if (v < 0 || v >= graph.V()) {
        throw new IllegalArgumentException();
      }
      queueV.offer(v);
    }
    for (Integer w : listW) {
      if (w < 0 || w >= graph.V()) {
        throw new IllegalArgumentException();
      }
      queueW.offer(w);
    }
    return findClosestAncestor(queueV, queueW);
  }

  private int[] findClosestAncestor(Queue<Integer> queueV, Queue<Integer> queueW) {
    if (queueV.isEmpty() || queueW.isEmpty()) {
      return null;
    }
    Integer[] levelsV = new Integer[graph.V()];
    Integer[] levelsW = new Integer[graph.V()];
    int levelV = 0;
    int levelW = 0;
    int[] min = null;
    while (!queueV.isEmpty() || !queueW.isEmpty()) {
      int[] c = findAncestor(queueV, levelV, levelsV, levelsW);
      if (c != null && (min == null || min[1] > c[1])) {
        min = c;
      }
      c = findAncestor(queueW, levelW, levelsW, levelsV);
      if (c != null && (min == null || min[1] > c[1])) {
        min = c;
      }
      levelV++;
      levelW++;
    }
    return min;
  }

  private int[] findAncestor(Queue<Integer> queue, int level, Integer[] levels, Integer[] visited) {
    int size = queue.size();
    int[] c = null;
    while (size-- > 0) {
      int v = queue.poll();
      levels[v] = level;
      if (visited[v] != null && (c == null || c[1] > levels[v] + visited[v])) {
        c = new int[] {v, levels[v] + visited[v]};
      }
      for (int n : graph.adj(v)) {
        if (levels[n] != null) {
          continue;
        }
        levels[n] = level + 1;
        if (visited[n] != null && (c == null || c[1] > levels[n] + visited[n])) {
          c = new int[] {n, levels[n] + visited[n]};
        }
        queue.add(n);
      }
    }
    return c;
  }
}
