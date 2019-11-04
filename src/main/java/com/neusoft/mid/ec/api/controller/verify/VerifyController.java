package com.neusoft.mid.ec.api.controller.verify;

import com.neusoft.mid.ec.api.util.JedisClusterUtil;
import com.neusoft.mid.ec.api.util.RandomValidateCodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Administrator
 */
@RequestMapping("/verify")
@RestController
public class VerifyController {

    private Logger LOG = LoggerFactory.getLogger(VerifyController.class);

    @Autowired
    private JedisClusterUtil jedisClusterUtil;

    private int expireTime = 60 ;

    @Autowired
    RandomValidateCodeUtil randomValidateCode;


    /**
     * 生成验证码
     */
    @GetMapping(value = "/getVerifyCode")
    public void getVerify(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 设置相应类型,告诉浏览器输出的内容为图片
            response.setContentType("image/jpeg");
            response.setHeader("Pragma", "No-cache");
            // 设置响应头信息，告诉浏览器不要缓存此内容
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0);
            // 输出验证码图片方法
            randomValidateCode.getRandcode(request, response);
        } catch (Exception e) {
            LOG.error("获取验证码失败>>>>   ", e);
        }
    }


    @GetMapping("/verifyCode")
    public  Object verifyCode(String randomCode,HttpServletRequest request, HttpServletResponse response){
       return randomValidateCode.verifyCode(request,response,randomCode,null);
    }


}
