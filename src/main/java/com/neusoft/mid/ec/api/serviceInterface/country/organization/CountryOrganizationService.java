package com.neusoft.mid.ec.api.serviceInterface.country.organization;

import com.neusoft.mid.ec.api.domain.country.CountryOrganization;

import me.puras.common.util.ListBounds;
import me.puras.common.util.ListSlice;
/**
 * @ClassName: CountryOrganizationService
 * @Description: 机构查询业务处理接口
 * @author 蔡旭新
 * @date 2019年10月5日
 */
public interface CountryOrganizationService {

	ListSlice<CountryOrganization> getShflyInfoList(CountryOrganization organization, ListBounds bounds);
    
	ListSlice<CountryOrganization> getCsjgInfo(CountryOrganization organization, ListBounds bounds);
	
	ListSlice<CountryOrganization> getYsgInfo(CountryOrganization organization, ListBounds bounds);
	
	ListSlice<CountryOrganization> getTygInfo(CountryOrganization organization, ListBounds bounds);
	
	ListSlice<CountryOrganization> getDagInfo(CountryOrganization organization, ListBounds bounds);
	
	ListSlice<CountryOrganization> getBwgInfo(CountryOrganization organization, ListBounds bounds);
	
	ListSlice<CountryOrganization> getJngInfo(CountryOrganization organization, ListBounds bounds);
	
	ListSlice<CountryOrganization> getTjjgInfo(CountryOrganization organization, ListBounds bounds);
	
	ListSlice<CountryOrganization> getWwbhdwInfo(CountryOrganization organization, ListBounds bounds);
	
	ListSlice<CountryOrganization> getGzjgInfo(CountryOrganization organization, ListBounds bounds);
	
	ListSlice<CountryOrganization> getTsgInfo(CountryOrganization organization, ListBounds bounds);
	
	ListSlice<CountryOrganization> getYfjzfwjgInfo(CountryOrganization organization, ListBounds bounds);
	
	ListSlice<CountryOrganization> getYljgInfo(CountryOrganization organization, ListBounds bounds);
	
	ListSlice<CountryOrganization> getBygcxInfo(CountryOrganization organization, ListBounds bounds);
	
	ListSlice<CountryOrganization> getJyxgmcxInfo(CountryOrganization organization, ListBounds bounds);
	
	ListSlice<CountryOrganization> getBsfwdtcxInfo(CountryOrganization organization, ListBounds bounds);
	
	ListSlice<CountryOrganization> getGjkczwdInfo(CountryOrganization organization, ListBounds bounds);
	
	ListSlice<CountryOrganization> getHnsgyxxInfo(CountryOrganization organization, ListBounds bounds);
	
	ListSlice<CountryOrganization> getJyffjgInfo(CountryOrganization organization, ListBounds bounds);
	
	ListSlice<CountryOrganization> getShbxjjglzxffdtInfo(CountryOrganization organization, ListBounds bounds);
	
	ListSlice<CountryOrganization> getSzgjjywblwdInfo(CountryOrganization organization, ListBounds bounds);
	
	ListSlice<CountryOrganization> getZshjyzInfo(CountryOrganization organization, ListBounds bounds);
	
	ListSlice<CountryOrganization> getYbddydInfo(CountryOrganization organization, ListBounds bounds);
	
	ListSlice<CountryOrganization> getYbddyyInfo(CountryOrganization organization, ListBounds bounds);
	
	ListSlice<CountryOrganization> getXzwsyInfo(CountryOrganization organization, ListBounds bounds);
	
	ListSlice<CountryOrganization> getSqmzInfo(CountryOrganization organization, ListBounds bounds);
	
	ListSlice<CountryOrganization> getCwsyInfo(CountryOrganization organization, ListBounds bounds);
}
