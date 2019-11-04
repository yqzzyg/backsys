package com.neusoft.mid.ec.api.controller.civiladministration;

import com.alibaba.fastjson.JSONObject;
import com.neusoft.mid.ec.api.controller.BaseController;
import com.neusoft.mid.ec.api.service.civiladministration.BookDept.BookDeptService;
import com.neusoft.mid.ec.api.service.civiladministration.BookDept.BookDeptServicePortType;
import com.neusoft.mid.ec.api.service.civiladministration.BookTime.BookTimeService;
import com.neusoft.mid.ec.api.service.civiladministration.BookTime.BookTimeServicePortType;
import com.neusoft.mid.ec.api.service.civiladministration.CancelBook.CancelBookMarryService;
import com.neusoft.mid.ec.api.service.civiladministration.CancelBook.CancelBookMarryServicePortType;
import com.neusoft.mid.ec.api.service.civiladministration.MaryyBook.MaryyBookService;
import com.neusoft.mid.ec.api.service.civiladministration.MaryyBook.MaryyBookServicePortType;
import com.neusoft.mid.ec.api.service.civiladministration.QueryBook.QueryBookMarryService;
import com.neusoft.mid.ec.api.service.civiladministration.QueryBook.QueryBookMarryServicePortType;
import com.neusoft.mid.ec.api.util.JacksonUtils;
import com.neusoft.mid.ec.api.util.MD5Util;
import me.puras.common.json.Response;

import net.sf.json.util.JSONUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 民政业务
 */
@RestController
@RequestMapping("/civiladministration")
public class CivilAdministrationController extends BaseController {


    @Autowired
    private Environment environment;
    private Map<String, Object> areaMap = new HashMap<String, Object>();

    /**
     *预约登记机关
     * @param params
     * @return
     */
    @RequestMapping(value = "/getDeptInfo", method = RequestMethod.POST)
    public Response getDeptInfo(@RequestBody Map params, HttpServletRequest request) {
        logger.info("预约登记机关查询接口[getDeptInfo]入参" + params);
        Response<Object> object = new Response<>();
        try {
            if(null==params.get("opType")||null==params.get("regionCode")){
                object.setCode(1);
                object.setDescription("业务类型或行政编码为空");
                return object;
            }
            String applyKey = getApplyKeyStr(params);
            String urlStr = environment.getProperty("mz.apply.url");
            URL url = new URL(urlStr+"getDeptInfo?wsdl");
            BookDeptService bds = new BookDeptService(url);
            BookDeptServicePortType bdsp = bds.getBookDeptServicePort();
            String deptInfo = bdsp.getDeptInfo(applyKey,params.get("opType").toString(), params.get("regionCode").toString());
            //String deptInfo = bdsp.getDeptInfo("7af398e2940bed7ad72678c86434e441","IA", "500114");
            logger.info("调用预约登记机关查询到的数据：" + deptInfo);
            if (StringUtils.isNotBlank(deptInfo)) {
                JSONObject json = JSONObject.parseObject(deptInfo);
                //如果回传数据成功取出data
                if ("0".equals(json.getString("returnCode"))) {
                    if(json.get("returnMessage") != null){
                        if(JSONUtils.isString(json.get("returnMessage"))){
                            object.setDescription(json.get("returnMessage").toString());
                        }else{
                            object.setPayload(json.get("returnMessage"));
                            object.setDescription("获取预约登记机关成功!");
                        }
                        object.setCode(0);
                    }else{
                        object.setCode(0);
                        object.setDescription("该区县市暂无登记机构!");
                    }
                }else{
                    if(JSONUtils.isString(json.get("returnMessage"))){
                        object.setDescription(json.get("returnMessage").toString());
                    }else{
                        object.setDescription("获取预约登记机关失败!");
                        object.setPayload(json.get("returnMessage"));
                    }
                    object.setCode(-1);
                }

            } else {
                logger.info("调用预约登记机关接口异常返回值为空,入参为：[{}]", params);
                object.setCode(500);
                object.setDescription("内部服务错误");
            }
            object.setLastUpdateTime(System.currentTimeMillis());
        }catch (Exception e){
            logger.info("调用预约登记机关接口异常返回值为空,入参为：[{}]", params);
            object.setCode(500);
            object.setDescription("内部服务错误");
        }

        return object;
    }

