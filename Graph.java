import java.util.*;

public class Graph {
  private int V;
  private List<List<Integer>> adj;

  public Graph(int V) {
    this.V = V;
    adj = new ArrayList<>(V);
    for (int i = 0; i < V; i++) {
      adj.add(new LinkedList<Integer>());
    }
  }

  public void addEdge(int src, int dest) {
    adj.get(src).add(dest);
  }

  private void topoLogicalSort(int node, Set<Integer> visited, Stack<Integer> s) {
    visited.add(node);
    for (int neighbor : adj.get(node)) {
      if (!visited.contains(neighbor)) {
        topoLogicalSort(neighbor, visited, s);
      }
    }
    s.push(node);
  }

  private void reverseGraph(List<List<Integer>> transpose) {
    for (int i = 0; i < V; i++) {
      for (int j : adj.get(i)) {
        transpose.get(j).add(i);
      }
    }
  }

  public void KosaRajuAlgo() {
    Stack<Integer> st = new Stack<>();
    Set<Integer> visited = new HashSet<>();
    for (int i = 0; i < V; i++) {
      if (!visited.contains(i)) {
        topoLogicalSort(i, visited, st);
      }
    }

    List<List<Integer>> transpose = new ArrayList<>(V);
    for (int i = 0; i < V; i++) {
      transpose.add(new LinkedList<>());
    }
    reverseGraph(transpose);

    visited.clear();
    while (!st.isEmpty()) {
      int curr = st.pop();
      if (!visited.contains(curr)) {
        dfs(transpose, curr, visited);
        System.out.println();
      }
    }
  }

  private void dfs(List<List<Integer>> transpose, int node, Set<Integer> visited) {
    visited.add(node);
    System.out.print(node + " ");
    for (int neighbor : transpose.get(node)) {
      if (!visited.contains(neighbor)) {
        dfs(transpose, neighbor, visited);
      }
    }
  }

  public void bfs(int startNode) {
    boolean[] visited = new boolean[V];
    Queue<Integer> queue = new LinkedList<>();

    visited[startNode] = true;
    queue.add(startNode);

    while (!queue.isEmpty()) {
      int node = queue.poll();
      System.out.print(node + " ");

      for (int neighbor : adj.get(node)) {
        if (!visited[neighbor]) {
          visited[neighbor] = true;
          queue.add(neighbor);
        }
      }
    }
    System.out.println();
  }

  public boolean isCyclic() {
    boolean[] visited = new boolean[V];
    boolean[] recStack = new boolean[V];

    for (int i = 0; i < V; i++) {
      if (!visited[i]) {
        if (isCyclicUtil(i, visited, recStack)) {
          return true;
        }
      }
    }
    return false;
  }

  private boolean isCyclicUtil(int node, boolean[] visited, boolean[] recStack) {
    visited[node] = true;
    recStack[node] = true;

    for (int neighbor : adj.get(node)) {
      if (!visited[neighbor] && isCyclicUtil(neighbor, visited, recStack)) {
        return true;
      } else if (recStack[neighbor]) {
        return true;
      }
    }

    recStack[node] = false;
    return false;
  }

  private boolean safeCheckDFS(int src, boolean[] vis, boolean[] pathVis, boolean[] check) {
    vis[src] = true;
    pathVis[src] = true;
    check[src] = false;

    for (int i : adj.get(src)) {
      if (!vis[i]) {
        if (safeCheckDFS(i, vis, pathVis, check)) {
          return true;
        }
      } else if (pathVis[i]) {
        return true;
      }
    }

    check[src] = true;
    pathVis[src] = false;
    return false;
  }

  public List<Integer> eventualSafeNodes() {
    boolean[] visited = new boolean[V];
    boolean[] pathVis = new boolean[V];
    boolean[] check = new boolean[V];

    for (int i = 0; i < V; i++) {
      if (!visited[i]) {
        safeCheckDFS(i, visited, pathVis, check);
      }
    }

    List<Integer> res = new ArrayList<>();
    for (int i = 0; i < V; i++) {
      if (check[i]) {
        res.add(i);
      }
    }

    return res;
  }

  public boolean isBipartite() {
    int[] color = new int[V];
    Arrays.fill(color, -1);

    for (int i = 0; i < V; i++) {
      if (color[i] == -1) {
        if (!isBipartiteUtil(i, color)) {
          return false;
        }
      }
    }

    return true;
  }

  public boolean isBipartiteUtil(int node, int[] color) {
    Queue<Integer> queue = new LinkedList<>();
    queue.add(node);
    color[node] = 0;

    while (!queue.isEmpty()) {
      int current = queue.poll();
      for (int neighbor : adj.get(current)) {
        if (color[neighbor] == -1) {
          color[neighbor] = 1 - color[current];
          queue.add(neighbor);
        } else if (color[neighbor] == color[current]) {
          return false;
        }
      }
    }

    return true;
  }

  public void dfsTraversal(int startNode) {
    boolean[] visited = new boolean[V];
    dfsUtil(startNode, visited);
    System.out.println();
  }

  private void dfsUtil(int node, boolean[] visited) {
    visited[node] = true;
    System.out.print(node + " ");
    for (int neighbor : adj.get(node)) {
      if (!visited[neighbor]) {
        dfsUtil(neighbor, visited);
      }
    }
  }

  public int countConnectedComponents() {
    boolean[] visited = new boolean[V];
    int count = 0;

    for (int i = 0; i < V; i++) {
      if (!visited[i]) {
        dfsUtil(i, visited);
        count++;
      }
    }

    return count;
  }
}
