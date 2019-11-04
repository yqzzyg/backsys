package com.neusoft.mid.ec.api.serviceInterface.socialsecurity;

import com.neusoft.mid.ec.api.domain.socialsecurity.CardUserInfo;
import com.neusoft.mid.ec.api.domain.socialsecurity.ReportLossInfo;
import com.neusoft.mid.ec.api.exception.GeneralException;
import org.jdom.JDOMException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * 社保卡办理、申报业务
 */
public interface SocialSecurityCardApplyService {
    String changeUserInfo(CardUserInfo userInfo) throws IOException, JDOMException, GeneralException;

    String reportLossInfo(ReportLossInfo reportLossInfo) throws GeneralException, IOException, JDOMException;
}