    /**
     *预约登记时间
     * @param params
     * @return
     */
    @RequestMapping(value = "/getBookTime", method = RequestMethod.POST)
    public Response getBookTime(@RequestBody Map params, HttpServletRequest request) {
        logger.info("预约登记时间查询接口[getBookTime]入参" + params);
        Response<Object> object = new Response<>();

        try {
            if(null==params.get("opType")||null==params.get("deptCode")){
                object.setCode(1);
                object.setDescription("业务类型或登记机关代码为空");
                return object;
            }
            String applyKey = getApplyKeyStr(params);
            String urlStr = environment.getProperty("mz.apply.url");
            URL url = new URL(urlStr+"getBookTime?wsdl");
            BookTimeService bs= new BookTimeService(url);
            BookTimeServicePortType bsp = bs.getBookTimeServicePort();
            String bookTime = bsp.getBookTime(applyKey,params.get("opType").toString(), params.get("deptCode").toString());// 业务类型， 登记处代码
            logger.info("调用预约登记时间查询到的数据：" + bookTime);
            if (StringUtils.isNotBlank(bookTime)) {
                JSONObject json = JSONObject.parseObject(bookTime);
                //如果回传数据成功取出data
                if ("0".equals(json.getString("returnCode"))) {
                    if(json.get("returnMessage") != null){
                        if(JSONUtils.isString(json.get("returnMessage"))){
                            object.setDescription(json.get("returnMessage").toString());
                        }else{
                            object.setPayload(json.get("returnMessage"));
                            object.setDescription("获取预约登记时间成功!");
                        }
                        object.setCode(0);
                    }else{
                        object.setCode(0);
                        object.setDescription("暂无预约登记时间!");
                    }
                }else{
                    if(JSONUtils.isString(json.get("returnMessage"))){
                        object.setDescription(json.get("returnMessage").toString());
                    }else{
                        object.setDescription("获取预约登记时间失败!");
                        object.setPayload(json.get("returnMessage"));
                    }
                    object.setCode(-1);
                }

            } else {
                logger.info("调用预约登记时间接口异常返回值为空,入参为：[{}]", params);
                object.setCode(500);
                object.setDescription("内部服务错误");
            }
            object.setLastUpdateTime(System.currentTimeMillis());
        } catch (Exception e) {
            logger.info("调用预约登记时间接口异常返回值为空,入参为：[{}]", params);
            object.setCode(500);
            object.setDescription("内部服务错误");
        }

        return object;
    }


    /**
     *预约信息查询(预结、 预离)
     * @param params
     * @return
     */
    @RequestMapping(value = "/queryBookMarryInfo", method = RequestMethod.POST)
    public Response queryBookMarryInfo(@RequestBody Map params, HttpServletRequest request) {
        logger.info("预约信息查询接口[queryBookMarryInfo]入参" + params);
        Response<Object> object = new Response<>();

        try {
            if(null==params.get("certNoMan")||null==params.get("certNoWoman")){
                object.setCode(1);
                object.setDescription("证件号码不能为空!");
                return object;
            }
            String applyKey = getApplyKeyStr(params);
            String urlStr = environment.getProperty("mz.apply.url");
            URL url = new URL(urlStr+"queryBookMarryInfo?wsdl");
            QueryBookMarryService bookMarryService = new QueryBookMarryService(url);
            QueryBookMarryServicePortType qbmspt
                    = bookMarryService.getQueryBookMarryServicePort();
            String queryBookMarryInfo = qbmspt.queryBookMarryInfo(applyKey , params.get("opType").toString(),
                    params.get("certNoMan").toString(), params.get("certNoWoman").toString());
            logger.info("调用预约信息查询到的数据：" + queryBookMarryInfo);
            if (StringUtils.isNotBlank(queryBookMarryInfo)) {
                JSONObject json = JSONObject.parseObject(queryBookMarryInfo);
                //如果回传数据成功取出data
                if ("0".equals(json.getString("returnCode"))) {
                    if(json.get("returnMessage") != null){
                        if(JSONUtils.isString(json.get("returnMessage"))){
                            object.setDescription(json.get("returnMessage").toString());
                        }else{
                            object.setPayload(json.get("returnMessage"));
                            object.setDescription("获取预约信息查询成功!");
                        }
                    }else{
                        object.setDescription("获取预约信息查询成功!");
                    }
                    object.setCode(0);
                }else if("01".equals(json.getString("returnCode"))){
                    object.setCode(-1);
                    object.setDescription("没有查到相关信息!");
                }else{
                    if(JSONUtils.isString(json.get("returnMessage"))){
                        object.setDescription(json.get("returnMessage").toString());
                    }else{
                        object.setDescription("获取预约信息查询失败!");
                        object.setPayload(json.get("returnMessage"));
                    }
                    object.setCode(-1);
                }

            } else {
                logger.info("调用预约信息查询接口异常返回值为空,入参为：[{}]", params);
                object.setCode(500);
                object.setDescription("内部服务错误");
            }
            object.setLastUpdateTime(System.currentTimeMillis());
        } catch (Exception e) {
            logger.info("调用预约信息查询接口异常返回值为空,入参为：[{}]", params);
            object.setCode(500);
            object.setDescription("内部服务错误");
        }

        return object;
    }


