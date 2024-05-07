import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Your implementation of various graph traversal algorithms.
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you should use is the adjacency
     * list from graph. DO NOT create new instances of Map for BFS
     * (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * You may assume that the passed in start vertex and graph will not be null.
     * You may assume that the start vertex exists in the graph.
     *
     * @param <T>   The generic typing of the data.
     * @param start The vertex to begin the bfs on.
     * @param graph The graph to search through.
     * @return List of vertices in visited order.
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        Set<Vertex<T>> visitedSet = new LinkedHashSet<>();
        Queue<Vertex<T>> vertQueue = new LinkedList<>();
        visitedSet.add(start);
        vertQueue.add(start);
        Vertex<T> currVertex;
        List<VertexDistance<T>> adjacentV;
        while (!vertQueue.isEmpty()) {
            currVertex = vertQueue.remove();
            adjacentV = graph.getAdjList().get(currVertex);
            for (VertexDistance<T> vertexD: adjacentV) {
                currVertex = vertexD.getVertex();
                if (!visitedSet.contains(currVertex)) {
                    visitedSet.add(currVertex);
                    vertQueue.add(currVertex);
                }
            }
        }
        return visitedSet.stream().collect(Collectors.toList());
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * NOTE: This method should be implemented recursively. You may need to
     * create a helper method.
     *
     * You may import/use java.util.Set, java.util.List, and any classes that
     * implement the aforementioned interfaces, as long as they are efficient.
     *
     * The only instance of java.util.Map that you may use is the adjacency list
     * from graph. DO NOT create new instances of Map for DFS
     * (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * You may assume that the passed in start vertex and graph will not be null.
     * You may assume that the start vertex exists in the graph.
     *
     * @param <T>   The generic typing of the data.
     * @param start The vertex to begin the dfs on.
     * @param graph The graph to search through.
     * @return List of vertices in visited order.
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        Set<Vertex<T>> visitedSet = new LinkedHashSet<>();
        recursiveDFS(start, graph, visitedSet);
        return visitedSet.stream().collect(Collectors.toList());
    }

    private static <T> void recursiveDFS(Vertex<T> start, Graph<T> graph, Set<Vertex<T>> visitedSet) {
        visitedSet.add(start);
        List<VertexDistance<T>> adjacentV = graph.getAdjList().get(start);
        Vertex<T> currV;
        for (VertexDistance<T> vertexD: adjacentV) {
            currV = vertexD.getVertex();
            if (!visitedSet.contains(currV)) {
                recursiveDFS(currV, graph, visitedSet);
            }
        }
    }
}
