
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

    private boolean dmy(String[] tokens) {
        int d = Integer.parseInt(tokens[0]);
        int m = Integer.parseInt(tokens[1]);
        int y = Integer.parseInt(tokens[2]);

        return true;
    }

    private static boolean dym(String[] tokens) {

        String d = tokens[0];
        String y = tokens[1];
        String m = tokens[2];

        int dayInt;
        int yearInt;
        int monthInt;

        try {
            dayInt = Integer.parseInt(d);
            yearInt = Integer.parseInt(y);
            monthInt = Integer.parseInt(m);
        } catch (Exception e) {
            return false;
        }

        if (y.length() == 2) {
            if (yearInt < 49) {
                yearInt = yearInt + 2000;
            } else if (yearInt >= 50) {
                yearInt = yearInt + 1900;
            }
        }

        boolean isLeapYear = false;
        if (yearInt % 4 == 0 && !(yearInt % 100 == 0) && !(yearInt % 400 == 0)) {
            isLeapYear = true;
        }

        if (monthInt < 1 || monthInt > 12) { // month out of range
            return false;
        }

        // the max number of days by month (if not leap year)
        int[] daysInMonths = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
        int maxDays;

        if (isLeapYear && monthInt == 2) {
            maxDays = 29;
        } else {
            maxDays = daysInMonths[monthInt];
        }

        if (dayInt < 1 || dayInt > maxDays) {
            return false;
        }

        return true;
    }
}