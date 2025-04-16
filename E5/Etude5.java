// By Harrison Jones and Oliver Hurst
// COSC326 Etude 5 - Bus Routes

import java.io.IOException;
import java.util.*;

public class Etude5 {
    public static void main(String[] args) throws IOException {
        String[] tokens;
        Graph g = null;

        if (System.in.available() == 0) {
            System.out.print("Invalid: No input");
            System.exit(1);
        }

        Scanner testScan = new Scanner(System.in);

        boolean head = false;

        while (testScan.hasNext()) {
            if (!head) { // no head
                tokens = testScan.nextLine().toLowerCase().split(",");
                if (tokens.length != 2) {
                    System.out.print("Invalid: route");
                    testScan.close();
                    return;
                } else {
                    g = new Graph(tokens[0].trim().replaceAll("\\s+"," "), tokens[1].trim().replaceAll("\\s+"," "));
                    head = true;
                }
            }else{
                tokens = testScan.nextLine().toLowerCase().split(","); 
                if (tokens.length != 3){
                    System.out.print("Invalid: route set");
                    testScan.close();
                    return;
                } else {
                    try {
                        if(g.addEdge(tokens[0].trim().replaceAll("\\s+"," "), tokens[1].trim().replaceAll("\\s+"," "), Double.parseDouble(tokens[2]))) {
                            System.out.print("Invalid: Non-unique routes");
                            testScan.close();
                            return;
                        }
                    } catch (NumberFormatException e) {
                        testScan.close();
                        return;
                    }
                }
            }
        }
        testScan.close();

        //System.out.println(g.toString());

        System.out.println(g.findPath());
    }

    /**
     * An object that holds the triple of node, cumulative cost to that node and the
     * associated path.
     */
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
            if (a.cumCost < b.cumCost) {
                return -1;
            } else if (a.cumCost > b.cumCost) {
                return 1;
            } else {
                return 0;
            }
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

        public Edge(String source, String destination, double cost) {
            this.source = source;
            this.destination = destination;
            this.cost = cost;
        }
    }

    static class Graph {
        private String source; // start location
        private String destination; // end desintation

        // Map of locations (Strings), and the list of routes leaving them (Edges)
        private Map<String, ArrayList<Edge>> locations = new HashMap<>();

        Graph(String source, String destination) {
            this.source = source;
            this.destination = destination;
            this.locations.put(source, new ArrayList<Edge>());
        }

        private boolean addEdge(String source, String destination, double value) {
            if (locations.get(source) == null) { // Location not yet added to locations
                locations.put(source, new ArrayList<Edge>());
            }
            if (locations.get(destination) == null) {
                locations.put(destination, new ArrayList<Edge>());
            }

            // Check each edge
            for (Edge edge : locations.get(source)) {
                if (edge.destination.equals(destination)) {
                    return true;
                }
            }
            // Add new edge
            locations.get(source).add(new Edge(source, destination, value));
            locations.get(destination).add(new Edge(destination, source, value));

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

        // Appends least-cost paths to target until one has a greater cost, then returns
        // shortest least-cost solution.
        private String findPath() {

            // List of nodes to expand
            ArrayList<NodeCostPath> openArrayList = new ArrayList<>();
            // NodeCostPath n = null;

            String nName = source;
            double nCost = 0.0;
            ArrayList<String> nPath = new ArrayList<>();
            nPath.add(nName);

            // List of least-cost solutions
            ArrayList<NodeCostPath> solutions = new ArrayList<>();

            while (true) {
                if (nName.equals(destination)) {
                    // break if we have at least one solution, and the current solution is worse
                    if (solutions.size() > 0 && solutions.get(0).cumCost < nCost) {
                        break;
                    }
                    solutions.add(new NodeCostPath(nName, nCost, nPath));
                } else {
                    for (Edge edge : locations.get(nName)) {
                        if (nPath.contains(edge.destination)) {
                            continue;
                        }
                        openArrayList.add(new NodeCostPath(edge.destination, edge.cost + nCost, nPath));
                    }
                }
                Collections.sort(openArrayList, new SortByCost());

                // break if we reach the end of the search
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
            // sort solutions by shortest path (all solutions should have equal cost)
            Collections.sort(solutions, new SortByLength());

            ArrayList<String> solutionPath = solutions.get(0).path;

            StringBuilder s = new StringBuilder();
            for (String node : solutionPath) {
                s.append(node);
                s.append('-');
            }
            s.deleteCharAt(s.lastIndexOf("-"));

            return s.toString();
        }
    }
}