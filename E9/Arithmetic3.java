import java.util.Scanner;

public class Arithmetic3 {
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
        if(parts.length==2) return input + " Invalid\n";

        int n = parts.length-2;
        int[] seq = new int[n];
        int target;
        try{
            target = Integer.parseInt(parts[1]);
            for(int i=0; i<n; i++){
                seq[i]=Integer.parseInt(parts[i+2]);
            }
        }catch(NumberFormatException e){
            return input + " Invalid\n";
        }

        int lowerBound = lower(seq);
        int upperBound = upper(parts[0].equals("L"), seq);
        
        if(target < lowerBound || target > upperBound) return input + " impossible\n";

        return input + "\tLower Bound: " + lowerBound +"  Upper Bound: " + upperBound + "\n";
    }

    public static int lower(int[] parts){
        int lower = parts[0];
        for(int i=1; i<parts.length; i++){
            if(parts[i]!=1) lower+=parts[i];
        }
        return lower;
    }

    public static int upper(boolean L, int[] parts){
        int upper;
        if(L){
            upper = parts[0];
            for(int i=1; i<parts.length; i++){
                if(parts[i]==1){
                    upper+=1;
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
}

