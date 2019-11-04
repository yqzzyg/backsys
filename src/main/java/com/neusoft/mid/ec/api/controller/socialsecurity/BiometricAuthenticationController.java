package com.neusoft.mid.ec.api.controller.socialsecurity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.neusoft.mid.ec.api.controller.BaseController;
import com.neusoft.mid.ec.api.util.http.HttpRequestUtil;
import com.neusoft.mid.ec.api.util.http.WebServiceClientUtil;
import me.puras.common.json.Response;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 人社生物认证
 */
@RestController
@RequestMapping("/rs/bat")
public class BiometricAuthenticationController extends BaseController{

    private static Logger LOGGEER = Logger.getLogger(BiometricAuthenticationController.class);

    /*
    *  接口交易码
    * */
    private static  final  String KEY = "asdfbjewaytekyushiban";

    @PostMapping("/saveFaceVfyResult")
    public Response saveFaceVfyResult(@RequestBody Map params,HttpServletRequest request){
        Response<Object> response = new Response<>();
        try {
            LOGGEER.info("人社生物认证接口");
            if (StringUtils.isBlank(params.get("idNo")==null?null:params.get("idNo").toString())
                    || StringUtils.isBlank(params.get("personName")==null?null:params.get("personName").toString())
                    || StringUtils.isBlank(params.get("result")==null?null:params.get("result").toString())
                    || StringUtils.isBlank(params.get("vfyTime")==null?null:params.get("vfyTime").toString())
                    || StringUtils.isBlank(params.get("faceStr")==null?null:params.get("faceStr").toString())
                    ) {
                response.setCode(-2);
                response.setDescription("必传参数不能为空");
                response.setLastUpdateTime(System.currentTimeMillis());
                return  response;
            }
            String str = WebServiceClientUtil.callBiometricAuthenticationService(KEY, JSON.toJSONString(params));
            LOGGEER.info("调用一维生物认证接口反参："+str);
            if (StringUtils.isNotBlank(str)) {
                JSONObject jsonObj = JSONObject.parseObject(str);
                Integer code = jsonObj.get("code")==null?null:Integer.valueOf(jsonObj.getString("code"));
                if (200==code) {
                    response.setCode(0);
                }else {
                    response.setCode(jsonObj.get("code")==null?null:Integer.valueOf(jsonObj.getString("code")));
                }
                response.setDescription(jsonObj.get("message")==null?null:jsonObj.getString("message"));
            }else{
                response.setCode(-1);
                response.setDescription("保存失败");
            }
            response.setLastUpdateTime(System.currentTimeMillis());
        } catch (Exception e) {
            LOGGEER.info("人社生物认证接口[saveFaceVfyResult]异常：",e);
        }
        return response;
    }
}
