package com.neusoft.mid.ec.api.service.reservedfound;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReservedFundRepository {
	
	int insertReservedFundContent(Map map);

}
