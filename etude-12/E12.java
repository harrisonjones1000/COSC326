import java.io.File;
import java.util.Scanner;

public class E12{
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        boolean ip, op; //false is single, true is double

        System.out.print("Input file: ");
        String inputFile = scanner.nextLine();
        
        File inFile = new File(inputFile);
        if (!inFile.exists() || !inFile.isFile()){
            System.out.println("Invalid");
            return;
        }

        System.out.print("Is your input file single precision (1) or double precision (2)?: ");
        String input = scanner.nextLine();
        if(input.equals("1")){
            ip = true;
        }else if(input.equals("2")){
            ip = false;
        }else{
            System.out.println("Invalid");
            return;
        }

        System.out.print("Output file: ");
        String output = scanner.nextLine();
        System.out.print("Do you want your output in single precision (1) or double precision (2)?: ");
        input = scanner.nextLine();
        if(input.equals("1")){
            op = true;
        }else if(input.equals("2")){
            op = false;
        }else{
            System.out.println("Invalid");
            return;
        }

    }
}