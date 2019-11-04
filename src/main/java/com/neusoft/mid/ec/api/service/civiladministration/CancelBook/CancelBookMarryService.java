
package com.neusoft.mid.ec.api.service.civiladministration.CancelBook;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "CancelBookMarryService", targetNamespace = "http://service.ws.gUnit_marry_mzb.topsuntech.com/", wsdlLocation = "http://59.207.24.170:8099/marry_book/ws/cancelBook?wsdl")
public class CancelBookMarryService
    extends Service
{

    private final static URL CANCELBOOKMARRYSERVICE_WSDL_LOCATION;
    private final static WebServiceException CANCELBOOKMARRYSERVICE_EXCEPTION;
    private final static QName CANCELBOOKMARRYSERVICE_QNAME = new QName("http://service.ws.gUnit_marry_mzb.topsuntech.com/", "CancelBookMarryService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://59.207.24.170:8099/marry_book/ws/cancelBook?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        CANCELBOOKMARRYSERVICE_WSDL_LOCATION = url;
        CANCELBOOKMARRYSERVICE_EXCEPTION = e;
    }

    public CancelBookMarryService() {
        super(__getWsdlLocation(), CANCELBOOKMARRYSERVICE_QNAME);
    }

    public CancelBookMarryService(WebServiceFeature... features) {
        super(__getWsdlLocation(), CANCELBOOKMARRYSERVICE_QNAME, features);
    }

    public CancelBookMarryService(URL wsdlLocation) {
        super(wsdlLocation, CANCELBOOKMARRYSERVICE_QNAME);
    }

    public CancelBookMarryService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, CANCELBOOKMARRYSERVICE_QNAME, features);
    }

    public CancelBookMarryService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public CancelBookMarryService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns CancelBookMarryServicePortType
     */
    @WebEndpoint(name = "CancelBookMarryServicePort")
    public CancelBookMarryServicePortType getCancelBookMarryServicePort() {
        return super.getPort(new QName("http://service.ws.gUnit_marry_mzb.topsuntech.com/", "CancelBookMarryServicePort"), CancelBookMarryServicePortType.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns CancelBookMarryServicePortType
     */
    @WebEndpoint(name = "CancelBookMarryServicePort")
    public CancelBookMarryServicePortType getCancelBookMarryServicePort(WebServiceFeature... features) {
        return super.getPort(new QName("http://service.ws.gUnit_marry_mzb.topsuntech.com/", "CancelBookMarryServicePort"), CancelBookMarryServicePortType.class, features);
    }

    private static URL __getWsdlLocation() {
        if (CANCELBOOKMARRYSERVICE_EXCEPTION!= null) {
            throw CANCELBOOKMARRYSERVICE_EXCEPTION;
        }
        return CANCELBOOKMARRYSERVICE_WSDL_LOCATION;
    }

}
