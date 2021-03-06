package com.david.test.ECCtest;


import com.david.test.ECCtest.BASE64Decoder;
import com.david.test.ECCtest.ECCEnum;

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
     * 加密
     *
     * @param data
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data, String publicKey) throws Exception{

        byte[] keyBytes = BASE64Decoder.decodeBuffer(publicKey);

        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ECCEnum.ALGORITHM.value());

        ECPublicKey pubKey = (ECPublicKey) keyFactory.generatePublic(x509KeySpec);

        Cipher cipher = new NullCipher();
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);

        return cipher.doFinal(data);
    }


    /**
     * 解密
     *
     * @param data
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(byte[] data, String privateKey) throws Exception {
        byte[] keyBytes = BASE64Decoder.decodeBuffer(privateKey);

        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ECCEnum.ALGORITHM.value());

        ECPrivateKey priKey = (ECPrivateKey) keyFactory
                .generatePrivate(pkcs8KeySpec);

        Cipher cipher = new NullCipher();
        cipher.init(Cipher.DECRYPT_MODE, priKey);

//        String str3 = "G:/TestFile/ECC/end.txt";
//        File f=new File(str3);//新建一个文件对象，如果不存在则创建一个该文件
//        FileWriter fw;
//        try {
//            fw=new FileWriter(f);
//            String str=new String(cipher.doFinal(data));
////            System.out.println("解密文本：" + str);
//            fw.write(str);//将字符串写入到指定的路径下的文件中
//            fw.close();
//        } catch (IOException e) { e.printStackTrace(); }

        return cipher.doFinal(data);
    }
}
