package com.nurzainpradana.koperasimasjid.util;

import com.nurzainpradana.koperasimasjid.view.registerone.RegisterOneAct;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EncryptMd5Java {
    public static String encryptMd5Java(String message) {
        String digest = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(message.getBytes(StandardCharsets.UTF_8));

            //merubah byte array ke dalam String Hexadecimal
            StringBuilder sb = new StringBuilder(2*hash.length);
            for(byte b : hash)
            {
                sb.append(String.format("%02x", b&0xff));
            }
            digest = sb.toString();
        } catch (
                NoSuchAlgorithmException ex)
        {
            Logger.getLogger(RegisterOneAct.class.getName()).log(Level.SEVERE, null, ex);
        }
        return digest;
    }
}
