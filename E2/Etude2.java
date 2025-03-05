
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
        String[] date;


        for(int i=0; i<dates.size(); i++){
            date = dates.get(i).split("/");

            int first, second, third;
            if(date.length!=3){
                valid[i]=false; //not int/int/int
            }else{
                try{
                    first = Integer.parseInt(date[0]);
                    second = Integer.parseInt(date[1]);
                    third = Integer.parseInt(date[2]);

                    valid[i]=true;

                    //Note: need to check for cases such as 00003/002/2004

                    if(date[0].length()==4){ //first entry is yyyy
                        if(dateChecker(third, second, first, false)==1) values[4] += 1; //4 - ymd
                        if(dateChecker(second, third, first, false)==1) values[5] += 1; //5 - ydm
                    }else if(date[1].length()==4){ //second entry is yyyy
                        if(dateChecker(first, third, second, false)==1) values[1] += 1; //1 - dym
                        if(dateChecker(third, first, second, false)==1) values[3] += 1; //3 - myd
                        
                    }else if(date[2].length()==4){ //third entry is yyyy
                        if(dateChecker(first, second, third, false)==1) values[0] += 1; //0 - dmy
                        if(dateChecker(second, first, third, false)==1) values[2] += 1; //2 - mdy
                    }else{ //year is in yy format
                        //note: could do more checks for single digit cases for efficiency
                        if(dateChecker(first, second, third, true)==1) values[0] += 1; //0 - dmy
                        if(dateChecker(first, third, second, true)==1) values[1] += 1; //1 - dym
                        if(dateChecker(second, first, third, true)==1) values[2] += 1; //2 - mdy
                        if(dateChecker(third, first, second, true)==1) values[3] += 1; //3 - myd
                        if(dateChecker(third, second, first, true)==1) values[4] += 1; //4 - ymd
                        if(dateChecker(second, third, first, true)==1) values[5] += 1; //5 - ydm
                    }
    
                }catch(NumberFormatException e){
                    valid[i]=false; //not int/int/int
                }
            }
        }

        System.out.println(Arrays.toString(values));

        int maxIdx=0; //most common format
        //0 - dmy, 1 - dym, 2 - mdy, 3 - myd, 4 - ymd, 5 - ydm
        for(int i=1; i<6; i++){
            if(values[i]>values[maxIdx]) maxIdx=i;
        }

        int[] input = new int[3];
        boolean yy;

        for(int i=0; i<dates.size(); i++){
            if(!valid[i]){ //if not int/int/int
                System.out.println(dates.get(i) + " -  INVALID: Not of the form int/int/int");
            }else{
                date = dates.get(i).split("/");
                if(maxIdx==0){
                    input[0]=Integer.parseInt(date[0]);
                    input[1]=Integer.parseInt(date[1]);
                    input[2]=Integer.parseInt(date[2]);
                    yy= date[2].length()==2;
                }else if(maxIdx==1){
                    input[0]=Integer.parseInt(date[0]);
                    input[1]=Integer.parseInt(date[2]);
                    input[2]=Integer.parseInt(date[1]);
                    yy= date[1].length()==2;
                }else if(maxIdx==2){
                    input[0]=Integer.parseInt(date[1]);
                    input[1]=Integer.parseInt(date[0]);
                    input[2]=Integer.parseInt(date[2]);
                    yy= date[2].length()==2;
                }else if(maxIdx==3){
                    input[0]=Integer.parseInt(date[2]);
                    input[1]=Integer.parseInt(date[0]);
                    input[2]=Integer.parseInt(date[1]);
                    yy= date[1].length()==2;
                }else if(maxIdx==4){
                    //0 - dmy, 1 - dym, 2 - mdy, 3 - myd, 4 - ymd, 5 - ydm
                    input[0]=Integer.parseInt(date[2]);
                    input[1]=Integer.parseInt(date[1]);
                    input[2]=Integer.parseInt(date[0]);
                    yy= date[0].length()==2;
                }else{
                    input[0]=Integer.parseInt(date[1]);
                    input[1]=Integer.parseInt(date[2]);
                    input[2]=Integer.parseInt(date[0]);
                    yy= date[0].length()==2;
                }
                
                int output= dateChecker(input[0], input[1], input[2], yy);

                if(output==1){
                    System.out.println(dates.get(i));
                }else if(output==-3){
                    System.out.println(dates.get(i) + " - INVALID: Year out of range");
                }else if(output==-2){
                    System.out.println(dates.get(i) + " - INVALID: Month out of range");
                }else{
                    System.out.println(dates.get(i) + " - INVALID: Day out of range");
                }

                
            }
        }
    }

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

        if (yearInt > 3000 || yearInt < 1753) return -3; //year out of range

        if (monthInt < 1 || monthInt > 12) return -2; // month out of range

        // the max number of days by month (if not leap year)
        int[] daysInMonths = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
        int maxDays;

        if (isLeapYear && monthInt == 2) {
            maxDays = 29;
        } else {
            maxDays = daysInMonths[monthInt];
        }

        if (dayInt < 1 || dayInt > maxDays) return -1; //day out of range

        return 1; //valid date
    }
}