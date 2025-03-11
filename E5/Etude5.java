import java.io.*;
import java.util.*;

public class Etude5 {
    public static void main(String[] args) {
        String[] tokens;
        boolean head = false;
        Graph g = null;

        if(args.length==0){
            System.out.println("No test file provided");
        }else{
            try{
                Scanner testScan = new Scanner(new File(args[0]));
                while(testScan.hasNext()){
                    if(!head){
                        tokens = testScan.nextLine().split(", ");
                        if(tokens.length!=2){
                            System.out.println("Invalid input file");
                            testScan.close();
                            return;
                        }else{
                            g = new Graph(tokens);
                            head=true;
                        }
                    }else{
                        tokens = testScan.nextLine().split(", ");
                        if(tokens.length!=3){
                            System.out.println("Invalid input file"); 
                            testScan.close();
                            return;
                        }else{
                            try{
                                g.addEdge(tokens[0], tokens[1], Double.parseDouble(tokens[2]));
                            }catch(NumberFormatException e){
                                System.out.println("Invalid input file");   
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
        private Map<String, ArrayList<Edge>> map = new HashMap<>(); 

        Graph(String[] targets) {
            this.source=targets[0];
            this.destination=targets[1];
            this.map.put(targets[0], new ArrayList<Edge>()); 
        }

        private void addEdge(String source, String destination, double value){
            if(map.get(source)==null) map.put(source, new ArrayList<Edge>());
            
            map.get(source).add(new Edge(source, destination, value));
            
        }

        public String toString(){
            String print = "";
            for (String location : map.keySet()) {
                print += location + " : ";    
                List<Edge> edges = map.get(location);
                
                for (Edge edge : edges) {
                    print += edge.destination + " " + edge.cost + ", ";
                }
                print += "\n";
            }

            return print;
        }

    }
}