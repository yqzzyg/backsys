package com.neusoft.mid.ec.api.service.userlog;

import com.neusoft.mid.ec.api.domain.DepartmentDict;
import com.neusoft.mid.ec.api.domain.InterfaceDict;
import com.neusoft.mid.ec.api.domain.UserLog;
import com.neusoft.mid.ec.api.serviceInterface.userlog.UserLogService;
import com.neusoft.mid.ec.api.util.JedisClusterUtil;
import me.puras.common.util.ListBounds;
import me.puras.common.util.ListSlice;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserLogServiceImpl implements UserLogService {
	
	private static Logger LOGGEER = Logger.getLogger(UserLogServiceImpl.class);
	
	@Autowired
	public JedisClusterUtil jedisClusterUtil;
	@Autowired
	private UserLogRepository userLogRepository;

	@Override
	public ListSlice<UserLog> selectUserLogList(UserLog userLog, ListBounds bounds) {
		List<UserLog> list = new ArrayList<>();
		long total = 0;
		int offset = (bounds.getOffset()-1)*bounds.getLimit();
		try {
			total = userLogRepository.selectUserLogCount(userLog);
			list = userLogRepository.selectUserLogList(userLog, offset, bounds.getLimit());
		} catch (Exception e) {
			LOGGEER.error("[userList]用户查询出错：",e);
		}
		return new ListSlice(total,list);
	}

	@Override
	public ListSlice<DepartmentDict> selectDepartmentDict(DepartmentDict departmentDict, ListBounds bounds) {
		List<UserLog> list = new ArrayList<>();
		long total = 0;
		int offset = (bounds.getOffset()-1)*bounds.getLimit();
		try {
			total = userLogRepository.selectDepartmentDictCount(departmentDict);
			list = userLogRepository.selectDepartmentDictList(departmentDict, offset, bounds.getLimit());
		} catch (Exception e) {
			LOGGEER.error("[selectDepartmentDict]厅局查询出错：",e);
		}
		return new ListSlice(total,list);
	}


	@Override
	public ListSlice<InterfaceDict> selectInterfaceDict(InterfaceDict interfaceDict, ListBounds bounds) {
		List<UserLog> list = new ArrayList<>();
		long total = 0;
		int offset = (bounds.getOffset()-1)*bounds.getLimit();
		try {
			total = userLogRepository.selectInterfaceDictCount(interfaceDict);
			list = userLogRepository.selectInterfaceDictList(interfaceDict, offset, bounds.getLimit());
		} catch (Exception e) {
			LOGGEER.error("[selectInterfaceDict]接口查询出错：",e);
		}
		return new ListSlice(total,list);
	}

	@Override
	public void insertLog(UserLog userLog) {
		userLogRepository.insertLog(userLog);
	}

	@Override
	public void insertDeparment(DepartmentDict departmentDict) {
		userLogRepository.insertDeparment(departmentDict);
	}

	@Override
	public void updateDepartment(DepartmentDict departmentDict) {
		userLogRepository.updateDepartment(departmentDict);
	}

	@Override
	public void deleteDepartment(String ids) {
		if (StringUtils.isNotBlank(ids)) {
			String[] str = ids.split(",");
			for (String id:str) {
				userLogRepository.deleteDepartment(Long.valueOf(id));
			}
		}
	}

	@Override
	public void insertInterface(InterfaceDict interfaceDict) {
		userLogRepository.insertInterface(interfaceDict);
	}

	@Override
	public void updateInterface(InterfaceDict interfaceDict) {
		userLogRepository.updateInterface(interfaceDict);
	}

	@Override
	public void deleteInterface(String ids) {
		if (StringUtils.isNotBlank(ids)) {
			String[] str = ids.split(",");
			for (String id:str) {
				userLogRepository.deleteInterface(Long.valueOf(id));
			}
		}
	}
}
