package com.neusoft.mid.ec.api.util.http;

import java.io.*;
import java.net.URLEncoder;
import java.util.*;

import com.neusoft.mid.ec.api.util.UUIDGenerator;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.*;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;


/**
 * @ClassName: HttpRequestUtil
 * @Description: Http请求工具类
 * * @date 2018年2月9日上午9:16:20
 */
public class HttpRequestUtil {

	private static final Logger logger = LoggerFactory.getLogger(HttpRequestUtil.class);

	public static final String UTF8 = "UTF-8";

	private static final String URL_PARAM_CONNECT_FLAG = "&";

	private static final String EMPTY = "";

	private static MultiThreadedHttpConnectionManager connectionManager = null;

	private static int connectionTimeOut = 15000 * 2;

	private static int socketTimeOut = 15000 * 2;

	private static int maxConnectionPerHost = 500;

	private static int maxTotalConnections = 500;

	private static HttpClient client;

	static {
		connectionManager = new MultiThreadedHttpConnectionManager();
		connectionManager.getParams().setConnectionTimeout(connectionTimeOut);
		connectionManager.getParams().setSoTimeout(socketTimeOut);
		connectionManager.getParams().setDefaultMaxConnectionsPerHost(maxConnectionPerHost);
		connectionManager.getParams().setMaxTotalConnections(maxTotalConnections);
		client = new HttpClient(connectionManager);
	}



	/**
	 * POST方式表单提交数据
	 *   包含文件
	 * @param url
	 * @param headParams
	 *            请求头参数
	 */
	public static String URLPostFile(String url, Map<String, Object> headParams, Part[] parts,List<File> listFile) {
        logger.info("URLPostFile>>>url>>>" + url  + ">>>headParams>>>" + headParams);
        String response = EMPTY;
		PostMethod postMethod = null;
		try {
			postMethod = new PostMethod(url);
			// 将表单的head放入postMethod中
			Set<String> headKeySet = headParams.keySet();
			for (String key : headKeySet) {
				Object value = headParams.get(key);
				postMethod.setRequestHeader(key, String.valueOf(value));
			}
			postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
			MultipartRequestEntity entity = new MultipartRequestEntity(parts, postMethod.getParams());
			postMethod.setRequestEntity(entity);

			// 执行postMethod
			int statusCode = client.executeMethod(postMethod);
			if (statusCode == HttpStatus.SC_OK) {
				response = postMethod.getResponseBodyAsString();
			} else {
				logger.error("响应状态码=" + postMethod.getStatusCode());
			}
			//删除临时文件
			for (File f: listFile) {
				if (f.exists()) {
					f.delete();
				}
			}
		} catch (HttpException e) {
			logger.error("发生致命的异常，可能是协议不对或者返回的内容有问题", e);
		} catch (IOException e) {
			logger.error("发生网络异常", e);
		} finally {
			if (postMethod != null) {
				postMethod.releaseConnection();
				postMethod = null;
			}
		}
		return response;
	}
	public static File setFile(MultipartFile file,String name) throws IOException {
		File toFile = null;
		if(file.equals("")||file.getSize()<=0){
			file = null;
		}else {
			InputStream ins = null;
			ins = file.getInputStream();
			toFile = new File(System.currentTimeMillis()+ UUIDGenerator.getUUID()+file.getOriginalFilename());
			inputStreamToFile(ins, toFile);
			ins.close();
		}
		return toFile;
	}
	public static void inputStreamToFile(InputStream ins, File file) {
		try {
			OutputStream os = new FileOutputStream(file);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			os.close();
			ins.close();
		} catch (Exception e) {
			logger.error("文件转换异常");
		}
	}

