package com.david.action;

import com.david.model.AESFileEncryptor;
import com.david.model.ECCFileEncryptor;
import com.david.model.PICEncryptor;
import com.david.model.RSAFileEncryptor;
import com.opensymphony.xwork2.ActionSupport;

import java.io.*;

/**
 * Created by Gavin on 2019/4/22.
 */
public class FileDencryptorAction extends ActionSupport{
    // 要上传的文件
    private File file;
    // 上传文件的格式类型名
    private String fileFileName;
    // 上传的文件名
    private String fileContentType;
    // 解密类型
    private String encryptorType;
    // 解密文件存放地址
    private String dencryptedFileAddress;

    public String getDencryptedFileAddress(){
        return dencryptedFileAddress;
    }
    public void setDencryptedFileAddress(String dencryptedFileAddress){
        this.dencryptedFileAddress = dencryptedFileAddress;
    }

    public String getEncryptorType(){
        return encryptorType;
    }
    public void setEncryptorType(String encryptorType){
        this.encryptorType = encryptorType;
    }

    public File getFile(){
        return file;
    }
    public void setFile(File file){
        this.file = file;
    }

    public String getFileContentType(){
        return fileContentType;
    }
    public void setFileContentType(String fileContentType){
        this.fileContentType = fileContentType;
    }

    public String getFileFileName(){
        return fileFileName;
    }
    public void setFileFileName(String fileFileName){
        this.fileFileName = fileFileName;
    }

    // 文件暂存位置
    String filename = "F:\\ShoolData\\大四学习安排\\毕业设计\\文件加密与隐藏工具设计与实现\\代码实现\\secret\\src\\com\\txt" + fileFileName;

    @Override
    public String execute() throws Exception{
//        System.out.println("File: " + file);
//        System.out.println("fileFileName: " + fileFileName);
//        System.out.println("fileContentType: " + fileContentType);
//        System.out.println("encryptorType: " + encryptorType);
//        System.out.println("encryptedFileAddress: " + dencryptedFileAddress);
        // 上传文件扩展名
//        String expandedName = fileFileName.substring(fileFileName.lastIndexOf("."));
        // 文件暂存位置
//        String decryptedFileName = "F:/ShoolData/大四学习安排/毕业设计" +
//                "/文件加密与隐藏工具的加密与实现/代码实现/secret/src/com/txt/end" + expandedName;
        System.out.println("\n\n\n...文件解密开始！");
        // 打开文件输入流与输出流
        InputStream inputStream = new FileInputStream(file);
        OutputStream outputStream = new FileOutputStream(filename);
        byte[] buffer = new byte[(int)file.length()];
        try{
            while (inputStream.read(buffer) >0){
                outputStream.write(buffer);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            outputStream.close();
            inputStream.close();
        }

        File file1 = new File(filename);
//        File file2 = new File(decryptedFileName);
        int flag = 0;
        // 选择解密模块
        switch (encryptorType){
            case "AES":
                flag = AESFileEncryptor.decryptedFile(filename, dencryptedFileAddress);
                if (file1.exists()) file1.delete();
//                if (file2.exists()) file2.delete();
                break;
            case "ECC":
                flag = ECCFileEncryptor.decryptedFile(filename, dencryptedFileAddress);
                if (file1.exists()) file1.delete();
//                if (file2.exists()) file2.delete();
                break;
            case "RSA":
                flag = RSAFileEncryptor.decryptedFile(filename, dencryptedFileAddress);
                if (file1.exists()) file1.delete();
//                if (file2.exists()) file2.delete();
                break;
            case "PIC":
                flag = PICEncryptor.decryptedFile(filename, dencryptedFileAddress);
                if (file1.exists()) file1.delete();
//                if (file2.exists()) file2.delete();
                break;
            default:
                return "fail";
        }
        if (flag == 1){
            System.out.println("...文件解密完成！");
            return "success";
        }else {
            System.out.println("...文件解密失败！");
            return "fail";
        }
    }
}
