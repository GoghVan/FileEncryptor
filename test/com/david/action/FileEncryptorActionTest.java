package com.david.action;

import com.david.model.AESFileEncryptor;
import com.david.model.ECCFileEncryptor;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Gavin on 2019/6/1.
 */
public class FileEncryptorActionTest {
    static String str1 = "G:/TestFile/AES/start.txt";
    static String str2 = "G:/TestFile/AES/middle.txt";
    @Test
    public void execute() throws Exception {
        ECCFileEncryptor.encryptFile(str1,str2);
    }

}