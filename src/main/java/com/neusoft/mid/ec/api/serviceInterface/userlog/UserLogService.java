package com.neusoft.mid.ec.api.serviceInterface.userlog;

import com.neusoft.mid.ec.api.domain.DepartmentDict;
import com.neusoft.mid.ec.api.domain.InterfaceDict;
import com.neusoft.mid.ec.api.domain.UserLog;
import me.puras.common.util.ListBounds;
import me.puras.common.util.ListSlice;


public interface UserLogService {

    ListSlice<UserLog> selectUserLogList(UserLog userLog, ListBounds bounds);

    ListSlice<DepartmentDict> selectDepartmentDict(DepartmentDict departmentDict, ListBounds bounds);

    ListSlice<InterfaceDict> selectInterfaceDict(InterfaceDict interfaceDict, ListBounds bounds);

    void insertLog(UserLog userLog);

    void insertDeparment(DepartmentDict departmentDict);

    void updateDepartment(DepartmentDict departmentDict);

    void deleteDepartment(String ids);

    void insertInterface(InterfaceDict interfaceDict);

    void updateInterface(InterfaceDict interfaceDict);

    void deleteInterface(String ids);
}
