package qtgl.api;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;
import java.util.Vector;

import javax.wsdl.Port;
import javax.wsdl.extensions.soap.SOAPAddress;
import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.transport.http.HTTPConstants;
import org.apache.axis.wsdl.gen.Parser;
import org.apache.axis.wsdl.symbolTable.ServiceEntry;
import org.apache.axis.wsdl.symbolTable.SymTabEntry;
import org.apache.commons.io.Charsets;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DynamicWebServiceImpl implements Serializable {
	
	private static Log log = LogFactory.getLog(DynamicWebServiceImpl.class);

    private Parser wsdlParser;
	private String wsdlLocation;
	private String portName;
	private String serviceName;
	private String serviceNS;
	
	private Service service;
	private Port port;

    /**Hell
     * For Http Digest Authentication
     */
    private String digestHA1;

	public void setWsdlLocation(String wsdlLocation) {
		this.wsdlLocation = wsdlLocation;
	}
	
	public void setPortName(String portName) {
		this.portName = portName;
	}
	
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	public void setServiceNS(String serviceNS) {
		this.serviceNS = serviceNS;
	}

    public void setDigestHA1(String ha1) {
        this.digestHA1 = ha1;
    }

	@SuppressWarnings("unchecked")
	private synchronized void initialize() throws Exception {
		if (StringUtils.isEmpty(wsdlLocation)) {
			throw new RuntimeException("wsdlLocation not set");
		}
        wsdlParser = new Parser();
        if (log.isDebugEnabled()) {
        	log.debug("Reading WSDL document from '" + wsdlLocation + "'");
        }
        wsdlParser.run(wsdlLocation);
        javax.wsdl.Service _service = this.selectService(serviceNS, serviceName);
        service = new Service(wsdlParser, _service.getQName());
        port = selectPort(_service.getPorts(), portName);
        if (port != null && StringUtils.isEmpty(portName)) {
        	portName = port.getName();
        }
	}
	
	private javax.wsdl.Service selectService(String serviceNS, String serviceName) throws Exception {
        QName serviceQName = (
        		((serviceNS != null) && (serviceName != null))
                ? new QName(serviceNS, serviceName)
                : null);
        ServiceEntry serviceEntry;
        if (serviceQName == null) {
        	serviceEntry = (ServiceEntry) getSymTabEntry(ServiceEntry.class);
        } else {
        	serviceEntry =  wsdlParser.getSymbolTable().getServiceEntry(serviceQName);
        } 
        return serviceEntry.getService();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	private SymTabEntry getSymTabEntry(Class cls) {
        HashMap<QName, Vector<SymTabEntry>> map = wsdlParser.getSymbolTable().getHashMap();

        for (Vector<SymTabEntry> v : map.values()) {
            for (SymTabEntry symTabEntry : v) {
                if (cls.isInstance(symTabEntry)) {
                    return symTabEntry;
                }
            }
        }
        return null;
    }

    private Port selectPort(Map<String, Port> ports, String portName) throws Exception {
    	if (!StringUtils.isEmpty(portName)) {
    		return ports.get(portName);
    	}
		for (Port port : ports.values()) {
			for (Object obj : port.getExtensibilityElements()) {
                if (obj instanceof SOAPAddress) {
                    return port;
                }
			}
		}
        return null;
    }

	protected synchronized Call getCall(String operationName) throws Exception {
		if (null == this.service || StringUtils.isBlank(portName)) {
			this.initialize();
		}
		/*
		SymbolTable table = wsdlParser.getSymbolTable();
		BindingEntry bEntry = table.getBindingEntry(port.getBinding().getQName());
		Parameters parameters = null;
		Map<Operation, Parameters> map = bEntry.getParameters();
		for (Entry<Operation, Parameters> entry : map.entrySet()) {
			Operation op = entry.getKey();
			if (op.getName().equals(operationName)) {
				parameters = entry.getValue();
				break;
			}
		}
		if (parameters == null) {
			throw new NoSuchMethodException(operationName);
		}
		*/
		Call call = (Call) service.createCall(QName.valueOf(portName), QName.valueOf(operationName));
		//call.setTimeout(new Integer(15*1000));
		//call.setProperty(ElementDeserializer.DESERIALIZE_CURRENT_ELEMENT, Boolean.TRUE);
		return call;
	}

    public Object invoke(String operationName, Object[] params) throws Exception {
        try {
            Call call = this.getCall(operationName);
           
           // log.debug("WebService invoke:" + this.wsdlLocation + ", operation:" + operationName + ", params:" + JsonConvert.toJson(params));
            return call.invoke(params);
        } catch (Exception e) {
			//log.warn("WebService invoke failed:" + this.wsdlLocation + ", operation:" + operationName + ", params:" + JsonConvert.toJson(params), e);
			throw e;
		}
    }

}
