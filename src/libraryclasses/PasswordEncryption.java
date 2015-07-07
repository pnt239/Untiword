/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libraryclasses;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

/**
 *
 * @author Lilium Aikia
 */
public class PasswordEncryption 
{
    public static String encryptPassword(String password)
    {
        String sha1 = "";
        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(password.getBytes("UTF-8"));
            sha1 = byteToHex(crypt.digest());
        }
        catch(NoSuchAlgorithmException | UnsupportedEncodingException e)
        {
        }
        return sha1;
    }

    private static String byteToHex(final byte[] hash)
    {
        String result;
        try (Formatter formatter = new Formatter()) {
            for (byte b : hash)
            {
                formatter.format("%02x", b);
            }   result = formatter.toString();
        }
        return result;
    }
}
