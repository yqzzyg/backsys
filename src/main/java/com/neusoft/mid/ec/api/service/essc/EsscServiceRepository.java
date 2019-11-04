/**   
* @Title: EsscServiceRepository.java
* @Package com.neusoft.mid.ec.api.service.essc
* @Description: TODO
* @author zhaohk   
* @date 2019年10月16日 下午3:28:37
* @version V1.0   
*/
package com.neusoft.mid.ec.api.service.essc;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.neusoft.mid.ec.api.domain.essc.HnesscSigninfo;

/**
* @ClassName: EsscServiceRepository
* @Description: TODO(这里用一句话描述这个类的作用)
* @author zhaohk
* @date 2019年10月16日 下午3:28:37
* 
*/
@Mapper
public interface EsscServiceRepository {
	void insert(@Param(value = "record")HnesscSigninfo signinfo);
	void update(@Param(value = "record")HnesscSigninfo signinfo);
	List<HnesscSigninfo> query(@Param(value = "record")HnesscSigninfo signinfo);
}
