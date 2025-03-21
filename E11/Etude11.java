//Harrison Jones

import java.util.Scanner;
import java.util.regex.*;

public class Etude11 {
    public static void main(String[] args){
        Scanner testScan = new Scanner(System.in);
        while (testScan.hasNext()) {
            if(woofv2(testScan.nextLine())){
                System.out.println("woof");
            }else{
                System.out.println("not woof");
            }
        }
        testScan.close();
    }

    public static boolean woofv2(String word){
        //Goes down and replaces woof3's to woof1
        for(int i=word.length()-1; i>=0; i--){
            if(word.charAt(i)=='C' || word.charAt(i)=='A' || word.charAt(i)=='K' || word.charAt(i)=='E'){
                int a = woof3v2(word.substring(i+1));
                if(a!=-1){
                    word = word.substring(0,i) + "p" + word.substring(i+a+1);
                }else{
                    return false; //Not a valid woof3
                }

            }
        }

        Pattern woof = Pattern.compile("[pqrs]|N*([pqrs])");
        Matcher m = woof.matcher(word);

        if(m.find()){
            if (m.start()!=0) return false; //pattern does not start at first index
            if(m.end() != word.length()) return false;
            return true;
        }else{
            return false; //pattern not found
        }
    }

    public static int woof3v2(String word){
        Pattern woof = Pattern.compile("([pqrs]|N(N*)([pqrs]))([pqrs]|N(N*)([pqrs]))");
        Matcher m = woof.matcher(word);

        if(m.find()){
            if (m.start()!=0) return -1; //pattern does not start at first index

            return m.end();
        }else{
            return -1; //pattern not found
        }
    }
}
