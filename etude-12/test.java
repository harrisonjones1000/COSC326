import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Scanner;

public class test {
    public static void main(String[] args){
        int maxBytes = 4;
        float value;

        try(FileInputStream fileStream = new FileInputStream("output.bin")) {
            while(fileStream.available() > 0) {

                byte[] data = new byte[maxBytes];
                for(int i = 0; i < maxBytes; i++) {
                    if(fileStream.available() == 0) {
                        System.out.println("End of file cuts current IBM input short");
                        return; 
                    }
                    data[i] = (byte)fileStream.read(); 
                }
                value = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN).getFloat();

                System.out.println(value);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
