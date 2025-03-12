//Harrison Jones

import java.util.Scanner;
import java.util.regex.*;

public class Etude11 {
    public static void main(String[] args){
        Scanner testScan = new Scanner(System.in);
        while (testScan.hasNext()) {
            System.out.print(woof(testScan.nextLine()));
        }
        testScan.close();
    }

    public static String woof(String word){
        Pattern woof = Pattern.compile("(([pqrs])|(N*[pqrs])|[CAKE])^");

        
        //Pattern woof3 = Pattern.compile("([CAKE])([pqrs]|N*[pqrs])");
        Matcher m = woof.matcher(word);
        boolean b = m.matches();
        return b + "\n";
    }
}
