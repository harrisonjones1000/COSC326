/**
 * Harrison Jones and Ollie Hurst
 * Notes: assume each text file has the same format throughout
 */

package E2;

import java.io.*;
import java.util.Scanner;

public class Etude2 {
    public static void main(String[] args) {
        try{
            Scanner testScan = new Scanner(new File("E1/E1_tests.txt"));
            while(testScan.hasNext()) System.out.println(testScan.nextLine());
            testScan.close();

        }catch(FileNotFoundException e){
            System.out.println("No file of that name found");
        }
    }
}