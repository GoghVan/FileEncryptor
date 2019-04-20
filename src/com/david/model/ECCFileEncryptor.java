package com.david.model;

import com.david.ECCModel.*;

import java.io.*;
import java.util.Map;

/**
 * Created by Gavin on 2019/4/18.
 */
public class ECCFileEncryptor{

    /**
     * 调用加密模块对文件进行加密
     * @param fileName 待加密文件的地址
     * @param encryptedFileName 加密后文件的地址
     * @throws Exception 抛出异常
     */
    public static void encryptFile(String fileName,String encryptedFileName) throws Exception{
        // 产生秘钥对
        GenerateKey.saveKeyPair();

        // 文件加密
        System.out.println("...开始加密文件！");
        BufferedReader bufferedReader = null;
        StringBuffer stringBuffer = null;
        try{
            System.out.println("...正在取出publicKey!");
            // 取出公共秘钥
            Map<String,String> map = GenerateKey.getGenerateKey();
            String publicKey = map.get(ECCEnum.PUBLIC_KEY.value());

            System.out.println("...正在读取待加密文件！");
            // 将源文件取出并赋值为String类型
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName),"GBK"));
            stringBuffer = new StringBuffer();
            String line = null;
            while((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }
            String data = new String(stringBuffer);

            System.out.println("...正在加密 " + fileName + " 文件!");
            // 将源文件加密并存入文件
            byte [] encryptFile = ECCUtil.encrypt(data.getBytes("UTF-8"), publicKey);
            File file = new File(encryptedFileName);
            FileWriter fileWriter = new FileWriter(file);
            for (byte i : encryptFile) {
                fileWriter.write(String.valueOf((int) i));
            }
            fileWriter.flush();
            System.out.println("...文件加密完成!" + "加密文件存放在" + encryptedFileName + "中，请注意查收！");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }catch (Exception e){
            System.out.println("...文件加密失败！");
            throw e;
        }finally {
            try {
                bufferedReader.close();
            }catch (Exception e){
                System.out.println("...源文件关闭失败!");
                e.printStackTrace();
            }
        }
    }

    /**
     * 调用解密模块对文件进行解密
     * @param encryptedFileName 待解密文件地址
     * @param decryptedFileName 解密后文件的地址
     * @throws Exception 抛出异常
     */
    public static void decryptedFile(String encryptedFileName,String decryptedFileName)throws Exception{

        // 加密文件解密
        File file1 = new File(encryptedFileName);
        File file2 = new File(decryptedFileName);

        System.out.println("...开始解密文件！");
        FileWriter fileWriter = null;
        FileInputStream fileInputStream = null;

        System.out.println("...正在取出privateKey!");
        // 取出公共秘钥
        Map<String,String> map = GenerateKey.getGenerateKey();
        String privateKey = map.get(ECCEnum.PRIVATE_KEY.value());

        // encryptedFileName文件的内容长度
        byte[] decryptedFile = new byte[(int) file1.length()];
        try {
            System.out.println("...正在读取待解密文件！");
            // 将加密文件的内容读取到decryptedFile中
            fileInputStream = new FileInputStream(file1);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            bufferedInputStream.read(decryptedFile);
            fileInputStream.close();

            System.out.println("...正在解密 " + encryptedFileName + " 文件!");
            // 将加密文件进行解密
            fileWriter=new FileWriter(file2);
            String str=new String(ECCUtil.decrypt(decryptedFile, privateKey));
            fileWriter.write(str);
            fileWriter.close();
            System.out.println("...文件解密完成!" + "解密文件存放在" + decryptedFileName + "中，请注意查收！");
        } catch (Exception e) {
            System.out.println("...文件解密失败！");
            e.printStackTrace();
        }
    }

}