    /**
     *撤销预约(预结、 预离)
     * @param params
     * @return
     */
    @RequestMapping(value = "/cancelBook", method = RequestMethod.POST)
    public Response cancelBook(@RequestBody Map params, HttpServletRequest request) {
        logger.info("撤销预约查询接口[cancelBook]入参" + params);
        Response<Object> object = new Response<>();

        try {
            if(null==params.get("certNoMan")||null==params.get("certNoWoman")){
                object.setCode(1);
                object.setDescription("证件号码不能为空!");
                return object;
            }
            String applyKey = getApplyKeyStr(params);
            String urlStr = environment.getProperty("mz.apply.url");
            URL url = new URL(urlStr+"cancelBook?wsdl");
            CancelBookMarryService cbms= new CancelBookMarryService(url);
            CancelBookMarryServicePortType cbmspt = cbms.getCancelBookMarryServicePort();
            String cancelBook = cbmspt.cancelBook(applyKey , params.get("opType").toString(),
                    params.get("certNoMan").toString(), params.get("certNoWoman").toString());
            System.out.println(cancelBook);
            logger.info("调用撤销预约到的数据：" + cancelBook);
            if (StringUtils.isNotBlank(cancelBook)) {
                JSONObject json = JSONObject.parseObject(cancelBook);
                //如果回传数据成功取出data
                if ("4".equals(json.getString("returnCode"))) {
                    if(json.get("returnMessage") != null){
                        if(JSONUtils.isString(json.get("returnMessage"))){
                            object.setDescription(json.get("returnMessage").toString());
                        }else{
                            object.setPayload(json.get("returnMessage"));
                            object.setDescription("撤销预约成功!");
                        }
                    }else{
                        object.setDescription("撤销预约成功!");
                    }
                    object.setCode(0);
                }else{
                    if(JSONUtils.isString(json.get("returnMessage"))){
                        object.setDescription(json.get("returnMessage").toString());
                    }else{
                        object.setDescription("撤销预约失败!");
                        object.setPayload(json.get("returnMessage"));
                    }
                    object.setCode(-1);
                }

            } else {
                logger.info("调用撤销预约接口异常返回值为空,入参为：[{}]", params);
                object.setCode(500);
                object.setDescription("内部服务错误");
            }
            object.setLastUpdateTime(System.currentTimeMillis());
        } catch (Exception e) {
            logger.info("调用撤销预约接口异常返回值为空,入参为：[{}]", params);
            object.setCode(500);
            object.setDescription("内部服务错误");
        }

        return object;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @PostConstruct
    private void init() {
        try {
            // 读取地区文件
            File file = ResourceUtils.getFile("classpath:config/sys_code.sql");
            String content = FileUtils.readFileToString(file, "UTF-8");
            // 解析文件：移除无效字符
            int index = content.indexOf("INSERT INTO");
            content = content.substring(index);
            content = content.replaceAll("INSERT INTO `sys_code` VALUES \\(", "");
            content = content.replaceAll("'| |\\s", "");
            String[] rows = content.split("\\);");
            // 解析文件：封装tree
            List<Map> treeList = new ArrayList<Map>();
            for(String row : rows) {
                if(StringUtils.isBlank(row)) {
                    continue;
                }
                String[] cells = row.split(",");
                Map node = new HashMap();
                node.put("id",cells[0]);
                node.put("code",cells[1]);
                node.put("name",cells[2]);
                node.put("parentId",cells[3]);
                treeList.add(node);
            }
            for (Map node : treeList) {
                String id = (String) node.get("id");
                String parentId = (String) node.get("parentId");
                //找到根
                if (StringUtils.equals(parentId, "1")) {
                    areaMap = node;
                }
                //找到子
                for (Map childrenNode : treeList) {
                    String childrenParentId = (String)childrenNode.get("parentId");
                    if (StringUtils.equals(childrenParentId, id)) {
                        List childrens = new ArrayList();
                        if (node.get("children") != null) {
                            childrens = (List) node.get("children");
                        }
                        childrens.add(childrenNode);
                        node.put("children", childrens);
                    }
                }
            }
        } catch (Exception e) {
            logger.info("民政地区初始化异常", e);
        }
    }

    /**
     * 查询所有地区
     * @return
     */
    @SuppressWarnings({ "rawtypes"})
    @RequestMapping(value = "/area")
    public Response area() {
        Response<Object> object = new Response<>();
        try {
            object.setPayload(areaMap);
            object.setCode(0);
            object.setDescription("成功!");
        } catch (Exception e) {
            logger.info("民政地区查询异常", e);
            object.setCode(500);
            object.setDescription("内部服务错误");
        }
        return object;
    }


    /**
     *登记信息录入
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveBookMarryInfo", method = RequestMethod.POST)
    public Response saveBookMarryInfo(@RequestBody Map params, HttpServletRequest request) {
        logger.info("登记信息录入接口[saveBookMarryInfo]入参" + params);
        Map paramMap = new HashMap();
        Response<Object> object = new Response<>();

        try {
           /* if(null==params.get("opType")||null==params.get("bookDate")||null==params.get("bookTimeId")||null==params.get("startTime")||null==params.get("endTime")||null==params.get("DeptId")
                    ||null==params.get("nameMan")||null==params.get("nameWoman")||null==params.get("birthMan")||null==params.get("birthWoman")||null==params.get("idTypeMan")||null==params.get("idTypeWoman")
                    ||null==params.get("certTypeMan")||null==params.get("certTypeWoman")||null==params.get("certNumMan")||null==params.get("certNumWoman")||null==params.get("nationMan")||null==params.get("nationWoman")
                    ||null==params.get("folkMan")||null==params.get("folkWoman")||null==params.get("jobMan")||null==params.get("jobWoman")||null==params.get("degreeMan")||null==params.get("degreeWoman")
                    ||null==params.get("marryStatusMan")||null==params.get("marryStatusWomam")||null==params.get("registypeMan")||null==params.get("registypeWoman")||null==params.get("regSjMan")||null==params.get("regSjWoman")
                    ||null==params.get("regDsMan")||null==params.get("regDsWoman")||null==params.get("regQxMan")||null==params.get("regQxWoman")||null==params.get("regDetailMan")||null==params.get("regDetailWoman")){
                object.setCode(1);
                object.setDescription("必传参数为空");
                return object;

            }*/
            String jsonStr = JacksonUtils.toJson(params);
            jsonStr = jsonStr.replaceAll("\r|\n|\t|","");
            //jsonStr = URLEncoder.encode(jsonStr,"UTF-8");
            paramMap.put("jsonStr",jsonStr);
            String applyKey = getApplyKeyStr(paramMap);
            String urlStr = environment.getProperty("mz.apply.url");
            URL url = new URL(urlStr+"saveBookMarryInfo?wsdl");
            MaryyBookService ms= new MaryyBookService(url);
            MaryyBookServicePortType msp = ms.getMaryyBookServicePort();
            String saveBookMarryInfo = msp.saveBookMarryInfo(applyKey, jsonStr);
            System.out.println(saveBookMarryInfo);
            logger.info("登记信息录入返回数据：" + saveBookMarryInfo);
            if (StringUtils.isNotBlank(saveBookMarryInfo)) {
                JSONObject json = JSONObject.parseObject(saveBookMarryInfo);
                //如果回传数据成功取出data
                if ("0".equals(json.getString("returnCode"))) {
                    if(json.get("returnMessage") != null){
                        if(JSONUtils.isString(json.get("returnMessage"))){
                            object.setDescription(json.get("returnMessage").toString());
                        }else{
                            object.setPayload(json.get("returnMessage"));
                            object.setDescription("登记信息录入成功!");
                        }
                        object.setCode(0);
                    }else{
                        object.setCode(0);
                        object.setDescription("登记信息录入成功!");
                    }

                }else if ("1".equals(json.getString("returnCode"))) {
                    object.setCode(-1);
                    if(json.get("returnMessage") != null){
                        JSONObject jsonObject = JSONObject.parseObject(json.toJSONString(json.get("returnMessage")));
                        object.setDescription(jsonObject.get("idnoErr")==null?null:jsonObject.get("idnoErr").toString());
                    }else{
                        object.setDescription("参数输入错误!");
                    }
                }else{
                    if(JSONUtils.isString(json.get("returnMessage"))){
                        object.setDescription(json.get("returnMessage").toString());
                    }else{
                        object.setDescription("登记信息录入失败!");
                        object.setPayload(json.get("returnMessage"));
                    }
                    object.setCode(-1);
                }

            } else {
                logger.info("调用登记信息录入接口异常返回值为空,入参为：[{}]", paramMap);
                object.setCode(500);
                object.setDescription("内部服务错误");
            }
            object.setLastUpdateTime(System.currentTimeMillis());
        } catch (Exception e) {
            logger.info("调用登记信息录入接口异常返回值为空,入参为：[{}]", paramMap);
            object.setCode(500);
            object.setDescription("内部服务错误");
        }

        return object;
    }

    //获取公钥生成私钥方法
    private String getApplyKeyStr(Map paramMap) {
        String privateCode = "";

        if(paramMap != null && paramMap.containsKey("opType") ){
            String opType = paramMap.get("opType").toString();
            String key = environment.getProperty("mz.apply.key");
            privateCode = MD5Util.string2Md5(key+opType);
        }else if(paramMap != null && paramMap.containsKey("jsonStr")){
            String opType = paramMap.get("jsonStr").toString();
            String key = environment.getProperty("mz.apply.key");
            privateCode = MD5Util.string2Md5(key+opType);
        }

        return privateCode;
    }
}
