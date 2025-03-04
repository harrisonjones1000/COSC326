/**
 * Harrison Jones and Ollie Hurst
 * Notes: assume each text file has the same format throughout
 */

// package E2;

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
}