package com.dc.itcs.core.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.dc.flamingo.core.utils.JsonUtils;
import com.dc.itcs.core.context.UserContext;
import com.dc.itcs.security.entity.UserInfo;
import com.dc.itcs.system.entity.UserActionLog;
import com.dc.itcs.system.service.UserActionLogService;

public class SystemInterceptor extends HandlerInterceptorAdapter{
	private static final Logger log = LoggerFactory.getLogger(SystemInterceptor.class);
	
	@Autowired
	private UserActionLogService userActionLogService;
	@Override  
    public boolean preHandle(HttpServletRequest request,  
            HttpServletResponse response, Object handler) throws Exception {
		
		String url = request.getRequestURI();
		String lowerUrl = url.toLowerCase();
		if(lowerUrl.endsWith(".css")||lowerUrl.endsWith(".png")||lowerUrl.endsWith(".jpg")||lowerUrl.endsWith(".gif")||lowerUrl.endsWith(".js")){
			 return super.preHandle(request, response, handler);  
		}
		
	    	UserInfo userinfo = UserContext.getCurUser();
	    	request.getSession().setAttribute("isLogin", userinfo != null);
	    	request.getSession().setAttribute("onlineUser", userinfo);
	    	request.getSession().setAttribute("isUser", UserContext.isUser());
	    	
	    	String basePath = null;
	    	int serverPost = request.getServerPort();
	    	if(serverPost==80){
	    		basePath = request.getScheme()+"://"+request.getServerName()+request.getContextPath();
	    	}else{
	    		basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
	    	}
	    	request.getSession().setAttribute("basePath", basePath);
	    	
	    	//添加用户菜单
	    	if(userinfo!=null){
	    		if(url.indexOf(".")<0){
	    			log.info("---["+userinfo.getUid()
	    					+"/"+userinfo.getUserName()+"]---["+url+"]---["+JsonUtils.toJson(request.getParameterMap())+"]");
	    			userActionLogService.saveLog(new UserActionLog(userinfo,url));
	    			request.getSession().setAttribute("onlineEngineerMenu", UserContext.getEngineerMenu(userinfo));
	    		}
	    	}else{
	    		if(url.indexOf(".")<0){
	    			log.info("---["+request.getRemoteHost()+"]"
	    					+ "---["+url+"]---["+JsonUtils.toJson(request.getParameterMap())+"]");
	    			userActionLogService.saveLog(new UserActionLog(null,url));
	    		}
	    	}
        return super.preHandle(request, response, handler);  
	}
}
