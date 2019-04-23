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

public class FileEncryptorAction extends ActionSupport{
    // 要上传的文件
    private File file;
    // 上传文件的格式类型名
    private String fileFileName;
    // 上传的文件名
    private String fileContentType;
    // 加密类型
    private String encryptorType;

    public String getEncryptorType(){
        return encryptorType;
    }

    public File getFile(){
        return file;
    }
    public void setFile(File file){
        this.file = file;
    }

    public void setEncryptorType(String encryptorType){
        this.encryptorType = encryptorType;
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


    @Override
    public String execute() throws Exception{
        // 上传文件扩展名
        String expandedName = fileFileName.substring(fileFileName.lastIndexOf("."));
        // 文件暂存位置
        String filename = "F:/ShoolData/大四学习安排/毕业设计" +
                "/文件加密与隐藏工具的加密与实现/代码实现/secret/src/com/txt/" + fileFileName;
        String encryptedFileName = "F:/ShoolData/大四学习安排/毕业设计" +
                "/文件加密与隐藏工具的加密与实现/代码实现/secret/src/com/txt/middle" + expandedName;

        // 打开文件输入流与输出流
        InputStream inputStream = new FileInputStream(file);
        OutputStream outputStream = new FileOutputStream(filename);

        try{
            byte[] buffer = new byte[(int)file.length()];
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
        File file2 = new File(encryptedFileName);

        // 选择加密模块
        switch (encryptorType){
            case "AES":
                AESFileEncryptor.encryptFile(filename, encryptedFileName);
                if (file1.exists()) file1.delete();
                if (file2.exists()) file2.delete();
                return "success";
            case "ECC":
                ECCFileEncryptor.encryptFile(filename, encryptedFileName);
                if (file1.exists()) file1.delete();
                if (file2.exists()) file2.delete();
                return "success";
            case "RSA":
                RSAFileEncryptor.encryptFile(filename, encryptedFileName);
                if (file1.exists()) file1.delete();
                if (file2.exists()) file2.delete();
                return "success";
            case "PIC":
                PICEncryptor.encryptFile(filename, encryptedFileName);
                if (file1.exists()) file1.delete();
                if (file2.exists()) file2.delete();
                return "success";
            default:
                return "fail";
        }
    }

}