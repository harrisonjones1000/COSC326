import java.io.*;
import java.util.*;

public class Etude5 {
    public static void main(String[] args) {
        String[] tokens;
        boolean head = false;
        Graph g = null;

        if(args.length==0){
            System.out.println("Invalid: No input");
            return;
        }else{
            try{
                Scanner testScan = new Scanner(new File(args[0]));
                while(testScan.hasNext()){
                    if(!head){
                        tokens = testScan.nextLine().toLowerCase().split(", ");
                        if(tokens.length!=2){
                            System.out.println("Invalid: route");
                            testScan.close();
                            return;
                        }else{
                            g = new Graph(tokens);
                            head=true;
                        }
                    }else{
                        tokens = testScan.nextLine().toLowerCase().split(", ");
                        if(tokens.length!=3){
                            System.out.println("Invalid: route set"); 
                            testScan.close();
                            return;
                        }else{
                            try{
                                if(g.addEdge(tokens[0], tokens[1], Double.parseDouble(tokens[2]))){
                                    System.out.println("Invalid: Non-unique routes");
                                    return;
                                }
                            }catch(NumberFormatException e){
                                System.out.println("Numer format exception");   
                                testScan.close();
                                return;
                            }
                        }
                    }
                }
                testScan.close();
            }catch(FileNotFoundException e){
                System.out.println("No file of that name found");
            }  
        }  
        
        System.out.println(g.toString());

        System.out.println(g.findPath());
        
    }

    static class Edge{
        String source;
        String destination;
        double cost;

        public Edge(String source, String destination, double cost) {
            this.source = source;
            this.destination = destination;
            this.cost = cost;
        }
    }

    static class Graph{
        private String source; //start location
        private String destination; //end desintation

        //Map of locations (Strings), and the list of routes leaving them (Edges)
        private Map<String, ArrayList<Edge>> locations = new HashMap<>(); 

        Graph(String[] targets) {
            this.source=targets[0];
            this.destination=targets[1];
            this.locations.put(targets[0], new ArrayList<Edge>()); 
        }

        private boolean addEdge(String source, String destination, double value){
            if(locations.get(source)==null){ //Location not yet added to locations
                locations.put(source, new ArrayList<Edge>());
            }

            //Check each edge 
            for(Edge edge : locations.get(source)){
                if(edge.destination.equals(destination)){ //edge already exists
                    return true;
                }
            }
            //Add new edge
            locations.get(source).add(new Edge(source, destination, value));
            return false;
        }

        //Lists each location and their routes
        public String toString(){
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

        //Dijkstra's shortest-path algorithm (weighted case)
        private String findPath(){ 
            //Cost to reach each node
            Map<String, Double> costs = new HashMap<>(); 
            costs.put(source, 0.0);


            return "";
        }

    }
}