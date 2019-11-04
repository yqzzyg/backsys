
package com.neusoft.mid.ec.api.service.civiladministration.QueryBook;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.neusoft.mid.ec.api.service.civiladministration.QueryBook package. 
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

    private final static QName _QueryBookMarryInfo_QNAME = new QName("http://service.ws.gUnit_marry_mzb.topsuntech.com/", "queryBookMarryInfo");
    private final static QName _QueryBookMarryInfoResponse_QNAME = new QName("http://service.ws.gUnit_marry_mzb.topsuntech.com/", "queryBookMarryInfoResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.neusoft.mid.ec.api.service.civiladministration.QueryBook
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link QueryBookMarryInfoResponse }
     * 
     */
    public QueryBookMarryInfoResponse createQueryBookMarryInfoResponse() {
        return new QueryBookMarryInfoResponse();
    }

    /**
     * Create an instance of {@link QueryBookMarryInfo }
     * 
     */
    public QueryBookMarryInfo createQueryBookMarryInfo() {
        return new QueryBookMarryInfo();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryBookMarryInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ws.gUnit_marry_mzb.topsuntech.com/", name = "queryBookMarryInfo")
    public JAXBElement<QueryBookMarryInfo> createQueryBookMarryInfo(QueryBookMarryInfo value) {
        return new JAXBElement<QueryBookMarryInfo>(_QueryBookMarryInfo_QNAME, QueryBookMarryInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryBookMarryInfoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ws.gUnit_marry_mzb.topsuntech.com/", name = "queryBookMarryInfoResponse")
    public JAXBElement<QueryBookMarryInfoResponse> createQueryBookMarryInfoResponse(QueryBookMarryInfoResponse value) {
        return new JAXBElement<QueryBookMarryInfoResponse>(_QueryBookMarryInfoResponse_QNAME, QueryBookMarryInfoResponse.class, null, value);
    }

}
