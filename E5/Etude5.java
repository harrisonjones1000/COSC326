import java.io.*;
import java.util.*;

public class Etude5 {
    public static void main(String[] args) {
        String[] tokens;
        boolean head = false;
        Graph g = null;

        Scanner testScan = new Scanner(System.in);
        while (testScan.hasNext()) {
            if (!head) {
                tokens = testScan.nextLine().toLowerCase().split(", ");
                if (tokens.length != 2) {
                    System.out.println("Invalid: route");
                    testScan.close();
                    return;
                } else {
                    g = new Graph(tokens);
                    head = true;
                }
            } else {
                tokens = testScan.nextLine().toLowerCase().split(", ");
                if (tokens.length != 3) {
                    System.out.println("Invalid: route set");
                    testScan.close();
                    return;
                } else {
                    try {
                        if (g.addEdge(tokens[0], tokens[1], Double.parseDouble(tokens[2]))) {
                            System.out.println("Invalid: Non-unique routes");
                            return;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Numer format exception");
                        testScan.close();
                        return;
                    }
                }
            }
        }
        testScan.close();

        System.out.println(g.toString());

        System.out.println(g.findPath());

    }

    static class NodeCostPath {
        String node;
        double cumCost;
        ArrayList<String> path;

        public NodeCostPath(String node, double cost, ArrayList<String> path) {
            this.node = node;
            this.cumCost = cost;
            this.path = path;
        }

        public String toString() {
            return this.node + ", " + this.cumCost + ", " + this.path;
        }
    }

    static class SortByCost implements Comparator<NodeCostPath> {
        public int compare(NodeCostPath a, NodeCostPath b) {
            return (int) (a.cumCost - b.cumCost);
        }
    }

    static class SortByLength implements Comparator<NodeCostPath> {
        public int compare(NodeCostPath a, NodeCostPath b) {
            return a.path.size() - b.path.size();
        }
    }

    static class Edge {
        String source;
        String destination;
        double cost;
        private double cumCost;
        private ArrayList<String> prevNodes;

        public Edge(String source, String destination, double cost) {
            this.source = source;
            this.destination = destination;
            this.cost = cost;
        }

        public String toString() {
            return source + " -> " + destination + " [" + cost + "]";
        }
    }

    static class Graph {
        private String source; // start location
        private String destination; // end desintation

        // Map of locations (Strings), and the list of routes leaving them (Edges)
        private Map<String, ArrayList<Edge>> locations = new HashMap<>();

        Graph(String[] targets) {
            this.source = targets[0];
            this.destination = targets[1];
            this.locations.put(targets[0], new ArrayList<Edge>());
        }

        private boolean addEdge(String source, String destination, double value) {
            if (locations.get(source) == null) { // Location not yet added to locations
                locations.put(source, new ArrayList<Edge>());
            }

            // Check each edge
            for (Edge edge : locations.get(source)) {
                if (edge.destination.equals(destination)) { // edge already exists
                    return true;
                }
            }
            // Add new edge
            locations.get(source).add(new Edge(source, destination, value));
            return false;
        }

        // Lists each location and their routes
        public String toString() {
            String print = "";
            for (String location : locations.keySet()) {
                print += location + " : ";
                List<Edge> edges = locations.get(location);

                for (Edge edge : edges) {
                    print += edge.destination + " " + edge.cost + ", ";
                }
                print += "\n";
            }

            return print;
        }

        // Dijkstra's shortest-path algorithm (weighted case)
        private String findPath() {
            // Cost to reach each node
            // Map<String, Double> openMap = new HashMap<>();
            // List<Set<String, Double>> openList = new List();

            ArrayList<NodeCostPath> openArrayList = new ArrayList<>();

            String nName = source;
            double nCost = 0.0;
            ArrayList<String> nPath = new ArrayList<>();
            nPath.add(nName);
            // openArrayList.add(new NodeCostPath(nName, nCost, nPath));

            ArrayList<NodeCostPath> solutions = new ArrayList<>();

            // double costOfFirstPath = -1;
            while (true) {
                if (nName.equals(destination)) {
                    if (solutions.size() > 0 && solutions.get(0).cumCost < nCost) {
                        break;
                    }
                    solutions.add(new NodeCostPath(nName, nCost, nPath));
                } else {
                    // System.out.println("Name: " + nName);
                    // System.out.println(">" + locations.get(nName) + "<");
                    for (Edge edge : locations.get(nName)) {
                        // System.out.println(edge.destination + ", " + edge.cost + ", " + nCost + ", "
                        // + nPath);
                        openArrayList.add(new NodeCostPath(edge.destination, edge.cost + nCost, nPath));
                    }
                    Collections.sort(openArrayList, new SortByCost());
                }
                // System.out.println(openArrayList);
                // System.out.println("Popping: " + n);

                if (openArrayList.size() < 1) {
                    break;
                }
                NodeCostPath n = openArrayList.remove(0);

                nName = n.node;
                nCost = n.cumCost;
                nPath = n.path;

                ArrayList<String> newPath = new ArrayList<>();
                for (String node : nPath) {
                    newPath.add(node);
                }
                newPath.add(nName);
                nPath = newPath;

            }

            Collections.sort(solutions, new SortByLength());
            System.out.println(solutions.get(0));
            // System.out.println(nPath.toString());
            // System.out.println(nCost);
            return "";

            // System.out.println(nName);
            // System.out.println(nCost);
            // System.out.println(nPath);

            // System.out.println(openArrayList);

            // System.out.println(openArrayList);

        }

    }
}