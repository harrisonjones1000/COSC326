import java.math.BigInteger;
import java.math.RoundingMode;
import java.math.BigDecimal;


public class IBMFloat32 {
    private BigDecimal value;
    private byte[] data;

    public IBMFloat32(byte[] data) {
        this.data = data;

        boolean ip = data.length==8;

        int sign = (data[0] & 0x80) >>> 7; //extracts the first bit
        int exp = data[0] & 0x7F; //extracts the exp

        byte[] fractionBytes = new byte[data.length]; 
        System.arraycopy(data, 1, fractionBytes, 1, data.length-1);   

        BigDecimal fraction = new BigDecimal(new BigInteger(fractionBytes));

        BigDecimal normalizedFraction = fraction.divide(
            new BigDecimal(BigInteger.ONE.shiftLeft(ip ? 56 : 24)),
            320,
            RoundingMode.HALF_EVEN
        );

        int exponentPower = exp - 64;
        BigDecimal scale;
        if (exponentPower >= 0) {
            scale = BigDecimal.valueOf(16).pow(exponentPower);
        } else {
            scale = BigDecimal.ONE.divide(
                BigDecimal.valueOf(16).pow(-exponentPower),
                320,
                RoundingMode.HALF_EVEN
            );
        }

        BigDecimal value = normalizedFraction.multiply(scale);
        
        if (sign == 1) {
           value = value.negate();
        }

        this.value = value;

        // System.out.println("Normalized Fraction: " + normalizedFraction);
        // System.out.println("Scale: " + scale);
        // System.out.println("Final Value : " + normalizedFraction.multiply(scale));
    }

    public float toFloat(){
        //System.out.println("Intermediate BigDecimal value: " + this.value);
        return this.value.floatValue();
    }

    public double toDouble(){ 
        //System.out.println("Intermediate BigDecimal value: " + this.value);
        return this.value.doubleValue();
    }

    // public float toFloat(){
    //     byte leadingBitMask = (byte) 0x80;
    //     for(int i = 0; i < 4; i++) {

    //         if((((byte)(getFraction()[0] << i)) & leadingBitMask) != 0) {
    //             //We found the leading one at i bitshifts and the decimal point should be now placed at i + 1.
    //             //The ibm exponent is in the form: (shifts/4) + 64.
    //             int IBMshifts = (getExp() - 64) * 4;
    //             //IEE exponent is in the form shifts: + 127.
    //             byte IEEexp =  (byte)((IBMshifts - (i + 1)) + 127);

    //             //IEE float 32: S   EEEEEEEE    FFFFFFFFFFFFFFFFFFFFFFF
                
    //             ByteBuffer floatBytes = ByteBuffer.allocate(4); 
    //             floatBytes.put((byte)(Byte.toUnsignedInt(getSign()) | (Byte.toUnsignedInt(IEEexp) >>> 1))); // SEEEEEEE
    //             floatBytes.put((byte)((byte)(Byte.toUnsignedInt(IEEexp) << 7) | (Byte.toUnsignedInt((byte)(Byte.toUnsignedInt( getFraction()[0]) << (i + 1) )) >>> 1) )); // EFFFFFFF
    //             floatBytes.put((byte)((byte)(Byte.toUnsignedInt(getFraction()[0]) << 7 + (i+1)) | (Byte.toUnsignedInt((byte)(Byte.toUnsignedInt( getFraction()[1]) << (i + 1) )) >>> 1) )); // FFFFFFFF
    //             floatBytes.put((byte)((byte)(Byte.toUnsignedInt(getFraction()[1]) << 7 + (i+1)) | (Byte.toUnsignedInt((byte)(Byte.toUnsignedInt( getFraction()[2]) << (i + 1) )) >>> 1) )); // FFFFFFFF
    //             floatBytes.position(0);
    //             return floatBytes.getFloat();
    //         }
    //     }
    //     return 0f;
    // }

    public String toBinaryString() {
        String s = "";
        byte leadingMask = (byte)0x80;
        for(int i = 0; i < data.length*8; i++) {
            int b = i / 8;
            if(( ((byte)(data[b] << (i % 8))) & leadingMask) != 0) {
                s = s + "1";
            } else {
                s = s + "0";
            }

        }
        return s;
    }

}
