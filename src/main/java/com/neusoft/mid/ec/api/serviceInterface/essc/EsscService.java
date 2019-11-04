/**   
* @Title: EsscService.java
* @Package com.neusoft.mid.ec.api.serviceInterface.essc
* @Description: TODO
* @author zhaohk   
* @date 2019年10月16日 下午3:19:55
* @version V1.0   
*/
package com.neusoft.mid.ec.api.serviceInterface.essc;

import java.util.List;

import com.neusoft.mid.ec.api.domain.essc.HnesscSigninfo;

/**
* @ClassName: EsscService
* @Description: TODO(这里用一句话描述这个类的作用)
* @author zhaohk
* @date 2019年10月16日 下午3:19:55
* 
*/
public interface EsscService {
	void insert(HnesscSigninfo signinfo);
	void update(HnesscSigninfo signinfo);
	List<HnesscSigninfo> query(HnesscSigninfo signinfo);
	void save(HnesscSigninfo signinfo);
}
