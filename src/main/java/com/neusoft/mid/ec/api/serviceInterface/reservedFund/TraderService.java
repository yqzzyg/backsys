package com.neusoft.mid.ec.api.serviceInterface.reservedFund;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by the JAX-WS RI. JAX-WS RI 2.1.3-hudson-390-
 * Generated source version: 2.0
 * 
 */
@WebService(name = "TraderService", targetNamespace = "http://service.core.trader.yinhai.com/")
public interface TraderService {

	/**
	 * 
	 * @param paramXml
	 * @return returns java.lang.String
	 */
	@WebMethod
	public String doTrader(
            @WebParam(name = "paramXml", targetNamespace = "") String paramXml);

}
