package com.neusoft.mid.ec.api.service.country.organization;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neusoft.mid.ec.api.domain.country.CountryOrganization;
import com.neusoft.mid.ec.api.serviceInterface.country.organization.CountryOrganizationService;
import com.neusoft.mid.ec.api.util.JedisClusterUtil;

import me.puras.common.util.ListBounds;
import me.puras.common.util.ListSlice;

/**
 * @ClassName: CountryOrganizationServiceImpl
 * @Description: 机构查询业务处理实现类
 * @author 蔡旭新
 * @date 2019年10月5日
 */
@Service
public class CountryOrganizationServiceImpl implements CountryOrganizationService {
	@Autowired
	public JedisClusterUtil jedisClusterUtil;

	@Autowired
	private CountryOrganizationRepository countryOrganizationRepository;

	@Override
	public ListSlice<CountryOrganization> getShflyInfoList(CountryOrganization organization, ListBounds bounds) {
		List<CountryOrganization> list = new ArrayList<>();
		int offset = (bounds.getOffset() - 1) * bounds.getLimit();
		long total = countryOrganizationRepository.getJGCount(organization);
		list = countryOrganizationRepository.getShflyInfoList(organization, offset, bounds.getLimit());
		return new ListSlice(total, list);
	}
	
	@Override
	public ListSlice<CountryOrganization> getCsjgInfo(CountryOrganization organization, ListBounds bounds) {
		List<CountryOrganization> list = new ArrayList<>();
		int offset = (bounds.getOffset() - 1) * bounds.getLimit();
		long total = countryOrganizationRepository.getJGCount(organization);
		list = countryOrganizationRepository.getCsjgInfo(organization, offset, bounds.getLimit());
		return new ListSlice(total, list);
	}
	
	@Override
	public ListSlice<CountryOrganization> getYsgInfo(CountryOrganization organization, ListBounds bounds) {
		List<CountryOrganization> list = new ArrayList<>();
		int offset = (bounds.getOffset() - 1) * bounds.getLimit();
		long total = countryOrganizationRepository.getJGCount(organization);
		list = countryOrganizationRepository.getYsgInfo(organization, offset, bounds.getLimit());
		return new ListSlice(total, list);
	}
	
	@Override
	public ListSlice<CountryOrganization> getTygInfo(CountryOrganization organization, ListBounds bounds) {
		List<CountryOrganization> list = new ArrayList<>();
		int offset = (bounds.getOffset() - 1) * bounds.getLimit();
		long total = countryOrganizationRepository.getJGCount(organization);
		list = countryOrganizationRepository.getTygInfo(organization, offset, bounds.getLimit());
		return new ListSlice(total, list);
	}
	
	@Override
	public ListSlice<CountryOrganization> getDagInfo(CountryOrganization organization, ListBounds bounds) {
		List<CountryOrganization> list = new ArrayList<>();
		int offset = (bounds.getOffset() - 1) * bounds.getLimit();
		long total = countryOrganizationRepository.getJGCount(organization);
		list = countryOrganizationRepository.getDagInfo(organization, offset, bounds.getLimit());
		return new ListSlice(total, list);
	}
	
	@Override
	public ListSlice<CountryOrganization> getBwgInfo(CountryOrganization organization, ListBounds bounds) {
		List<CountryOrganization> list = new ArrayList<>();
		int offset = (bounds.getOffset() - 1) * bounds.getLimit();
		long total = countryOrganizationRepository.getJGCount(organization);
		list = countryOrganizationRepository.getBwgInfo(organization, offset, bounds.getLimit());
		return new ListSlice(total, list);
	}
	
	@Override
	public ListSlice<CountryOrganization> getJngInfo(CountryOrganization organization, ListBounds bounds) {
		List<CountryOrganization> list = new ArrayList<>();
		int offset = (bounds.getOffset() - 1) * bounds.getLimit();
		long total = countryOrganizationRepository.getJGCount(organization);
		list = countryOrganizationRepository.getJngInfo(organization, offset, bounds.getLimit());
		return new ListSlice(total, list);
	}
	
	@Override
	public ListSlice<CountryOrganization> getTjjgInfo(CountryOrganization organization, ListBounds bounds) {
		List<CountryOrganization> list = new ArrayList<>();
		int offset = (bounds.getOffset() - 1) * bounds.getLimit();
		long total = countryOrganizationRepository.getJGCount(organization);
		list = countryOrganizationRepository.getTjjgInfo(organization, offset, bounds.getLimit());
		return new ListSlice(total, list);
	}
	
