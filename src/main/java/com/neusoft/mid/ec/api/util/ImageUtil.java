package com.neusoft.mid.ec.api.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;



public class ImageUtil {
	
	private static Logger LOGGEER = Logger.getLogger(ImageUtil.class);
	
	 /**
     * 保存文件，直接以multipartFile形式
     * @param multipartFile
     * @param path 文件保存绝对路径
     * @return 返回文件名
     * @throws IOException
     */
    public static String saveImg(MultipartFile multipartFile,String path,int picNum,int totalNum) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        //FileInputStream fileInputStream = (FileInputStream) multipartFile.getInputStream();
        LOGGEER.info("=========multipartFile.getSize()="+multipartFile.getSize());
        byte[] iagmeBytes = multipartFile.getBytes();     
        //String sf= new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date()).toString();
        String fileName = picNum + ".png";
        int picSize = getPicSize(totalNum);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path + File.separator + fileName));
        if(iagmeBytes.length>picSize*1024){
        	iagmeBytes=PicCompressUtil.compressPicForScale(iagmeBytes, picSize);
        }
        bos.write(iagmeBytes);
        /* byte[] bs = new byte[1024];
        int len;
        while ((len = fileInputStream.read(bs)) != -1) {
            bos.write(bs, 0, len);
        }*/
        bos.flush();
        bos.close();
        return fileName;
    }
    
    public static int getPicSize(int totalNum){
    	if(totalNum==1){
    		return 280;
    	}else if(totalNum==2){
    		return 140;
    	}else if(totalNum==3){
    		return 95;
    	}else if(totalNum==4){
    		return 75;
    	}else if(totalNum==5){
    		return 60;
    	}else if(totalNum==6){
    		return 50;
    	}else{
    		return 40;
    	}
    }
	
	

}
