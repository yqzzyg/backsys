package com.neusoft.mid.ec.api.service.country.organization;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.neusoft.mid.ec.api.domain.country.CountryOrganization;
/**
 * @ClassName: CountryOrganizationRepository
 * @Description: 机构查询数据库接口
 * @author 蔡旭新
 * @date 2019年10月5日
 */
@Mapper
public interface CountryOrganizationRepository {
	long getJGCount(@Param(value = "record")CountryOrganization organization);
	
	List<CountryOrganization> getShflyInfoList(@Param(value = "record")CountryOrganization organization,@Param(value = "offset") int offset, @Param(value = "limit") int limit);
	
	List<CountryOrganization> getCsjgInfo(@Param(value = "record")CountryOrganization organization,@Param(value = "offset") int offset, @Param(value = "limit") int limit);
	
	List<CountryOrganization> getYsgInfo(@Param(value = "record")CountryOrganization organization,@Param(value = "offset") int offset, @Param(value = "limit") int limit);
	
	List<CountryOrganization> getTygInfo(@Param(value = "record")CountryOrganization organization,@Param(value = "offset") int offset, @Param(value = "limit") int limit);
	
	List<CountryOrganization> getDagInfo(@Param(value = "record")CountryOrganization organization,@Param(value = "offset") int offset, @Param(value = "limit") int limit);
	
	List<CountryOrganization> getBwgInfo(@Param(value = "record")CountryOrganization organization,@Param(value = "offset") int offset, @Param(value = "limit") int limit);
	
	List<CountryOrganization> getJngInfo(@Param(value = "record")CountryOrganization organization,@Param(value = "offset") int offset, @Param(value = "limit") int limit);
	
	List<CountryOrganization> getTjjgInfo(@Param(value = "record")CountryOrganization organization,@Param(value = "offset") int offset, @Param(value = "limit") int limit);
	
	List<CountryOrganization> getWwbhdwInfo(@Param(value = "record")CountryOrganization organization,@Param(value = "offset") int offset, @Param(value = "limit") int limit);
	
	List<CountryOrganization> getGzjgInfo(@Param(value = "record")CountryOrganization organization,@Param(value = "offset") int offset, @Param(value = "limit") int limit);
	
	List<CountryOrganization> getTsgInfo(@Param(value = "record")CountryOrganization organization,@Param(value = "offset") int offset, @Param(value = "limit") int limit);
	
	List<CountryOrganization> getYfjzfwjgInfo(@Param(value = "record")CountryOrganization organization,@Param(value = "offset") int offset, @Param(value = "limit") int limit);
	
	List<CountryOrganization> getYljgInfo(@Param(value = "record")CountryOrganization organization,@Param(value = "offset") int offset, @Param(value = "limit") int limit);
	
	List<CountryOrganization> getBygcxInfo(@Param(value = "record")CountryOrganization organization,@Param(value = "offset") int offset, @Param(value = "limit") int limit);
	
	List<CountryOrganization> getJyxgmcxInfo(@Param(value = "record")CountryOrganization organization,@Param(value = "offset") int offset, @Param(value = "limit") int limit);
	
	List<CountryOrganization> getBsfwdtcxInfo(@Param(value = "record")CountryOrganization organization,@Param(value = "offset") int offset, @Param(value = "limit") int limit);
	
	List<CountryOrganization> getGjkczwdInfo(@Param(value = "record")CountryOrganization organization,@Param(value = "offset") int offset, @Param(value = "limit") int limit);

	List<CountryOrganization> getHnsgyxxInfo(@Param(value = "record")CountryOrganization organization,@Param(value = "offset") int offset, @Param(value = "limit") int limit);

	List<CountryOrganization> getJyffjgInfo(@Param(value = "record")CountryOrganization organization,@Param(value = "offset") int offset, @Param(value = "limit") int limit);

	List<CountryOrganization> getShbxjjglzxffdtInfo(@Param(value = "record")CountryOrganization organization,@Param(value = "offset") int offset, @Param(value = "limit") int limit);

	List<CountryOrganization> getSzgjjywblwdInfo(@Param(value = "record")CountryOrganization organization,@Param(value = "offset") int offset, @Param(value = "limit") int limit);

	List<CountryOrganization> getZshjyzInfo(@Param(value = "record")CountryOrganization organization,@Param(value = "offset") int offset, @Param(value = "limit") int limit);

	List<CountryOrganization> getYbddydInfo(@Param(value = "record")CountryOrganization organization,@Param(value = "offset") int offset, @Param(value = "limit") int limit);

	List<CountryOrganization> getYbddyyInfo(@Param(value = "record")CountryOrganization organization,@Param(value = "offset") int offset, @Param(value = "limit") int limit);

	List<CountryOrganization> getXzwsyInfo(@Param(value = "record")CountryOrganization organization,@Param(value = "offset") int offset, @Param(value = "limit") int limit);

	List<CountryOrganization> getSqmzInfo(@Param(value = "record")CountryOrganization organization,@Param(value = "offset") int offset, @Param(value = "limit") int limit);

	List<CountryOrganization> getCwsyInfo(@Param(value = "record")CountryOrganization organization,@Param(value = "offset") int offset, @Param(value = "limit") int limit);

}
