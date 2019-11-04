package com.neusoft.mid.ec.api.service.socialsecurity;

import com.neusoft.mid.ec.api.domain.socialsecurity.CardUserInfo;
import com.neusoft.mid.ec.api.domain.socialsecurity.ReportLossInfo;
import com.neusoft.mid.ec.api.exception.GeneralException;
import com.neusoft.mid.ec.api.serviceInterface.socialsecurity.SocialSecurityCardApplyService;
import com.neusoft.mid.ec.api.util.http.HttpRequestUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * 社保卡
 */
@Service
public class SocialSecurityCardApplyServiceImpl implements SocialSecurityCardApplyService {

    private static Logger LOG = LoggerFactory.getLogger(SocialSecurityCardApplyServiceImpl.class);

    @Value("${ssc.username}")
    private String username;

    @Value("${ssc.password}")
    private String password;

    @Value("${ssc.url}")
    private String url;

    @Value("${ssc.token}")
    private String token;

    private static String changeUserInfoXml;
    private static String reportLossXml;
    private static String allDsjkXml;

    static {
        try {
            changeUserInfoXml =  IOUtils.toString(SocialSecurityCardQueryServiceImpl.class.getClassLoader().getResourceAsStream("config/ssc/changeUserInfo.xml"),"utf-8");
        } catch (IOException e) {
            LOG.error("changeUserInfoXml error",e);
            changeUserInfoXml ="";
        }

        try {
            reportLossXml =  IOUtils.toString(SocialSecurityCardQueryServiceImpl.class.getClassLoader().getResourceAsStream("config/ssc/ReportLossInfo.xml"),"utf-8");
        } catch (IOException e) {
            LOG.error("reportLossXml error",e);
            changeUserInfoXml ="";
        }

        try {
            allDsjkXml =  IOUtils.toString(SocialSecurityCardQueryServiceImpl.class.getClassLoader().getResourceAsStream("config/ssc/allDsjk.xml"),"utf-8");
        } catch (IOException e) {
            LOG.error("allDsjkXml error",e);
            changeUserInfoXml ="";
        }
    }
    /**
     * * 社保卡用户信息
     * <操作*>人员信息修改</操作*>
     <用户名*>$username</用户名*>
     <密码*>$password</密码*>
     <证件号码*>$IDNo</证件号码*>
     <姓名*>$name</姓名*>
     <性别>$sex</性别>
     <移动电话>$phone</移动电话>
     <通讯地址>$address</通讯地址>
     <邮政编码>$postalCode</邮政编码
     <电子邮箱>$email</电子邮箱>
     <单位编号>$companyCode</单位编号>
     <单位名称>$company</单位名称>
     <所在社区(村)>$community</所在社区(村)>
     * @return
     */
    private String buildChangeUserInfoPackage(CardUserInfo userInfo){
        try {
            //String packageXml = IOUtils.toString(SocialSecurityCardQueryServiceImpl.class.getClassLoader().getResourceAsStream("config/ssc/changeUserInfo.xml"),"utf-8");
            String  packageXml = changeUserInfoXml.replaceAll("\\$username",username)
                    .replaceAll("\\$password",password)
                    .replaceAll("\\$IDNo",userInfo.getIDNo())
                    .replaceAll("\\$name",userInfo.getName())
                    .replaceAll("\\$sex", StringUtils.defaultIfBlank(userInfo.getSex(),""))
                    .replaceAll("\\$phone",StringUtils.defaultIfBlank(userInfo.getPhone(),""))
                    .replaceAll("\\$address",StringUtils.defaultIfBlank(userInfo.getAddress(),""))
                    .replaceAll("\\$postalCode",StringUtils.defaultIfBlank(userInfo.getPostalCode(),""))
                    .replaceAll("\\$email",StringUtils.defaultIfBlank(userInfo.getEmail(),""))
                    .replaceAll("\\$companyCode",StringUtils.defaultIfBlank(userInfo.getCompanyCode(),""))
                    .replaceAll("\\$company",StringUtils.defaultIfBlank(userInfo.getCompany(),""))
                    .replaceAll("\\$community",StringUtils.defaultIfBlank(userInfo.getCommunity(),""));


            packageXml = allDsjkXml.replaceAll(Matcher.quoteReplacement("$xml"),packageXml);

            LOG.info("package xml={}",packageXml);
            return packageXml;
        } catch (Exception e) {
            LOG.warn("读取错误",e);
        }
        return "";
    }

