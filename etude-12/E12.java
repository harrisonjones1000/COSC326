import java.io.File;
import java.io.FileNotFoundException;
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
            ip = false;
        }else if(input.equals("2")){
            ip = true;
        }else{
            System.out.println("Invalid");
            return;
        }

        System.out.print("Output file: ");
        String output = scanner.nextLine();
        System.out.print("Do you want your output in single precision (1) or double precision (2)?: ");
        input = scanner.nextLine();
        if(input.equals("1")){
            op = false;
        }else if(input.equals("2")){
            op = true;
        }else{
            System.out.println("Invalid");
            return;
        }

        try {
            Scanner fileScanner = new Scanner(inFile);
            while(fileScanner.hasNextByte()) {

                if(!ip) {
                    // 32 bit input.
                    byte[] data = new byte[4];
                    for(int i = 0; i < 4; i++) {
                        if(!fileScanner.hasNextByte()) {
                            System.out.println("Error! The input data does not fall on the 4 byte boundary for IBM floats.");
                            return;
                        }
                        byte b = fileScanner.nextByte();
                        data[i] = b; 
                    }
                    IBMFloat32 i = new IBMFloat32(data);

                    System.out.println(i.getData());

                }
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    public static float IBMFloat32ToFloat(IBMFloat32 i) {
        return 0;
    }

    public static double IBMFloat32ToDouble(IBMFloat32 i) {
        return 0d;
    }

    public static float IBMFloat64ToFloat() {
        return 0;
    }

    public static Double IBMFloat64ToDouble() {
        return 0d;
    }
}