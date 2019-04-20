package com.david.test.ECCtest;

/**
 * Created by Gavin on 2019/4/20.
 */

import java.io.*;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        StringBuffer stringBuffer = new StringBuffer();
        for (byte i : b) {
            stringBuffer = stringBuffer.append(String.valueOf(i) + ",");
        }
        System.out.println("stringBuffer: " + stringBuffer);

        // 将stringBuffer存入文件中
        String str1 = "G:/TestFile/ECC/middle.txt";
        File file = new File(str1);
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(stringBuffer.toString());
            bufferedWriter.flush();
            bufferedWriter.close();
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        //读取文件内容
        StringBuffer stringBuffer1 = new StringBuffer();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(str1));
            String str;
            while((str = bufferedReader.readLine()) != null) {//逐行读取
                stringBuffer1.append(str);//加在StringBuffer尾
                stringBuffer1.append("\r\n");//行尾 加换行符
            }
            bufferedReader.close();//别忘记，切记
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        StringBuffer buffer2 = new StringBuffer();
        System.out.println("stringBuffer1 中的值：" + stringBuffer1);
        Pattern p = Pattern.compile("\n|\r");
        Matcher m = p.matcher(stringBuffer1);
        buffer2.append(m.replaceAll(""));
        // 对stringBuffer1进行切片
        String[] c = buffer2.toString().split(",");
        System.out.print("c 中的值：");
        for (int i = 0; i < c.length; i++) {
            System.out.print(c[i] + " ");

        }
        System.out.println();
        System.out.println(c.length);

        // 将String数组转换成byte数组
        byte[] num = new byte[c.length];
        for(int i=0; i<c.length;i++){
            num[i] = (byte)Integer.valueOf(c[i]).intValue();
        }

        System.out.print("num 中的值：");
        for (int i = 0; i < num.length; i++) {
            System.out.print(num[i] + " ");
        }
        System.out.println();


        System.out.print("b 中的值：");
        for (byte i : b) {
            System.out.print(i + " ");
        }
        System.out.println();

//        File file1 = new File(str1);
//        FileWriter fileWriter = new FileWriter(file1);
//        for (byte i : b) {
//            fileWriter.write(String.valueOf((int)i));
//        }
//        fileWriter.flush();


//        File file = new File("G:/TestFile/ECC/middle.txt");
//        FileInputStream fis = null;
//        byte[] a = new byte[(int) file.length()];
//
//        try {
//            fis = new FileInputStream(file);
//            BufferedInputStream bis = new BufferedInputStream(fis);
//            bis.read(a);
//            fis.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        System.out.print("a 中的值：");
//        for (byte i : b) {
//            System.out.print(i + " ");
//        }
//        System.out.println();

//        System.out.println(Arrays.equals(a, b));
//        System.out.println(a.length);
//        System.out.println(b.length);
//        System.out.println(num.length);

        String outputStr1 = new String(ECCUtil.decrypt(num,privKey));
        String outputStr2 = new String(ECCUtil.decrypt(b,privKey));
        System.out.println("解密文本num：" + outputStr1);
        System.out.println("解密文本b：" + outputStr2);
    }

}
