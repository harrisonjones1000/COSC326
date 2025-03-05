/**
 * Harrison Jones and Ollie Hurst
 * Notes: assume each text file has the same format throughout
 * 
 * Note: we are running it via javac Etuide2.java
 * java Etude2 (to run)
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Etude2 {
    public static void main(String[] args) {
        ArrayList<String[]> tokenList = new ArrayList<>();
        try {
            Scanner testScan = new Scanner(new File("test1.txt"));
            while (testScan.hasNext())
                tokenList.add(testScan.nextLine().split("/"));
            testScan.close();

        } catch (FileNotFoundException e) {
            System.out.println("No file of that name found");
        }
        for (String[] tokens : tokenList) {
            System.out.println(
                    "First token: " + tokens[0] + "\nSecond token: " + tokens[1] + "\nThird token: " + tokens[2]);
        }
    }

    private boolean dmy(String[] tokens){
        int d = Integer.parseInt(tokens[0]);
        int m = Integer.parseInt(tokens[1]);
        int y = Integer.parseInt(tokens[2]);

        

        return true;
    }
}