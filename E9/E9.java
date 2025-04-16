import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class E9 {
    public static void main(String[] args) {
        Scanner testScan = new Scanner(System.in);
        while (testScan.hasNext()){
            System.out.print(e9(testScan.nextLine()));
        }
        testScan.close();
    }

    public static String e9(String input){
        String[] parts = input.split("\\ ");
        if(!parts[0].equals("N") && !parts[0].equals("L")) return input + " Invalid\n";
        if(parts.length==2) return input + " Invalid\n";

        int n = parts.length-2;
        int[] seq = new int[n];
        long target;

        try{
            target = Long.parseLong(parts[1]);

            if(parts.length==3){
                if(Long.parseLong(parts[2])==target){
                    return parts[0] + " " +  target + " = " +  target + "\n";
                }else{
                    return input + " impossible\n";
                }
            }

            for(int i=0; i<n; i++){
                seq[i]=Integer.parseInt(parts[i+2]);
            }
        }catch(NumberFormatException e){
            return input + " Invalid\n";
        }

        boolean[] results = new boolean[seq.length-1];
        if(parts[0].equals("L")){
            results = findL(seq, target, seq.length-2, results);
        }else{
            results = N3(seq, target);
        }

        if(results==null) return input + " impossible\n";

        
        String print = parts[0] + " " + target + " = " + seq[0];

        for(int i=1; i<seq.length; i++){
            if(results[i-1]==true){
                print += " + " + seq[i];
            }else{
                print += " * " + seq[i];
            }
        }

        return print + "\n";
    }

    static boolean[] findL(int[] seq, long target, int pos, boolean[] results){
        if(pos==0){ //base case
            if(target==seq[0]+seq[1]){
                results[pos]=true;
                return results;
            }else if(target==seq[0]*seq[1]){
                results[pos]=false;
                return results;
            }else{
                return null;
            }
        }else if(target<=0){
            return null;
        }else if(target % seq[pos+1] == 0){ //could be times or plus
            results[pos] = true; 
            boolean[] temp = findL(seq, target-seq[pos+1], pos-1, results); //plus
            if(temp==null){ //not pluss
                results[pos] = false; //times
                return findL(seq, target/seq[pos+1], pos-1, results);
            }else{
                return temp; //correct answer
            }
        }else{//has to plus
            results[pos] = true;
            return findL(seq, target-seq[pos+1], pos-1, results); //plus
        }   
    }

    private static boolean[] N3(int[] seq, long target){
        boolean[] results = new boolean[seq.length-1];
        ArrayList<Head> heads = new ArrayList<Head>();
        ArrayList<Head> headsCopy = new ArrayList<Head>();

        results[0]=true;
        heads.add(new Head(results.clone()));
        results[0]=false;
        heads.add(new Head(results.clone()));

        int pos = 1;

        while(true){
            if(pos==results.length+1){
                return null;
            }else if(pos==results.length){
                for(Head head : heads){
                    if(target==evaluateSolution(seq, head.order)) return head.order;
                }
            }else{
                headsCopy = new ArrayList<Head>();
                for(Head head : heads){
                    if(evaluate(head, seq, target)) headsCopy.add(head); //if in range, add to copy
                }

                heads = new ArrayList<Head>();
                boolean[] order;

                for(Head head : headsCopy){
                    order = head.order;

                    order[pos]=true;
                    heads.add(new Head(order.clone()));
                    order[pos]=false;
                    heads.add(new Head(order.clone()));
                    //for each head, add a multiplication and addition to heads
                }
            }

            pos++;
        }
    }

    public static boolean evaluate(Head head, int[] seq, long target){
        return true;
    }

    public static long evaluateSolution(int[] sequence, boolean[] order){
        long currentConsec = sequence[0]; 
        long value = 0;

        for(int i=1; i < sequence.length; i++){
            if(!order[i-1]){ //multiplication
                currentConsec *= sequence[i];
            }else{ //addition
                value += currentConsec;
                currentConsec = sequence[i];
            }
        }
        value+=currentConsec;
        return value;
    }

    static class Head{
        boolean[] order;
    
        Head(boolean[] order){
            this.order = order;
        }
    }

    public static int lower(int[] parts){
        int result = 1;
        for(int i=0; i<parts.length; i++){
            if(parts[i]!=1){
                if(result==1){
                    result = parts[i];
                }else{
                    result += parts[i];
                }
            }
        }
        return result;
    }

    public static long upper(int[] parts){ 
        long upper=0;
        long consec=1;

        for(int i=0; i<parts.length; i++){
            if(parts[i]==1){
                upper++;
            }else{
                consec *= parts[i];
            }
        }
        upper += consec;
        
        return upper;
    }

    public static long eval(int[] sequence, boolean[] order){
        long currentConsec = sequence[0]; 
        long value = 0;

        for(int i=1; i< sequence.length; i++){
            if(!order[i-1]){ //multiplication
                currentConsec *= sequence[i];
            }else{ //addition
                value += currentConsec;
                currentConsec = sequence[i];
            }
        }

        value+=currentConsec;

        return value;
    }
}

