package com.aaron.utils;

public class StringUtils {
    public static String reverse(String string) {
        return new StringBuilder(string).reverse().toString();
    }

    public static String int2FourByteString(int num) {
        num %= 10000;
        String str = String.format("%04d", num);
        return str;
    }

    public static int string2Int(String string) {
        int num = -1;

        try {
            num = Integer.valueOf(string).intValue();
        } catch (NumberFormatException exception) {
            exception.printStackTrace();
        }

        return num;
    }

    public static String toHex(String string) {
        StringBuffer sb = new StringBuffer(2 * string.length());

        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            sb.append(Integer.toHexString((c & 0xF0) >> 4));
            sb.append(Integer.toHexString((c & 0x0F)));
        }

        return sb.toString();
    }

    public static String byte2Hex(byte[] bytes) {
        StringBuffer sb = new StringBuffer(bytes.length * 2);

        for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toHexString((bytes[i] & 0xF0) >> 4));
            sb.append(Integer.toHexString((bytes[i] & 0x0F)));
        }

        return sb.toString();
    }

    public static byte[] hex2Byte(String hex) {
        byte[] decBytes = new byte[hex.length()/2];

        for (int i = 0; i < decBytes.length; i++) {
            decBytes[i] = Byte.valueOf(hex.substring(2*i, 2*i+1), 16);
        }

        return decBytes;
    }
}