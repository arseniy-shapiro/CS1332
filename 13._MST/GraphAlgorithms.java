import java.util.LinkedHashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Your implementation of Prim's algorithm.
 */
public class GraphAlgorithms {

    /**
     * Runs Prim's algorithm on the given graph and returns the Minimum
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * You should NOT allow self-loops or parallel edges in the MST.
     *
     * You may import/use java.util.PriorityQueue, java.util.Set, and any
     * class that implements the aforementioned interface.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * The only instance of java.util.Map that you may use is the adjacency
     * list from graph. DO NOT create new instances of Map for this method
     * (storing the adjacency list in a variable is fine).
     *
     * You may assume that the passed in start vertex and graph will not be null.
     * You may assume that the start vertex exists in the graph.
     *
     * @param <T>   The generic typing of the data.
     * @param start The vertex to begin Prims on.
     * @param graph The graph we are applying Prims to.
     * @return The MST of the graph or null if there is no valid MST.
     */
    public static <T> Set<Edge<T>> prims(Vertex<T> start, Graph<T> graph) {
        LinkedHashSet<Edge<T>> edges = getAllEdgesForVertex(start, graph);
        Set<Edge<T>> result = new LinkedHashSet<>();
        Set<Vertex<T>> visitedSet = new LinkedHashSet<>();
        visitedSet.add(start);
        PriorityQueue<Edge<T>> prQueue = new PriorityQueue<>();
        prQueue.addAll(edges);

        Edge<T> edge;
        Edge<T> revEdge;
        Vertex<T> tarVertex;
        LinkedHashSet<Edge<T>> unseenEdges;
        while (!prQueue.isEmpty() && visitedSet.size() != graph.getVertices().size()) {
            edge = prQueue.poll();
            tarVertex = edge.getV();
            if (!visitedSet.contains(tarVertex)) {
                revEdge = new Edge<>(edge.getV(), edge.getU(), edge.getWeight());
                result.add(edge);
                result.add(revEdge);
                visitedSet.add(tarVertex);
                unseenEdges = getAllEdgesForVertex(tarVertex, graph);
                for (Edge<T> newEdge: unseenEdges) {
                    if (!visitedSet.contains(newEdge.getV())) {
                        prQueue.add(newEdge);
                    }
                }
            }
        }
        if (visitedSet.size() != graph.getVertices().size()) {
            return null;
        }
        return result;
    }

    private static <T> LinkedHashSet<Edge<T>> getAllEdgesForVertex(Vertex<T> vertex, Graph<T> graph) {
        List<VertexDistance<T>> adjacency = graph.getAdjList().get(vertex);
        LinkedHashSet<Edge<T>> edges = new LinkedHashSet<>();
        Edge<T> edge;
        for(VertexDistance<T> n: adjacency) {
            edge = new Edge<T>(vertex, n.getVertex(), n.getDistance());
            edges.add(edge);
        }
        return edges;
    }
}
