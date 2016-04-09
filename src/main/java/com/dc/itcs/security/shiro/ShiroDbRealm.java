package com.dc.itcs.security.shiro;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.dc.itcs.core.base.support.ItcsConstants;
import com.dc.itcs.security.entity.UserInfo;
import com.dc.itcs.security.service.UserInfoService;

public class ShiroDbRealm extends AuthorizingRealm{
	@Autowired
	private UserInfoService userService;
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		UserInfo shiroUser = (UserInfo) principals.getPrimaryPrincipal();
		List<String> userAuths = userService.findRidByUserId(shiroUser.getId());
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addRoles(userAuths);
		return info;
	}

	@SuppressWarnings("unused")
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		UserInfo user = userService.findByUidAndEnabled(token.getUsername());
		if(user.getTenant().getState()==ItcsConstants.STATE_OFF){
			return null;
		}
		if (user != null) {
			String authPassword = user.getPassword();
			return new SimpleAuthenticationInfo(user, token.getPassword(),getName());
		} else  {
			return null;
		}
	}

}