package graph;

import java.util.*;

/**
 * Graph class
 * @param <V> vertices type
 */
public class Graph<V> {
    // is graph directed
    private boolean directed;

    private Map<V, Set<V>> edges;

    public Graph(boolean directed) {
        this.directed = directed;
        edges = new HashMap<>();
    }

    // returns if graph is directed
    public boolean isDirected() {
        return directed;
    }

    // returns if graph is directed
    public Set<V> getVertices() {
        return new HashSet<>(edges.keySet());
    }

    // returns edges from given vertex
    public Set<V> getEdgesFrom(V vertex) {
        if (! ((vertex != null) && edges.containsKey(vertex))) {
            throw new IllegalArgumentException("vertex shouldn't be null and should be in the graph");
        }
        return new HashSet<>(edges.get(vertex));
    }

    // add vertex
    public void addVertex(V vertex) {
        edges.put(vertex, new HashSet<>());
    }

    // add edge
    public void addEdge(V from, V to) {
        // check arguments are correct
        checkGraphContainsVertices(from, to);
        if (from.equals(to)) {
            throw new IllegalArgumentException("vertices should be different");
        }
        edges.get(from).add(to);
        if (! directed) {
            edges.get(to).add(from);
        }
    }

    // returns shortest path
    public LinkedList<V> getPath(V from, V to) {
        // check arguments are correct
        checkGraphContainsVertices(from, to);
        LinkedList<V> result = new LinkedList<>();
        result.addFirst(to);
        if (from.equals(to)) {
            // if we are looking for path from one vertex to the same vertex
            return result;
        }
        // key is a vertex
        // value is previous vertex for key vertex in the path
        Map<V, V> paths = new HashMap<>();
        paths.put(from, null);
        Set<V> prev = new HashSet<>();
        prev.add(from);
        while (! prev.isEmpty()) {
            Set<V> next = new HashSet<>();
            for (V prevVertex : prev) {
                // for each vertex found at previous step
                for (V nextVertex : edges.get(prevVertex)) {
                    // check all edges
                    // if edge goes to vertex we found before - skip it now
                    if (! paths.containsKey(nextVertex)) {
                        // if edge goes to new vertex
                        if (nextVertex.equals(to)) {
                            // if we found final destination, add path to result list from end to beginning
                            for (V pathElement = prevVertex; pathElement != null; pathElement = paths.get(pathElement)) {
                                result.addFirst(pathElement);
                            }
                            return result;
                        }
                        // store new vertex and remember previous vertex for it
                        paths.put(nextVertex, prevVertex);
                        next.add(nextVertex);
                    }
                }
            }
            prev = next;
        }
        return null;
    }

    // check that graph contains vertices
    private void checkGraphContainsVertices(V v1, V v2) throws IllegalArgumentException {
        if (! ((v1 != null) && (v2 != null) && edges.containsKey(v1) && edges.containsKey(v2))) {
            throw new IllegalArgumentException("vertices shouldn't be null and graph should contain both vertices");
        }
    }
}
