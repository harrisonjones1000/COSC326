import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.random.*;

public class Generator {
    public static void main(String[] args) {
        System.out.println("start, end");
        int depth;
        int width1;
        int width2;

        try{
            depth = Integer.parseInt(args[0]); 
            width1 = Integer.parseInt(args[1]);
            width2 = Integer.parseInt(args[2]);

        }catch(NumberFormatException e){
            return;
        }

        if(depth<1) return;

        String[][] locations = new String[depth][];
        int r;
        Random random = new Random();
        int d;

        for(int i=0; i<depth; i++){ //gen points at depth i
            r = random.nextInt(width1, width2);
            locations[i] = new String[r];
            for(int j=0; j<r; j++){
                locations[i][j] = i + "." + j;
            }
        }

        for(int i=0; i<depth; i++){
            if(depth==1){
                for(int k=0; k<locations[i].length; k++){
                    System.out.println("start, " + locations[0][k] + ", " + random.nextDouble(0.5,10));
                    System.out.println(locations[i][k] + ", end, " + random.nextDouble(0.5,10));
                }
                break;
            }if(i==0){
                for(int k=0; k<locations[i].length; k++){
                    System.out.println("start, " + locations[0][k] + ", " + random.nextDouble(0.5,10));
                }
            }else if(i==depth-1){
                for(int k=0; k<locations[i].length; k++){
                    System.out.println(locations[i][k] + ", end, " + random.nextDouble(0.5,10));
                }
                break;
            }

            d = locations[i+1].length; //number of points at depth i+1

            for(int j=0; j<locations[i].length; j++){// for every point at depth i, location j
                r = random.nextInt(0,d); //a random point at depth i+1
                System.out.println(locations[i][j] + ", " + locations[i+1][r] + ", " + random.nextDouble(0.5,10));
                
                //add paths between

            }
        }
    }
}
