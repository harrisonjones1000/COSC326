
/**
 * Harrison Jones and Ollie Hurst
 * Notes: assume each text file has the same format throughout
 * 
 * Note: we are running it via javac Etuide2.java
 * java Etude2 (to run)
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Etude2 {
    
    public static void main(String[] args) {
        ArrayList<String> dates = new ArrayList<>(); //Where we store our dates

        //Where we keep track of the format that works for each date
        int[] values = new int[6]; //0 - dmy, 1 - dym, 2 - mdy, 3 - myd, 4 - ymd, 5 - ydm

        try {
            Scanner testScan = new Scanner(new File("test1.txt"));
            while (testScan.hasNext()) dates.add(testScan.nextLine());
            testScan.close();

        } catch (FileNotFoundException e) {
            System.out.println("No file of that name found");
        }

        //where we store if our dates are valid int/int/int format
        boolean[] valid = new boolean[dates.size()]; 

        for(int i=0; i<dates.size(); i++){
            String[] date = dates.get(i).split("/");

            int first, second, third;
            if(date.length!=3){
                valid[i]=false; //not int/int/int
            }else{
                try{
                    first = Integer.parseInt(date[0]);
                    second = Integer.parseInt(date[1]);
                    third = Integer.parseInt(date[2]);

                    valid[i]=true;

                    if(date[0].length()==4){ //first entry is yyyy
                        values[4] += dateChecker(third, second, first, false); //4 - ymd
                        values[5] += dateChecker(second, third, first, false); //5 - ydm
                    }else if(date[1].length()==4){ //second entry is yyyy
                        values[1] += dateChecker(first, third, second, false); //1 - dym
                        values[3] += dateChecker(third, first, second, false); //3 - myd
                        
                    }else if(date[2].length()==4){ //third entry is yyyy
                        values[0] += dateChecker(first, second, third, false); //0 - dmy
                        values[2] += dateChecker(second, first, third, false); //2 - mdy
                    }else{ //year is in yy format
                        //note: could do more checks for single digit cases
                        values[0] += dateChecker(first, second, third, true); //0 - dmy
                        values[1] += dateChecker(first, third, second, true); //1 - dym
                        values[2] += dateChecker(second, first, third, true); //2 - mdy
                        values[3] += dateChecker(third, first, second, true); //3 - myd
                        values[4] += dateChecker(third, second, first, true); //4 - ymd
                        values[5] += dateChecker(second, third, first, true); //5 - ydm
                    }
    
                }catch(NumberFormatException e){
                    valid[i]=false; //not int/int/int
                }
            }
        }

        System.out.println(Arrays.toString(values));
        //Next: select most common format, then loop through and check if valid.

        for(int i=0; i<dates.size(); i++){
            if(!valid[i]){
                System.out.println(dates.get(i) + " -  INVALID: Not of the form int/int/int");
            }else{
                System.out.println(dates.get(i));
            }
        }
    }

    //return 1 if valid date
    //return 0 if invalid date
    private static int dateChecker(int dayInt, int monthInt, int yearInt, boolean yy) {
        if(yy){ //if yy format
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
            return 0;
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
            return 0;
        }

        return 1;
    }
}