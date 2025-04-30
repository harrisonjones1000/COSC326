import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class testGen {
    public static void main(String[] args){
        boolean isDouble = Integer.parseInt(args[0])==1;
        int n = Integer.parseInt(args[0]); 
        Random rand = new Random();

        try (FileOutputStream out = new FileOutputStream("test.bin")) {
            for (int i = 0; i < n; i++) {
                if (isDouble) {
                    double value = rand.nextDouble() * 1e5;
                    byte[] ibm = doubleToIBMBytes(value);
                    out.write(ibm);
                }else{
                    float value = rand.nextFloat() * 1e5f;
                    byte[] ibm = floatToIBMBytes(value);
                    out.write(ibm);
                }
            }
        }catch(FileNotFoundException e){
            return;
        }catch(IOException e){
            return;
        }
    }

    private static byte[] floatToIBMBytes(float value) {
        return new byte[4];
    }

    private static byte[] doubleToIBMBytes(double value) {
        return new byte[8];
    }

}
