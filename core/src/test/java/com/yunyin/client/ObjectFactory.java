
package com.yunyin.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.yunyin.client package. 
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

    private final static QName _WsCargoResponse_QNAME = new QName("http://www.ectongs.com", "wsCargoResponse");
    private final static QName _WsCargo_QNAME = new QName("http://www.ectongs.com", "wsCargo");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.yunyin.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link WsCargo }
     * 
     */
    public WsCargo createWsCargo() {
        return new WsCargo();
    }

    /**
     * Create an instance of {@link WsCargoResponse }
     * 
     */
    public WsCargoResponse createWsCargoResponse() {
        return new WsCargoResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WsCargoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ectongs.com", name = "wsCargoResponse")
    public JAXBElement<WsCargoResponse> createWsCargoResponse(WsCargoResponse value) {
        return new JAXBElement<WsCargoResponse>(_WsCargoResponse_QNAME, WsCargoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WsCargo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ectongs.com", name = "wsCargo")
    public JAXBElement<WsCargo> createWsCargo(WsCargo value) {
        return new JAXBElement<WsCargo>(_WsCargo_QNAME, WsCargo.class, null, value);
    }

}
