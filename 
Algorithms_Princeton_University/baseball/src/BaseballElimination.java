import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BaseballElimination {

  private int[] w;

  private int[] l;

  private int[] r;

  private int[][] g;

  private String[] nameList;

  private int numTeams;

  private Map<String, Integer> nameNumber;

  // create a baseball division from given filename in format specified below
  public BaseballElimination(String filename)  {
    In in = new In(filename);

     // Scanner input = new Scanner(new File(filename));
      numTeams = in.readInt();
      w = new int[numTeams];
      l = new int[numTeams];
      r = new int[numTeams];
      g = new int[numTeams][numTeams];
      nameList = new String[numTeams];
      nameNumber = new HashMap<>();
      for (int i = 0; i < numTeams; i++) {
        String name = in.readString();
        nameNumber.put(name, i);
        w[i] = in.readInt();
        l[i] = in.readInt();
        r[i] = in.readInt();
        for (int j = 0; j < numTeams; j++) {
          g[i][j] = in.readInt();
        }
        nameList[i] = name;
      }
      in.close();

  }

  // number of teams
  public int numberOfTeams() {
    return numTeams;
  }

  // all teams
  public Iterable<String> teams() {
    return Arrays.asList(nameList);
  }

  // number of wins for given team
  public int wins(String team) {
    if (!nameNumber.containsKey(team)) {
      throw new java.lang.IllegalArgumentException();
    }
    return w[nameNumber.get(team)];
  }

  // number of losses for given team
  public int losses(String team) {
    if (!nameNumber.containsKey(team)) {
      throw new java.lang.IllegalArgumentException();
    }
    return l[nameNumber.get(team)];
  }

  // number of remaining games for given team
  public int remaining(String team) {
    if (!nameNumber.containsKey(team)) {
      throw new java.lang.IllegalArgumentException();
    }
    return r[nameNumber.get(team)];
  }

  // number of remaining games between team1 and team2
  public int against(String team1, String team2) {
    if (!nameNumber.containsKey(team1)) {
      throw new java.lang.IllegalArgumentException();
    }
    if (!nameNumber.containsKey(team2)) {
      throw new java.lang.IllegalArgumentException();
    }
    return g[nameNumber.get(team1)][nameNumber.get(team2)];
  }

  // is given team eliminated?
  public boolean isEliminated(String team) {
    if (!nameNumber.containsKey(team)) {
      throw new java.lang.IllegalArgumentException();
    }
    int x = nameNumber.get(team);
    int sum = w[x] + r[x];

    for (int i = 0; i < numTeams; i++) {
      if (sum - w[i] < 0) {
        return true;
      }
    }

    int numGames = numTeams * (numTeams - 1) / 2;
    int nodeID = 0;

    FlowNetwork fn = new FlowNetwork(numGames + numTeams + 2);

    int s = numGames + numTeams;
    int t = s + 1;

    for (int i = 0; i < numTeams; i++) {
      for (int j = i + 1; j < numTeams; j++) {
        //if (i != j) {
          fn.addEdge(new FlowEdge(s, nodeID, g[i][j]));
          fn.addEdge(new FlowEdge(nodeID, numGames + i,
              Integer.MAX_VALUE));
          fn.addEdge(new FlowEdge(nodeID, numGames + j,
              Integer.MAX_VALUE));
          nodeID ++;
        //}
      }
      fn.addEdge(new FlowEdge(numGames + i, t, Math.max(0, w[x] + r[x] - w[i])));
    }

    FordFulkerson fordFulkerson = new FordFulkerson(fn, s, t);
    //System.out.println("maxflow " + fordFulkerson.value());

    for (FlowEdge e : fn.adj(s)) {
      if (e.flow() != e.capacity())
        return true;
    }
    return false;

  }

  // subset R of teams that eliminates given team; null if not eliminated
  public Iterable<String> certificateOfElimination(String team) {
    if (!nameNumber.containsKey(team)) {
      throw new java.lang.IllegalArgumentException();
    }
    int x = nameNumber.get(team);
    int sum = w[x] + r[x];
    List<String> teamList = new ArrayList<String>();
    for (int i = 0; i < numTeams; i++) {
      if (sum - w[i] < 0) {
        teamList.add(nameList[i]);
      }
    }
    if (teamList.size() > 0) {
      return teamList;
    }

    int numGames = numTeams * (numTeams - 1) / 2;
    int nodeID = 0;

    FlowNetwork fn = new FlowNetwork(numGames + numTeams + 2);

    int s = numGames + numTeams;
    int t = s + 1;

    for (int i = 0; i < numTeams; i++) {
      for (int j = i + 1; j < numTeams; j++) {
        if (i != j) {
          fn.addEdge(new FlowEdge(s, nodeID, g[i][j]));
          fn.addEdge(new FlowEdge(nodeID, numGames + i,
              Integer.MAX_VALUE));
          fn.addEdge(new FlowEdge(nodeID, numGames + j,
              Integer.MAX_VALUE));
          nodeID ++;
        }
      }
      fn.addEdge(new FlowEdge(numGames + i, t, Math.max(0, w[x] + r[x] - w[i])));
    }

    FordFulkerson fordFulkerson = new FordFulkerson(fn, s, t);
    boolean f = false;
    for (FlowEdge e : fn.adj(s)) {
      if (e.flow() != e.capacity()) {
        f = true;
        break;
      }
    }
    if (f == false) {
      return new ArrayList<String>();
    }

    List<Integer> nodeList = bfs(fn, s);
    List<String> list = new ArrayList<String>();
    for (Integer v : nodeList) {
      if (fordFulkerson.inCut(v) && v >= numGames) {
        list.add(this.nameList[v - numGames]);
      }
    }
    return list;
  }

  private List<Integer> bfs(FlowNetwork graph, int node) {
    Deque<Integer> deque = new LinkedList<>();
    boolean[] visited = new boolean[graph.V()];
    deque.offer(node);
    visited[node] = true;
    List<Integer> nodeList = new ArrayList<Integer>();
    while (!deque.isEmpty()) {
      int vert = deque.poll();
      int t;
      for (FlowEdge e : graph.adj(vert)) {
        if (e.from() == vert) {
          t = e.to();
        }
        else {
          t = e.from();
        }
        if (e.residualCapacityTo(t) > 0) {
          if (!visited[t]) {
            deque.offer(t);
            visited[t] = true;
            nodeList.add(t);
          }
        }
      }
    }
    return nodeList;
  }

  public static void main(String[] args) {
    BaseballElimination division = new BaseballElimination("teams5.txt");
    for (String team : division.teams()) {
      if (division.isEliminated(team)) {
        StdOut.print(team + " is eliminated by the subset R = { ");
        for (String t : division.certificateOfElimination(team)) {
          StdOut.print(t + " ");
        }
        StdOut.println("}");
      }
      else {
        StdOut.println(team + " is not eliminated");
      }
    }
  }
}