	@Override
	public ListSlice<CountryOrganization> getWwbhdwInfo(CountryOrganization organization, ListBounds bounds) {
		List<CountryOrganization> list = new ArrayList<>();
		int offset = (bounds.getOffset() - 1) * bounds.getLimit();
		long total = countryOrganizationRepository.getJGCount(organization);
		list = countryOrganizationRepository.getWwbhdwInfo(organization, offset, bounds.getLimit());
		return new ListSlice(total, list);
	}
	
	@Override
	public ListSlice<CountryOrganization> getGzjgInfo(CountryOrganization organization, ListBounds bounds) {
		List<CountryOrganization> list = new ArrayList<>();
		int offset = (bounds.getOffset() - 1) * bounds.getLimit();
		long total = countryOrganizationRepository.getJGCount(organization);
		list = countryOrganizationRepository.getGzjgInfo(organization, offset, bounds.getLimit());
		return new ListSlice(total, list);
	}
	
	@Override
	public ListSlice<CountryOrganization> getTsgInfo(CountryOrganization organization, ListBounds bounds) {
		List<CountryOrganization> list = new ArrayList<>();
		int offset = (bounds.getOffset() - 1) * bounds.getLimit();
		long total = countryOrganizationRepository.getJGCount(organization);
		list = countryOrganizationRepository.getTsgInfo(organization, offset, bounds.getLimit());
		return new ListSlice(total, list);
	}
	
	@Override
	public ListSlice<CountryOrganization> getYfjzfwjgInfo(CountryOrganization organization, ListBounds bounds) {
		List<CountryOrganization> list = new ArrayList<>();
		int offset = (bounds.getOffset() - 1) * bounds.getLimit();
		long total = countryOrganizationRepository.getJGCount(organization);
		list = countryOrganizationRepository.getYfjzfwjgInfo(organization, offset, bounds.getLimit());
		return new ListSlice(total, list);
	}
	
	@Override
	public ListSlice<CountryOrganization> getYljgInfo(CountryOrganization organization, ListBounds bounds) {
		List<CountryOrganization> list = new ArrayList<>();
		int offset = (bounds.getOffset() - 1) * bounds.getLimit();
		long total = countryOrganizationRepository.getJGCount(organization);
		list = countryOrganizationRepository.getYljgInfo(organization, offset, bounds.getLimit());
		return new ListSlice(total, list);
	}
	
	@Override
	public ListSlice<CountryOrganization> getBygcxInfo(CountryOrganization organization, ListBounds bounds) {
		List<CountryOrganization> list = new ArrayList<>();
		int offset = (bounds.getOffset() - 1) * bounds.getLimit();
		long total = countryOrganizationRepository.getJGCount(organization);
		list = countryOrganizationRepository.getBygcxInfo(organization, offset, bounds.getLimit());
		return new ListSlice(total, list);
	}
	
	@Override
	public ListSlice<CountryOrganization> getJyxgmcxInfo(CountryOrganization organization, ListBounds bounds) {
		List<CountryOrganization> list = new ArrayList<>();
		int offset = (bounds.getOffset() - 1) * bounds.getLimit();
		long total = countryOrganizationRepository.getJGCount(organization);
		list = countryOrganizationRepository.getJyxgmcxInfo(organization, offset, bounds.getLimit());
		return new ListSlice(total, list);
	}
	
	@Override
	public ListSlice<CountryOrganization> getBsfwdtcxInfo(CountryOrganization organization, ListBounds bounds) {
		List<CountryOrganization> list = new ArrayList<>();
		int offset = (bounds.getOffset() - 1) * bounds.getLimit();
		long total = countryOrganizationRepository.getJGCount(organization);
		list = countryOrganizationRepository.getBsfwdtcxInfo(organization, offset, bounds.getLimit());
		return new ListSlice(total, list);
	}

	@Override
	public ListSlice<CountryOrganization> getGjkczwdInfo(CountryOrganization organization, ListBounds bounds) {
		List<CountryOrganization> list = new ArrayList<>();
		int offset = (bounds.getOffset() - 1) * bounds.getLimit();
		long total = countryOrganizationRepository.getJGCount(organization);
		list = countryOrganizationRepository.getGjkczwdInfo(organization, offset, bounds.getLimit());
		return new ListSlice(total, list);
	}

