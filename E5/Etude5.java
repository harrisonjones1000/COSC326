import java.io.*;
import java.util.*;

public class Etude5 {
    public static void main(String[] args) {
        try{
            if(args.length==0){
                System.out.println("No test file provided");
            }else{
                Scanner testScan = new Scanner(new File(args[0]));
                if(args.length==1){
                    while(testScan.hasNext()) System.out.print(testScan.nextLine());
                }else if(args.length==2){
                    try (FileWriter writer = new FileWriter(args[1], false)) {
                        while(testScan.hasNext()) writer.write(testScan.nextLine());
                    } catch (IOException e) {}
                      
                }else{
                    System.out.println("Invalid input");
                }
                testScan.close();
            }  
        }catch(FileNotFoundException e){
            System.out.println("No file of that name found");
        }
    }
}
