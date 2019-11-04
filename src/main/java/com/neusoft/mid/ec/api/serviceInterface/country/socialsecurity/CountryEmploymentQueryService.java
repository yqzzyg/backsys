package com.neusoft.mid.ec.api.serviceInterface.country.socialsecurity;

import java.util.Map;

import me.puras.common.json.Response;

/**
 * 就业查询业务
 */
public interface CountryEmploymentQueryService {

    /**
     *就业创业证查询
     * @return
     */
    Response callEmploymentService(String employmentServiceCode, String jsonStr);


    @SuppressWarnings({ "rawtypes", "unchecked" })
    Response queryInfo(String functionName, Map params, String serviceCode);
}
