import java.io.*;
import java.nio.ByteBuffer;
import java.util.Scanner;

public class E12{
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        boolean ip, op; 

        // File inFile = new File("double_118_625.bin");
        // ip = true;
        // String output = "output.bin";
        // op = false;

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
            ip = false; //single precision
        }else if(input.equals("2")){
            ip = true; //double precision
        }else{
            System.out.println("Invalid");
            return;
        }

        System.out.print("Output file: ");
        String output = scanner.nextLine();
        System.out.print("Do you want your output in single precision (1) or double precision (2)?: ");
        input = scanner.nextLine();
        if(input.equals("1")){
            op = false; //single precision
        }else if(input.equals("2")){
            op = true; //double precision
        }else{
            System.out.println("Invalid");
            return;
        }

        try(FileInputStream fileStream = new FileInputStream(inFile)) {
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(output))) {
            int maxBytes;
            while(fileStream.available() > 0) {
                maxBytes = 4;
                if(ip) maxBytes = 8;

                byte[] data = new byte[maxBytes];
                for(int i = 0; i < maxBytes; i++) {
                    if(fileStream.available() == 0) {
                        System.out.println("End of file cuts current IBM input short");
                        return; 
                    }
                    data[i] = (byte)fileStream.read(); 
                }

                IBMFloat32 ibm = new IBMFloat32(data);

                if (!op) { // Convert to Float
                    float f = ibm.toFloat();
                    System.out.println("IEEE Float: " + f + " IBM Binary : " + ibm.toBinaryString());

                    int bits = Float.floatToIntBits(f);
                    out.writeByte(bits & 0xFF);
                    out.writeByte((bits >> 8) & 0xFF);
                    out.writeByte((bits >> 16) & 0xFF);
                    out.writeByte((bits >> 24) & 0xFF);

                } else { // Convert to Double
                    double d = ibm.toDouble();
                    System.out.println("IEEE Double: " + d + " IBM Binary : " + ibm.toBinaryString());

                    long bits = Double.doubleToLongBits(d);
                    out.writeByte((int)(bits & 0xFF));
                    out.writeByte((int)((bits >> 8) & 0xFF));
                    out.writeByte((int)((bits >> 16) & 0xFF));
                    out.writeByte((int)((bits >> 24) & 0xFF));
                    out.writeByte((int)((bits >> 32) & 0xFF));
                    out.writeByte((int)((bits >> 40) & 0xFF));
                    out.writeByte((int)((bits >> 48) & 0xFF));
                    out.writeByte((int)((bits >> 56) & 0xFF));
                }

            }
        }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}