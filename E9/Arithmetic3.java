import java.util.Arrays;
import java.util.Scanner;

public class Arithmetic3 {
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
            results = findN2(seq, target, 0, results);
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

    //N2 stuff down here

    public static boolean[] findN2(int[] seq, long target, int pos, boolean[] results){
        if(pos == seq.length-2){
            results[pos]=true; //addition
            if(eval(seq, results) == target) return results;

            results[pos]=false; //multiplication
            if(eval(seq, results) == target) return results;
            return null;

        }else if(pos==0){
            if((upper(seq)<target || lower(seq)>target) && upper(seq) > 0){
                // System.out.println(upper(seq)+ " < " + target);
                // System.out.println(lower(seq)+ " > " + target);
                return null;
            }
                
            boolean[] resultsCopy = results;

            resultsCopy[0] = true; //addition
            resultsCopy = findN2(seq, target, 1, results);
            if(resultsCopy!=null) return resultsCopy;

            results[0] = false; //multiplication
            results = findN2(seq, target, 1, results);
            return results;
            
        }else{
            long currentConsec = seq[0]; 
            long value = 0;

            for(int i=1; i<=pos; i++){
                if(!results[i-1]){ //multiplication
                    currentConsec *= seq[i];
                }else{ //addition
                    value += currentConsec;
                    currentConsec = seq[i];
                }
            }

            int[] seqCopy = Arrays.copyOfRange(seq, pos, seq.length);
            seqCopy[0]=(int)currentConsec;

            if((upper(seqCopy)< target-value || lower(seqCopy) > target-value) && upper(seqCopy)>0) return null;

            boolean[] resultsCopy = results;

            resultsCopy[pos] = true; //addition
            resultsCopy = findN2(seq, target, pos+1, results);
            if(resultsCopy!=null) return resultsCopy;

            results[pos] = false; //multiplication
            results = findN2(seq, target, pos+1, results);
            return results;
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

