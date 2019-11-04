package com.neusoft.mid.ec.api.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.bind.DatatypeConverter;

import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


//import org.json.JSONObject;



/** 
 ***************************************************************
 * @File Package: com.neusoft.mid.csbc.mpp.servlet
 * @File name:    DataServlet.java 
 * @Created on:   2017-5-2 上午10:38:30
 * @Description:  
 * @Company:      东软集团股份有限公司
 * @Department:   移动互联网事业部
 * @author       <a href="mailto:zhangling@neusoft.com">zhangling</a>
/***************************************************************/

public class DataServlet {
	
	/**日志处理类*/
    private final static Log LOG = LogFactory.getLog(DataServlet.class);
    
	public static JSONObject GetResponseDataByID(String url, String postData,String method) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		method = method.toUpperCase();
		LOG.info("URL:"+url);
		LOG.info("请求参数"+postData);
		
		try { 
			//-----------------------------------------------------
			//创建SSLContext  https
		    /*SSLContext sslContext=SSLContext.getInstance("SSL");  
		    TrustManager[] tm={new MyX509TrustManager()};  
		    //初始化  
		    sslContext.init(null, tm, new java.security.SecureRandom()); 
		    //获取SSLSocketFactory对象  
		    SSLSocketFactory ssf=sslContext.getSocketFactory();*/
		    
			//------------------------------------------------------
            //get请求的url  
            URL url1=new URL(url);  
            //url写法：    协议://IP地址:端口号/访问的文件  
            //协议一般是http，IP地址是要访问的IP地址，端口一般是8080，然后加上访问的是什么  
            //新建一个HttpURLConnection，从url开启一个连接  
            
            HttpURLConnection connection= (HttpURLConnection) url1.openConnection();
            //https
            //HttpsURLConnection conn=(HttpsURLConnection)url.openConnection(); 
            
            //设置请求的模式  
            connection.setRequestMethod(method);  
            //设置请求连接超时时间  
            connection.setConnectTimeout(25000);  
            //设置访问时的超时时间  
            connection.setReadTimeout(25000);  
            
            /*//https鉴权请求
            connection.setRequestProperty("Authorization", " Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==");
            connection.setUseCaches(false); 
            //设置当前实例使用的SSLSoctetFactory  
            connection.setSSLSocketFactory(ssf); */
            
            
            if("POST".equals(method) || "PUT".equals(method) ){
            	connection.setDoOutput(true);
            	
            	//connection.setDoInput(true);
            	//设置请求属性
            	connection.setRequestProperty("Content-Type", "application/json");
            	connection.setRequestProperty("Charset", "UTF-8");
            	//开启连接  
                connection.connect();
            	// 获取URLConnection对象对应的输出流
                PrintWriter printWriter = new PrintWriter(connection.getOutputStream());
                // 发送请求参数
                printWriter.write(postData);//post的参数 xx=xx&yy=yy
               
                // flush输出流的缓冲
                printWriter.flush();
                printWriter.close();
           
            }	
                InputStream inputStream=null;  
                BufferedReader reader=null;
                
                //如果应答码为200的时候，表示成功的请求带了，这里的HttpURLConnection.HTTP_OK就是200 
                if(connection.getResponseCode()==HttpURLConnection.HTTP_OK){
                	LOG.info("连接成功！");
                    //获得连接的输入流  
                    inputStream=connection.getInputStream();  
                    //转换成一个加强型的buffered流  
                    reader=new BufferedReader(new InputStreamReader(inputStream, "utf-8")); 
                    
                    //把读到的内容赋值给result  
                    String result;  
                    while ((result=reader.readLine()) != null) {
                    	buffer.append(result);
                    }
                    
                    if("".equals(buffer) || buffer == null || buffer.length() == 0){ 
                    	jsonObject = null;
                    }else{
                    	jsonObject = JSONObject.fromObject(buffer.toString());
                    }
                    
                    //子线程不能更新UI线程的内容，要更新需要开启一个Ui线程，这里Toast要在Ui线程  
                    //result:{"code":0,"description":"","lastUpdateTime":0,"payload":{"id":14,"deleted":0,"createdAt":1493706839000,"updatedAt":1493706839000,"mobile":"11111111111","password":null,"ecCorpId":null,"siCorpId":null,"role":0,"status":0,"createdBy":"admin","updatedBy":"admin","remark":null}}
                    //code:30001短信验证码错误 code:0成功
                    
                }else{
                	jsonObject = null;
                	LOG.error("连接失败，连接码为："+connection.getResponseCode());
                }
              //关闭流和连接  
                if(reader!=null){
                	reader.close();  
                }
                if(inputStream!=null){
                	inputStream.close();
                }
                connection.disconnect();
                System.out.println("result:"+buffer.toString());
                LOG.info("result:"+buffer.toString());
            
        } catch (MalformedURLException e) {  
            e.printStackTrace(); 
            jsonObject = null;
        } catch (IOException e) {  
            e.printStackTrace();
            jsonObject = null;
        /*} catch (NoSuchAlgorithmException e){
        	e.printStackTrace(); 
            jsonObject = null;
        } catch (KeyManagementException e){
        	e.printStackTrace(); 
            jsonObject = null;*/
        }
        return jsonObject;
    }
	
	public static JSONObject GetResponseDataByIDS(String url, String postData,String method,String userName,String passwd) {
   		JSONObject jsonObject = null;
   		StringBuffer buffer = new StringBuffer();
   		method = method.toUpperCase();
           System.out.println("URL:"+url); 
           System.out.println("请求参数"+postData);
   		
   		try { 
   			//-----------------------------------------------------
   			//创建SSLContext  https
   		    SSLContext sslContext=SSLContext.getInstance("SSL");  
   		    //TrustManager[] tm={new MyX509TrustManager()};  
   		    
   		    TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
   	               public java.security.cert.X509Certificate[] getAcceptedIssuers() {
   	                   return null;
   	               }
   	               public void checkClientTrusted(X509Certificate[] certs, String authType) {
   	               }
   	               public void checkServerTrusted(X509Certificate[] certs, String authType) {
   	               }
   	           }
   		    };
   		
   		  
   		    
   		    //初始化  
   		    //--sslContext.init(null, tm, new java.security.SecureRandom());
   		    sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
   		    //获取SSLSocketFactory对象  
   		    //--SSLSocketFactory ssf=sslContext.getSocketFactory();
   		    SSLSocketFactory ssf=sslContext.getSocketFactory();
   		    
   		    
   		 // Create all-trusting host name verifier
   	           HostnameVerifier allHostsValid = new HostnameVerifier() {
   	               public boolean verify(String hostname, SSLSession session) {
   	                   return true;
   	               }
   	           };

   	           // Install the all-trusting host verifier
   	           HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
   		    
   			//------------------------------------------------------
               //get请求的url  
               URL url1=new URL(url);  
               //url写法：    协议://IP地址:端口号/访问的文件  
               //协议一般是http，IP地址是要访问的IP地址，端口一般是8080，然后加上访问的是什么  
               //新建一个HttpURLConnection，从url开启一个连接  
               
               //HttpURLConnection connection= (HttpURLConnection) url1.openConnection();
               //https
               HttpsURLConnection conn=(HttpsURLConnection)url1.openConnection(); 
               
               //设置请求的模式  
               conn.setRequestMethod(method);  
               //设置请求连接超时时间  
               conn.setConnectTimeout(25000);  
               //设置访问时的超时时间  
               conn.setReadTimeout(25000); 
               
             //username:password--->访问的用户名，密码,并使用base64进行加密，将加密的字节信息转化为string类型，encoding--->token
               String user = userName+":"+passwd;
               String encoding = DatatypeConverter.printBase64Binary(user.getBytes("UTF-8"));
               
               System.out.println("encoding："+encoding);
               
               //https鉴权请求
               conn.setRequestProperty("Authorization", " Basic "+encoding);
               conn.setUseCaches(false); 
               //设置当前实例使用的SSLSoctetFactory  
               conn.setSSLSocketFactory(ssf);
               //conn.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
               
               
               if("POST".equals(method) || "PUT".equals(method) ){
               	conn.setDoOutput(true);
               	
               	//connection.setDoInput(true);
               	//设置请求属性
               	conn.setRequestProperty("Content-Type", "application/json");
               	conn.setRequestProperty("Charset", "UTF-8");
               	
               	//开启连接  
               	conn.connect();
               	// 获取URLConnection对象对应的输出流
                   PrintWriter printWriter = new PrintWriter(conn.getOutputStream());
                   // 发送请求参数
                   printWriter.write(postData);//post的参数 xx=xx&yy=yy
                  
                   // flush输出流的缓冲
                   printWriter.flush();
                   printWriter.close();
              
               }	
                   InputStream inputStream=null;  
                   BufferedReader reader=null;
                   
                   //如果应答码为200的时候，表示成功的请求带了，这里的HttpURLConnection.HTTP_OK就是200 
                   if(conn.getResponseCode()==HttpURLConnection.HTTP_OK){  
                       //获得连接的输入流  
                       inputStream=conn.getInputStream();  
                       //转换成一个加强型的buffered流  
                       reader=new BufferedReader(new InputStreamReader(inputStream, "utf-8")); 
                       
                       //把读到的内容赋值给result  
                       String result;  
                       while ((result=reader.readLine()) != null) {
                       	buffer.append(result);
                       }
                       
                       if("".equals(buffer)){ 
                       	jsonObject = null;
                       }else{
                       	jsonObject = JSONObject.fromObject(buffer.toString());
                       }
                       
                       //子线程不能更新UI线程的内容，要更新需要开启一个Ui线程，这里Toast要在Ui线程  
                       //result:{"code":0,"description":"","lastUpdateTime":0,"payload":{"id":14,"deleted":0,"createdAt":1493706839000,"updatedAt":1493706839000,"mobile":"11111111111","password":null,"ecCorpId":null,"siCorpId":null,"role":0,"status":0,"createdBy":"admin","updatedBy":"admin","remark":null}}
                       //code:30001短信验证码错误 code:0成功
                       
                   }else{//自定义错误信息
                   	//获得连接的输入流  
                       inputStream = conn.getErrorStream();
                       //转换成一个加强型的buffered流  
                       reader=new BufferedReader(new InputStreamReader(inputStream, "utf-8")); 
                       //把读到的内容赋值给result  
                       String result;  
                       while ((result=reader.readLine()) != null) {
                       	buffer.append(result);
                       }
                       if("".equals(buffer)){ 
                       	jsonObject = null;
                       }else{
                       	jsonObject = JSONObject.fromObject(buffer.toString());
                       }
                   }
                 //关闭流和连接  
                   reader.close();  
                   inputStream.close();
                   conn.disconnect();
                   System.out.println("result:"+buffer.toString());
               
           } catch (MalformedURLException e) {  
               e.printStackTrace(); 
               jsonObject = null;
           } catch (IOException e) {  
               e.printStackTrace();
               jsonObject = null;
           } catch (NoSuchAlgorithmException e){
           	e.printStackTrace(); 
               jsonObject = null;
           } catch (KeyManagementException e){
           	e.printStackTrace(); 
               jsonObject = null;
           }
           return jsonObject;
       }
	
}
