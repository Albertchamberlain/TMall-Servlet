package com.amos.utils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Amos
 * @date 8/5/2020 7:07 PM
 */
public class PasswordUtil {
    /**MD5 Hash
     * @param password 未hash的原始密码
     * @return hash后的密码
     */
    public static String md5Password(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(password.getBytes());
            StringBuffer buffer = new StringBuffer();
            for (byte b : result) {
                int number = b & 0xff;
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(str);
            }
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }
}
