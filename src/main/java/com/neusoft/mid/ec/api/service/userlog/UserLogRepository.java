package com.neusoft.mid.ec.api.service.userlog;

import com.neusoft.mid.ec.api.domain.DepartmentDict;
import com.neusoft.mid.ec.api.domain.InterfaceDict;
import com.neusoft.mid.ec.api.domain.UserLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserLogRepository {

    List<UserLog> selectUserLogList(@Param(value = "record") UserLog userLog,@Param(value = "offset") int offset,@Param(value = "limit") int limit);

    List<UserLog> selectDepartmentDictList(@Param(value = "record") DepartmentDict departmentDict,@Param(value = "offset") int offset,@Param(value = "limit") int limit);

    List<UserLog> selectInterfaceDictList(@Param(value = "record") InterfaceDict InterfaceDict,@Param(value = "offset") int offset,@Param(value = "limit") int limit);

    void insertLog(@Param(value = "record") UserLog userLog);

    void insertDeparment(@Param(value = "record")DepartmentDict departmentDict);

    void updateDepartment(@Param(value = "record")DepartmentDict departmentDict);

    void deleteDepartment(@Param(value = "id")Long id);

    void insertInterface(@Param(value = "record")InterfaceDict interfaceDict);

    void updateInterface(@Param(value = "record")InterfaceDict interfaceDict);

    void deleteInterface(@Param(value = "id")Long id);

    long selectUserLogCount(@Param(value = "record")UserLog userLog);

    long selectDepartmentDictCount(@Param(value = "record")DepartmentDict departmentDict);

    long selectInterfaceDictCount(@Param(value = "record")InterfaceDict interfaceDict);
}