	@Override
	public ListSlice<CountryOrganization> getHnsgyxxInfo(CountryOrganization organization, ListBounds bounds) {
		List<CountryOrganization> list = new ArrayList<>();
		int offset = (bounds.getOffset() - 1) * bounds.getLimit();
		long total = countryOrganizationRepository.getJGCount(organization);
		list = countryOrganizationRepository.getHnsgyxxInfo(organization, offset, bounds.getLimit());
		return new ListSlice(total, list);
	}

	@Override
	public ListSlice<CountryOrganization> getJyffjgInfo(CountryOrganization organization, ListBounds bounds) {
		List<CountryOrganization> list = new ArrayList<>();
		int offset = (bounds.getOffset() - 1) * bounds.getLimit();
		long total = countryOrganizationRepository.getJGCount(organization);
		list = countryOrganizationRepository.getJyffjgInfo(organization, offset, bounds.getLimit());
		return new ListSlice(total, list);
	}

	@Override
	public ListSlice<CountryOrganization> getShbxjjglzxffdtInfo(CountryOrganization organization, ListBounds bounds) {
		List<CountryOrganization> list = new ArrayList<>();
		int offset = (bounds.getOffset() - 1) * bounds.getLimit();
		long total = countryOrganizationRepository.getJGCount(organization);
		list = countryOrganizationRepository.getShbxjjglzxffdtInfo(organization, offset, bounds.getLimit());
		return new ListSlice(total, list);
	}

	@Override
	public ListSlice<CountryOrganization> getSzgjjywblwdInfo(CountryOrganization organization, ListBounds bounds) {
		List<CountryOrganization> list = new ArrayList<>();
		int offset = (bounds.getOffset() - 1) * bounds.getLimit();
		long total = countryOrganizationRepository.getJGCount(organization);
		list = countryOrganizationRepository.getSzgjjywblwdInfo(organization, offset, bounds.getLimit());
		return new ListSlice(total, list);
	}

	@Override
	public ListSlice<CountryOrganization> getZshjyzInfo(CountryOrganization organization, ListBounds bounds) {
		List<CountryOrganization> list = new ArrayList<>();
		int offset = (bounds.getOffset() - 1) * bounds.getLimit();
		long total = countryOrganizationRepository.getJGCount(organization);
		list = countryOrganizationRepository.getZshjyzInfo(organization, offset, bounds.getLimit());
		return new ListSlice(total, list);
	}

	@Override
	public ListSlice<CountryOrganization> getYbddydInfo(CountryOrganization organization, ListBounds bounds) {
		List<CountryOrganization> list = new ArrayList<>();
		int offset = (bounds.getOffset() - 1) * bounds.getLimit();
		long total = countryOrganizationRepository.getJGCount(organization);
		list = countryOrganizationRepository.getYbddydInfo(organization, offset, bounds.getLimit());
		return new ListSlice(total, list);
	}

	@Override
	public ListSlice<CountryOrganization> getYbddyyInfo(CountryOrganization organization, ListBounds bounds) {
		List<CountryOrganization> list = new ArrayList<>();
		int offset = (bounds.getOffset() - 1) * bounds.getLimit();
		long total = countryOrganizationRepository.getJGCount(organization);
		list = countryOrganizationRepository.getYbddyyInfo(organization, offset, bounds.getLimit());
		return new ListSlice(total, list);
	}

	@Override
	public ListSlice<CountryOrganization> getXzwsyInfo(CountryOrganization organization, ListBounds bounds) {
		List<CountryOrganization> list = new ArrayList<>();
		int offset = (bounds.getOffset() - 1) * bounds.getLimit();
		long total = countryOrganizationRepository.getJGCount(organization);
		list = countryOrganizationRepository.getXzwsyInfo(organization, offset, bounds.getLimit());
		return new ListSlice(total, list);
	}

	@Override
	public ListSlice<CountryOrganization> getSqmzInfo(CountryOrganization organization, ListBounds bounds) {
		List<CountryOrganization> list = new ArrayList<>();
		int offset = (bounds.getOffset() - 1) * bounds.getLimit();
		long total = countryOrganizationRepository.getJGCount(organization);
		list = countryOrganizationRepository.getSqmzInfo(organization, offset, bounds.getLimit());
		return new ListSlice(total, list);
	}

	@Override
	public ListSlice<CountryOrganization> getCwsyInfo(CountryOrganization organization, ListBounds bounds) {
		List<CountryOrganization> list = new ArrayList<>();
		int offset = (bounds.getOffset() - 1) * bounds.getLimit();
		long total = countryOrganizationRepository.getJGCount(organization);
		list = countryOrganizationRepository.getCwsyInfo(organization, offset, bounds.getLimit());
		return new ListSlice(total, list);
	}
}
