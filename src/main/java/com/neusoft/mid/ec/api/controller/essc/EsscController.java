/**   
* @Title: EsscController.java
* @Package com.neusoft.mid.ec.api.controller.essc
* @Description: TODO
* @author zhaohk   
* @date 2019年10月16日 下午1:07:35
* @version V1.0   
*/
package com.neusoft.mid.ec.api.controller.essc;

import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.neusoft.mid.ec.api.Constants;
import com.neusoft.mid.ec.api.controller.BaseController;
import com.neusoft.mid.ec.api.domain.essc.HnesscSigninfo;
import com.neusoft.mid.ec.api.serviceInterface.essc.EsscService;
import com.neusoft.mid.ec.api.util.Date2TampsUtil;
import com.neusoft.mid.ec.api.util.http.HttpRequestUtil;
import com.neusoft.mid.ec.api.util.rypto.AESUtils;
import com.neusoft.mid.ec.api.util.rypto.RSAUtils;
import me.puras.common.json.Response;


/**
* @ClassName: EsscController
* @Description: 河南省电子社保卡接入
* @author zhaohk
* @date 2019年10月16日 下午1:07:35
* 
*/
@RestController
@RequestMapping("/hnessc")
public class EsscController extends BaseController {
	
	@Autowired
    private Environment environment;

