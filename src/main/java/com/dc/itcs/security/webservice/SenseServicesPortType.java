
package com.dc.itcs.security.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(name = "SenseServicesPortType", targetNamespace = "http://itss.sense.com")
@SOAPBinding(use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface SenseServicesPortType {
	
	@WebMethod(operationName = "userValidation", action = "")
	@WebResult(name = "out", targetNamespace = "http://itss.sense.com")
	public String userValidation(
			@WebParam(name = "in0", targetNamespace = "http://itss.sense.com")
			String in0,
			@WebParam(name = "in1", targetNamespace = "http://itss.sense.com")
			String in1);

}