	/**
	 * POST方式表单提交数据
	 * 
	 * @param url
	 * @param busiParams
	 *            业务参数
	 * @param headParams
	 *            请求头参数
	 */
	public static String URLPost(String url, Map<String, Object> busiParams, Map<String, Object> headParams) {
		String response = EMPTY;
		PostMethod postMethod = null;
		try {
            logger.info("URLPost>>>url>>>" + url + ">>>busiParams>>>" + busiParams + ">>>headParams>>>" + headParams);
            postMethod = new PostMethod(url);
			// 将表单的head放入postMethod中
			Set<String> headKeySet = headParams.keySet();
			for (String key : headKeySet) {
				Object value = headParams.get(key);
				postMethod.setRequestHeader(key, String.valueOf(value));
			}
			// 将表单的值放入postMethod中
			Set<String> keySet = busiParams.keySet();
			for (String key : keySet) {
				Object value = busiParams.get(key);
				postMethod.addParameter(key, String.valueOf(value));
			}
			// 执行postMethod
			postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + UTF8);
			int statusCode = client.executeMethod(postMethod);
			if (statusCode == HttpStatus.SC_OK) {
				response = postMethod.getResponseBodyAsString();
			} else {
				logger.error("响应状态码=" + postMethod.getStatusCode());
			}
		} catch (HttpException e) {
			logger.error("发生致命的异常，可能是协议不对或者返回的内容有问题", e);
		} catch (IOException e) {
			logger.error("发生网络异常", e);
		} finally {
			if (postMethod != null) {
				postMethod.releaseConnection();
				postMethod = null;
			}
		}
		return response;
	}
	public static String URLPost(String url, String busiParams, Map<String, String> headParams) {
		String response = EMPTY;
		PostMethod postMethod = null;
		try {
            logger.info("URLPost>>>url>>>" + url + ">>>busiParams>>>" + busiParams + ">>>headParams>>>" + headParams);
            postMethod = new PostMethod(url);
			// 将表单的head放入postMethod中
			Set<String> headKeySet = headParams.keySet();
			for (String key : headKeySet) {
				Object value = headParams.get(key);
				postMethod.setRequestHeader(key, String.valueOf(value));
			}
			StringRequestEntity requestEntity = new StringRequestEntity(busiParams, "text/plain", "utf-8");
			postMethod.setRequestEntity(requestEntity);
			// 执行postMethod
			postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + UTF8);
			int statusCode = client.executeMethod(postMethod);
			if (statusCode == HttpStatus.SC_OK) {
				response = postMethod.getResponseBodyAsString();
			} else {
				logger.error("响应状态码=" + postMethod.getStatusCode());
			}
		} catch (HttpException e) {
			logger.error("发生致命的异常，可能是协议不对或者返回的内容有问题", e);
		} catch (IOException e) {
			logger.error("发生网络异常", e);
		} finally {
			if (postMethod != null) {
				postMethod.releaseConnection();
				postMethod = null;
			}
		}
		return response;
	}
	/**
	 * POST方式提交数据 json
	 * 
	 * @param url
	 * @param busiParams
	 *            业务参数
	 * @param headParams
	 *            请求头参数
	 */
	public static String URLPostJSONParams(String url, String busiParams, Map<String, Object> headParams) {
		logger.info("URLPostJSONParams>>>url>>>" + url + ">>>busiParams>>>" + busiParams + ">>>headParams>>>" + headParams);
		String response = EMPTY;
		PostMethod postMethod = null;
		try {
			postMethod = new PostMethod(url);
			// 将表单的head放入postMethod中
			if (headParams != null && !headParams.isEmpty()) {
				Set<String> headKeySet = headParams.keySet();
				for (String key : headKeySet) {
					Object value = headParams.get(key);
					postMethod.setRequestHeader(key, String.valueOf(value));
				}
			}
			RequestEntity se = new StringRequestEntity(busiParams, "application/json", UTF8);
			postMethod.setRequestEntity(se);
			// 执行postMethod
			int statusCode = client.executeMethod(postMethod);
			logger.error("响应数据 ={} ",postMethod.getResponseBodyAsString());
			if (statusCode == HttpStatus.SC_OK) {
				byte[] responseBody = postMethod.getResponseBody();
				response = new String(responseBody, UTF8);
			} else {
				logger.error("响应状态码 = " + postMethod.getStatusCode());
			}
		} catch (HttpException e) {
			logger.error("发生致命的异常，可能是协议不对或者返回的内容有问题", e);
		} catch (IOException e) {
			logger.error("发生网络异常", e);
		} finally {
			if (postMethod != null) {
				postMethod.releaseConnection();
				postMethod = null;
			}
		}
		return response;
	}

	/**
	 * POST方式提交数据 json
	 *
	 * @param url
	 * @param busiParams
	 *            业务参数
	 * @param headParams
	 *            请求头参数
	 */
	public static String URLPostXmlParams(String url, String busiParams, Map<String, Object> headParams) {
		logger.info("URLPostXmlParams>>>url>>>" + url + ">>>busiParams>>>" + busiParams + ">>>headParams>>>" + headParams);
		String response = EMPTY;
		PostMethod postMethod = null;
		try {
			postMethod = new PostMethod(url);
			// 将表单的head放入postMethod中
			if (headParams != null && !headParams.isEmpty()) {
				Set<String> headKeySet = headParams.keySet();
				for (String key : headKeySet) {
					Object value = headParams.get(key);
					postMethod.setRequestHeader(key, String.valueOf(value));
				}
			}
			RequestEntity se = new StringRequestEntity(busiParams, "text/xml", UTF8);
			postMethod.setRequestEntity(se);
			// 执行postMethod
			int statusCode = client.executeMethod(postMethod);
			logger.error("响应数据 ={} ",postMethod.getResponseBodyAsString());
			if (statusCode == HttpStatus.SC_OK) {
				byte[] responseBody = postMethod.getResponseBody();
				response = new String(responseBody, UTF8);
			} else {
				logger.error("响应状态码 = " + postMethod.getStatusCode());
			}
		} catch (HttpException e) {
			logger.error("发生致命的异常，可能是协议不对或者返回的内容有问题", e);
		} catch (IOException e) {
			logger.error("发生网络异常", e);
		} finally {
			if (postMethod != null) {
				postMethod.releaseConnection();
				postMethod = null;
			}
		}
		return response;
	}

	/**
	 * GET方式提交数据
	 *
	 * @param url
	 * @param params
	 * @param enc
	 */
	public static String URLGet(String url, Map<String, String> params, String enc) {
        logger.info("URLGet>>>url>>>" + url + ">>>params>>>" + params + ">>>enc>>>" + enc);
	    String response = EMPTY;
		GetMethod getMethod = null;
		StringBuffer strtTotalURL = new StringBuffer(EMPTY);
		if (url.indexOf("?") == -1) {
			strtTotalURL.append(url).append("?").append(getUrl(params, enc));
		} else {
			strtTotalURL.append(url).append("&").append(getUrl(params, enc));
		}
		logger.info("GET请求URL = \n" + strtTotalURL.toString());
		try {
			getMethod = new GetMethod(strtTotalURL.toString());
			getMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + enc);
			// 执行getMethod
			int statusCode = client.executeMethod(getMethod);
			if (statusCode == HttpStatus.SC_OK) {
				response = getMethod.getResponseBodyAsString();
			} else {
				logger.debug("响应状态码 = " + getMethod.getStatusCode());
			}
		} catch (HttpException e) {
			logger.error("发生致命的异常，可能是协议不对或者返回的内容有问题", e);
		} catch (IOException e) {
			logger.error("发生网络异常", e);
		} finally {
			if (getMethod != null) {
				getMethod.releaseConnection();
				getMethod = null;
			}
		}
		return response;
	}


	/**
	 * GET方式提交数据
	 *
	 * @param url
	 * @param params
	 * @param enc
	 */
	public static String URLGet(String url, Map<String, String> params, String enc,Map<String, Object> headParams) {
        logger.info("URLGet>>>url>>>" + url + ">>>params>>>" + params + ">>>enc>>>" + enc+ ">>>headParams>>>" + headParams);
        String response = EMPTY;
		GetMethod getMethod = null;
		StringBuffer strtTotalURL = new StringBuffer(EMPTY);
		if (strtTotalURL.indexOf("?") == -1) {
			strtTotalURL.append(url).append("?").append(getUrl(params, enc));
		} else {
			strtTotalURL.append(url).append("&").append(getUrl(params, enc));
		}
		logger.info("GET请求URL = \n" + strtTotalURL.toString());
		try {
			getMethod = new GetMethod(strtTotalURL.toString());
			getMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + enc);
			if (headParams != null && !headParams.isEmpty()) {
				Set<String> headKeySet = headParams.keySet();
				for (String key : headKeySet) {
					Object value = headParams.get(key);
					getMethod.setRequestHeader(key, String.valueOf(value));
				}
			}
			// 执行getMethod
			int statusCode = client.executeMethod(getMethod);
			logger.info("响应码={},响应结果={}",statusCode,getMethod.getResponseBodyAsString());
			if (statusCode == HttpStatus.SC_OK) {
				response = getMethod.getResponseBodyAsString();
			} else {
				logger.debug("响应状态码 = " + getMethod.getStatusCode());
			}
		} catch (HttpException e) {
			logger.error("发生致命的异常，可能是协议不对或者返回的内容有问题", e);
		} catch (IOException e) {
			logger.error("发生网络异常", e);
		} finally {
			if (getMethod != null) {
				getMethod.releaseConnection();
				getMethod = null;
			}
		}
		return response;
	}
	/**
	 * GET方式提交数据2
	 * application/json
	 * @param url
	 * @param params
	 * @param enc
	 */
	public static String URLGet2(String url, Map<String, String> params, String enc,Map<String, Object> headParams) {
        logger.info("URLGet>>>url>>>" + url + ">>>params>>>" + params + ">>>enc>>>" + enc+ ">>>headParams>>>" + headParams);
        String response = EMPTY;
		GetMethod getMethod = null;
		StringBuffer strtTotalURL = new StringBuffer(EMPTY);
		if (strtTotalURL.indexOf("?") == -1) {
			strtTotalURL.append(url).append("?").append(getUrl(params, enc));
		} else {
			strtTotalURL.append(url).append("&").append(getUrl(params, enc));
		}
		logger.info("GET请求URL = \n" + strtTotalURL.toString());
		try {
			getMethod = new GetMethod(strtTotalURL.toString());
			getMethod.setRequestHeader("Content-Type", "application/json;charset=" + enc);
			if (headParams != null && !headParams.isEmpty()) {
				Set<String> headKeySet = headParams.keySet();
				for (String key : headKeySet) {
					Object value = headParams.get(key);
					getMethod.setRequestHeader(key, String.valueOf(value));
				}
			}
			// 执行getMethod
			int statusCode = client.executeMethod(getMethod);
			if (statusCode == HttpStatus.SC_OK) {
				InputStream is = getMethod.getResponseBodyAsStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				StringBuffer sb = new StringBuffer();
				String result = "";
				while ((result = br.readLine()) != null) {
					sb.append(result);
				}
				response = sb.toString();
				logger.info("响应码={},响应结果={}",statusCode,sb.toString());
			} else {
				logger.debug("响应状态码 = " + getMethod.getStatusCode());
			}
		} catch (HttpException e) {
			logger.error("发生致命的异常，可能是协议不对或者返回的内容有问题", e);
		} catch (IOException e) {
			logger.error("发生网络异常", e);
		} finally {
			if (getMethod != null) {
				getMethod.releaseConnection();
				getMethod = null;
			}
		}
		return response;
	}
	/**
	 * 据Map生成URL字符串
	 * 
	 * @param map
	 * @param valueEnc
	 */
	private static String getUrl(Map<String, String> map, String valueEnc) {
        logger.info("getUrl>>>map>>>" + map + ">>>valueEnc>>>" + valueEnc);
        if (null == map || map.keySet().size() == 0) {
			return (EMPTY);
		}
		StringBuffer url = new StringBuffer();
		Set<String> keys = map.keySet();
		for (Iterator<String> it = keys.iterator(); it.hasNext();) {
			String key = it.next();
			if (map.containsKey(key)) {
				String val = map.get(key);
				String str = val != null ? val : EMPTY;
				try {
					str = URLEncoder.encode(str, valueEnc);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				url.append(key).append("=").append(str).append(URL_PARAM_CONNECT_FLAG);
			}
		}
		String strURL = EMPTY;
		strURL = url.toString();
		if (URL_PARAM_CONNECT_FLAG.equals(EMPTY + strURL.charAt(strURL.length() - 1))) {
			strURL = strURL.substring(0, strURL.length() - 1);
		}
		return (strURL);
	}


}
