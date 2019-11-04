package com.neusoft.mid.ec.api.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.stream.FileImageInputStream;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.log4j.Logger;

 
/**
 * 图片压缩工具类
 *
 */
public class PicCompressUtil {
 
	private static Logger logger = Logger.getLogger(PicCompressUtil.class);
 
     public static void main(String[] args){
    	 
    	
       // byte[] bytes = FileUtils.readFileToByteArray(new File("D://imagetest//2.png"));
        String srcFile ="E://imagetest//pic//1.jpg";
        String destFile ="E://imagetest//pic//17.jpg";
       // PicCompressUtil.compressPic(srcFile, 280, destFile);
        try{
        	 byte[] iagmeBytes = image2byte(srcFile);
             BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(destFile));
            // if(iagmeBytes.length>40*1024){
             	iagmeBytes=PicCompressUtil.compressPicForScale(iagmeBytes, 45);
           //  }
             bos.write(iagmeBytes);
             /* byte[] bs = new byte[1024];
             int len;
             while ((len = fileInputStream.read(bs)) != -1) {
                 bos.write(bs, 0, len);
             }*/
             bos.flush();
             bos.close();
             System.out.println("压缩完毕");
        }catch(Exception e){
        	e.printStackTrace();
        }
       
    //    long l = System.currentTimeMillis();
    //    bytes = PicUtils.compressPicForScale(bytes, 100, "x");// 图片小于300kb
    //    System.out.println(System.currentTimeMillis() - l);
    //   FileUtils.writeByteArrayToFile(new File("D://imagetest//02.png"), bytes);
    }
 
    /**
     * 根据指定大小压缩图片
     *
     * @param imageBytes  源图片字节数组
     * @param desFileSize 指定图片大小，单位kb，根据业务需要建议传99L
     * @return 压缩质量后的图片字节数组
     */
    public static byte[] compressPicForScale(byte[] imageBytes, long desFileSize) {
        if (imageBytes == null || imageBytes.length <= 0 || imageBytes.length < desFileSize * 1024) {
            return imageBytes;
        }
        long srcSize = imageBytes.length;
        double accuracy = 0.85;
        try {
            while (imageBytes.length > desFileSize * 1024) {
                ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream(imageBytes.length);
                Thumbnails.of(inputStream)
                        .scale(accuracy)
                        .outputQuality(accuracy)
                        .toOutputStream(outputStream);
                imageBytes = outputStream.toByteArray();
            }
            logger.info("【图片压缩】 完成");
        } catch (Exception e) {
            logger.error("【图片压缩】msg=图片压缩失败!", e);
        }
        return imageBytes;
    }
    
    public static void compressPic(String srcFile, long desFileSize, String destFile) {
        double accuracy = 0.85;
        try {
        	Thumbnails.of(srcFile).scale(accuracy).toFile(destFile);;
            logger.info("【图片压缩】 完成");
        
        } catch (Exception e) {
            logger.error("【图片压缩】msg=图片压缩失败!", e);
        }
    }
 
    /**
     * 自动调节精度(经验数值)
     *
     * @param size 源图片大小
     * @return 图片压缩质量比
     */
    private static double getAccuracy(long size) {
        double accuracy;
        accuracy=0.85;
        /*if (size < 50) {
            accuracy = 0.95;
        } else if (size < 60) {
            accuracy = 0.85;
        }  else if (size < 80) {
            accuracy = 0.8;
        } else if (size < 100) {
            accuracy = 0.7;
        } else {
            accuracy = 0.6;
        }*/
        return accuracy;
    }
    
    private static byte[] image2byte(String path){
        byte[] data = null;
        FileImageInputStream input = null;
        try {
          input = new FileImageInputStream(new File(path));
          ByteArrayOutputStream output = new ByteArrayOutputStream();
          byte[] buf = new byte[1024];
          int numBytesRead = 0;
          while ((numBytesRead = input.read(buf)) != -1) {
          output.write(buf, 0, numBytesRead);
          }
          data = output.toByteArray();
          output.close();
          input.close();
        }
        catch (FileNotFoundException ex1) {
          ex1.printStackTrace();
        }
        catch (IOException ex1) {
          ex1.printStackTrace();
        }
        return data;
      } 
 
}


