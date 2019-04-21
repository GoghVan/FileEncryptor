package com.david.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Gavin on 2019/4/21.
 */
public class AESFileEncryptorTest {
    String str1 = "F:/ShoolData/test/before.txt";
    String str2 = "F:/ShoolData/test/after.txt";
    String str3 = "F:/ShoolData/test/final.txt";

    @Test
    public void encryptFile() throws Exception {
        System.out.println("encryptFile start:");
        AESFileEncryptor.encryptFile(str1, str2);
        System.out.println("encryptFile finish:");
    }

    @Test
    public void decryptedFile() throws Exception {
        System.out.println("decryptedFile start:");
        AESFileEncryptor.decryptedFile(str2, str3);
        System.out.println("decryptedFile finish:");
    }

}