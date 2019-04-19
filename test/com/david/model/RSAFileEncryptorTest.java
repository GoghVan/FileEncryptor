package com.david.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Gavin on 2019/4/19.
 */
public class RSAFileEncryptorTest {

    static String str1 = "G:/TestFile/RSA/start.txt";
    static String str2 = "G:/TestFile/RSA/middle.txt";
    static String str3 = "G:/TestFile/RSA/end.txt";

    @Test
    public void encryptFile() throws Exception {
        System.out.println("encryptFile start:");
        RSAFileEncryptor.encryptFile(str1, str2);
        System.out.println("encryptFile finish:");
    }

    @Test
    public void decryptedFile() throws Exception {
        System.out.println("decryptedFile start:");
        RSAFileEncryptor.decryptedFile(str2, str3);
        System.out.println("decryptedFile finish:");
    }

}