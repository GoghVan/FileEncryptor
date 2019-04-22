package com.david.action;


import com.opensymphony.xwork2.ActionSupport;
import java.io.*;

/**
 * Created by Gavin on 2019/4/22.
 */
public class FileAction extends ActionSupport{
    // 要上传的文件
    private File file;
    // 上传文件的格式类型名
    private String fileFileName;
    // 上传的文件名
    private String fileContentType;

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

    @Override
    public String execute() throws Exception{
        System.out.println("文件: " + file);
        System.out.println("文件的格式类型名: " + fileFileName);
        System.out.println("文件名: " + fileContentType);

        InputStream inputStream = new FileInputStream(file);
        OutputStream outputStream = new FileOutputStream("d:/" + fileFileName);
        try{
            byte[] buffer = new byte[(int)file.length()];

            while (inputStream.read(buffer) >0){
                outputStream.write(buffer);
            }
            System.out.print("buffer: ");
            for (byte i : buffer) {
                System.out.print(i + " ");
            }
            System.out.println();
        }catch (IOException e){
            e.printStackTrace();
            return "fail";
        }finally {
            outputStream.close();
            inputStream.close();
        }
        return "success";
    }

}
