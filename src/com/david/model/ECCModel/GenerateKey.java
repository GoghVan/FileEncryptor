package com.david.model.ECCModel;

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
    // 默认密钥对的存放地址
    static String ECCKeyFile = "G:/TestFile/ECC/ECCKey.xml";
    static {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    /**
     * 产生不同的秘钥对并且进行存储
     * @throws NoSuchProviderException 抛出异常
     * @throws NoSuchAlgorithmException 抛出异常
     *
     */
    public static void saveKeyPair() throws NoSuchProviderException, NoSuchAlgorithmException {
        try {
            System.out.println("...密钥对正在产生！");
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ECCEnum.ALGORITHM.value(),
                    ECCEnum.PROVIDER.value());
            keyPairGenerator.initialize(256, new SecureRandom());
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            System.out.println("...密钥对已经产生！");
            FileOutputStream fileOutputStream = new FileOutputStream(ECCKeyFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(keyPair);
            objectOutputStream.close();
            System.out.println("...密钥对已存入ECCKey.xml文件中！");

        } catch (Exception e) {
            System.out.println("...密钥对未存入ECCKey.xml文件中！");
            e.printStackTrace();
        }
    }

    /**
     * 从文件中取出密钥对
     * @return 返回KeyPair类型的数据
     * @throws NoSuchProviderException 抛出异常
     */
    public static KeyPair getKeyPair() throws NoSuchProviderException{
        //产生新密钥对
        KeyPair keyPair = null;
        try{
            System.out.println("...秘钥正在从ECCKey.xml文件中取出！");
            FileInputStream fileInputStream = new FileInputStream(ECCKeyFile);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            //读出密钥对
            keyPair = (KeyPair)objectInputStream.readObject();
            objectInputStream.close();
            System.out.println("...秘钥对已从ECCKey.xml文件中取出！");
        }catch (Exception e){
            System.out.println("...读取密钥对出错！");
            e.printStackTrace();
        }
        return keyPair;
    }

    /**
     * 将得到的keyPair分解为publicKey与privateKey并存入map中
     * @return 返回map类型数据
     * @throws NoSuchProviderException 抛出异常
     * @throws NoSuchAlgorithmException 抛出异常
     */
    public static Map<String, String> getGenerateKey() throws NoSuchProviderException, NoSuchAlgorithmException{
        KeyPair keyPair = getKeyPair();
        ECPublicKey publicKey = (ECPublicKey) keyPair.getPublic();
        ECPrivateKey privateKey = (ECPrivateKey) keyPair.getPrivate();
        Map<String, String> map = new HashMap<>();

        // 将私钥与公钥进行加密然后再存入map中
        map.put(ECCEnum.PRIVATE_KEY.value(), BASE64Encoder.encodeBuffer(privateKey.getEncoded()));
        map.put(ECCEnum.PUBLIC_KEY.value(), BASE64Encoder.encodeBuffer(publicKey.getEncoded()));
        return map;
    }

}
