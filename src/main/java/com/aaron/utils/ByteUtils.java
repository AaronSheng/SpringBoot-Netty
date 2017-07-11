package com.aaron.utils;

public class ByteUtils {
    /**
     * fourBytes2Int method transfer 4 bytes to int.
     * @param bytes
     * @return
     */
    public static int fourBytes2Int(byte[] bytes) {
        if (bytes.length != 4) {
            throw new IllegalArgumentException("parameter's length must be 4");
        }

        int val = 0;
        val += ((bytes[3] & 0xFF) << 24);
        val += ((bytes[2] & 0xFF) << 16);
        val += ((bytes[1] & 0xFF) << 8);
        val += (bytes[0] & 0xFF);
        return val;
    }

    /**
     * int2FourBytes method transfer int to 4 bytes.
     * @param val
     * @return
     */
    public static byte[] int2FourBytes(int val) {
        byte[] bytes = new byte[4];
        bytes[3] = (byte) ((val & 0xFF000000)>>24);
        bytes[2] = (byte) ((val & 0x00FF0000)>>16);
        bytes[1] = (byte) ((val & 0x0000FF00)>>8);
        bytes[0] = (byte) ((val & 0x000000FF));
        return bytes;
    }

    /**
     * bytes2Hex method transfer byte array to hex string.
     * @param bytes
     * @return
     */
    public static String bytes2Hex(byte[] bytes) {
        StringBuffer sb = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toHexString((bytes[i] & 0xF0) >> 4));
            sb.append(Integer.toHexString((bytes[i] & 0x0F)));
        }
        return sb.toString();
    }
}
