
/**
 * Harrison Jones and Ollie Hurst
 * Notes: assume each text file has the same format throughout
 * 
 * Note: we are running it via javac Etuide2.java
 * java Etude2 (to run)
 */

import java.util.ArrayList;
import java.util.Scanner;

public class Etude2 {

    public static void main(String[] args) {
        ArrayList<String> dates = new ArrayList<>(); // Where we store our dates

        // Where we keep track of the format that works for each date
        int[] values = new int[6]; // 0 - dmy, 1 - dym, 2 - mdy, 3 - myd, 4 - ymd, 5 - ydm

        Scanner testScan = new Scanner(System.in);
        while (testScan.hasNext()) {
            dates.add(testScan.nextLine());
        }
        testScan.close();

        // where we store if our dates are valid int/int/int format
        boolean[] valid = new boolean[dates.size()];
        String[] date;

        for (int i = 0; i < dates.size(); i++) {
            date = dates.get(i).split("/");

            if (date.length != 3) {
                valid[i] = false; // not length of 3
            } else {
                String first = date[0];
                String second = date[1];
                String third = date[2];

                valid[i] = true;

                if (date[0].length() == 4) { // first entry is yyyy
                    if (dateChecker(third, second, first) == 1)
                        values[4] += 1; // 4 - ymd
                    if (dateChecker(second, third, first) == 1)
                        values[5] += 1; // 5 - ydm
                } else if (date[1].length() == 4) { // second entry is yyyy
                    if (dateChecker(first, third, second) == 1)
                        values[1] += 1; // 1 - dym
                    if (dateChecker(third, first, second) == 1)
                        values[3] += 1; // 3 - myd

                } else if (date[2].length() == 4) { // third entry is yyyy
                    if (dateChecker(first, second, third) == 1)
                        values[0] += 1; // 0 - dmy
                    if (dateChecker(second, first, third) == 1)
                        values[2] += 1; // 2 - mdy
                } else { // year is in yy format
                            // note: could do more checks for single digit cases for efficiency
                    if (dateChecker(first, second, third) == 1)
                        values[0] += 1; // 0 - dmy
                    if (dateChecker(first, third, second) == 1)
                        values[1] += 1; // 1 - dym
                    if (dateChecker(second, first, third) == 1)
                        values[2] += 1; // 2 - mdy
                    if (dateChecker(third, first, second) == 1)
                        values[3] += 1; // 3 - myd
                    if (dateChecker(third, second, first) == 1)
                        values[4] += 1; // 4 - ymd
                    if (dateChecker(second, third, first) == 1)
                        values[5] += 1; // 5 - ydm
                }
            }
        }

        //System.out.print(Arrays.toString(values));

        int maxIdx = 0; // most common format

        for (int i = 1; i < 6; i++) {
            if (values[i] > values[maxIdx])
                maxIdx = i;
        }

        String[] monthLetters = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };

        for (int i = 0; i < dates.size(); i++) {
            if (!valid[i]) { //spluts into 3.
                System.out.print(dates.get(i) + " -  INVALID: inputs are not of length 3\n");
            }else {

                date = dates.get(i).split("/");
                int output;
                String[] input2 = new String[3];

                if (maxIdx == 0) { // dmy
                    input2[0] = date[0];
                    input2[1]=date[1];
                    input2[2]=date[2];
                } else if (maxIdx == 1) { // dym
                    input2[0] = date[0];
                    input2[1]=date[2];
                    input2[2]=date[1];
                } else if (maxIdx == 2) { // mdy
                    input2[0] = date[1];
                    input2[1]=date[0];
                    input2[2]=date[2];
                } else if (maxIdx == 3) { // myd
                    input2[0] = date[2];
                    input2[1]=date[0];
                    input2[2]=date[1];
                } else if (maxIdx == 4) { // ymd
                    input2[0] = date[2];
                    input2[1]=date[1];
                    input2[2]=date[0];
                } else { // ydm
                    input2[0] = date[1];
                    input2[1]=date[2];
                    input2[2]=date[0];
                }

                output = dateChecker(input2[0], input2[1], input2[2]);    


                if (output == 1) {
                    int[] input = {Integer.parseInt(input2[0]), Integer.parseInt(input2[1]), Integer.parseInt(input2[2])};

                    boolean yy = input2[2].length()==2;
                    
                    int yearInt = input[2];
                    if (yy) {

                        if (yearInt < 49) {
                            yearInt = yearInt + 2000;
                        } else if (yearInt >= 50) {
                            yearInt = yearInt + 1900;
                        }
                    }

                    String dayString = "" + input[0];
                    if (dayString.length() < 2) {
                        dayString = "0" + dayString;
                    }
                    System.out.print(dayString + " " + monthLetters[input[1] - 1] + " " + yearInt + "\n");
                    
                }else if(output == -4){ //
                    System.out.print(dates.get(i) + " - INVALID: Inputs not numbers\n");
                }else if(output == -5){
                    System.out.print(dates.get(i) + " - INVALID: Strings of wrong length\n");
                }else if (output == -3) {
                    System.out.print(dates.get(i) + " - INVALID: Year out of range\n");
                }else if (output == -2) {
                    System.out.print(dates.get(i) + " - INVALID: Month out of range\n");
                }else {
                    System.out.print(dates.get(i) + " - INVALID: Day out of range\n");
                }
            }
        }
    }

    private static int dateChecker(String day, String month, String year) {
        int dayInt, monthInt, yearInt;
        try {
            dayInt = Integer.parseInt(day);
            monthInt = Integer.parseInt(month);
            yearInt = Integer.parseInt(year);
        }catch(NumberFormatException e){
            return -4; //number format exception
        }

        //

        int dlen = day.length();
        int mlen = month.length();
        int ylen = year.length();

        if (!((dlen == 1 || dlen == 2) && (mlen == 1 || mlen == 2) && (ylen == 2 || ylen == 4))){
            return -5; //wrong length of strings.
        }
        
        boolean yy = (year.length()==2);

        if(!yy){
            int count=0;
            for (int i = 0; i < year.length(); i++) {
                if (year.charAt(i) == '0') {
                    count++;
                }
            }
            //catches "0003" and "0000"
            if(count>2){
                if(year.charAt(0) == '0') return -5;
            }
        }

        if (yy) { // if yy format
            if (yearInt < 49) {
                yearInt = yearInt + 2000;
            } else if (yearInt >= 50) {
                yearInt = yearInt + 1900;
            }
        }

        boolean isLeapYear = false;
        // A leap year is every year evenly divisible by 4, except years evenly
        // divisible by 100, but not 400.
        if (yearInt % 4 == 0 && !((yearInt % 100 == 0) && !(yearInt % 400 == 0))) {
            isLeapYear = true;
        }

        if (yearInt >= 3000 || yearInt < 1753)
            return -3; // year out of range

        if (monthInt < 1 || monthInt > 12)
            return -2; // month out of range

        // the max number of days by month (if not leap year)
        int[] daysInMonths = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
        int maxDays;

        if (isLeapYear && monthInt == 2) {
            maxDays = 29;
        } else {
            maxDays = daysInMonths[monthInt - 1];
        }

        if (dayInt < 1 || dayInt > maxDays)
            return -1; // day out of range

        return 1; // valid date
    }
}