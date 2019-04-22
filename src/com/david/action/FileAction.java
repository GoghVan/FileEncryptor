package com.david.action;


import com.opensymphony.xwork2.ActionSupport;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
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

    @Override
    public String execute() throws Exception{
        System.out.println("文件: " + file);
        System.out.println("文件的格式类型名: " + fileFileName);
        System.out.println("文件名" + fileContentType);

        String realPath = ServletActionContext.getServletContext().getRealPath("/fileAction");
        File file1 = new File(realPath);
        if (!file1.exists()) file1.mkdirs();
        try{
            FileUtils.copyFile(file, new File(file1, fileFileName));
//            System.out.println("file: " + file1.);
        }catch (IOException e){
            e.printStackTrace();
            return "fail";
        }
        return "success";
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

    public File getFile(){
        return file;
    }
    private void setFile(File file){
        this.file = file;
    }

}
