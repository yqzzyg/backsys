
package com.neusoft.mid.ec.api.service.civiladministration.MaryyBook;

import javax.xml.namespace.QName;
import javax.xml.ws.*;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "MaryyBookService", targetNamespace = "http://service.ws.gUnit_marry_mzb.topsuntech.com/", wsdlLocation = "http://59.207.24.170:8099/marry_book/ws/saveBookMarryInfo?wsdl")
public class MaryyBookService
    extends Service
{

    private final static URL MARYYBOOKSERVICE_WSDL_LOCATION;
    private final static WebServiceException MARYYBOOKSERVICE_EXCEPTION;
    private final static QName MARYYBOOKSERVICE_QNAME = new QName("http://service.ws.gUnit_marry_mzb.topsuntech.com/", "MaryyBookService");

    static {
        URL url = null;
        WebServiceException e = null;

        try {
            url = new URL("http://59.207.24.170:8099/marry_book/ws/saveBookMarryInfo?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        MARYYBOOKSERVICE_WSDL_LOCATION = url;
        MARYYBOOKSERVICE_EXCEPTION = e;
    }

    public MaryyBookService() {
        super(__getWsdlLocation(), MARYYBOOKSERVICE_QNAME);
    }

    public MaryyBookService(WebServiceFeature... features) {
        super(__getWsdlLocation(), MARYYBOOKSERVICE_QNAME, features);
    }

    public MaryyBookService(URL wsdlLocation) {
        super(wsdlLocation, MARYYBOOKSERVICE_QNAME);
    }

    public MaryyBookService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, MARYYBOOKSERVICE_QNAME, features);
    }

    public MaryyBookService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public MaryyBookService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns MaryyBookServicePortType
     */
    @WebEndpoint(name = "MaryyBookServicePort")
    public MaryyBookServicePortType getMaryyBookServicePort() {
        return super.getPort(new QName("http://service.ws.gUnit_marry_mzb.topsuntech.com/", "MaryyBookServicePort"), MaryyBookServicePortType.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns MaryyBookServicePortType
     */
    @WebEndpoint(name = "MaryyBookServicePort")
    public MaryyBookServicePortType getMaryyBookServicePort(WebServiceFeature... features) {
        return super.getPort(new QName("http://service.ws.gUnit_marry_mzb.topsuntech.com/", "MaryyBookServicePort"), MaryyBookServicePortType.class, features);
    }

    private static URL __getWsdlLocation() {
        if (MARYYBOOKSERVICE_EXCEPTION!= null) {
            throw MARYYBOOKSERVICE_EXCEPTION;
        }
        return MARYYBOOKSERVICE_WSDL_LOCATION;
    }

}
