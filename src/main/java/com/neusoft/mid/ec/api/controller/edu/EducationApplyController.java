package com.neusoft.mid.ec.api.controller.edu;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.neusoft.mid.ec.api.controller.BaseController;
import com.neusoft.mid.ec.api.domain.Education;
import com.neusoft.mid.ec.api.util.http.HttpRequestUtil;
import me.puras.common.json.Response;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 教育
 */
@RestController
@RequestMapping("/edu/apply")
public class EducationApplyController extends BaseController {
    @Autowired
    private Environment environment;

    /**
     * 自学考试补办毕业证明书申请
     * @return
     */
    @RequestMapping(value = "/zkbbbyzms/addInfo",method = RequestMethod.POST)
    public Response zkbbbyzmsAddInfo(Education params, MultipartFile originalFileName1,
            MultipartFile originalFileName2, MultipartFile originalFileName3, MultipartFile originalFileName4,
                                     HttpServletRequest request){
        Response<Object> object = new Response<>();
        try {
            logger.info("调用自学考试补办毕业证明书申请,[{}],入参：[{}]","/edu/apply/zkbbbyzms/addInfo",JSON.toJSONString(params));
            object.setLastUpdateTime(System.currentTimeMillis());
            if (null == params || null == originalFileName1
                    || null == originalFileName2 || null == originalFileName3 || null == originalFileName4) {
                object.setCode(-1);
                object.setDescription("必传参数不能为空");
                return object;
            }
            if (checkParamIsNull(params.getName()) || checkParamIsNull(params.getIdno()) || checkParamIsNull(params.getUserName())
                    || checkParamIsNull(params.getIdCode()) || checkParamIsNull(params.getMobile())
                    || ("1".equals(params.getIsPost()) && checkParamIsNull(params.getPostAddr()))
                    ) {
                object.setCode(-1);
                object.setDescription("必传参数不能为空");
                return object;
            }
            List<File> listFile = new ArrayList<>();
            File filePart1 = HttpRequestUtil.setFile(originalFileName1,"originalFileName1");
            File filePart2 = HttpRequestUtil.setFile(originalFileName2,"originalFileName2");
            File filePart3 = HttpRequestUtil.setFile(originalFileName3,"originalFileName3");
            File filePart4 = HttpRequestUtil.setFile(originalFileName4,"originalFileName4");
            listFile.add(filePart1);
            listFile.add(filePart2);
            listFile.add(filePart3);
            listFile.add(filePart4);
            Part[] part = {
                    new FilePart("originalFileName1", filePart1),
                    new FilePart("originalFileName2", filePart2),
                    new FilePart("originalFileName3", filePart3),
                    new FilePart("originalFileName4", filePart4),
                    new StringPart("name",params.getName(),"UTF-8"),
                    new StringPart("idno",params.getIdno(),"UTF-8"),
                    new StringPart("userName",params.getUserName(),"UTF-8"),
                    new StringPart("idCode",params.getIdCode(),"UTF-8"),
                    new StringPart("formName1","一张两寸照片","UTF-8"),
                    new StringPart("formName2","身份证照片(正面)","UTF-8"),
                    new StringPart("formName3","身份证照片(反面)","UTF-8"),
                    new StringPart("formName4","毕业生登记表(加盖档案部门印章)","UTF-8"),
                    new StringPart("mobile",params.getMobile(),"UTF-8"),
                    new StringPart("isPost",params.getIsPost(),"UTF-8"),
                    new StringPart("postAddr",params.getPostAddr(),"UTF-8")
            };
            Map<String, Object> mapHeaders = getMapHeaders(request);
            String urlPath = environment.getProperty("edu.apply.url.zkbbbyzms.addInfo");
            httpFile(object, part, mapHeaders, urlPath,listFile);
            logger.info("调用自学考试补办毕业证明书申请,[()],入参：[{}]","/edu/apply/zkbbbyzms/addInfo", JSON.toJSONString(object));
        } catch (Exception e) {
            logger.info("调用自学考试补办毕业证明书申请异常返回值为空,入参为：[{}]", params);
            object.setCode(500);
            object.setDescription("内部服务错误");
        }
        return object;
    }

