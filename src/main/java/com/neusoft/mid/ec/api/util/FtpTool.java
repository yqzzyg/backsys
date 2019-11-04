/*******************************************************************************
 * @(#)FtpTool.java 2016-10-13
 *
 * Copyright 2016 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.ec.api.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPTransferType;



/**
 * @author <a href="mailto:li_jun_neu@neusoft.com">Li Jun</a>
 * @version $Revision 1.1 $ 2016-10-13 上午10:12:02
 */
public class FtpTool {
    private Log log = LogFactory.getLog(FtpTool.class);

    /*private String FTPIP = "10.112.5.80";

    private String FTPPort = "22";

    private String FTPID = "ftpcustmng";

    private String FTPPasswd = "d5u@v@St";*/

    private String transferType = "ASCII";
    
    private String FTPIP = "10.10.105.30";

    private int FTPPort = 21;

    private String FTPID = null;

    private String FTPPasswd = null;


    /**
     * 上传文件
     * @param localFileList List<String> 上传的本地文件全路径列表
     * @param remotePath String 上传到FTP主机的目录路径
     * @param fileName String 文件名称（文件路径的'/'以后的名称不是真正的文件名称时，取该参数作为真正的文件名称）
     */
    public void uploadFile(List<String> localFileList, String remotePath,String fileName) {

        FTPClient ftp = new FTPClient();
        try {
            log.info("开始上传文件");
            try {
                ftp.setControlPort(FTPPort);
            } catch (Exception ee) {
                ftp.setControlPort(21);
            }
            
            ftp.setRemoteHost(this.FTPIP);
            ftp.setControlEncoding("gb2312");
            ftp.setConnectMode(FTPConnectMode.ACTIVE);
            ftp.connect();
            log.info("连接");

            ftp.login(this.FTPID, this.FTPPasswd);

            if (transferType.equalsIgnoreCase("ascii")) {
                ftp.setType(FTPTransferType.ASCII);
            } else {
                ftp.setType(FTPTransferType.BINARY);
            }

            ftp.chdir(remotePath);
            
            for (String localFileName: localFileList) {
                File localFile = new File(localFileName);
//                String fileName = localFile.getName();
                ftp.put(localFile.getPath(), fileName);

                log.info(fileName + "上传完毕");
            }
        } catch (com.enterprisedt.net.ftp.FTPException e) {
            if (e.getReplyCode() == 530) 
            	log.info("FTP服务器用户名密码校验失败!");
                log.error("错误代码:" + String.valueOf(e.getReplyCode()) + " 错误内容:" + e);
        } catch (java.io.IOException e) {
            log.error("错误内容:", e);
        } finally {
            try {
                ftp.quit();
            } catch (Exception et) {
                log.error(et);
            }
        }
    }
    	
    
    public String getFTPID() {
        return FTPID;
    }

    public void setFTPID(String ftpid) {
        FTPID = ftpid;
    }

    public String getFTPIP() {
        return FTPIP;
    }

    public void setFTPIP(String ftpip) {
        FTPIP = ftpip;
    }

    public String getFTPPasswd() {
        return FTPPasswd;
    }

    public void setFTPPasswd(String passwd) {
        FTPPasswd = passwd;
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }
}
