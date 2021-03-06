package com.dc.itcs.security.shiro;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

/**
 * 自定义角色过滤器
 * @ClassName: MyRolesAuthorizationFilter
 * @Description: TODO
 * @Create In 2014年12月22日 By lee
 */
public class MyRolesAuthorizationFilter extends AuthorizationFilter {
	
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {

        Subject subject = getSubject(request, response);
        String[] rolesArray = (String[]) mappedValue;

        if (rolesArray == null || rolesArray.length == 0) {
            //no roles specified, so nothing to check - allow access.
            return true;
        }

        Set<String> roles = CollectionUtils.asSet(rolesArray);
        for(String role : roles){
        	if(subject.hasRole(role)){
        		return true;
        	}
        }
        return false;
    }

}

