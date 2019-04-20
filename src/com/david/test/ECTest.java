package com.david.test;

/**
 * Created by Gavin on 2019/4/20.
 */

import java.io.*;
import java.util.Arrays;
import java.util.Map;

public class ECTest {

    public static void main(String[] args) throws Exception {
        GenerateKey.saveKeyPair();
        Map<String,String> map = GenerateKey.getGenerateKey();
        String privKey = map.get(ECCEnum.PRIVATE_KEY.value());
        String pubKey = map.get(ECCEnum.PUBLIC_KEY.value());

        System.out.println("私钥：" + privKey);
        System.out.println("公钥：" + pubKey);

        String text = "都挺好其实最开始大家过的都不好！！";
        System.out.println("原始文本：" + text);
        byte [] b = ECCUtil.encrypt(text.getBytes("UTF-8"),pubKey);
        System.out.print("b 中的值：");
        for (byte i : b) {
            System.out.print(i + " ");
        }
        System.out.println();

        String str1 = "G:/TestFile/ECC/middle.txt";
        File file1 = new File(str1);
        FileWriter fileWriter = new FileWriter(file1);
        for (byte i : b) {
            fileWriter.write(String.valueOf((int) i));
        }
        fileWriter.flush();


        File file = new File("G:/TestFile/ECC/middle.txt");
        FileInputStream fis = null;
        byte[] a = new byte[(int) file.length()];
        try {
            fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            bis.read(a);
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.print("a 中的值：");
        for (byte i : b) {
            System.out.print(i + " ");
        }
        System.out.println();

        System.out.println(Arrays.equals(a, b));
        System.out.println(a instanceof byte[]);
        System.out.println(b instanceof byte[]);

        String outputStr1 = new String(ECCUtil.decrypt(a,privKey));
        String outputStr2 = new String(ECCUtil.decrypt(b,privKey));
        System.out.println("解密文本a：" + outputStr1);
        System.out.println("解密文本b：" + outputStr2);
    }

}
