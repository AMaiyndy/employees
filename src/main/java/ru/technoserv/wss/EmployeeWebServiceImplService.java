
package ru.technoserv.wss;

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
@WebServiceClient(name = "EmployeeWebServiceImplService", targetNamespace = "http://ws.technoserv.ru/", wsdlLocation = "file:/D:/EmployeeWebServiceImplService.wsdl")
public class EmployeeWebServiceImplService
    extends Service
{

    private final static URL EMPLOYEEWEBSERVICEIMPLSERVICE_WSDL_LOCATION;
    private final static WebServiceException EMPLOYEEWEBSERVICEIMPLSERVICE_EXCEPTION;
    private final static QName EMPLOYEEWEBSERVICEIMPLSERVICE_QNAME = new QName("http://ws.technoserv.ru/", "EmployeeWebServiceImplService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("classpath://EmployeeWebServiceImplService.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        EMPLOYEEWEBSERVICEIMPLSERVICE_WSDL_LOCATION = url;
        EMPLOYEEWEBSERVICEIMPLSERVICE_EXCEPTION = e;
    }

    public EmployeeWebServiceImplService() {
        super(__getWsdlLocation(), EMPLOYEEWEBSERVICEIMPLSERVICE_QNAME);
    }

    public EmployeeWebServiceImplService(WebServiceFeature... features) {
        super(__getWsdlLocation(), EMPLOYEEWEBSERVICEIMPLSERVICE_QNAME, features);
    }

    public EmployeeWebServiceImplService(URL wsdlLocation) {
        super(wsdlLocation, EMPLOYEEWEBSERVICEIMPLSERVICE_QNAME);
    }

    public EmployeeWebServiceImplService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, EMPLOYEEWEBSERVICEIMPLSERVICE_QNAME, features);
    }

    public EmployeeWebServiceImplService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public EmployeeWebServiceImplService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns EmployeeWebService
     */
    @WebEndpoint(name = "EmployeeWebServiceImpl")
    public EmployeeWebService getEmployeeWebServiceImpl() {
        return super.getPort(new QName("http://ws.technoserv.ru/", "EmployeeWebServiceImpl"), EmployeeWebService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns EmployeeWebService
     */
    @WebEndpoint(name = "EmployeeWebServiceImpl")
    public EmployeeWebService getEmployeeWebServiceImpl(WebServiceFeature... features) {
        return super.getPort(new QName("http://ws.technoserv.ru/", "EmployeeWebServiceImpl"), EmployeeWebService.class, features);
    }

    private static URL __getWsdlLocation() {
        if (EMPLOYEEWEBSERVICEIMPLSERVICE_EXCEPTION!= null) {
            throw EMPLOYEEWEBSERVICEIMPLSERVICE_EXCEPTION;
        }
        return EMPLOYEEWEBSERVICEIMPLSERVICE_WSDL_LOCATION;
    }

}