    /**
     * 自学考试毕业生登记表证明申请
     * @return
     */
    @RequestMapping(value = "/bysdjbzm/addInfo",method = RequestMethod.POST)
    public Response bysdjbzmAddInfo(Education params, MultipartFile originalFileName1,
                                     MultipartFile originalFileName2, MultipartFile originalFileName3, MultipartFile originalFileName4,
                                     HttpServletRequest request){
        Response<Object> object = new Response<>();
        try {
            logger.info("调用自学考试毕业生登记表证明申请,[{}],入参：[{}]","/edu/apply/bysdjbzm/addInfo",JSON.toJSONString(params));
            object.setLastUpdateTime(System.currentTimeMillis());
            if (null == params || null == originalFileName1
                    || null == originalFileName2 || null == originalFileName3 || null == originalFileName4) {
                object.setCode(-1);
                object.setDescription("必传参数不能为空");
                return object;
            }
            if (checkParamIsNull(params.getName()) || checkParamIsNull(params.getIdno()) || checkParamIsNull(params.getUserName())
                    || checkParamIsNull(params.getIdCode()) || checkParamIsNull(params.getMobile()) || checkParamIsNull(params.getApplyReason())
                    || ("1".equals(params.getIsPost()) && checkParamIsNull(params.getPostAddr()))
                    ) {
                object.setCode(-1);
                object.setDescription("必传参数不能为空");
                return object;
            }
            List<File> listFile = new ArrayList<>();
            File filePart1 = HttpRequestUtil.setFile(originalFileName1,"originalFileName1");
            File filePart2 = HttpRequestUtil.setFile(originalFileName2,"originalFileName2");
            File filePart3 = HttpRequestUtil.setFile(originalFileName3,"originalFileName3");
            File filePart4 = HttpRequestUtil.setFile(originalFileName4,"originalFileName4");
            listFile.add(filePart1);
            listFile.add(filePart2);
            listFile.add(filePart3);
            listFile.add(filePart4);
            Part[] part = {
                    new FilePart("originalFileName1", filePart1),
                    new FilePart("originalFileName2", filePart2),
                    new FilePart("originalFileName3", filePart3),
                    new FilePart("originalFileName4", filePart4),
                    new StringPart("name",params.getName(),"UTF-8"),
                    new StringPart("idno",params.getIdno(),"UTF-8"),
                    new StringPart("userName",params.getUserName(),"UTF-8"),
                    new StringPart("idCode",params.getIdCode(),"UTF-8"),
                    new StringPart("applyReason",params.getApplyReason(),"UTF-8"),
                    new StringPart("formName1","身份证照片(正面)","UTF-8"),
                    new StringPart("formName2","身份证照片(反面)","UTF-8"),
                    new StringPart("formName3","毕业证","UTF-8"),
                    new StringPart("formName4","毕业生名册","UTF-8"),
                    new StringPart("mobile",params.getMobile(),"UTF-8"),
                    new StringPart("isPost",params.getIsPost(),"UTF-8"),
                    new StringPart("postAddr",params.getPostAddr(),"UTF-8")
            };
            Map<String, Object> mapHeaders = getMapHeaders(request);
            String urlPath = environment.getProperty("edu.apply.url.bysdjbzm.addInfo");
            httpFile(object, part, mapHeaders, urlPath,listFile);
            logger.info("调用自学考试毕业生登记表证明申请,[()],入参：[{}]","/edu/apply/bysdjbzm/addInfo", JSON.toJSONString(object));
        } catch (Exception e) {
            logger.info("调用自学考试毕业生登记表证明申请异常返回值为空,入参为：[{}]", params);
            object.setCode(500);
            object.setDescription("内部服务错误");
        }
        return object;
    }