	@Autowired
	private EsscService esscService;
	/**
	* <p>Title: </p>
	* <p>Description: </p>
	*/
	public EsscController() {
	}
	/**
               * 河南省电子社保卡H5签发
     * @param params
     * @return
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/hnessCheck", method = RequestMethod.POST)
	public Response<Map<String, String>> hnessCheck(HttpServletRequest request) {
    	Map<String, String> ret = new HashMap<>();
    	Response<Map<String, String>> response = new Response<>();
    	int rescode = 0;

		try {
		    	String user_id = request.getHeader("yunzheng");
		    	String real_name = URLDecoder.decode(request.getHeader("name"), Constants.CHARSET);
		    	String user_card_no = request.getHeader("idno");
				if (user_id!=null&&!user_id.equals("")&&real_name!=null&&!real_name.equals("")&&user_card_no!=null&&!user_card_no.equals("")) {
					Map<String, String> prtMap = new HashMap<>();
					prtMap.put("user_id", user_id);
					prtMap.put("real_name", real_name);
					prtMap.put("user_card_no", user_card_no);
					prtMap.put("elec_card_url", environment.getProperty("hnessc.ELEC_CARD_URL"));
					String prtMapstr = RSAUtils.getJsonFromMap(RSAUtils.sortedMap(prtMap));
					Map<String, String> paramsMap = new HashMap<String, String>();
					String datestr = Date2TampsUtil.date2Str2(new Date());
					String serialnum=RandomStringUtils.randomAlphanumeric(12);
					paramsMap.put("outorderno", datestr+serialnum);
					paramsMap.put("bizid", environment.getProperty("hnessc.BIZID"));
					paramsMap.put("timestamp", Date2TampsUtil.convertDateToString2(new Date()));
					paramsMap.put("cmdno", "EC1006");
					paramsMap.put("platreqcontext", prtMapstr);
					paramsMap.put("extendparams", "");
					String ret1 = RSAUtils.sortedParams(paramsMap);
					//System.out.println("待签名串："+ret1);
					String ret2 = RSAUtils.signByPrivateKey(ret1,environment.getProperty("hnessc.RSAPRIVATEKEY"));
					//System.out.println("已签名串："+ret2);
					
					Map<String, String> paramsMap2 =  RSAUtils.sortedMap(paramsMap);
					paramsMap2.put("sign",ret2);
					paramsMap2.replace("platreqcontext", "######");
					
					String paramsMapstr = RSAUtils.getJsonFromMap(paramsMap2);
					paramsMapstr =  paramsMapstr.replace("\"######\"", prtMapstr);
					//System.out.println("请求报文明文："+paramsMapstr);
					String aexedtest = AESUtils.encrypt(paramsMapstr,environment.getProperty("hnessc.AESKEY"));
					//System.out.println("请求报文密文："+aexedtest);
					
					Map<String, String> mheader = new HashMap<>();
					mheader.put("Content-Type", "text/plain;charset=utf-8");

					String encryStr = HttpRequestUtil.URLPost(environment.getProperty("hnessc.HNSESSCPLTFMURL"),aexedtest,mheader);
						String plaintest = AESUtils.decrypt(encryStr,environment.getProperty("hnessc.AESKEY"));
						System.out.println("plaintest="+plaintest);
						Map<String, Object> retMap = JSON.parseObject(plaintest, new TypeReference<Map<String, Object>>() {});
						if (retMap.get("rspcode").equals("0")) {
							boolean succ = verifySign(retMap);
							if (succ) {
								Map<String, Object> platrspconmap = (Map<String, Object>)retMap.get("platrspcontext");
								String return_url = (String)platrspconmap.get("return_url");
								ret.put("return_url", return_url);
								ret.put("msg", "成功返回");
							} else {
								rescode = -8;
								ret.put("msg", "签名错误");
							}
						} else if(retMap.get("rspcode").equals("-8")){
							ret.put("msg", "签名错误");
							rescode = -8;
						} else if(retMap.get("rspcode").equals("-9")){
							ret.put("msg", "参数错误");
							rescode = -9;
						} else if(retMap.get("rspcode").equals("-10")){
							ret.put("msg", "业务错误");
							rescode = -10;
						}
					
				}else {
					ret.put("msg", "参数错误");
					rescode = -1;
				}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			ret.put("msg", e.getMessage());
			rescode = -1;
		}
		response.setCode(rescode);
		response.setLastUpdateTime(new Date().getTime());
		if (!ret.isEmpty()) {
			response.setPayload(ret);
		}
		return response;
		
	}
    
	@SuppressWarnings("unchecked")
	private boolean verifySign(Map<String, Object> retMap) throws Exception {
		boolean ret = false;
		String sign = retMap.get("sign").toString();
		System.out.println("sign="+sign);
		Map<String, Object> datamap = new HashMap<>();
		datamap.putAll(retMap);
		datamap.remove("sign");
		Map<String, String> platrspconmap = (Map<String, String>)datamap.get("platrspcontext");
		String prtMapstr = RSAUtils.getJsonFromMap(RSAUtils.sortedMap(platrspconmap));
		datamap.remove("platrspcontext");
		
		Map<String, String> datastrmap = new HashMap<>();
		for (Map.Entry<String, Object> entry : datamap.entrySet()) {
			datastrmap.put(entry.getKey(), (String)entry.getValue());
		}
		datastrmap.put("platrspcontext",prtMapstr);
		String data = RSAUtils.sortedParams(datastrmap);
		ret = RSAUtils.verifyByPublicKey(data, sign,environment.getProperty("hnessc.RSAPUBLICKEY"));
				
		return ret;
	}
	
	@RequestMapping(value = "/receiveData", method = RequestMethod.POST)
	public Map<String, String> receiveData(@RequestBody String para, HttpServletRequest request) {
		Map<String, String> map = new HashMap<>();
		map.put("rspTime", Date2TampsUtil.dateFormat());
		try {
			String plaintest = AESUtils.decrypt(para,environment.getProperty("hnessc.AESKEY"));
			System.out.println("plaintest="+plaintest);
			Map<String, String> retMap = JSON.parseObject(plaintest, new TypeReference<Map<String, String>>() {});
			map.put("rspMsgId", retMap.get("reqMsgId"));
			boolean succ = verifySign2(retMap);
			if (succ) {
				String reqContent = retMap.get("reqContent");
				if (reqContent!=null&&!reqContent.equals("")) {
					HnesscSigninfo signinfo = JSON.parseObject(plaintest, new TypeReference<HnesscSigninfo>() {});
					signinfo.setReqTime(retMap.get("reqTime"));
					signinfo.setReqMsgId(retMap.get("reqMsgId"));
					signinfo.setCmdNo(retMap.get("cmdNo"));
					esscService.save(signinfo);
					map.put("code", "SUCCESS");
					map.put("msg", "成功");
				} else {
					map.put("code", "SUCCESS");
					map.put("msg", "没有携带业务参数");
				}
			} else {
				map.put("code", "SIGN_ERROR");
				map.put("msg", "签名异常");
				//response.setCode(-8);
			}
		} catch (Exception e) {
			map.put("code", "INNER_ERROR");
			map.put("msg", "内部错误");
		}

		try {
			String ret1 = RSAUtils.sortedParams(map);
			String ret2 = RSAUtils.signByPrivateKey(ret1,environment.getProperty("hnessc.RSAPRIVATEKEY"));
			map.put("sign", ret2);
			//response.setPayload(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return map;
	}
	
	private boolean verifySign2(Map<String, String> retMap) throws Exception {
		boolean ret = false;
		String sign = retMap.get("sign").toString();
		System.out.println("sign="+sign);
		retMap.remove("sign");
		String data = RSAUtils.sortedParams(retMap);
		ret = RSAUtils.verifyByPublicKey(data, sign,environment.getProperty("hnessc.RSAPUBLICKEY"));		
		return ret;
	}

}
