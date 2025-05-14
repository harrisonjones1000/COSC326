import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/*
 * Generates random bytes and outputs them to test.bin
 */

public class testGen {
    public static void main(String[] args){
        boolean isFloat=true;

        int maxBytes = 8;
        if(isFloat) maxBytes = 4;

        try (FileOutputStream out = new FileOutputStream("test.bin")) {
            for (int i = 0; i < 100; i++){
                byte[] bytes = new byte[maxBytes];

                for(int j=0; j<maxBytes; j++){
                    bytes[j] = (byte) ThreadLocalRandom.current().nextInt(256);
                }

                out.write(bytes);
            }
        }catch(FileNotFoundException e){
            return;
        }catch(IOException e){
            return;
        }
    }



}