    /**
     * * 社保卡用户信息
     * <操作*>人员信息修改</操作*>
     <用户名*>$username</用户名*>
     <密码*>$password</密码*>
     <证件号码*>$IDNo</证件号码*>
     <姓名*>$name</姓名*>
     <性别>$sex</性别>
     <移动电话>$phone</移动电话>
     <通讯地址>$address</通讯地址>
     <邮政编码>$postalCode</邮政编码
     <电子邮箱>$email</电子邮箱>
     <单位编号>$companyCode</单位编号>
     <单位名称>$company</单位名称>
     <所在社区(村)>$community</所在社区(村)>
     * @return
     */
    private String buildReportLossInfoPackage(ReportLossInfo reportLossInfo){
        try {
           // String packageXml = IOUtils.toString(SocialSecurityCardQueryServiceImpl.class.getClassLoader().getResourceAsStream("config/ssc/ReportLossInfo.xml"),"utf-8");
             String  packageXml = reportLossXml.replaceAll("\\$username",username)
                    .replaceAll("\\$password",password)
                    .replaceAll("\\$IDNo",reportLossInfo.getIDNo())
                    .replaceAll("\\$name", reportLossInfo.getName())
                    .replaceAll("\\$cardNo",reportLossInfo.getCardNo())
                    .replaceAll("\\$cityCode",reportLossInfo.getCityCode() == null ? "" : reportLossInfo.getCityCode());

            LOG.info("allDsjkXml xml={}",allDsjkXml);
            LOG.info("allDsjkXml xml={}",Matcher.quoteReplacement("$xml"));

            packageXml = allDsjkXml.replaceAll(Matcher.quoteReplacement("$xml"),packageXml);

            LOG.info("package xml={}",packageXml);

            return packageXml;
        } catch (Exception e) {
            LOG.warn("读取错误",e);
        }
        return "";
    }


    @Override
    public String changeUserInfo(CardUserInfo userInfo) throws IOException, JDOMException, GeneralException {
        LOG.info("changeUserInfo request >>userInfo={}",userInfo.toString());

        Map headerParams = new HashMap(1);
        headerParams.put("Authorization","bearer " + token);

        String result = HttpRequestUtil.URLPostXmlParams(url, buildChangeUserInfoPackage(userInfo), headerParams);
        LOG.info("changeUserInfo result = {}",result);

        if(result == null){
            throw new GeneralException("查询接口出错");
        }

        SAXBuilder saxBuilder = new SAXBuilder();
        Document document = saxBuilder.build(new ByteArrayInputStream(result.getBytes("utf-8")));

        //4.获取根节点
        Element rootElement = document.getRootElement();
        System.out.println(rootElement.getName());
        //5.获取子节点
        List<Element> children = rootElement.getChildren();
        for (Element child : children) {
            List<Element> childrenList = child.getChildren();
            for (Element o : childrenList) {
                if("allDsjkResponse".equals(o.getName())){
                    if(o.getValue() == null){
                        return "";
                    }
                    String tmp = o.getValue().replaceAll(Matcher.quoteReplacement("<ERR>"),"")
                            .replaceAll(Matcher.quoteReplacement("</ERR>"),"");
                        return tmp;
                }
            }
        }
        return  "";
    }

    @Override
    public String reportLossInfo(ReportLossInfo reportLossInfo) throws GeneralException, IOException, JDOMException {
        LOG.info("reportLossInfo request >>reportLossInfo={}",reportLossInfo.toString());

        Map headerParams = new HashMap(1);
        headerParams.put("Authorization","bearer " + token);

        String result = HttpRequestUtil.URLPostXmlParams(url, buildReportLossInfoPackage(reportLossInfo), headerParams);
        LOG.info("reportLossInfo result = {}",result);

        if(result == null){
            throw new GeneralException("查询接口出错");
        }

        SAXBuilder saxBuilder = new SAXBuilder();
        Document document = saxBuilder.build(new ByteArrayInputStream(result.getBytes("utf-8")));

        //4.获取根节点
        Element rootElement = document.getRootElement();
        System.out.println(rootElement.getName());
        //5.获取子节点
        List<Element> children = rootElement.getChildren();
        for (Element child : children) {
            List<Element> childrenList = child.getChildren();
            for (Element o : childrenList) {
                if("allDsjkResponse".equals(o.getName())){
                    if(o.getValue() != null){
                        String tmp =  o.getValue().replaceAll(Matcher.quoteReplacement("<ERR>"),"")
                                .replaceAll(Matcher.quoteReplacement("</ERR>"),"");
                        return tmp;
                    }
                }
            }
        }
        return  "";
    }
}