    /**
     *  普通中专录取审批表申请
     * @return
     */
    @RequestMapping(value = "/ptzzlqspCheck/addInfo",method = RequestMethod.POST)
    public Response ptzzlqspCheckAddInfo(Education params, MultipartFile originalFileName1,
                                     MultipartFile originalFileName2, MultipartFile originalFileName3,
                                     HttpServletRequest request){
        Response<Object> object = new Response<>();
        try {
            logger.info("调用普通中专录取审批表申请,[{}],入参：[{}]","/edu/apply/ptzzlqspCheck/addInfo",JSON.toJSONString(params));
            object.setLastUpdateTime(System.currentTimeMillis());
            if (null == params || null == originalFileName1
                    || null == originalFileName2 || null == originalFileName3) {
                object.setCode(-1);
                object.setDescription("必传参数不能为空");
                return object;
            }
            if (checkParamIsNull(params.getName()) || checkParamIsNull(params.getIdno()) || checkParamIsNull(params.getaName())
                    || checkParamIsNull(params.getGender()) || checkParamIsNull(params.getIdCard())
                    || checkParamIsNull(params.getLinkerPhone()) || checkParamIsNull(params.getEducationType())
                    || checkParamIsNull(params.getEducationLevel()) || checkParamIsNull(params.getSubjectType())
                    || checkParamIsNull(params.getApplyReason())
                    || ("1".equals(params.getIsPost()) && checkParamIsNull(params.getPostAddr()))
                    ) {
                object.setCode(-1);
                object.setDescription("必传参数不能为空");
                return object;
            }
            List<File> listFile = new ArrayList<>();
            File filePart1 = HttpRequestUtil.setFile(originalFileName1,"originalFileName1");
            File filePart2 = HttpRequestUtil.setFile(originalFileName2,"originalFileName2");
            File filePart3 = HttpRequestUtil.setFile(originalFileName3,"originalFileName3");
            listFile.add(filePart1);
            listFile.add(filePart2);
            listFile.add(filePart3);
            Part[] part = {
                    new FilePart("originalFileName1", filePart1),
                    new FilePart("originalFileName2", filePart2),
                    new FilePart("originalFileName3", filePart3),
                    new StringPart("name",params.getName(),"UTF-8"),
                    new StringPart("idno",params.getIdno(),"UTF-8"),
                    new StringPart("aName",params.getaName(),"UTF-8"),
                    new StringPart("gender",params.getGender(),"UTF-8"),
                    new StringPart("idCard",params.getIdCard(),"UTF-8"),
                    new StringPart("linkerPhone",params.getLinkerPhone(),"UTF-8"),
                    new StringPart("educationType",params.getEducationType(),"UTF-8"),
                    new StringPart("educationLevel",params.getEducationLevel(),"UTF-8"),
                    new StringPart("subjectType",params.getSubjectType(),"UTF-8"),
                    new StringPart("applyReason",params.getApplyReason(),"UTF-8"),
                    new StringPart("formName1","身份证照片(正面)","UTF-8"),
                    new StringPart("formName2","身份证照片(反面)","UTF-8"),
                    new StringPart("formName3","毕业证","UTF-8"),
                    new StringPart("isPost",params.getIsPost(),"UTF-8"),
                    new StringPart("postAddr",params.getPostAddr(),"UTF-8")
            };
            Map<String, Object> mapHeaders = getMapHeaders(request);
            String urlPath = environment.getProperty("edu.apply.url.ptzzlqspCheck.addInfo");
            httpFile(object, part, mapHeaders, urlPath,listFile);
            logger.info("调用自学考试补办毕业证明书申请,[()],入参：[{}]","/edu/apply/ptzzlqspCheck/addInfo", JSON.toJSONString(object));
        } catch (Exception e) {
            logger.info("调用自学考试补办毕业证明书申请异常返回值为空,入参为：[{}]", params);
            object.setCode(500);
            object.setDescription("内部服务错误");
        }
        return object;
    }

