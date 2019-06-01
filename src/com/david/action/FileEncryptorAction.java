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

public class FileEncryptorAction extends ActionSupport {
    // 要上传的文件
    private File file;
    // 上传文件的格式类型名
    private String fileFileName;
    // 上传的文件名
    private String fileContentType;
    // 加密类型
    private String encryptorType;
    // 加密后文件存放地址
    private String encryptedFileAddress;

    public String getEncryptedFileAddress() {
        return encryptedFileAddress;
    }

    public void setEncryptedFileAddress(String encryptedFileAddress) {
        this.encryptedFileAddress = encryptedFileAddress;
    }

    public String getEncryptorType() {
        return encryptorType;
    }

    public void setEncryptorType(String encryptorType) {
        this.encryptorType = encryptorType;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public String getFileFileName() {
        return fileFileName;
    }

    public void setFileFileName(String fileFileName) {
        this.fileFileName = fileFileName;
    }

    @Override
    public String execute() throws Exception {
        // 文件暂存位置
        String filename = "F:\\ShoolData\\大四学习安排\\毕业设计\\文件加密与隐藏工具设计与实现\\代码实现\\secret\\src\\com\\txt\\" + getFileFileName();
        // 打开文件输入流与输出流
        InputStream inputStream = new FileInputStream(file);
        OutputStream outputStream = new FileOutputStream(filename);

        try {
            byte[] buffer = new byte[(int) file.length()];
            while (inputStream.read(buffer) > 0) {
                outputStream.write(buffer);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            outputStream.close();
            inputStream.close();
        }
        File file1 = new File(filename);
        int flag = 0;
        // 选择加密模块
        switch (encryptorType){
            case "AES":
                flag = AESFileEncryptor.encryptFile(filename, encryptedFileAddress);
                if (file1.exists()) file1.delete();
                break;
            case "ECC":
                flag = ECCFileEncryptor.encryptFile("G:/TestFile/AES/start.txt", "G:/TestFile/AES/middle.txt");
                if (file1.exists()) file1.delete();
                break;
            case "RSA":
                flag = RSAFileEncryptor.encryptFile(filename, encryptedFileAddress);
                if (file1.exists()) file1.delete();
                break;
            case "PIC":
                flag = PICEncryptor.encryptFile(filename, encryptedFileAddress);
                if (file1.exists()) file1.delete();
                break;
            default:
                return "fail";
        }
        if (flag == 1){
            return "success";
        }else {
            return "fail";
        }

    }
}