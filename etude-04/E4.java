public class E4{
    public static void main(String[] args){
        method();

        //easter(2050);
    }

    //31st Decemeber 2049 is a Friday.
    //all leap days will be added to December

    //Takes an old calendar, and must convert to new calendar
    private static void method(){
        String[]  oldMonths ={"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        int[] daysInMonths = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

        String[]  newMonths = {"January", "March", "April", "May", "June", "August", "September", "October", "November", "December"};

        String[] dow = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Xtraday", "Superday", "Holyday", "Sunday"};

        int totalDays = 1;
        int m = 0;
        int d = 1;
        int y = 2050;

        int nm = 0;
        int nd = 1;
        int ny = 2050;

        while(y<2053){
            if(y==2050){
                //System.out.println(d + " " + oldMonths[m] + " " + y + "\t ---> \t " + dow[(totalDays+4)%10]+ " " + nd + " " + newMonths[nm] + " " + ny);
                System.out.println(dow[(totalDays+4)%10]+ " " + nd + " " + newMonths[nm] + " " + ny);
                //System.out.println(d + " " + oldMonths[m] + " " + y);
            }

            if(nd<36){
                nd++;
            }else if(nm == 9){
                if(((y % 4 == 0 && y % 100 != 0) || (y % 400 == 0)) && nd == 36){
                    nd++;
                }else{
                    ny++;
                    nm=0;
                    nd=1;
                }
            }else{
                nm++;
                nd=1;
            }
            
            if(daysInMonths[m]>d){
                d++;
            }else if(m==11){
                y++;
                m=0;
                d=1;
                if((y % 4 == 0 && y % 100 != 0) || (y % 400 == 0)){
                    daysInMonths[1]++;
                }else if(((y+1) % 4 == 0 && (y+1) % 100 != 0) || ((y+1) % 400 == 0)){
                    daysInMonths[1]--;
                }
            }else{
                m++;
                d=1;
            }

            totalDays++;
        }

        return;
    }

    public static void easter(int y){
        int a = y%19;
        int b = y%4;
        int c = y%7;
        int d = (19*a + 24)%30;
        int e = (2*b + 4*c + 6*d +5) % 7;
        int easter = 22 + d + e;

        if(easter>31){
            System.out.println("April " + easter%31);
        }else{
            System.out.println("March " + easter);
        }
    }
}