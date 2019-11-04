package com.neusoft.mid.ec.api.serviceInterface.socialsecurity;

import com.neusoft.mid.ec.api.controller.socialsecurity.EmploymentQueryController;
import com.neusoft.mid.ec.api.util.http.WebServiceClientUtil;
import me.puras.common.json.Response;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.regex.Matcher;

/**
 * 就业查询业务
 */
public interface EmploymentQueryService {

    /**
     *就业创业证查询
     * @return
     */
    Response callEmploymentService(String employmentServiceCode, String jsonStr);


    @SuppressWarnings({ "rawtypes", "unchecked" })
    Response queryInfo(String functionName, Map params, String serviceCode, EmploymentQueryController employmentQueryController);
}
