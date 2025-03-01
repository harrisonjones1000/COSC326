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

        //2 numbers or more
        int n = parts.length-2;
        int[] seq = new int[n];
        int target;
        int a;
        
        try{
            target = Integer.parseInt(parts[1]);

            if(parts.length==3){
                if(Integer.parseInt(parts[2])==target){
                    return parts[0] + " " +  target + " = " +  target;
                }else{
                    return input + " Impossible";
                }
            }

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
        boolean[] result = tree.search();

        if(result==null) return input + " Impossible";

        String print = parts[0] + " " + target + " = " + seq[0];

        for(int i=1; i<seq.length; i++){
            if(result[i-1]==true){
                print += " + " + seq[i];
            }else{
                print += " * " + seq[i];
            }
        }

        return print;
    }    

}





