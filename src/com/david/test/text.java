package com.david.test;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import sun.java2d.loops.DrawRect;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Gavin on 2019/5/30.
 */
public class text {
    public static void getData(String path){
        try{
            BufferedImage bimg = ImageIO.read(new File(path));
            int width = bimg.getWidth();
            int height = bimg.getHeight();
            System.out.println("getMinX: " + bimg);
            System.out.println("getMinY: " + bimg);
            int [][] data = new int[bimg.getWidth()][bimg.getHeight()];
//            int [][] data = {
//                    {-3498667 ,-4882371, -2510495, -1258641 },
//                    {-3564717 ,-1719954, -797578 ,-2774174 },
//                    {-929155 ,-75902 ,-3827890 ,-995200},
//                    {-995976 ,-468613, -6768 ,-4487102}
//            };
            //方式一：通过getRGB()方式获得像素矩阵
            //此方式为沿Height方向扫描
            for(int i=0;i<bimg.getWidth();i++){
                for(int j=0;j<bimg.getHeight();j++){
                    data[i][j]=bimg.getRGB(i,j) & 0xFFFFFF;
                    System.out.print(data[i][j] + " ");
                }
                System.out.println();
            }
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            //we either have to loop through all values, or convert to 1-d array
            for(int x=0; x< width; x++){
                for(int y=0; y< height; y++){
                    bufferedImage.setRGB(x,y,data[x][y]);
                    System.out.print(data[x][y] + " ");
                }
                System.out.println();
            }
            FileOutputStream fileOutputStream = new FileOutputStream("G:/TestFile/PIC/22222.png");
            JPEGImageEncoder jpegImageEncoder = JPEGCodec.createJPEGEncoder(fileOutputStream);
            jpegImageEncoder.encode(bufferedImage);
            fileOutputStream.close();

        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public static void main(String [] args){
        getData("G:/TestFile/PIC/start3.png");
    }

}
