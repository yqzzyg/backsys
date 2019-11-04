package com.neusoft.mid.ec.api.serviceInterface.reservedfunddict;

import com.neusoft.mid.ec.api.domain.ReservedFundDict;

public interface ReservedFundDictService {
    ReservedFundDict getDictDesc(String key, String dictCode);
}
