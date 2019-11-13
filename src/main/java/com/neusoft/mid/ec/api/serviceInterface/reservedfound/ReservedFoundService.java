package com.neusoft.mid.ec.api.serviceInterface.reservedfound;

import java.util.Map;

/**
 *洛阳公积金
 */
public interface ReservedFoundService {

    public Map<String,String> getAuthInfo(String idno);

    public Map<String, String> getCommonInfo(Map<String, String> map);
    
    public void insertReservedFundContent(Map map);


}
