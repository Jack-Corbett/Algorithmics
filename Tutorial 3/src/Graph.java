import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * A class to represent a graph of vertices and edges
 */
public class Graph {

    private List<Vertex> vertices = new ArrayList<>();
    private List<Edge> edges = new ArrayList<>();
    private static Random rand = new Random();

    public static void main(String[] args) {
        Graph graph = new Graph();
        graph.randomGraph(30, 0.8);
        for (Edge edge : graph.edges) {
            System.out.println(edge.toString());
        }
        graph.prims().displayGraph("Prims");
        graph.kruskals().displayGraph("Kruskals");
        graph.displayGraph("Standard");
    }

    /**
     * Uses GraphDisplay to represent the graph visually.
     * @param graphName The name displayed in the JFrame bar.
     */
    private void displayGraph(String graphName) {
        GraphDisplay gd = new GraphDisplay();
        gd.showInWindow(400, 400, graphName);

        // Add nodes to display
        for (Vertex vertex : vertices) {
            gd.addNode(vertex.name, vertex.position.x, vertex.position.y);
        }

        // Add edges to display
        for (Edge edge : edges) {
            gd.addEdge(edge.source.name, edge.destination.name, Color.black);
        }
    }

    /**
     * An implementation of Prims algorithm using a priority queue.
     * @return A new graph object containing the minimum spanning tree.
     */
    private Graph prims() {
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>();
        boolean[] visited = new boolean[vertices.size()];
        visited[0] = true;
        double totalWeight = 0;
        Graph mst = new Graph();
        mst.vertices = this.vertices;

        // Add every edge connected to the root to the queue
        priorityQueue.addAll(vertices.get(0).neighbours);

        // When the queue is empty we know every accessible node has been reached
        while (!priorityQueue.isEmpty()) {
            // Fetch the edge with the smallest weight by taking the first element of the queue
            Edge edge = priorityQueue.poll();

            /* If we have already visited the destination vertex, check the next edge in the queue
            as we don't need to add it to the MST */
            if (visited[edge.destination.name]) continue;

            // Mark the destination vertex as visited
            visited[edge.destination.name] = true;

            // Add the weight of the chosen edge to the total and add it to the graph
            totalWeight += edge.weight;
            mst.edges.add(edge);

            // For every edge connected to the destination vertex, add it to the queue
            priorityQueue.addAll(edge.destination.neighbours);
        }

        System.out.println(totalWeight);
        // Return the minimum spanning tree graph object
        return mst;
    }

    private Graph kruskals() {
        double totalWeight = 0;
        Graph mst = new Graph();
        mst.vertices = this.vertices;

        // Sort the edges array list
        Collections.sort(edges);

        // Create a new disjoint set the size of the number of vertices
        DisjointSets vertexSet = new DisjointSets(vertices.size());

        for (int i = 0; i < edges.size() && mst.edges.size()<(vertices.size()); i++) {
            Edge edge = edges.get(i);
            // Use find to fetch the subtree (accessible vertices)
            int find1 = vertexSet.find(edge.source.name);
            int find2 = vertexSet.find(edge.destination.name);

            // Check a cycle won't be created
            if (find1 != find2) {
                // If it won't add the edge to the total and graph
                totalWeight += edge.weight;
                mst.edges.add(edge);
                // Union the sets of vertices
                vertexSet.union(find1, find2);
            }
        }
        System.out.println(totalWeight);
        // Return the minimum spanning tree graph object
        return mst;
    }

    /**
     * Generates a random graph.
     * @param n The number of vertices in the graph.
     * @param p The probability of an edge existing between any two vertices.
     */
    private void randomGraph(int n, double p) {
        // Add the vertices
        for (int i = 0; i < n; i++) {
            vertices.add(new Vertex(i, rand.nextInt(100), rand.nextInt(100)));

            for (Vertex vertex : vertices) {
                // Check there is more than one vertex and then use the probability to decide if to add an edge
                if (i > 0 && rand.nextDouble() <= p) {
                    if (!edgeExists(vertex, vertices.get(i))) {
                        double edgeWeight = calculateDistance(vertex, vertices.get(i));
                        Edge edge = new Edge(vertex, vertices.get(i), edgeWeight);

                        // KRUSKALS
                        // Add to the overall edges array list
                        edges.add(edge);

                        // PRIMS
                        // Add the edge reference to the neighbouring edges of the source vertex
                        vertex.neighbours.add(edge);
                        /* Create an inverse edge and add to the neighbours of the destination vertex (this
                         makes the prims implementation simpler as it allows us to travel in both directions) */
                        vertices.get(i).neighbours.add(new Edge(vertices.get(i), vertex, edgeWeight));
                    }
                }
            }
        }
    }

    /**
     * Determine the weight of an edge by calculating the distance between them.
     * @param vertex1 The first vertex.
     * @param vertex2 The second vertex.
     * @return The distance between the vertices.
     */
    private double calculateDistance(Vertex vertex1, Vertex vertex2) {
        return Math.hypot(vertex1.position.x - vertex2.position.x,
                vertex1.position.y - vertex2.position.y);
    }

    /**
     * Checks if an edge exists between two vertices.
     * @param vertex1 The first vertex.
     * @param vertex2 The second vertex.
     * @return True if they are connected, false otherwise.
     */
    private Boolean edgeExists(Vertex vertex1, Vertex vertex2) {
        for (Edge edge : edges) {
            if (edge.source == vertex1 && edge.destination == vertex2 ||
                    edge.source == vertex2 && edge.destination == vertex1 ||
                    vertex1 == vertex2) {
                return true;
            }
        }
        return false;
    }

    /**
     * Inner class to represent a node/vertex in the graph
     */
    private class Vertex {
        private int name;
        private Point position;
        private List<Edge> neighbours;

        Vertex(int name, int x, int y) {
            this.name = name;
            position = new Point(x, y);
            neighbours = new ArrayList<>();
        }
    }

    /**
     * Inner class to represent the edges between vertices
     */
    private class Edge implements Comparable<Edge> {
        private Vertex source;
        private Vertex destination;
        double weight;

        Edge(Vertex source, Vertex destination, double weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }

        /**
         * Used to correctly order edges when sorted.
         * @param edge The edge to compare this to.
         * @return -1 if the edge passed is larger, 1 if the edge passed is smaller
         * 0 if they are the same.
         */
        public int compareTo(Edge edge) {
            if (this.weight < edge.weight)
                return -1;
            else  if (edge.weight < this.weight)
                return 1;
            return 0;
        }

        /**
         * @return String containing the source, destination and weight of the edge
         */
        @Override
        public String toString() {
            return "Edge [source=" + source.name + ", destination=" + destination.name
                    + ", weight=" + weight + "]";
        }
    }


    // PROVIDED CLASS
    private class DisjointSets {
        DisjointSets(int numElements) {
            s = new int[numElements];
            for (int i = 0; i < s.length; i++)
                s[i] = -1;
        }

        void union(int root1, int root2) {
            if (s[root2] < s[root1]) {
                s[root1] = root2;
            } else {
                if (s[root1] == s[root2])
                    s[root1]--;
                s[root2] = root1;
            }
        }

        int find(int x) {
            if (s[x] < 0)
                return x;
            else
                return s[x] = find(s[x]);
        }

        private int[] s;
    }
}
