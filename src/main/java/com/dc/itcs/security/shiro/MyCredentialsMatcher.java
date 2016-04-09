package com.dc.itcs.security.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;

import com.dc.itcs.core.base.utils.Encryption;
import com.dc.itcs.security.entity.UserInfo;
import com.dc.itcs.security.webservice.SenseServicesUitl;

/**
 * ldap认证
 * @ClassName: LdapCredentialsMatcher
 * @Description: TODO
 * @Create In 2014年12月15日 By lee
 */
public class MyCredentialsMatcher implements CredentialsMatcher{

	@Override
	public boolean doCredentialsMatch(AuthenticationToken token,AuthenticationInfo info) {
		boolean pass = false;
		UserInfo loginUser = (UserInfo) info.getPrincipals().getPrimaryPrincipal();
		char[] ps = (char[]) token.getCredentials();
		StringBuffer passwordBuffer = new StringBuffer();
		for(char p : ps){
			passwordBuffer.append(p);
		}
		String passwordBufferAfterEncryption = Encryption.encrypt(loginUser.getUid(), passwordBuffer.toString());
		if(loginUser.getIsAdUser()){	//如果是域控用户
			if("admin".equals(loginUser.getUid())){
				pass = passwordBufferAfterEncryption.equals(loginUser.getPassword());
			}else{
				try{
					pass = SenseServicesUitl.IsAuthenticatedByWebService(loginUser.getUid(), passwordBuffer.toString());
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		}else{
			pass = passwordBufferAfterEncryption.equals(loginUser.getPassword());
		}
		return pass;
		//return true;
	}
 
}
