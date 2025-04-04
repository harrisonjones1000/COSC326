//args[0], 0=L, 1=N
//args[0], n
//args[1], trials

import java.util.Random;

public class Number {
    public static void main(String[] args) {
        for(int i = 0; i< Integer.parseInt(args[2]); i++){
            int n = Integer.parseInt(args[1]);
            boolean[] a = genRandom(n-1);
            int[] seq = genRandomNums(n);
            
            String s = " ";
            for(int num : seq){
                s+=num + " ";
            }

            boolean L = Integer.parseInt(args[0])==0;

            if(L){
                System.out.println("L " + evaluate(L, seq, a) + s);
            }else{
                if(evaluate(L, seq, a) < 66000){
                    System.out.println("N " + evaluate(L, seq, a) + s);
                }
            }
            System.out.println(evaluate2(L, seq, a));
        }
        
    }

    public static boolean[] genRandom(int n){
        boolean[] a = new boolean[n];
        Random rand = new Random();

        for (int i = 0; i < n; i++) {
            a[i] = rand.nextBoolean();
        }
        return a;
    }

    public static int[] genRandomNums(int n){
        int[] a = new int[n];
        Random rand = new Random();

        for (int i = 0; i < n; i++) {
            a[i] = rand.nextInt(1,7);
        }
        return a;
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
