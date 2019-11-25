/**   
* @Title: CcbController.java
* @Package com.neusoft.mid.ec.api.controller.ccb
* @Description: TODO
* @author zhaohk   
* @date 2019年11月18日 上午9:18:03
* @version V1.0   
*/
package com.neusoft.mid.ec.api.controller.ccb;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.neusoft.mid.ec.api.controller.BaseController;
import com.neusoft.mid.ec.api.util.Date2TampsUtil;
import com.neusoft.mid.ec.api.util.ccbRypto.AESUtil;
import com.neusoft.mid.ec.api.util.ccbRypto.RSAUtilV2;
import com.neusoft.mid.ec.api.util.ccbRypto.SHA256withRSA;

import me.puras.common.json.Response;

/**
* @ClassName: CcbController
* @Description: TODO(这里用一句话描述这个类的作用)
* @author zhaohk
* @date 2019年11月18日 上午9:18:03
* 
*/
@RestController
@RequestMapping("/ccb")
public class CcbController extends BaseController {

	@Autowired
    private Environment environment;
	
	public CcbController() {
	}
	
	@RequestMapping(value = "/getReqInfo", method = RequestMethod.POST)
	public Response<Map<String, String>> getReqInfo(@RequestBody Map<String,String> params,HttpServletRequest request) {
		Response<Map<String, String>> response = new Response<>();
    	int rescode = 0;
    	String msg = "";
    	try {
        	boolean succ = verifyParams(params);
        	if (succ) {
        		Map<String, Object> mapHeaders = getMapHeaders(request);
                
        		Map<String, String> paraMap = new LinkedHashMap<>();
                paraMap.put("Vno","2");
                paraMap.put("Sgn_Algr","SHA256withRSA");
                paraMap.put("CstPty_Ordr_No","");
                paraMap.put("Fee_Itm_Prj_User_No","");
                paraMap.put("MrchCd",environment.getProperty("CCB_MrchCd"));
                paraMap.put("PyF_ClCd",params.get("pyFClCd"));
                paraMap.put("Admn_Rgon_Cd",params.get("admnRgonCd"));
                paraMap.put("Fee_Itm_PyF_MtdCd",params.get("feeItmPyFMtdCd"));
                paraMap.put("Usr_ID",(String)mapHeaders.get("yunzheng"));
                paraMap.put("Cst_Nm","");
                paraMap.put("Crdt_Tp","");
                paraMap.put("Crdt_No",(String)mapHeaders.get("idno"));
                paraMap.put("MblPh_No","");
                paraMap.put("Email","");
                paraMap.put("Ret_Url","");
                paraMap.put("Tms",Date2TampsUtil.date2Str3(new Date()));
                paraMap.put("IsMobile","1");
                String oriParam =  map2Str(paraMap);
                System.out.println("oriParam = "+oriParam);
                String signInf = SHA256withRSA.sign(environment.getProperty("CCB_PRIVATE_KEY_C"), oriParam);
                /*succ = SHA256withRSA.verifySign(environment.getProperty("CCB_PUBLIC_KEY_C"), oriParam, signInf);
                if (!succ){
        			System.out.println("验签失败");
        			signInf = SHA256withRSA.sign(environment.getProperty("CCB_PRIVATE_KEY_C"), oriParam);
        			succ = SHA256withRSA.verifySign(environment.getProperty("CCB_PUBLIC_KEY_C"), oriParam, signInf);
        			if (!succ) {
        				rescode = 3;
        				msg = "签名错误";
        			}
        		}*/
                if (succ) {
                	String paraStr = oriParam+"&SIGN_INF="+signInf;
                    System.out.println("paraStr = "+paraStr);
                    String AESKEY = UUID.randomUUID().toString().replace("-","")+UUID.randomUUID().toString().replace("-","");
                    System.out.println("AESKEY = "+AESKEY);
                    String Tprt_Mode = "1";
                    System.out.println("String Tprt_Mode = \""+Tprt_Mode+"\";");
                    String Tprt_Parm = RSAUtilV2.encryptByPublic(AESKEY.getBytes(), RSAUtilV2.getPublicKey(environment.getProperty("CCB_PUBLIC_KEY_P")));
                    System.out.println("String Tprt_Parm = \""+Tprt_Parm+"\";");
                    String Bsn_Data = AESUtil.encrypt(paraStr, AESKEY);
                    
                    Map<String, String> ret = new HashMap<>();
                    ret.put("tprtMode", "1");
                    ret.put("versionflag", "2");
                    ret.put("url",environment.getProperty("CCB_URL"));
                    ret.put("tprtParm", Tprt_Parm);
                    ret.put("bsnData", Bsn_Data);
                    response.setPayload(ret);
                    msg = "成功";
    			}
    		}else {
    			rescode = 2;
    			msg = "参数错误";
    		}
		} catch (Exception e) {
			rescode = -1;
			msg = e.getMessage();
		}
    	response.setCode(rescode);
    	response.setDescription(msg);
    	response.setLastUpdateTime(System.currentTimeMillis());
    	return response;
	}
	
    private String map2Str(Map<String, String> map) {
    	String ret = "";
    	if (map!=null&&!map.isEmpty()) {
    		StringBuffer strbuf = new StringBuffer();
        	Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        	while (iterator.hasNext()) {
        		Map.Entry<String, String> entry = iterator.next();
        		strbuf.append(entry.getKey());
        		strbuf.append("=");
        		strbuf.append(entry.getValue());
        		if (iterator.hasNext()) {
        			strbuf.append("&");
    			}
    		}
        	ret = strbuf.toString();
		}
    	return ret;
    }
    
    private boolean verifyParams(Map<String,String> params) {
    	boolean succ = false;
    	if (params.containsKey("pyFClCd")&&params.containsKey("feeItmPyFMtdCd")&&params.containsKey("admnRgonCd")) {
    		succ = test("\\d{5}", params.get("pyFClCd"));
    		if (succ) {
        		succ = test("0[0,1]",params.get("feeItmPyFMtdCd"));
        		if (succ) {
        			succ = test("\\d{6}",params.get("admnRgonCd"));
				}
			}
		}
    	return succ;
    }
    
    private boolean test(String patternStr, String source) {
    	boolean ret = false;
    	Pattern pattern = Pattern.compile(patternStr, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(source);
		ret = matcher.matches();
    	return ret;
    }

}
