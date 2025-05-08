import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Binarywriter {

    /**
     * Takes in an unsigned 32 bit integer and writes its binary value to a specifed output file. 
     * 
     */
    
    static String byteToHex(byte b) {
        int i = Byte.toUnsignedInt(b);
        String s = Integer.toHexString(i);
        return s;
    }
    public static void main(String[] args) {

        if(args.length == 2 && args[1].matches("[0-9]*")) {
            int data = Integer.parseUnsignedInt(args[1]);
            try( FileOutputStream fos = new FileOutputStream(args[0])) {
                byte b = (byte)(data >>> 24);
                System.out.println("Writing byte: " + byteToHex(b));
                fos.write(b);
                b = (byte)(data >>> 16);
                System.out.println("Writing byte: " + byteToHex(b));
                fos.write(b);
                b = (byte)(data >>> 8);
                System.out.println("Writing byte: " + byteToHex(b));
                fos.write(b);
                b = (byte)(data);
                System.out.println("Writing byte: " + byteToHex(b));
                fos.write(b);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Invalid! Please enter a filename and binary number(in form of an integer).");
            return;
        }
    }
}
