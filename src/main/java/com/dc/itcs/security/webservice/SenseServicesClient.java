
package com.dc.itcs.security.webservice;

import java.lang.reflect.Proxy;
import java.net.MalformedURLException;

import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.client.Client;
import org.codehaus.xfire.client.XFireProxy;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;

import com.dc.flamingo.core.utils.PropertiesUtils;

public class SenseServicesClient {
    private SenseServicesPortType service;

    public SenseServicesClient() {

    	Service serviceModel = new ObjectServiceFactory().create(SenseServicesPortType.class);
		try {
			service = (SenseServicesPortType) new XFireProxyFactory()
					.create(serviceModel,PropertiesUtils.getProperty("soap.tivoli.address"));
			XFireProxy proxy = (XFireProxy) Proxy.getInvocationHandler(service);
			Client client = proxy.getClient();
			SenseServicesClientAuthHandler handler = new SenseServicesClientAuthHandler();

			handler.setUsername(PropertiesUtils.getProperty("soap.tivoli.user"));
			handler.setPassword(PropertiesUtils.getProperty("soap.tivoli.pwd"));
			client.addOutHandler(handler);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }

	public SenseServicesPortType getService() {
		return service;
	}


	public void setService(SenseServicesPortType service) {
		this.service = service;
	}

    public static void main(String[] args) {	
        SenseServicesClient client = new SenseServicesClient();
        SenseServicesPortType service = client.getService();
        System.out.println(service.userValidation("limingn", "123"));
		System.out.println("test client completed");
        System.exit(0);
    }


}
