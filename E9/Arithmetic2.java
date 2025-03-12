//Harrison Jones

import java.util.Scanner;

public class Arithmetic2 {
    public static void main(String[] args) {
        Scanner testScan = new Scanner(System.in);
        while (testScan.hasNext()) {
            System.out.print(e9(testScan.nextLine()));
        }
        testScan.close();
    }

    public static String e9(String input){
        String[] parts = input.split("\\ ");
        if(!parts[0].equals("N") && !parts[0].equals("L")) return input + " Invalid\n";

        if(parts.length==2) return input + " Invalid\n"; //0 numbers

        //2 numbers or more
        int n = parts.length-2;
        int[] seq = new int[n];
        int target;
        int a;
        
        try{
            target = Integer.parseInt(parts[1]);

            if(parts.length==3){
                if(Integer.parseInt(parts[2])==target){
                    return parts[0] + " " +  target + " = " +  target + "\n";
                }else{
                    return input + " impossible\n";
                }
            }

            for(int i=0; i<n; i++){
                a = Integer.parseInt(parts[i+2]);
                if(a>0){
                    seq[i]=a;                    
                }else{
                    return input + " Invalid\n"; 
                }
            }
        }catch(NumberFormatException e){
            return input + " Invalid\n";
        }
       
        MyTree tree = new MyTree(target, seq, parts[0].equals("N"));
        boolean[] result = tree.search();

        if(result==null) return input + " Impossible\n";

        String print = parts[0] + " " + target + " = " + seq[0];

        for(int i=1; i<seq.length; i++){
            if(result[i-1]==true){
                print += " + " + seq[i];
            }else{
                print += " * " + seq[i];
            }
        }

        return print + "\n";
    }    

}