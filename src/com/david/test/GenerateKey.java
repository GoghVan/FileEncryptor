package com.david.test;

/**
 * Created by Gavin on 2019/4/20.
 */

import org.bouncycastle.jce.interfaces.ECPrivateKey;
import org.bouncycastle.jce.interfaces.ECPublicKey;

import java.io.*;
import java.security.*;
import java.util.HashMap;
import java.util.Map;


public class GenerateKey implements Serializable {
    static String ECCKeyFile = "G:/TestFile/ECC/ECCKey.xml";
    static {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    public static void saveKeyPair() throws NoSuchProviderException, NoSuchAlgorithmException {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ECCEnum.ALGORITHM.value(),
                    ECCEnum.PROVIDER.value());
            keyPairGenerator.initialize(256, new SecureRandom());
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            System.out.println("密钥对已产生！");
            FileOutputStream fileOutputStream = new FileOutputStream(ECCKeyFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(keyPair);
            objectOutputStream.close();
            System.out.println("密钥对已存入ECCKey.xml文件中！");

        } catch (Exception e) {
            System.out.println("密钥对未存入ECCKey.xml文件中！");
            e.printStackTrace();
        }
    }

    public static KeyPair getKeyPair() throws NoSuchProviderException, NoSuchProviderException{
        //产生新密钥对
        KeyPair keyPair = null;
        try{
            FileInputStream fileInputStream = new FileInputStream(ECCKeyFile);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            keyPair = (KeyPair)objectInputStream.readObject(); //读出密钥对
            objectInputStream.close();
            System.out.println("秘钥对已从ECCKey.xml文件中取出！");
        }catch (Exception e){
            System.out.println("读取密钥对出错！");
            e.printStackTrace();
        }
        return keyPair;
    }

    public static Map<String, String> getGenerateKey() throws NoSuchProviderException, NoSuchAlgorithmException{
        KeyPair keyPair = getKeyPair();
        ECPublicKey publicKey = (ECPublicKey) keyPair.getPublic();
        ECPrivateKey privateKey = (ECPrivateKey) keyPair.getPrivate();
        Map<String, String> map = new HashMap<>();

        map.put(ECCEnum.PRIVATE_KEY.value(), BASE64Encoder.encodeBuffer(privateKey.getEncoded()));
        map.put(ECCEnum.PUBLIC_KEY.value(), BASE64Encoder.encodeBuffer(publicKey.getEncoded()));
        return map;
    }

}
