package com.neusoft.mid.ec.api.service.reservedfunddict;

import com.neusoft.mid.ec.api.domain.ReservedFundDict;
import com.neusoft.mid.ec.api.serviceInterface.reservedfunddict.ReservedFundDictService;
import com.neusoft.mid.ec.api.util.JedisClusterUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservedFundDictServiceImpl implements ReservedFundDictService {
	
	private static Logger LOGGEER = Logger.getLogger(ReservedFundDictServiceImpl.class);
	
	@Autowired
	public JedisClusterUtil jedisClusterUtil;
	@Autowired
	private ReservedFundDictRepository reservedFundDictRepository;

	@Override
	public ReservedFundDict getDictDesc(String key, String dictCode) {
		return reservedFundDictRepository.getDictDesc(key,dictCode);
	}
}
