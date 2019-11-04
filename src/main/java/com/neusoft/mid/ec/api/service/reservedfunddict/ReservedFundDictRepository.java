package com.neusoft.mid.ec.api.service.reservedfunddict;

import com.neusoft.mid.ec.api.domain.ReservedFundDict;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface ReservedFundDictRepository {

    ReservedFundDict getDictDesc(@Param(value = "dictcode") String key,@Param(value = "dictvalue") String dictCode);
}
