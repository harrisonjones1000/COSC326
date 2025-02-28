package E9;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Arithmetic2 {
    public static void main(String[] args) {
        try{
            Scanner testScan = new Scanner(new File("E9/E9_tests.txt"));
            while(testScan.hasNext()) System.out.println(e9(testScan.nextLine()));
            testScan.close();

        }catch(FileNotFoundException e){
            System.out.println("No file of that name found");
        }
    }

    public static String e9(String input){
        String[] parts = input.split("\\ ");
        if(!parts[0].equals("N") && !parts[0].equals("L")) return input + " Invalid";

        if(parts.length==2) return input + " Invalid"; //0 numbers
        if(parts.length==3 && parts[1]!=parts[2]) return input + " Invalid"; //1 number

        //2 numbers or more
        int n = parts.length-2;
        int[] seq = new int[n];
        int target;
        int a;
        
        
        try{
            target = Integer.parseInt(parts[1]);

            for(int i=0; i<n; i++){
                a = Integer.parseInt(parts[i+2]);
                if(a>0){
                    seq[i]=a;                    
                }else{
                    return input + " Invalid"; 
                }
            }
        }catch(NumberFormatException e){
            return input + " Invalid";
        }
       
        MyTree tree = new MyTree(target, seq, parts[0].equals("N"));
        

        return input;
    }

    public static String leftToRight(int target, int[] seq){
        
        return "";
    }

    
    

}





