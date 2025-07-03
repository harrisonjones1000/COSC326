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

        int totalDays = 1; //Jan 1st 2050, td=1

        int m = 1, d = 1, nm = 1, nd = 1;
        int y = 2050, ny = 2050;

        boolean leap = true;

        while(y<2102){
            if((y % 4 == 0 && y % 100 != 0) || (y % 400 == 0)){
                daysInMonths[1] = 29;
                leap = true;
            } else {
                daysInMonths[1] = 28;
                leap=false;
            }
            

            if(y<2101 || (y==2101 & m==1 & d==1)){
                System.out.println(d + " " + oldMonths[m-1] + " " + y + "\t ---> \t " + dow[(totalDays+4)%10]+ " " + nd + " " + newMonths[nm-1] + " " + ny);
                //System.out.println(dow[(totalDays+4)%10]+ " " + nd + " " + newMonths[nm-1] + " " + ny);
                //System.out.println(d + " " + oldMonths[m-1] + " " + y);

                // if (nd == 1) {  // New Month starts
                //     System.out.println("\n" + newMonths[nm-1] + " " + ny);
                //     System.out.println("-------------------------------------------");
                //     System.out.println("Mon Tue Wed Thu Fri Sat Xtr Sup Hol Sun");
                
                //     int dayIndex = (totalDays + 4) % 10;
                    
                //     // Print empty spaces for alignment
                //     for (int i = 0; i < dayIndex; i++) {
                //         System.out.print("    ");  // 4 spaces to match day format
                //     }
                // }
                
                // System.out.print(String.format("%3d ", nd));
                
                // if (dow[(totalDays + 4) % 10].equals("Sunday")) {
                //     System.out.println();  // Newline at end of week
                // }
            }
            
            if(daysInMonths[m-1] > d){
                d++;
            }else if(m == 12){
                m = 1;
                d = 1;
                y++;
            }else{
                d = 1;
                m++;
            }
            
            if(leap){
                if(nd < 36){
                    nd++;
                }else if(nm==10){
                    if(nd < 37){
                        nd++;
                    }else{
                        nd=1;
                        nm=1;
                        ny++;
                    }
                }else{
                    nd=1;
                    nm++;
                }
            }else{
                if(nd < 36){
                    nd++;
                }else if(nm==10){
                    nd = 1;
                    nm = 1;
                    ny++;
                }else{
                    nd = 1;
                    nm++;
                }
            }

            totalDays++;
        }
        return;
    }

    public static void easter(int y){
        int a = y % 19;
        int b = y % 4;
        int c = y % 7;
        int d = (19 * a + 24) % 30;
        int e = (2 * b + 4 * c + 6 * d + 5) % 7;
        int easter = 22 + d + e;
    
        if(easter > 31){
            System.out.println("Easter in " + y + ": April " + (easter - 31));
        } else {
            System.out.println("Easter in " + y + ": March " + easter);
        }
    }    
}