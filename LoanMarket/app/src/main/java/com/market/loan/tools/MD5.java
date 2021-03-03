package com.market.loan.tools;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MD5 {

    public static String encode(String chars) {
        byte[] digestBytes;
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        assert md5 != null;
        digestBytes = md5.digest(chars.getBytes());

        StringBuilder hexBuilder = new StringBuilder(digestBytes.length * 2);
        for (byte i : digestBytes) {
            int tmp = i & 0xFF;
            if (tmp < 0x10) {
                hexBuilder.append("0");
            }
            hexBuilder.append(Integer.toHexString(tmp));
        }
        return hexBuilder.toString();
    }
}
