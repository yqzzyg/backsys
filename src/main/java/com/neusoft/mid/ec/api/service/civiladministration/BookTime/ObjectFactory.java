
package com.neusoft.mid.ec.api.service.civiladministration.BookTime;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.neusoft.mid.ec.api.service.civiladministration.BookTime package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetBookTime_QNAME = new QName("http://service.ws.gUnit_marry_mzb.topsuntech.com/", "getBookTime");
    private final static QName _GetBookTimeResponse_QNAME = new QName("http://service.ws.gUnit_marry_mzb.topsuntech.com/", "getBookTimeResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.neusoft.mid.ec.api.service.civiladministration.BookTime
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetBookTimeResponse }
     * 
     */
    public GetBookTimeResponse createGetBookTimeResponse() {
        return new GetBookTimeResponse();
    }

    /**
     * Create an instance of {@link GetBookTime }
     * 
     */
    public GetBookTime createGetBookTime() {
        return new GetBookTime();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBookTime }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ws.gUnit_marry_mzb.topsuntech.com/", name = "getBookTime")
    public JAXBElement<GetBookTime> createGetBookTime(GetBookTime value) {
        return new JAXBElement<GetBookTime>(_GetBookTime_QNAME, GetBookTime.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBookTimeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ws.gUnit_marry_mzb.topsuntech.com/", name = "getBookTimeResponse")
    public JAXBElement<GetBookTimeResponse> createGetBookTimeResponse(GetBookTimeResponse value) {
        return new JAXBElement<GetBookTimeResponse>(_GetBookTimeResponse_QNAME, GetBookTimeResponse.class, null, value);
    }

}
