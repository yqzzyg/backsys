package com.neusoft.mid.ec.api.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.tools.zip.ZipEntry; 
import org.apache.tools.zip.ZipOutputStream; 


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
/**
 * 每月1号执行一次，保留前6个月数据，并将保留数据打成zip包
 * @author Administrator
 */
@Component
public class FileQuartz {

	@Scheduled(cron = "0 0 1 1 * ?") //每月1号凌晨1点执行一次：0 0 1 1 * ?
    public static void  doSomething() {
		//将文件保留6个月，将前5个月文件压缩
		// 根据系统的实际情况选择目录分隔符（windows下是，linux下是/）
        String separator = File.separator;
		String directory = ".."+separator+"bossFile" + separator;//检查的文件路径
		String srcDir = ".."+separator+"bossFileBak" + separator;//备份文件路径
        
        //String directory = "D:\\workspace\\workspace-mvn-api\\bossFile" + separator;//检查的文件路径
      	//String srcDir = "D:\\workspace\\workspace-mvn-api\\bossFileBak" + separator;//备份文件路径
        
		try{
			//计算将要删除的文件日期 start
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd"); 
			Calendar calendar = Calendar.getInstance();   
			calendar.add(Calendar.DATE, -1);    //得到前一天   
			calendar.add(Calendar.MONTH, -6);    //得到前一个月
			calendar.set(Calendar.DAY_OF_MONTH,0);
			String  lastDay = format.format(calendar.getTime());//将要删除文件的最近日期
			System.out.println("删除 "+lastDay+" 天前的数据"); 
			//计算将要删除的文件日期 end
			
			//遍历文件目录备份及删除
			traverseFolder2(directory,Integer.valueOf(lastDay),srcDir); 
			//遍历备份文件夹
			traverseFolder2(srcDir,Integer.valueOf(lastDay),""); 
	        
		}catch (Exception e) {
			e.printStackTrace();
		}
		  
    }
	
	//递归删除文件夹
	public static void deleteDir(File dir){
	  if(dir.isDirectory()){
	    File[] files = dir.listFiles();
	    for(int i=0; i<files.length; i++) {
	    	System.out.println("删除文件："+files[i]); 
		   deleteDir(files[i]);
	    }
	  }
	  dir.delete();
	}

	//遍历文件
	public static void traverseFolder2(String path,int beforeDay,String srcDir) {
		System.out.println("检查路径："+path);
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files.length == 0) {
                System.out.println("文件夹是空的!");
                return;
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {//文件夹
                        System.out.println("文件夹:" + file2.getAbsolutePath());
                        
                        int name = 0;
                        Pattern pattern = Pattern.compile("[0-9]*");
                        Matcher isNum = pattern.matcher(file2.getName());
                        if(isNum.matches()){
                        	name = Integer.valueOf(file2.getName());
                        	if(name < beforeDay){//小于beforeDay日期文件夹删除
                            	deleteDir(file2);
                            }else{//大于beforeDay后日期数据打zip包备份
                            	boolean zipFlag = fileToZip(file2.getAbsolutePath(),srcDir,file2.getName());
                            	System.out.println("打包状态："+zipFlag);
                            	if(zipFlag){
                            		deleteDir(file2);
                            		System.out.println("未删除文件夹："+file2.getAbsolutePath()); 
                            	}
                            }
                        }else{
                        	System.out.println(file2.getName() + " 文件夹名不是数字");
                        }
                    } else {//文件
                    	//获取文件名，去除后缀
                    	Pattern pattern = Pattern.compile("[0-9]*");
                        Matcher isNum = pattern.matcher(file2.getName().substring(0,file2.getName().indexOf(".")));
                    	
                        if(isNum.matches()){
                        	int name = Integer.valueOf(file2.getName().substring(0,file2.getName().indexOf("."))); 
                        	if(name < beforeDay){//小于beforeDay日期文件夹删除
                            	deleteDir(file2);
                            	System.out.println("删除文件:" + file2.getAbsolutePath());
                            }
                        }else{
                        	System.out.println(file2.getName() + " 文件名不是数字。");
                        }
                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
	}
	

	
	/** 
     * 将存放在sourceFilePath目录下的源文件，打包成fileName名称的zip文件，并存放到zipFilePath路径下 
     * @param sourceFilePath :待压缩的文件路径 
     * @param zipFilePath :压缩后存放路径 
     * @param fileName :压缩后文件的名称 
     * @return 
     */  
    public static boolean fileToZip(String sourceFilePath,String zipFilePath,String fileName){  
        boolean flag = false;  
        File sourceFile = new File(sourceFilePath);  
        FileInputStream fis = null;  
        BufferedInputStream bis = null;  
        FileOutputStream fos = null;  
        ZipOutputStream zos = null;  
          
        if(sourceFile.exists() == false){  
            System.out.println("待压缩的文件目录："+sourceFilePath+"不存在.");  
        }else{  
            try {  
                File zipFile = new File(zipFilePath + "/" + fileName +".zip");  
                if(zipFile.exists()){  
                    System.out.println(zipFilePath + "目录下存在名字为:" + fileName +".zip" +"打包文件.");  
                }else{  
                    File[] sourceFiles = sourceFile.listFiles();  
                    if(null == sourceFiles || sourceFiles.length<1){  
                        System.out.println("待压缩的文件目录：" + sourceFilePath + "里面不存在文件，无需压缩.");  
                    }else{  
                        fos = new FileOutputStream(zipFile);  
                        zos = new ZipOutputStream(new BufferedOutputStream(fos));  
                        byte[] bufs = new byte[1024*10];  
                        for(int i=0;i<sourceFiles.length;i++){  
                            //创建ZIP实体，并添加进压缩包  
                            ZipEntry zipEntry = new ZipEntry(sourceFiles[i].getName());  
                            zos.putNextEntry(zipEntry);  
                            //读取待压缩的文件并写进压缩包里  
                            System.out.println(sourceFiles[i]);
                            fis = new FileInputStream(sourceFiles[i]);  
                            bis = new BufferedInputStream(fis, 1024*10);  
                            int read = 0;  
                            while((read=bis.read(bufs, 0, 1024*10)) != -1){  
                                zos.write(bufs,0,read);  
                            }  
                        }  
                        flag = true;  
                    }  
                }  
            } catch (FileNotFoundException e) {  
                e.printStackTrace();  
                throw new RuntimeException(e);  
            } catch (IOException e) {  
                e.printStackTrace();  
                throw new RuntimeException(e);  
            } finally{  
                //关闭流  
                try {  
                    if(null != bis) bis.close();  
                    if(null != zos) zos.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                    throw new RuntimeException(e);  
                }  
            }  
        }  
        return flag;  
    }  

	
	public static void main(String[] args) {
			doSomething();
	}

}
