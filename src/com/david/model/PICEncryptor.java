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
    public static int encryptFile(String fileName,String encryptedFileName) throws Exception{
        try{
            System.out.println("\n\n\n...图像开始 置乱 算法加密！");
            // 获取对象算法
            MyAlgorithms myAlgorithms = new MyAlgorithms();
            ArrayFunctions arrayFunctions = new ArrayFunctions();

            // 获取图像像素矩阵的行数(width)与列数(height)
            System.out.println("...正在提取图片像素！");
            BufferedImage bufferedImage = ImageIO.read(new File(fileName));
            // 获取图像像素行数与列数
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();
//            System.out.println("hight: " + height);
//            System.out.println("width: " + width);
            // 获取图像像素矩阵
//            Raster raster = bufferedImage.getData();
//            int [] temp = new int[raster.getWidth() * raster.getHeight() * raster.getNumBands()];
//            int [] pixel  = raster.getPixels(0, 0, raster.getWidth(), raster.getHeight(), temp);
//            // 像素矩阵转二维
//            int [][] pixels = new int[height][width];
//            arrayFunctions.change(pixel, pixels, height, width);
            // 获取图像像素矩阵
//            int [] pixel = new int[width * height];
            int [][] pixels_1 = new int[width][height];
            System.out.println("原始图像二维数组为：");
            for (int i = 0; i < width; i ++){
                for (int j = 0; j < height; j ++){
                    pixels_1[i][j] = bufferedImage.getRGB(i,j) & 0xFFFFFF;
//                    System.out.print(pixels_1[i][j] + " ");
                }
//                System.out.println();
            }
            int [][] pixels = new int[height][width];
            // 转置
            arrayFunctions.arr_change(pixels_1,pixels,width,height);

            System.out.println("...正在加密图像！");
            myAlgorithms.encrypt(pixels, 0.05, height, width);
            // 加密后图像降一维
//            arrayFunctions.recovery(pixels, pixel, height, width);
            // 转置
            arrayFunctions.arr_change(pixels,pixels_1,height,width);
            // 生成加密后的图像
            BufferedImage bufferedImage1 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//            bufferedImage1.setRGB(0, 0, width, height, pixel, 0, width);
//            System.out.println("加密图像二维数组为：");
            for(int x = 0; x < width; x++){
                for(int y = 0; y < height; y++){
                    bufferedImage1.setRGB(x,y,pixels_1[x][y]);
//                    System.out.print(pixels_1[x][y] + " ");
                }
//                System.out.println();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(encryptedFileName);
            JPEGImageEncoder jpegImageEncoder = JPEGCodec.createJPEGEncoder(fileOutputStream);
            jpegImageEncoder.encode(bufferedImage1);
            fileOutputStream.close();
            System.out.println("...图像加密完成！");
            return 1;
        }catch (Exception e){
            System.out.println("...图像加密失败！");
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
            System.out.println("\n\n\n...图像开始 置乱 算法解密！");
            // 获取对象算法
            MyAlgorithms myAlgorithms = new MyAlgorithms();
            ArrayFunctions arrayFunctions = new ArrayFunctions();
            // 获取图像像素矩阵的行数(width)与列数(height)
            System.out.println("...正在提取图片像素！");
            BufferedImage bufferedImage = ImageIO.read(new File(encryptedFileName));
            // 获取图向像素行数与列数
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();
//            System.out.println("hight: " + height);
//            System.out.println("width: " + width);
            // 获取图像像素矩阵
//            Raster raster = bufferedImage.getData();
//            int[] temp = new int[raster.getWidth() * raster.getHeight() * raster.getNumBands()];
//            int[] pixel = raster.getPixels(0, 0, raster.getWidth(), raster.getHeight(), temp);
//            // 像素矩阵转二维
//            int[][] pixels = new int[height][width];
//            arrayFunctions.change(pixel, pixels, height, width);
            // 获取图像像素矩阵
//            int [] pixel = new int[width * height];
            int [][] pixels_1 = new int[width][height];
            for (int i = 0; i < width; i ++){
                for (int j = 0; j < height; j ++){
                    pixels_1[i][j] = bufferedImage.getRGB(i,j) & 0xFFFFFF;
                }
            }
            int [][] pixels = new int[height][width];
            arrayFunctions.arr_change(pixels_1,pixels,width,height);
            // 进行图像解密
            System.out.println("...正在解密图像！");
//            System.out.println("原始图像二维数组为：");
//            for (int i = 0; i < height; i++){
//                for (int j = 0; j < width; j++) System.out.print(pixels[i][j] + " ");
//                System.out.println();
//            }

            myAlgorithms.decrypt(pixels, 0.05, height, width);
            // 加密后图像降一维
//            arrayFunctions.recovery(pixels, pixel, height, width);
            // 生成加密后的图像
            arrayFunctions.arr_change(pixels,pixels_1,height,width);
            BufferedImage bufferedImage1 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//            bufferedImage1.setRGB(0, 0, width, height, pixel, 0, width);
            for(int x = 0; x< width; x++){
                for(int y = 0; y< height; y++){
                    bufferedImage1.setRGB(x,y,pixels_1[x][y]);
//                    System.out.print(pixels_1[x][y] + " ");
                }
//                System.out.println();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(decryptedFileName);
            JPEGImageEncoder jpegImageEncoder = JPEGCodec.createJPEGEncoder(fileOutputStream);
            jpegImageEncoder.encode(bufferedImage1);
            fileOutputStream.close();
            System.out.println("...图像解密完成！");
            return 1;
        } catch (Exception e) {
            System.out.println("...图像解密失败！");
            e.printStackTrace();
            return 0;
        }
    }
}
