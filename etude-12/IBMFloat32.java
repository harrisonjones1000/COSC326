public class IBMFloat32 {

    /*
     * S   EEEEEEE   FFFF FFFF FFFF FFFF FFFF FFFF
     * 31  30 - 24   23 ------------------------ 0 
     */

    private byte[] data;

    public IBMFloat32(byte[] data) {
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

    public String toBinaryString() {
        String s = "";
        byte leadingMask = (byte)0x80;
        for(int i = 0; i < 32; i++) {
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