    /**
     *  自学考试合格成绩证明申请
     * @return
     */
    @RequestMapping(value = "/zxkshgcjzmCheck/addInfo",method = RequestMethod.POST)
    public Response zxkshgcjzmCheckAddInfo(Education params, MultipartFile originalFileName1,
                                           MultipartFile originalFileName2, MultipartFile originalFileName3,
                                           HttpServletRequest request){
        Response<Object> object = new Response<>();
        try {
            logger.info("调用自学考试合格成绩证明申请,[{}],入参：[{}]","/edu/apply/zxkshgcjzmCheck/addInfo",JSON.toJSONString(params));
            object.setLastUpdateTime(System.currentTimeMillis());
            if (null == params || null == originalFileName1
                    || null == originalFileName2 || null == originalFileName3) {
                object.setCode(-1);
                object.setDescription("必传参数不能为空");
                return object;
            }
            if (checkParamIsNull(params.getName()) || checkParamIsNull(params.getIdno()) || checkParamIsNull(params.getUserName())
                    || checkParamIsNull(params.getMobile()) || checkParamIsNull(params.getIdCode())
                    || checkParamIsNull(params.getApplyReason())
                    || ("1".equals(params.getIsPost()) && checkParamIsNull(params.getPostAddr()))
                    ) {
                object.setCode(-1);
                object.setDescription("必传参数不能为空");
                return object;
            }
            List<File> listFile = new ArrayList<>();
            File filePart1 = HttpRequestUtil.setFile(originalFileName1,"originalFileName1");
            File filePart2 = HttpRequestUtil.setFile(originalFileName2,"originalFileName2");
            File filePart3 = HttpRequestUtil.setFile(originalFileName3,"originalFileName3");
            listFile.add(filePart1);
            listFile.add(filePart2);
            listFile.add(filePart3);
            Part[] part = {
                    new FilePart("originalFileName1", filePart1),
                    new FilePart("originalFileName2", filePart2),
                    new FilePart("originalFileName3", filePart3),
                    new StringPart("name",params.getName(),"UTF-8"),
                    new StringPart("idno",params.getIdno(),"UTF-8"),
                    new StringPart("userName",params.getUserName(),"UTF-8"),
                    new StringPart("idCode",params.getIdCode(),"UTF-8"),
                    new StringPart("mobile",params.getMobile(),"UTF-8"),
                    new StringPart("applyReason",params.getApplyReason(),"UTF-8"),
                    new StringPart("formName1","身份证照片(正面)","UTF-8"),
                    new StringPart("formName2","身份证照片(反面)","UTF-8"),
                    new StringPart("formName3","准考证","UTF-8"),
                    new StringPart("isPost",params.getIsPost(),"UTF-8"),
                    new StringPart("postAddr",params.getPostAddr(),"UTF-8")
            };
            Map<String, Object> mapHeaders = getMapHeaders(request);
            String urlPath = environment.getProperty("edu.apply.url.ptzzlqspCheck.addInfo");
            httpFile(object,part, mapHeaders, urlPath,listFile);
            logger.info("调用自学考试合格成绩证明申请,[()],入参：[{}]","/edu/apply/zxkshgcjzmCheck/addInfo", JSON.toJSONString(object));
        } catch (Exception e) {
            logger.info("调用自学考试合格成绩证明申请异常返回值为空,入参为：[{}]", params);
            object.setCode(500);
            object.setDescription("内部服务错误");
        }
        return object;
    }

    /**
     *  发送文件
     * @param object
     * @param part
     * @param mapHeaders
     * @param urlPath
     */
    private void httpFile(Response<Object> object, Part[] part, Map<String, Object> mapHeaders, String urlPath,List<File> listFile) {
        String result = HttpRequestUtil.URLPostFile(urlPath,mapHeaders, part,listFile);
        if (StringUtils.isNotBlank(result)) {
            JSONObject json = JSONObject.parseObject(result);
            //获取code值，如果msg为空，根据code值匹配对应的msg值
            object.setCode(json.getInteger("code"));
            object.setDescription(json.getString("msg"));
        }else{
            object.setCode(500);
            object.setDescription("内部服务器异常");
        }
        object.setLastUpdateTime(System.currentTimeMillis());
    }


    /**
     * 校验参数是否为空
     * @param param
     * @return
     */
    private Boolean checkParamIsNull(String param){
        return StringUtils.isBlank(param);
    }

}
