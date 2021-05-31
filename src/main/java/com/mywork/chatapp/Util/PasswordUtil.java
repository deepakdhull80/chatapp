package com.mywork.chatapp.Util;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtil {

    private static final String ENCRYPTION_ALGO = "SHA-256";

    private static byte[] salt(){
        return "strongHashed".getBytes();
    }

    public static String EncrpytPassword(String unHashedPassword) throws NoSuchAlgorithmException, IOException {
        MessageDigest messageDigest = MessageDigest.getInstance(ENCRYPTION_ALGO);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(unHashedPassword.getBytes());

        outputStream.write(salt());

        return DatatypeConverter.printHexBinary(messageDigest.digest(outputStream.toByteArray()));
    }

    /*

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        System.out.println(EncrpytPassword("deepak"));
        System.out.println(EncrpytPassword("deepak"));
    }
    */
}
