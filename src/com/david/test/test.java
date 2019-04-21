package com.david.test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by Gavin on 2019/4/20.
 */
public class test {
    public static void pictureEncryption(String fileName,String encryptedFileName) throws Exception{
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(fileName));
            bos = new BufferedOutputStream(new FileOutputStream(encryptedFileName));

            int b;
            while ((b = bis.read()) != -1) {
                bos.write(b ^ 123);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            bis.close();
            bos.close();
        }
    }

    public static void pictureDecryption(String encryptedFileName,String decryptedFileName) throws Exception{
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(encryptedFileName));
            bos = new BufferedOutputStream(new FileOutputStream(decryptedFileName));

            int b;
            while ((b = bis.read()) != -1) {
                bos.write(b ^ 123);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            bis.close();
            bos.close();
        }
    }
}
