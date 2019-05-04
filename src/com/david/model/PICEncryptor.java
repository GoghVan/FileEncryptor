package com.david.model;

import com.david.model.PICModel.ArrayFunctions;
import com.david.model.PICModel.MyAlgorithms;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import sun.java2d.opengl.WGLSurfaceData;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Gavin on 2019/4/17.
 */
public class PICEncryptor {

//    /**
//     * 获取图片像素矩阵
//     * @param path 待提取图片的地址
//     * @return 返回像素矩阵
//     */
//    public static int[][] getData(String path) throws Exception{
//        int[][] data = null;
//        try{
//            System.out.println("...正在提取图片像素！");
//            BufferedImage bufferedImage = ImageIO.read(new File(path));
//            data = new int[bufferedImage.getWidth()][bufferedImage.getHeight()];
//            for (int i = 0; i < bufferedImage.getWidth(); i++){
//                for (int j = 0; j < bufferedImage.getHeight(); j++){
//                    data[i][j] = bufferedImage.getRGB(i, j);
//                }
//            }
//            System.out.println("...提取图片像素成功！");
//        }catch (Exception e){
//            System.out.println("...提取图片像素失败！");
//            e.printStackTrace();
//        }
//
//        return data;
//    }

    /**
     * 图片隐藏
     * @param fileName 待加密图片的地址
     * @param encryptedFileName 加密后图片的存放地址
     * @throws Exception 抛出异常
     */
    public static void encryptFile(String fileName,String encryptedFileName) throws Exception{
        // 获取对象算法
        MyAlgorithms myAlgorithms = new MyAlgorithms();
        ArrayFunctions arrayFunctions = new ArrayFunctions();

        // 获取图像像素矩阵的行数(width)与列数(height)
        System.out.println("...正在提取图片像素！");
        BufferedImage bufferedImage = ImageIO.read(new File(fileName));
        // 获取图像像素行数与列数
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        // 获取图像像素矩阵
        Raster raster = bufferedImage.getData();
        int [] temp = new int[raster.getWidth() * raster.getHeight() * raster.getNumBands()];
        int [] pixel  = raster.getPixels(0, 0, raster.getWidth(), raster.getHeight(), temp);
        // 像素矩阵转二维
        int [][] pixels = new int[height][width];
        arrayFunctions.change(pixel, pixels, height, width);
        // 进行图像加密
        System.out.println("...正在加密图像！");
        myAlgorithms.encrypt(pixels, 0.01, height, width);
        // 加密后图像降一维
        arrayFunctions.recovery(pixels, pixel, height, width);
        // 生成加密后的图像
        BufferedImage bufferedImage1 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//        int [] rgb = new int[width * height];
//        for (int i = 0; i < rgb.length; i++){
//            rgb[i] = Color.white.getRGB();
//        }
        bufferedImage1.setRGB(0, 0, width, height, pixel, 0, width);
        FileOutputStream fileOutputStream = new FileOutputStream(encryptedFileName);
        JPEGImageEncoder jpegImageEncoder = JPEGCodec.createJPEGEncoder(fileOutputStream);
        jpegImageEncoder.encode(bufferedImage1);
        fileOutputStream.close();
        System.out.println("...图像加密完成，请注意查收！");
    }

    /**
     * 图片提取
     * @param encryptedFileName 待解密图片的地址
     * @param decryptedFileName 解密后图片的存放地址
     * @throws Exception 抛出异常
     */
    public static void decryptedFile(String encryptedFileName,String decryptedFileName)throws Exception{
        // 获取对象算法
        MyAlgorithms myAlgorithms = new MyAlgorithms();
        ArrayFunctions arrayFunctions = new ArrayFunctions();
        // 获取图像像素矩阵的行数(width)与列数(height)
        System.out.println("...正在提取图片像素！");
        BufferedImage bufferedImage = ImageIO.read(new File(encryptedFileName));
        // 获取图向像素行数与列数
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        // 获取图像像素矩阵
        Raster raster = bufferedImage.getData();
        int [] temp = new int[raster.getWidth() * raster.getHeight() * raster.getNumBands()];
        int [] pixel  = raster.getPixels(0, 0, raster.getWidth(), raster.getHeight(), temp);
        // 像素矩阵转二维
        int [][] pixels = new int[height][width];
        arrayFunctions.change(pixel, pixels, height, width);
        // 进行图像解密
        System.out.println("...正在解密图像！");
        myAlgorithms.decrypt(pixels, 0.01, height, width);
        // 加密后图像降一维
        arrayFunctions.recovery(pixels, pixel, height, width);
        // 生成加密后的图像
        BufferedImage bufferedImage1 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//        int [] rgb = new int[width * height];
//        for (int i = 0; i < rgb.length; i++){
//            rgb[i] = Color.white.getRGB();
//        }
        bufferedImage1.setRGB(0, 0, width, height, pixel, 0, width);
        FileOutputStream fileOutputStream = new FileOutputStream(decryptedFileName);
        JPEGImageEncoder jpegImageEncoder = JPEGCodec.createJPEGEncoder(fileOutputStream);
        jpegImageEncoder.encode(bufferedImage1);
        fileOutputStream.close();
        System.out.println("...图像解密完成，请注意查收！");
    }
}
