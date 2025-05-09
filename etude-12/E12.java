import java.io.*;
import java.nio.ByteBuffer;
import java.util.Scanner;

public class E12{
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        boolean ip, op; 

        File inFile = new File("test.bin");
        ip = false;
        String output = "output.bin";
        op = false;

        // System.out.print("Input file: ");
        // String inputFile = scanner.nextLine();
        
        // File inFile = new File(inputFile);
        // if (!inFile.exists() || !inFile.isFile()){
        //     System.out.println("Invalid");
        //     return;
        // }

        // System.out.print("Is your input file single precision (1) or double precision (2)?: ");
        // String input = scanner.nextLine();
        // if(input.equals("1")){
        //     ip = false; //single precision
        // }else if(input.equals("2")){
        //     ip = true; //double precision
        // }else{
        //     System.out.println("Invalid");
        //     return;
        // }

        // System.out.print("Output file: ");
        // String output = scanner.nextLine();
        // System.out.print("Do you want your output in single precision (1) or double precision (2)?: ");
        // input = scanner.nextLine();
        // if(input.equals("1")){
        //     op = false; //single precision
        // }else if(input.equals("2")){
        //     op = true; //double precision
        // }else{
        //     System.out.println("Invalid");
        //     return;
        // }

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
                        byte b = (byte)fileStream.read();
                        data[i] = b; 
                    }

                    if(!ip){ 
                        IBMFloat32 i = new IBMFloat32(data);
                        if(!op){
                            float f = IBMFloat32ToFloat(i);
                            out.writeFloat(f);

                            System.out.println("IBM Float  " + i.toBinaryString() + "  vs  IEEE Float: " + f) ;
                        }else{
                            double d = IBMFloat32ToDouble(i);
                            out.writeDouble(d);
                        }
                    }else{
                        //IBMFloat64 i = new IBMFloat64(data);
                        if(!op){
                            //float o = IBMFloat64ToFloat(i);
                        }else{
                            //double o = IBMFloat64ToDouble(i);
                        }
                    }
                }
            }
        }catch (IOException e) {
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