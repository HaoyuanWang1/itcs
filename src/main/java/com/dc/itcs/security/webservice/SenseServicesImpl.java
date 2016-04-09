
package com.dc.itcs.security.webservice;

import javax.jws.WebService;

@WebService(serviceName = "SenseServices", targetNamespace = "http://itss.sense.com", endpointInterface = "com.sense.itss.SenseServicesPortType")
public class SenseServicesImpl implements SenseServicesPortType {

	public String userValidation(String in0, String in1) {
		throw new UnsupportedOperationException();
	}

}
