package com.david.model.ECCModel;


import javax.crypto.Cipher;
import javax.crypto.NullCipher;
import java.io.*;
import java.security.KeyFactory;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;


/**
 * Created by Gavin on 2019/4/20.
 */

public class ECCUtil implements Serializable {

    /**
     * 文件加密模块
     * @param data 要加密内容，以字节形式呈现
     * @param publicKey 公共秘钥
     * @return 返回字节类型数据
     * @throws Exception 抛出异常
     */
    public static byte[] encrypt(byte[] data, String publicKey) throws Exception{

        // 将公共秘钥进行解码
        byte[] keyBytes = BASE64Decoder.decodeBuffer(publicKey);

        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ECCEnum.ALGORITHM.value());
        ECPublicKey pubKey = (ECPublicKey) keyFactory.generatePublic(x509KeySpec);

        Cipher cipher = new NullCipher();
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);

        // 返回加密后数据
        return cipher.doFinal(data);
    }


    /**
     * 文件解密模块
     * @param data 待解密内容，以字节形式呈现
     * @param privateKey 私有秘钥
     * @return 返回字节类型数据
     * @throws Exception 抛出异常
     */
    public static byte[] decrypt(byte[] data, String privateKey) throws Exception {

        // 将私有秘钥进行解码
        byte[] keyBytes = BASE64Decoder.decodeBuffer(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ECCEnum.ALGORITHM.value());

        ECPrivateKey priKey = (ECPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);

        Cipher cipher = new NullCipher();
        cipher.init(Cipher.DECRYPT_MODE, priKey);

        // 返回解密后数据
        return cipher.doFinal(data);
    }
}
