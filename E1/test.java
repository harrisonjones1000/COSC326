package E1;

import java.io.*;
import java.util.Scanner;

public class test {
    public static void main(String[] args) {
        try{
            Scanner testScan = new Scanner(new File("E1/E1_tests.txt"));
            boolean valid = true;
            while(testScan.hasNext()){
                String email = testScan.nextLine();
                for(int i = 0; i<email.length(); i++){
                    if(email.charAt(i)=='@'){
                        System.out.println("success");
                    }
                }
            }
            testScan.close();
        }catch(FileNotFoundException e){
            System.out.println("No file of that name found");
        }
    }
    
}