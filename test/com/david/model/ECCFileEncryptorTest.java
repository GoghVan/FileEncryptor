package com.david.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Gavin on 2019/4/20.
 */
public class ECCFileEncryptorTest {
    static String str1 = "G:/TestFile/AES/start.txt";
    static String str2 = "G:/TestFile/AES/middle.txt";
    static String str3 = "G:/TestFile/AES/end.txt";
    @Test
    public void encryptFile() throws Exception {
        System.out.println("encryptFile start:");
        ECCFileEncryptor.encryptFile(str1, str2);
        System.out.println("encryptFile finish:");
    }

    @Test
    public void decryptedFile() throws Exception {
        System.out.println("decryptedFile start:");
        ECCFileEncryptor.decryptedFile(str2, str3);
        System.out.println("decryptedFile finish:");
    }

}