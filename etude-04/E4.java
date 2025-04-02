import java.util.Scanner;

public class E4{
    public static void main(String[] args){
        String[] parts;
        Scanner testScan = new Scanner(System.in);
        while (testScan.hasNext()) {
            parts = testScan.nextLine().split(" ");
            System.out.println(method(parts[0], parts[1], Integer.parseInt(parts[2].substring(0,parts[2].length()-2)), Integer.parseInt(parts[3])));
        }
        testScan.close();
    }

    //Takes an old calendar, and must convert to new calendar
    private static String method(String time, String month, int day, int year){
        String[]  oldMonths ={"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        String[]  newMonths = {"January", "March", "April", "May", "June", "August", "September", "October", "November", "December"};

        String[] dow = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Xtraday", "Superday", "Holyday"};

        boolean isLeapYear = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);

        boolean morning = time.substring(time.length()-2).equals("am"); //both tests are pm

        int hour = Integer.parseInt((time.substring(0,time.length()-2)).split(":")[0]);
        int min = Integer.parseInt(time.substring(0, time.length()-2).split(":")[1]);

        int NH; 
        double a=0;
        int b;

        if(!morning){ //afternoon
            NH=5;
            
            if(hour!=12){
                System.out.println(hour);
                a = hour*5/12 + min/(12*60); //NH
                System.out.println(a);
                b = (int)Math.floor(a); 
                NH += b;
                a = a-b; 
                a = a*100; //NM
            }else{
                a=min*100/144;
            }
        }else{ //morning
            NH=0;
        }


        System.out.println(NH + ":" + (int)a);

        //31st Decemeber 2049 is a Friday.
        //all leap days will be added to December

        //NOTE: check if December is to have 36+5 days and 36+6 days in leap years
        //OR: if this calendar now lags. 

        return "";

        //return "time: " + time + "\nmonth: " + month + "\nday: " + day + "\nyear: " + year;
    }
}