package com.david.action;

import com.david.model.ECCFileEncryptor;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Gavin on 2019/6/1.
 */
public class FileDencryptorActionTest {
    static String str2 = "G:/TestFile/AES/middle.txt";
    static String str3 = "G:/TestFile/AES/end.txt";
    @Test
    public void execute() throws Exception {
        ECCFileEncryptor.decryptedFile(str2,str3);
    }

}