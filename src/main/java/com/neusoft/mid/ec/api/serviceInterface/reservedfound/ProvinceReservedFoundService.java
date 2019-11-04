package com.neusoft.mid.ec.api.serviceInterface.reservedfound;

/**
 * 
 * @author dev
 * 省直公积金查询
 *
 */
public interface ProvinceReservedFoundService {

	 /**
     * 省直公积金查询
     * @param IDNo
     * @param name
     * @return
     * @throws Exception
     */
    String getBasicInfo(String IDNo,String name,String biz_type) throws Exception;

}
