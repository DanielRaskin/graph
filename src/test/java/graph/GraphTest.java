package graph;

import org.junit.Test;

import java.util.LinkedList;
import java.util.Set;

/**
 * Tests
 */
public class GraphTest {
    // check getVertices() works correct, vertices are always 1, 2, ... n
    private void checkVertices(Set<Integer> vertices, int n) {
        assert vertices.size() == n;
        assert vertices.stream().allMatch(i -> (i >= 1) && (i <= n));
    }

    // check path is correct, correct path in all cases is always like 1, 2, ... n
    private void checkPath(LinkedList<Integer> path, int n) {
        assert path.size() == n;
        int i = 1;
        for (Integer p : path) {
            assert p == i;
            i++;
        }
    }

    @Test
    public void testDirectedGraphPath() {
        // create directed graph
        Graph<Integer> graph = new Graph<>(true);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        assert graph.isDirected();

        // check vertices
        checkVertices(graph.getVertices(), 3);

        // check there is only one edge from vertex 2
        Set<Integer> vertices = graph.getEdgesFrom(2);
        assert (vertices.size() == 1) && vertices.contains(3);

        // find simple path
        checkPath(graph.getPath(1, 3), 3);
    }

    @Test
    public void testUndirectedGraphPath() {
        // create undirected graph
        Graph<Integer> graph = new Graph<>(false);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addVertex(5);
        graph.addVertex(6);
        graph.addVertex(7);
        graph.addVertex(8);
        graph.addVertex(9);
        graph.addVertex(10);
        graph.addVertex(11);
        graph.addVertex(12);
        graph.addEdge(2, 1);
        graph.addEdge(3, 2);
        graph.addEdge(4, 3);
        graph.addEdge(4, 5);
        graph.addEdge(3, 6);
        graph.addEdge(6, 7);
        graph.addEdge(7, 8);
        graph.addEdge(8, 9);
        graph.addEdge(9, 10);
        graph.addEdge(10, 11);
        graph.addEdge(11, 5);
        graph.addEdge(11, 12);
        graph.addEdge(7, 12);
        graph.addEdge(8, 12);
        assert !graph.isDirected();

        // check vertices
        checkVertices(graph.getVertices(), 12);

        // check edges from 11
        Set<Integer> vertices = graph.getEdgesFrom(11);
        assert (vertices.size() == 3) && vertices.contains(5) && vertices.contains(10) && vertices.contains(12);

        // find path
        checkPath(graph.getPath(1, 5), 5);
    }

    @Test
    public void testNoPath() {
        Graph<Integer> graph = new Graph<>(true);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addVertex(5);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(5, 4);
        graph.addEdge(4, 2);
        graph.addEdge(4, 1);
        graph.addEdge(1, 4);

        // where is no path from 1 to 5
        assert graph.getPath(1, 5) == null;
    }

    @Test
    public void testOnePointPath() {
        Graph<Integer> graph = new Graph<>(true);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        // find path from 1 to 1
        checkPath(graph.getPath(1, 1), 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEdgeToSameVertex() {
        Graph<Integer> graph = new Graph<>(true);
        graph.addVertex(1);
        // add incorrect edge (to same vertex), exception should be thrown
        graph.addEdge(1, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEdgeToUnknownVertex() {
        Graph<Integer> graph = new Graph<>(true);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        // add incorrect edge (to unknown vertex), exception should be thrown
        graph.addEdge(1, 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidPathRequest() {
        Graph<Integer> graph = new Graph<>(true);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        // invalid getPath() request (to unknown vertex)
        graph.getPath(1, 5);
    }
}
