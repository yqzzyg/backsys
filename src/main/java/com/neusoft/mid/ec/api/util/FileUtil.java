package com.neusoft.mid.ec.api.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtil {
	/**
	 * boss报文备份文件
	 * @param content 文件内容
	 * @param fileName 文件名
	 */
	public static void CreateFile(String content,String fileName){
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
        String date = df.format(new Date());
        String dataFile = date.substring(0,8);
        fileName = fileName + "_" + date + ".txt";
		// 根据系统的实际情况选择目录分隔符（windows下是，linux下是/）
        String separator = File.separator;
        String directory = "../serviceFile" + separator + dataFile + separator;
        // 在内存中创建一个文件对象，注意：此时还没有在硬盘对应目录下创建实实在在的文件
        File f = new File(directory,fileName);
        if(f.exists()) {
          // 文件已经存在，输出文件的相关信息
            System.out.println(f.getAbsolutePath());
            System.out.println(f.getName());
            System.out.println(f.length());
        } else {
          //  先创建文件所在的目录
            f.getParentFile().mkdirs();
            try {
             // 创建新文件
                f.createNewFile();
                FileWriter fw = new FileWriter(f.getAbsolutePath(), true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(content);// 往已有的文件上添加字符串
                bw.close();
                fw.close();
            } catch (IOException e) {
                System.out.println("创建新文件时出现了错误。。。");
                e.printStackTrace();
            }
        }
	}
	
}
