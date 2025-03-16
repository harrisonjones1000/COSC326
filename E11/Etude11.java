//Harrison Jones

import java.util.Scanner;
import java.util.regex.*;

public class Etude11 {
    public static void main(String[] args){
        Scanner testScan = new Scanner(System.in);
        while (testScan.hasNext()) {
            if(woof(testScan.nextLine())){
                System.out.println("woof");
            }else{
                System.out.println("not woof");
            }
        }
        testScan.close();
    }

    public static boolean woof(String word){
        Pattern woof = Pattern.compile("[pqrs]|N(N*)([pqrs]|[CAKE])|[CAKE]");
        Matcher m = woof.matcher(word);

        if(m.find()){
            if (m.start()!=0) return false; //pattern does not start

            int endPos = m.end();

            char ch = word.charAt(endPos-1);

            if(m.end()==word.length()){
                if(ch == 'C' || ch == 'A' || ch == 'K' || ch == 'E') return false;
                return true;
            }else{
                if (ch == 'C' || ch == 'A' || ch == 'K' || ch == 'E'){ //we have woof3
                    
                    word = word.substring(endPos,word.length());

                    //This method checks if we have 1 woof following the C
                    //Returns the end position in our string
                    endPos = woof3(word); 

                    if(endPos==-1){ //no woof or only one woof
                        return false;
                    }else{//Check for final woof
                        //System.out.println(word + " " + endPos);
                        word = word.substring(endPos+1, word.length());
                        //System.out.println(word);
                        if(woof(word)) return true; //check for second woof
                        return false; //no second woof
                    }
                }else{
                    return false;
                }
            }
        }else{
            return false;
        }
    }

    //Method that finds the first woof it sees, and returns its end position from the word
    public static int woof3(String word){
        Pattern woof = Pattern.compile("[pqrs]|N(N*)([pqrs]|[CAKE])|[CAKE]");
        Matcher m = woof.matcher(word);

        System.out.println(word);

        if(m.find()){
            if (m.start()!=0) return -1; //no woof

            int endPos = m.end();

            char ch = word.charAt(endPos-1);

            if (ch == 'C' || ch == 'A' || ch == 'K' || ch == 'E'){ 
                
                word = word.substring(endPos,word.length());
                endPos = woof3(word); //Check for first woof
                if(endPos==-1) return -1;

                word = word.substring(endPos, word.length());
                int temp = woof3(word); //check for second woof
                if(temp==-1) return -1; //no second woof
                
                return endPos+temp;
            }else{
                return endPos;
            }
        }else{
            return -1; //no woof
        }
    }
}
