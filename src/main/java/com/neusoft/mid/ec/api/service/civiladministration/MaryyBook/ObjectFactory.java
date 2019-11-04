
package com.neusoft.mid.ec.api.service.civiladministration.MaryyBook;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.neusoft.mid.ec.api.service.civiladministration.MaryyBook package. 
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

    private final static QName _SaveBookMarryInfo_QNAME = new QName("http://service.ws.gUnit_marry_mzb.topsuntech.com/", "saveBookMarryInfo");
    private final static QName _SaveBookMarryInfoResponse_QNAME = new QName("http://service.ws.gUnit_marry_mzb.topsuntech.com/", "saveBookMarryInfoResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.neusoft.mid.ec.api.service.civiladministration.MaryyBook
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SaveBookMarryInfoResponse }
     * 
     */
    public SaveBookMarryInfoResponse createSaveBookMarryInfoResponse() {
        return new SaveBookMarryInfoResponse();
    }

    /**
     * Create an instance of {@link SaveBookMarryInfo }
     * 
     */
    public SaveBookMarryInfo createSaveBookMarryInfo() {
        return new SaveBookMarryInfo();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveBookMarryInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ws.gUnit_marry_mzb.topsuntech.com/", name = "saveBookMarryInfo")
    public JAXBElement<SaveBookMarryInfo> createSaveBookMarryInfo(SaveBookMarryInfo value) {
        return new JAXBElement<SaveBookMarryInfo>(_SaveBookMarryInfo_QNAME, SaveBookMarryInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveBookMarryInfoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ws.gUnit_marry_mzb.topsuntech.com/", name = "saveBookMarryInfoResponse")
    public JAXBElement<SaveBookMarryInfoResponse> createSaveBookMarryInfoResponse(SaveBookMarryInfoResponse value) {
        return new JAXBElement<SaveBookMarryInfoResponse>(_SaveBookMarryInfoResponse_QNAME, SaveBookMarryInfoResponse.class, null, value);
    }

}
