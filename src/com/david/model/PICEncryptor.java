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
    /**
     * 图片隐藏
     * @param fileName 待加密图片的地址
     * @param encryptedFileName 加密后图片的存放地址
     * @throws Exception 抛出异常
     */
    public static int encryptFile(String fileName,String encryptedFileName) throws Exception{
        try{
            // 获取对象算法
            MyAlgorithms myAlgorithms = new MyAlgorithms();
            ArrayFunctions arrayFunctions = new ArrayFunctions();
            // 获取图像像素矩阵的行数(width)与列数(height)
            BufferedImage bufferedImage = ImageIO.read(new File(fileName));
            // 获取图像像素行数与列数
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();
            // 获取图像像素矩阵
            int [][] pixels_1 = new int[width][height];
            for (int i = 0; i < width; i ++){
                for (int j = 0; j < height; j ++){
                    pixels_1[i][j] = bufferedImage.getRGB(i,j) & 0xFFFFFF;
                }
            }
            int [][] pixels = new int[height][width];
            // 转置
            arrayFunctions.arr_change(pixels_1,pixels,width,height);
            myAlgorithms.encrypt(pixels, 0.05, height, width);
            // 转置
            arrayFunctions.arr_change(pixels,pixels_1,height,width);
            // 生成加密后的图像
            BufferedImage bufferedImage1 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for(int x = 0; x < width; x++){
                for(int y = 0; y < height; y++){
                    bufferedImage1.setRGB(x,y,pixels_1[x][y]);
                }
            }
            FileOutputStream fileOutputStream = new FileOutputStream(encryptedFileName);
            JPEGImageEncoder jpegImageEncoder = JPEGCodec.createJPEGEncoder(fileOutputStream);
            jpegImageEncoder.encode(bufferedImage1);
            fileOutputStream.close();
            return 1;
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 图片提取
     * @param encryptedFileName 待解密图片的地址
     * @param decryptedFileName 解密后图片的存放地址
     * @throws Exception 抛出异常
     */
    public static int decryptedFile(String encryptedFileName,String decryptedFileName)throws Exception {
        try {
            // 获取对象算法
            MyAlgorithms myAlgorithms = new MyAlgorithms();
            ArrayFunctions arrayFunctions = new ArrayFunctions();
            // 获取图像像素矩阵的行数(width)与列数(height)
            BufferedImage bufferedImage = ImageIO.read(new File(encryptedFileName));
            // 获取图向像素行数与列数
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();
            // 获取图像像素矩阵
            int [][] pixels_1 = new int[width][height];
            for (int i = 0; i < width; i ++){
                for (int j = 0; j < height; j ++){
                    pixels_1[i][j] = bufferedImage.getRGB(i,j) & 0xFFFFFF;
                }
            }
            int [][] pixels = new int[height][width];
            arrayFunctions.arr_change(pixels_1,pixels,width,height);
            // 进行图像解密
            myAlgorithms.decrypt(pixels, 0.05, height, width);
            // 生成加密后的图像
            arrayFunctions.arr_change(pixels,pixels_1,height,width);
            BufferedImage bufferedImage1 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for(int x = 0; x< width; x++){
                for(int y = 0; y< height; y++){
                    bufferedImage1.setRGB(x,y,pixels_1[x][y]);
                }
            }
            FileOutputStream fileOutputStream = new FileOutputStream(decryptedFileName);
            JPEGImageEncoder jpegImageEncoder = JPEGCodec.createJPEGEncoder(fileOutputStream);
            jpegImageEncoder.encode(bufferedImage1);
            fileOutputStream.close();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
