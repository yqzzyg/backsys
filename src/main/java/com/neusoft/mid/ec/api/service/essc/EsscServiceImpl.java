/**   
* @Title: EsscServiceImpl.java
* @Package com.neusoft.mid.ec.api.service.essc
* @Description: TODO
* @author zhaohk   
* @date 2019年10月16日 下午3:23:50
* @version V1.0   
*/
package com.neusoft.mid.ec.api.service.essc;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neusoft.mid.ec.api.domain.essc.HnesscSigninfo;
import com.neusoft.mid.ec.api.serviceInterface.essc.EsscService;

/**
* @ClassName: EsscServiceImpl
* @Description: TODO(这里用一句话描述这个类的作用)
* @author zhaohk
* @date 2019年10月16日 下午3:23:50
* 
*/
@Service
public class EsscServiceImpl implements EsscService {

	@Autowired
	private EsscServiceRepository repository;
	/**
	* <p>Title: </p>
	* <p>Description: </p>
	*/
	public EsscServiceImpl() {
	}

	/* (非 Javadoc)
	* <p>Title: save</p>
	* <p>Description: </p>
	* @param signinfo
	* @return
	* @see com.neusoft.mid.ec.api.serviceInterface.essc.EsscService#save(com.neusoft.mid.ec.api.domain.essc.HnesscSigninfo)
	*/
	@Override
	public void insert(HnesscSigninfo signinfo) {
		if (signinfo.getId()==null) {
			signinfo.setId(UUID.randomUUID().toString().replace("-",""));
		}
		if (signinfo.getCreateTime()==null) {
			signinfo.setCreateTime(new Date());
		}
		if (signinfo.getModifyTime()==null) {
			signinfo.setModifyTime(signinfo.getCreateTime());
		}
		repository.insert(signinfo);
	}
	
	@Override
	public void update(HnesscSigninfo signinfo) {
		signinfo.setModifyTime(new Date());
		repository.update(signinfo);
	}
	@Override
	public List<HnesscSigninfo> query(HnesscSigninfo signinfo){
		return repository.query(signinfo);
	}
	
	@Override
	public void save(HnesscSigninfo signinfo) {
		if (signinfo.getId()!=null&&!signinfo.getId().equals("")) {
			update(signinfo);
		}else {
			HnesscSigninfo info = new HnesscSigninfo();
			if (signinfo.getBizid()!=null&&signinfo.getUserid()!=null&&signinfo.getAreaCode()!=null
					&&signinfo.getUserCardNo()!=null) {
				info.setBizid(signinfo.getBizid());
				info.setUserid(signinfo.getUserid());
				info.setAreaCode(signinfo.getAreaCode());
				info.setUserCardNo(signinfo.getUserCardNo());
				List<HnesscSigninfo> list = query(info);
				if (list!=null&&list.size()>0) {
					signinfo.setId(list.get(0).getId());
					update(signinfo);
				}else{
					insert(signinfo);
				}
			}else {
				insert(signinfo);
			}
			
			
		}
	}

}
