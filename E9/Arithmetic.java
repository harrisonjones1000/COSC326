package E9;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Arithmetic {
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

        int n = parts.length-2;
        int[] seq = new int[n];
        int target;
        int sum=0;
        int product;
        int a;
        
        try{
            target = Integer.parseInt(parts[1]); //second item, target
            product = Integer.parseInt(parts[2]); //third item, first item in sequence

            for(int i=0; i<n; i++){
                a = Integer.parseInt(parts[i+2]);
                if(a>0){
                    seq[i]=a;
                    sum=sum+a;
                    if(i>0) product=product*a;
                    
                }else{
                    return input + " Invalid"; 
                }
            }
        }catch(NumberFormatException e){
            return input + " Invalid";
        }
        
        if(parts[0].equals("N")) return input + normal(target, seq);
        return input + leftToRight(target, seq);
    }

    public static String normal(int target, int[] seq){
        int lowerLimit=0;
        int upperLimit=0;
        int ones=0;

        int current_consec=0;

        for(int i=0; i<seq.length; i++){
            //lower limit calculations
            if(seq[i]==1) ones++;
            lowerLimit=lowerLimit+seq[i];

            //upper limit calculations
            if(current_consec==0){
                if(seq[i]!=1){
                    current_consec=seq[i];
                }else{
                    upperLimit++;
                }
            }else{
                if(seq[i]!=1){
                    current_consec=current_consec*seq[i];
                }else{
                    upperLimit=upperLimit+current_consec+1;  
                    current_consec=0;
                }
            }

            if(i==seq.length-1) upperLimit=upperLimit+current_consec;
            
        }
        //lower limit calculations cont.
        lowerLimit=lowerLimit-ones;
        if(lowerLimit==0) lowerLimit=1; 

        System.out.println("LL=" + lowerLimit + " UL=" + upperLimit);

        if(target>upperLimit || target<lowerLimit) return " impossible";
        return "";
    }

    public static String leftToRight(int target, int[] seq){
        return "";
    }
}