package com.david.model;

import org.apache.commons.codec.binary.Base64;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import java.io.*;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by Gavin on 2019/4/17.
 */

public class RSAFileEncryptor {

    // 默认RSAKey.xml文件存放处
    static String RSAKeyFile = "G:/TestFile/RSA/RSAKey.xml";

    /**
     * 随机生成密钥对并存到RSAKey.xml文件中
     * @throws NoSuchAlgorithmException
     */
    public static void saveKeyPair() throws NoSuchAlgorithmException {
        try{
            // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            // 初始化密钥对生成器，密钥大小为96-1024位
            keyPairGen.initialize(1024,new SecureRandom());
            // 生成一个密钥对，保存在keyPair中
            KeyPair keyPair = keyPairGen.generateKeyPair();
            System.out.println("密钥对已产生！");
            // 打开文件输入流
            FileOutputStream fileOutputStream = new FileOutputStream(RSAKeyFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            //将密钥对keypair存入RSAKey.xml文件中
            objectOutputStream.writeObject(keyPair);
            objectOutputStream.close();
            System.out.println("密钥对已存入RSAKey.xml文件中！");
        }catch (Exception e){
            System.out.println("密钥未存入RSAKey.xml文件中！");
            e.printStackTrace();
        }
    }

    /**
     * 将RSAKey.xml中的密钥对取出
     * @return KeyPair返回对称秘钥
     */
    public static KeyPair getKeyPair(){
        //产生新密钥对
        KeyPair keyPair = null;
        try{
            FileInputStream fileInputStream = new FileInputStream(RSAKeyFile);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            keyPair = (KeyPair)objectInputStream.readObject(); //读出密钥对
            objectInputStream.close();
            System.out.println("秘钥对已取出！");
        }catch (Exception e){
            System.out.println("读取密钥对出错！");
            e.printStackTrace();
        }
        return keyPair;
    }
    /**
     * RSA公钥加密
     * pubKey 公钥
     * FileName:需加密文件路径("G:/TestFile/RSA/start.txt")
     * encryptedFileName:加密完文件路径("G:/TestFile/RSA/middle.txt")
     * @throws Exception 加密过程中的异常信息
     */
    public static void encryptFile(String fileName,String encryptedFileName) throws Exception{

        saveKeyPair(); //每加密一个文件就产生一次密钥对
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;

        try{

            RSAPublicKey publicKey = (RSAPublicKey) getKeyPair().getPublic();  // 得到公钥
            String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
            //base64编码的公钥
            byte[] decoded = Base64.decodeBase64(publicKeyString);
            RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
            //RSA加密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);

            fileInputStream = new FileInputStream(fileName);
            fileOutputStream = new FileOutputStream(encryptedFileName);

            CipherInputStream cis = new CipherInputStream(fileInputStream, cipher);
            byte[] buffer = new byte[1024];
            int n = 0;
            // 将加密文件写入文件中
            while((n = cis.read(buffer))!= -1){
                fileOutputStream.write(buffer, 0, n);
            }
            cis.close();
            fileOutputStream.close();
            System.out.println("文件加密成功！");
        }catch (Exception e){
            System.out.println("文件加密失败！");
            throw e;
        }finally {
            try {
                if (fileOutputStream != null){
                    fileInputStream.close();
                }
                if (fileInputStream != null){
                    fileInputStream.close();
                }
            }catch (Exception e){
                System.out.println("源文件关闭失败!");
                e.printStackTrace();
            }
        }
    }

    /**
     * RSA私钥解密
     *
     * @param encryptedFileName:已加密文件路径("G:/test/middle.txt")
     * @param decryptedFileName:解密文件路径("G:/test/end.txt")
     * @throws Exception 加密过程中的异常信息
     */
    public static void decryptedFile(String encryptedFileName,String decryptedFileName)throws Exception{

        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;

        try{

            RSAPrivateKey privateKey = (RSAPrivateKey) getKeyPair().getPrivate();   // 得到私钥
            String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));
            //base64编码的公钥
            byte[] decoded = Base64.decodeBase64(privateKeyString);
            RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));            //RSA加密
            // RSA解密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, priKey);

            fileInputStream = new FileInputStream(encryptedFileName);
            fileOutputStream = new FileOutputStream(decryptedFileName);

            CipherInputStream cis = new CipherInputStream(fileInputStream, cipher);
            byte[] buffer = new byte[1024];
            int n = 0;
            // 将解密文件写入文件
            while((n = cis.read(buffer))!= -1){
                fileOutputStream.write(buffer, 0, n);
            }
            cis.close();
            fileOutputStream.close();
            System.out.println("加密文件解密成功!");

        }catch (Exception e){
            System.out.println("加密文件解密失败!");
            throw e;
        }finally {
            try {
                if (fileOutputStream != null){
                    fileInputStream.close();
                }
                if (fileInputStream != null){
                    fileInputStream.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
