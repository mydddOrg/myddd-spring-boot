package org.myddd.security.account;

import org.myddd.security.api.PasswordEncrypt;

import java.security.MessageDigest;

public class MD5PasswordEncrypt implements PasswordEncrypt {
    @Override
    public String encrypt(String password) {
        StringBuffer sb = new StringBuffer(32);

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(password.getBytes("utf-8"));
            for (int i = 0; i < array.length; i++) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).toUpperCase().substring(1, 3));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return password;
        }
        return sb.toString();
    }
}
