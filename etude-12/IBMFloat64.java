import java.nio.ByteBuffer;

public class IBMFloat64 {

    /*
     * S   EEEEEEE   FFFF FFFF FFFF FFFF FFFF FFFF
     * 31  30 - 24   23 ------------------------ 0 
     */

    private byte[] data;

    public IBMFloat64(byte[] data) {
        this.data = data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public byte[] getData() {
        return data;
    }

    public byte getExp() {
        byte mask = (byte)0x7F;
        return (byte)(data[0] & mask);
    }

    public void setExp(byte exp) {
        data[0] = (byte)(getSign() | exp);
    }

    public byte getSign() {
        byte mask = (byte)0x80;
        return (byte)(data[0] & mask);
    }

    public void setSign(byte sign) {
        data[0] = (byte)(sign | getExp());
    }

    public byte[] getFraction() {
        return new byte[] {data[1], data[2], data[3]};
    }

    public void setFraction(byte[] fraction) {
        data[1] = fraction[0];
        data[2] = fraction[1];
        data[3] = fraction[2];
    }

    public float toFloat(){
        return 0f;
    }

    public double toDouble(){ 
        return 0d;
    }
}