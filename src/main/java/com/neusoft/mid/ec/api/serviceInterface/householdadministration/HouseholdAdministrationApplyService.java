package com.neusoft.mid.ec.api.serviceInterface.householdadministration;

import javax.servlet.http.HttpServletRequest;

/**
 * 户政服务办理、申报
 */
public interface HouseholdAdministrationApplyService {
    String initToken(HttpServletRequest request);
}
