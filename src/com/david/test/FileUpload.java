package com.david.test;
import java.io.*;


public class FileUpload {
    private  static final long serialVeresionUID=1L;
    private File file;
    private String fileFileName;
    public  File getFile(){
        return  file;
    }
    public  void setFile(File file){
        this.file=file;
    }
    public  String getFileFileName(){
        return fileFileName;
    }
    public void  setFileFileName(String fileFileName){
        this.fileFileName=fileFileName;
    }
    public  String execute()throws Exception{
        InputStream is=new FileInputStream(file);
        OutputStream os=new FileOutputStream("d://"+fileFileName);
        byte[]buffer=new byte[1024];
        int length=0;
        while ((length=is.read(buffer))!=-1){
            os.write(buffer,0,length);
        }
        os.close();
        is.close();
        return "success";
    }
}
