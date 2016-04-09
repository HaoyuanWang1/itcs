package com.dc.itcs.security.webservice;

/**
 * tivoli与ldap交互工具
 * @author lee
 *
 */
public class SenseServicesUitl {
	public static boolean IsAuthenticatedByWebService(String UserName, String Pwd) {
		try{
			SenseServicesClient client = new SenseServicesClient();
	        SenseServicesPortType service = client.getService();
	        String isAuth = service.userValidation(UserName, Pwd);
	        boolean b_auth = false;
	        if(null != isAuth){
	        	b_auth = Boolean.valueOf(isAuth);
	        }
	        return b_auth; 
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
}
