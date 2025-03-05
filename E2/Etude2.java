
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
        ArrayList<String[]> dates = new ArrayList<>(); //Where we store our dates
        int[][] values = new int[6][5]; //Where we keep track of the format that works for each date

        try {
            Scanner testScan = new Scanner(new File("test1.txt"));
            while (testScan.hasNext()) dates.add(testScan.nextLine().split("/"));
            testScan.close();

        } catch (FileNotFoundException e) {
            System.out.println("No file of that name found");
        }

        boolean[] valid = new boolean[dates.size()]; //where we check if dates format to int/int/int
        int a,b,c;

        for(int i=0; i<dates.size(); i++){
            String[] date = dates.get(i);

            try{
                if(date.length!=3){
                    valid[i]=false;
                }else{
                    a = Integer.parseInt(date[0]);
                    b = Integer.parseInt(date[1]);
                    c = Integer.parseInt(date[2]);

                    System.out.println(
                    "First token: " + date[0] + "\nSecond token: " + date[1] + "\nThird token: " + date[2]);

                }
            }catch(NumberFormatException e){
                valid[i]=false;
            }
            
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