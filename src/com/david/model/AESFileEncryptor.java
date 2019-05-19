package com.david.model;

/**
 * Created by Gavin on 2019/4/18.
 */

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.util.Random;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;


public class AESFileEncryptor {

    /**
     * 文件加密的实现方法
     * fileName:需加密文件路径("G:/test/start.txt")
     * encryptedFileName:加密完文件路径("G:/test/middle.txt")
     */
    @SuppressWarnings("static-access")
    public static int encryptFile(String fileName,String encryptedFileName){
        try {
            System.out.println("\n\n\n...文件开始 AES 算法加密！");
            System.out.println("...正在读取待加密文件！");
            FileInputStream fileInputStream = new FileInputStream(fileName);
            FileOutputStream fileOutputStream = new FileOutputStream(encryptedFileName);

            //秘钥自动生成
            System.out.println("...正在生成密钥！");
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128);
            Key key = keyGenerator.generateKey();

            byte[] keyValue = key.getEncoded();
            //记录输入的加密密码的消息摘要
            fileOutputStream.write(keyValue);
            //恢复秘钥
            System.out.println("...正在加密密钥！");
            SecretKeySpec encryKey = new SecretKeySpec(keyValue, "AES");

            byte[] ivValue=new byte[16];
            Random random = new Random(System.currentTimeMillis());
            random.nextBytes(ivValue);
            //获取系统时间作为IV
            IvParameterSpec iv = new IvParameterSpec(ivValue);
            //文件标识符
            fileOutputStream.write("AESFileEncryptor".getBytes());
            //记录IV
            fileOutputStream.write(ivValue);

            Cipher cipher = Cipher.getInstance("AES/CFB/PKCS5Padding");
            cipher.init(cipher.ENCRYPT_MODE, encryKey, iv);
            System.out.println("...正在加密文件!");
            CipherInputStream cis = new CipherInputStream(fileInputStream, cipher);

            byte[] buffer = new byte[1024];
            int n = 0;
            while((n = cis.read(buffer))!= -1){
                fileOutputStream.write(buffer, 0, n);
            }
            cis.close();
            fileOutputStream.close();
            System.out.println("...文件加密完成!");
            return 1;
        } catch (Exception e) {
            System.out.println("...文件加密失败！");
            e.printStackTrace();
            return 0;
        }

    }


    /**
     * 文件解密的实现方法
     * encryptedFileName:已加密文件路径("G:/test/middle.txt")
     * decryptedFileName:解密文件路径("G:/test/end.txt")
     */

    @SuppressWarnings("static-access")
    public static int decryptedFile(String encryptedFileName,String decryptedFileName){

        try {
            System.out.println("\n\n\n...文件开始 AES 算法解密！");
            System.out.println("...正在读取待解密文件！");
            //文件输入流
            FileInputStream fileInputStream = new FileInputStream(encryptedFileName);
            // 文件输出流
            FileOutputStream fileOutputStream = new FileOutputStream(decryptedFileName);

            byte[] fileIdentifier = new byte[16];
            byte[] keyValue = new byte[16];
            System.out.println("...正在取出加密密钥!");
            //读记录的文件加密密码的消息摘要
            fileInputStream.read(keyValue);
            fileInputStream.read(fileIdentifier);

            if(new String (fileIdentifier).equals("AESFileEncryptor")){
                SecretKeySpec key= new SecretKeySpec(keyValue,"AES");
                byte[] ivValue= new byte[16];
                //获取IV值
                fileInputStream.read(ivValue);
                IvParameterSpec iv= new IvParameterSpec(ivValue);
                Cipher cipher = Cipher.getInstance("AES/CFB/PKCS5Padding");
                cipher.init(cipher.DECRYPT_MODE, key,iv);
                System.out.println("...正在解密文件!");
                CipherInputStream cis= new CipherInputStream(fileInputStream, cipher);
                byte[] buffer=new byte[1024];
                int n=0;
                while((n=cis.read(buffer))!=-1){
                    fileOutputStream.write(buffer,0,n);
                }
                cis.close();
                fileOutputStream.close();
                //跳出提示框
                //JOptionPane.showMessageDialog(null, "解密成功");
                System.out.println("...文件解密成功!");
                return 1;
            }else{
                System.out.println("...文件解密失败！");
                return 0;
                //跳出提示框
                //JOptionPane.showMessageDialog(null, "文件不是我加密的，爱找谁着谁去");
            }
        } catch (Exception e) {
            System.out.println("...文件解密失败！");
            e.printStackTrace();
            return 0;
        }
    }
}

