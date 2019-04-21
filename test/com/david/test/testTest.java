package com.david.test;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Gavin on 2019/4/21.
 */
public class testTest {
    String str1 = "G:/TestFile/PIC/start.jpg";
    String str2 = "G:/TestFile/PIC/middle.jpg";
    String str3 = "G:/TestFile/PIC/end.jpg";

    @Test
    public void pictureEncryption() throws Exception {
        System.out.println("encryptFile start:");
        test.pictureEncryption(str1, str2);
        System.out.println("encryptFile finish:");
    }

    @Test
    public void pictureDecryption() throws Exception {
        System.out.println("decryptedFile start:");
        test.pictureDecryption(str2, str3);
        System.out.println("decryptedFile finish:");
    }

}