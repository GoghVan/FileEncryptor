package com.david.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Gavin on 2019/4/21.
 */
public class PICEncryptorTest {
    String str1 = "G:/TestFile/PIC/start3.jpg";
    String str2 = "G:/TestFile/PIC/middle3.jpg";
    String str3 = "G:/TestFile/PIC/end3.jpg";
    @Test
    public void encryptFile() throws Exception {
        System.out.println("encryptFile start:");
        PICEncryptor.encryptFile(str1, str2);
        System.out.println("encryptFile finish:");
    }

    @Test
    public void decryptedFile() throws Exception {
        System.out.println("decryptFile start:");
        PICEncryptor.decryptedFile(str2, str3);
        System.out.println("decryptFile finish:");
    }

}