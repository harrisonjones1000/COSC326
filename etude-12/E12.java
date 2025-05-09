import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.util.Scanner;

public class E12{
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        boolean ip, op; 

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
        File output = new File(scanner.nextLine());
        System.out.print("Do you want your output in single precision (1) or double precision (2)?: ");
        input = scanner.nextLine();
        if(input.equals("1")){
            op = false; //single precision
        }else if(input.equals("2")){
            op = true; //dobule precision
        }else{
            System.out.println("Invalid");
            return;
        }


        try {
            if (!output.exists()) {
                output.createNewFile();
            }

            PrintStream fileOut = new PrintStream(new FileOutputStream(output));
            System.setOut(fileOut);

        }catch (IOException e) {}

        


        try {
            FileInputStream fileStream = new FileInputStream(inFile);
            while(fileStream.available() > 0) {

                if(!ip) { //Single precision, 32 bit input
                    byte[] data = new byte[4];
                    for(int i = 0; i < 4; i++) {
                        if(fileStream.available() == 0) {
                            System.out.println("Error! The input data does not fall on the 4 byte boundary for IBM floats.");
                            return;
                        }
                        byte b = (byte)fileStream.read();
                        data[i] = b; 
                    }
                    
                    IBMFloat32 i = new IBMFloat32(data);
                    System.out.println("IBM32 binary: " + i.toBinaryString());

                    float o = IBMFloat32ToFloat(i);
                    System.out.println("Float32 binary: " + Integer.toBinaryString(Float.floatToIntBits(o)));
                    System.out.println("Float32 after conversion: " + o);


                }
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static float IBMFloat32ToFloat(IBMFloat32 ibm) {
        //count bit shifts until we find the leading 1. It has to be in the first 4 bits.
        byte leadingBitMask = (byte) 0x80;
        for(int i = 0; i < 4; i++) {

            if( ( ((byte)(ibm.getFraction()[0] << i)) & leadingBitMask) != 0) {
                //We found the leading one at i bitshifts and the decimal point should be now placed at i + 1.
                //The ibm exponent is in the form: (shifts/4) + 64.
                int IBMshifts = (ibm.getExp() - 64) * 4;
                //IEE exponent is in the form shifts: + 127.
                byte IEEexp =  (byte)((IBMshifts - (i + 1)) + 127);

                //IEE float 32: S   EEEEEEEE    FFFFFFFFFFFFFFFFFFFFFFF
                
                ByteBuffer floatBytes = ByteBuffer.allocate(4); 
                floatBytes.put((byte)(Byte.toUnsignedInt(ibm.getSign()) | (Byte.toUnsignedInt(IEEexp) >>> 1))); // SEEEEEEE
                floatBytes.put((byte)((byte)(Byte.toUnsignedInt(IEEexp) << 7) | (Byte.toUnsignedInt((byte)(Byte.toUnsignedInt( ibm.getFraction()[0]) << (i + 1) )) >>> 1) )); // EFFFFFFF
                floatBytes.put((byte)((byte)(Byte.toUnsignedInt(ibm.getFraction()[0]) << 7 + (i+1)) | (Byte.toUnsignedInt((byte)(Byte.toUnsignedInt( ibm.getFraction()[1]) << (i + 1) )) >>> 1) )); // FFFFFFFF
                floatBytes.put((byte)((byte)(Byte.toUnsignedInt(ibm.getFraction()[1]) << 7 + (i+1)) | (Byte.toUnsignedInt((byte)(Byte.toUnsignedInt( ibm.getFraction()[2]) << (i + 1) )) >>> 1) )); // FFFFFFFF
                floatBytes.position(0);
                return floatBytes.getFloat();
            }
        }
        return 0f;
    }

    public static double IBMFloat32ToDouble(IBMFloat32 ibm) {
        return 0d;
    }

    public static float IBMFloat64ToFloat() {
        return 0;
    }

    public static Double IBMFloat64ToDouble() {
        return 0d;
    }

    static String byteToHex(byte b) {
        int i = Byte.toUnsignedInt(b);
        String s = Integer.toHexString(i);
        return s;
    }
}