package com.david.model;

import com.david.model.ECCModel.*;

import java.io.*;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public static int encryptFile(String fileName,String encryptedFileName) throws Exception{
        // 产生秘钥对
        GenerateKey.saveKeyPair();
        // 文件加密
        BufferedReader bufferedReader = null;
        StringBuffer stringBuffer = null;
        StringBuffer stringBuffer1 = null;
        try{
            // 取出公共秘钥
            Map<String,String> map = GenerateKey.getGenerateKey();
            String publicKey = map.get(ECCEnum.PUBLIC_KEY.value());
            // 将源文件取出并赋值为String类型
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName),"GBK"));
            stringBuffer = new StringBuffer();
            String line = null;
            while((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }
            String data = new String(stringBuffer);
            // 将源文件加密并存入文件
            // 编码问题注意！！！网页版GBK，本地测试UTF-8
            byte [] encryptFile = ECCUtil.encrypt(data.getBytes("GBK"), publicKey);
            // 将byte[]类型转换成StringBuffer类型，方便后续操作
            stringBuffer1 = new StringBuffer();
            for (byte i : encryptFile) {
                stringBuffer1 = stringBuffer1.append(String.valueOf(i) + ",");
            }
            // 将stringBuffer1存入encryptedFileName文件中
            File file = new File(encryptedFileName);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(stringBuffer1.toString());
            bufferedWriter.flush();
            bufferedWriter.close();
            bufferedReader.close();
            return 1;
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 调用解密模块对文件进行解密
     * @param encryptedFileName 待解密文件地址
     * @param decryptedFileName 解密后文件的地址
     * @throws Exception 抛出异常
     */
    public static int decryptedFile(String encryptedFileName,String decryptedFileName)throws Exception{
        // 加密文件解密
        File file1 = new File(encryptedFileName);
        File file2 = new File(decryptedFileName);
        FileWriter fileWriter = null;
        // 取出公共秘钥
        Map<String,String> map = GenerateKey.getGenerateKey();
        String privateKey = map.get(ECCEnum.PRIVATE_KEY.value());
        // encryptedFileName文件的内容长度
        StringBuffer stringBuffer = new StringBuffer();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(encryptedFileName));
            String str;
            // 逐行读取
            while((str = bufferedReader.readLine()) != null) {
                // 加在StringBuffer尾
                stringBuffer.append(str);
                // 行尾 加换行符
                stringBuffer.append("\r\n");
            }
            bufferedReader.close();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return 0;
        }
        // 去除stringBuffer中的\n与\r字符
        StringBuffer buffer = new StringBuffer();
        Pattern p = Pattern.compile("\n|\r");
        Matcher m = p.matcher(stringBuffer);
        buffer.append(m.replaceAll(""));
        // 将读取的内容进行切片，成为String数组
        String[] strings = buffer.toString().split(",");
        // 将String数组转换成byte数组
        byte[] decryptedFile = new byte[strings.length];
        try {
            for(int i=0; i<strings.length; i++){
                decryptedFile[i] = (byte)Integer.parseInt(strings[i]);
            }
            // 将加密文件进行解密
            fileWriter=new FileWriter(file2);
            String str=new String(ECCUtil.decrypt(decryptedFile, privateKey));
            fileWriter.write(str);
            fileWriter.close();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}


