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
        Pattern woof = Pattern.compile("[pqrs]|N*([pqrs]|[CAKE])|[CAKE]");
        Matcher m = woof.matcher(word);

        if(m.find()){
            if (m.start()!=0) return false; //pattern does not start

            int endPos = m.end();

            if(m.end()==word.length()){
                return true;
            }else{
                char ch = word.charAt(endPos-1);
                if (ch == 'C' || ch == 'A' || ch == 'K' || ch == 'E'){ //we have woof3
                    
                    word = word.substring(endPos,word.length());

                    endPos = woof3(word);

                    if(endPos==-1){ //no woof or only one woof
                        return false;
                    }else{
                        word = word.substring(endPos, word.length());
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

    //Method for checking if first woof valid
    //If valid, returns end position of first woof
    public static int woof3(String word){
        Pattern woof = Pattern.compile("[pqrs]|N*([pqrs]|[CAKE])|[CAKE]");
        Matcher m = woof.matcher(word);

        if(m.find()){
            if (m.start()!=0) return -1; //no woof

            int endPos = m.end();

            if(m.end()==word.length()){ //only one woof
                return -1;
            }else{
                char ch = word.charAt(endPos-1);
                if (ch == 'C' || ch == 'A' || ch == 'K' || ch == 'E'){ //we have woof3
                    
                    word = word.substring(endPos,word.length());
                    endPos = woof3(word); //Check for first woof
                    if(endPos==-1) return -1; //Only one woof or no woof
                    
                    return endPos;

                }else{
                    return endPos;
                }
            }
        }else{
            return -1; //no woof
        }
    }
}
