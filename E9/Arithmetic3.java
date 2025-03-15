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

        int lowerBound = lower(seq);
        long upperBound = upper(parts[0].equals("L"), seq);
        System.out.println(lowerBound);
        System.out.println(upperBound);
        if(target < lowerBound || target > upperBound) return input + " impossible \t Lower bound: " + lowerBound + " Upper bound: " + upperBound + " \n";
        
        //boolean[] a = genAlternating(seq.length-1);
        //System.out.println(evaluate2(true, seq, a));

        boolean[] results = new boolean[seq.length-1];
        if(parts[0].equals("L")){
            results = findL(seq, target, seq.length-2, results);
        }else{
            results = findN2(seq, target, seq.length-2, results);
        }

        if(results==null) return input + " impossible2\n";
        
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

    //Generates alternating pattern of addition and multiplication
    public static boolean[] genAlternating(int n){
        boolean[] a = new boolean[n];

        for(int i=0; i<n; i++){  
            if(i%2==0){ //0, 2, 4
                a[i]=true;
            }else{
                a[i]=false;
            }
        }

        return a;
    }

    public static int lower(int[] parts){
        int lower = parts[0];
        for(int i=1; i<parts.length; i++){
            if(parts[i]!=1) lower+=parts[i];
        }
        return lower;
    }

    public static long upper(boolean L, int[] parts){
        long upper;
        if(L){
            upper = parts[0];
            for(int i=1; i<parts.length; i++){
                if(parts[i]==1||upper==1){
                    upper+=parts[i];
                }else{
                    upper=upper*parts[i];
                }        
            }
        }else{//N upper limit, sum consec non 1's, plus 1's
            upper=0;
            int consec=0;
            for(int i=0; i<parts.length; i++){
                if(consec==0){
                    if(parts[i]==1){
                        upper+=1;
                    }else{
                        consec=parts[i];
                    }
                }else{
                    if(parts[i]==1){
                        upper+=consec+1;
                        consec=0;
                    }else{
                        consec=consec*parts[i];
                    }
                }
            }
            upper+=consec;
        }
        return upper;
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

    static boolean[] findN2(int[] seq, long target, int pos, boolean[] results){
        if(pos==results.length-1){
            int consec=0;
            for(int i=pos-1; i>0; i--){
                if(!results[i]){//multiplication
                    if(consec==0){
                        consec=seq[i]*seq[i+1];
                    }else{
                        consec=consec*seq[i];
                    }
                }else{//addition
                    break;
                }
            }
            
            if(consec==0){ //last operation was addition
                if(target==seq[pos]*seq[pos+1]){
                    results[pos]=false;
                    return results;
                }else if(target==seq[pos]+seq[pos+1]){
                    results[pos]=true;
                    return results;
                }else{
                    return null;
                }
            }
            return null;
        }else if(pos==0){
            long upper = upper(false, seq);
            long lower = lower(seq);
            if(target>upper || target<lower){
                return null;
            }else{//in range
                results[0]=true; //plus
                boolean[] temp;
                temp = findN2(seq, target-seq[0], pos+1, results);
                if(temp==null){
                    results[0]=false; //multiplication
                    return findN2(seq, target, pos+1, results);
                }else{
                    return temp;
                }
            }
        }else{
            //normal. 
            return null;
        }
    }

    public static long evaluate(boolean L, int[] sequence, boolean[] order){
        if(L){
            long value=sequence[0];
            for(int i=1; i<sequence.length; i++){
                if(order[i-1]){
                    value+=sequence[i];
                }else{
                    value=value*sequence[i];
                }
            }
            return value;
        }else{
            int current_consec=0;
            int value=0;
    
            for(int i=0; i<order.length; i++){
                if(order[i]){ //plus
                    if(current_consec!=0){ //last operation was multiply
                        current_consec=current_consec*sequence[i];
                        value+=current_consec;
                        current_consec=0;
                    }else{
                        value+=sequence[i];
                    }
                }else{ //multiply
                    if(current_consec==0){
                        current_consec=sequence[i];
                    }else{
                        current_consec=current_consec*sequence[i];
                    }
                }
            }
    
            if(order[order.length-1]){ //final plus
                if(current_consec!=0){ //second last operation was multiplication
                    current_consec=current_consec*sequence[order.length-1];
                    value+=current_consec;
                }
                    value+=sequence[order.length];
    
            }else{ //final multiply
                current_consec=current_consec*sequence[order.length];
                value+=current_consec;
            }
            
            return value;
        }
    }

    public static String evaluate2(boolean L, int[] seq, boolean[] results){
        long value = evaluate(L, seq, results);
        String print = value + " = " + seq[0];
        for(int i=1; i<seq.length; i++){
            if(results[i-1]==true){
                print += " + " + seq[i];
            }else{
                print += " * " + seq[i];
            }
        }
        return print;
    }
}

