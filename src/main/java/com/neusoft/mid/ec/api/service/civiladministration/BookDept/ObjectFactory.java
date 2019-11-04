
package com.neusoft.mid.ec.api.service.civiladministration.BookDept;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.neusoft.mid.ec.api.service.civiladministration.BookDept package. 
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

    private final static QName _GetDeptInfo_QNAME = new QName("http://service.ws.gUnit_marry_mzb.topsuntech.com/", "getDeptInfo");
    private final static QName _GetDeptInfoResponse_QNAME = new QName("http://service.ws.gUnit_marry_mzb.topsuntech.com/", "getDeptInfoResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.neusoft.mid.ec.api.service.civiladministration.BookDept
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetDeptInfo }
     * 
     */
    public GetDeptInfo createGetDeptInfo() {
        return new GetDeptInfo();
    }

    /**
     * Create an instance of {@link GetDeptInfoResponse }
     * 
     */
    public GetDeptInfoResponse createGetDeptInfoResponse() {
        return new GetDeptInfoResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDeptInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ws.gUnit_marry_mzb.topsuntech.com/", name = "getDeptInfo")
    public JAXBElement<GetDeptInfo> createGetDeptInfo(GetDeptInfo value) {
        return new JAXBElement<GetDeptInfo>(_GetDeptInfo_QNAME, GetDeptInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDeptInfoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ws.gUnit_marry_mzb.topsuntech.com/", name = "getDeptInfoResponse")
    public JAXBElement<GetDeptInfoResponse> createGetDeptInfoResponse(GetDeptInfoResponse value) {
        return new JAXBElement<GetDeptInfoResponse>(_GetDeptInfoResponse_QNAME, GetDeptInfoResponse.class, null, value);
    }

}
