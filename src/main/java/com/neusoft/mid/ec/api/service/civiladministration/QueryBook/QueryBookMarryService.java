
package com.neusoft.mid.ec.api.service.civiladministration.QueryBook;

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
@WebServiceClient(name = "QueryBookMarryService", targetNamespace = "http://service.ws.gUnit_marry_mzb.topsuntech.com/", wsdlLocation = "http://59.207.24.170:8099/marry_book/ws/queryBookMarryInfo?wsdl")
public class QueryBookMarryService
    extends Service
{

    private final static URL QUERYBOOKMARRYSERVICE_WSDL_LOCATION;
    private final static WebServiceException QUERYBOOKMARRYSERVICE_EXCEPTION;
    private final static QName QUERYBOOKMARRYSERVICE_QNAME = new QName("http://service.ws.gUnit_marry_mzb.topsuntech.com/", "QueryBookMarryService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://59.207.24.170:8099/marry_book/ws/queryBookMarryInfo?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        QUERYBOOKMARRYSERVICE_WSDL_LOCATION = url;
        QUERYBOOKMARRYSERVICE_EXCEPTION = e;
    }

    public QueryBookMarryService() {
        super(__getWsdlLocation(), QUERYBOOKMARRYSERVICE_QNAME);
    }

    public QueryBookMarryService(WebServiceFeature... features) {
        super(__getWsdlLocation(), QUERYBOOKMARRYSERVICE_QNAME, features);
    }

    public QueryBookMarryService(URL wsdlLocation) {
        super(wsdlLocation, QUERYBOOKMARRYSERVICE_QNAME);
    }

    public QueryBookMarryService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, QUERYBOOKMARRYSERVICE_QNAME, features);
    }

    public QueryBookMarryService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public QueryBookMarryService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns QueryBookMarryServicePortType
     */
    @WebEndpoint(name = "QueryBookMarryServicePort")
    public QueryBookMarryServicePortType getQueryBookMarryServicePort() {
        return super.getPort(new QName("http://service.ws.gUnit_marry_mzb.topsuntech.com/", "QueryBookMarryServicePort"), QueryBookMarryServicePortType.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns QueryBookMarryServicePortType
     */
    @WebEndpoint(name = "QueryBookMarryServicePort")
    public QueryBookMarryServicePortType getQueryBookMarryServicePort(WebServiceFeature... features) {
        return super.getPort(new QName("http://service.ws.gUnit_marry_mzb.topsuntech.com/", "QueryBookMarryServicePort"), QueryBookMarryServicePortType.class, features);
    }

    private static URL __getWsdlLocation() {
        if (QUERYBOOKMARRYSERVICE_EXCEPTION!= null) {
            throw QUERYBOOKMARRYSERVICE_EXCEPTION;
        }
        return QUERYBOOKMARRYSERVICE_WSDL_LOCATION;
    }

}
