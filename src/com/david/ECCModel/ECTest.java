package com.david.ECCModel;

/**
 * Created by Gavin on 2019/4/20.
 */

import java.util.Map;

public class ECTest {

    public static void main(String[] args) throws Exception {
        Map<String,String> map = GenerateKey.getGenerateKey();
        String privKey = map.get(ECCEnum.PRIVATE_KEY.value());
        String pubKey = map.get(ECCEnum.PUBLIC_KEY.value());

        System.out.println("私钥：" + privKey);

        System.out.println("公钥：" + pubKey);
        String text = "都挺好其实最开始大家过的都不好！！";
        byte [] b = ECCUtil.encrypt(text.getBytes("UTF-8"),pubKey);
        String str = BASE64Encoder.encodeBuffer(b);
        System.out.println("密文：" + str);
        String outputStr = new String(ECCUtil.decrypt(b,privKey));
        System.out.println("原始文本：" + text);
        System.out.println("解密文本：" + outputStr);
    }
}
