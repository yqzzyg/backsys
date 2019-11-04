
package com.neusoft.mid.ec.api.service.civiladministration.CancelBook;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.neusoft.mid.ec.api.service.civiladministration.CancelBook package. 
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

    private final static QName _CancelBook_QNAME = new QName("http://service.ws.gUnit_marry_mzb.topsuntech.com/", "cancelBook");
    private final static QName _CancelBookResponse_QNAME = new QName("http://service.ws.gUnit_marry_mzb.topsuntech.com/", "cancelBookResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.neusoft.mid.ec.api.service.civiladministration.CancelBook
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CancelBookResponse }
     * 
     */
    public CancelBookResponse createCancelBookResponse() {
        return new CancelBookResponse();
    }

    /**
     * Create an instance of {@link CancelBook }
     * 
     */
    public CancelBook createCancelBook() {
        return new CancelBook();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelBook }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ws.gUnit_marry_mzb.topsuntech.com/", name = "cancelBook")
    public JAXBElement<CancelBook> createCancelBook(CancelBook value) {
        return new JAXBElement<CancelBook>(_CancelBook_QNAME, CancelBook.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelBookResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ws.gUnit_marry_mzb.topsuntech.com/", name = "cancelBookResponse")
    public JAXBElement<CancelBookResponse> createCancelBookResponse(CancelBookResponse value) {
        return new JAXBElement<CancelBookResponse>(_CancelBookResponse_QNAME, CancelBookResponse.class, null, value);
    }

}
