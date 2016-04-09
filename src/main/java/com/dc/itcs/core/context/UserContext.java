package com.dc.itcs.core.context;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;

import com.dc.flamingo.core.context.ContextHolder;
import com.dc.itcs.core.base.support.ItcsConstants;
import com.dc.itcs.security.domain.ResourceNode;
import com.dc.itcs.security.entity.UserInfo;
import com.dc.itcs.security.service.ResourceNodeService;

/**
 * 用户上下文
 * @author lee
 *
 */
public final class UserContext {
	/**
	 * 获取当前登录用户，如果无登录信息则返回null
	 * @return
	 */
	public static final UserInfo getCurUser(){
		try{
			Subject subject = SecurityUtils.getSubject();
			UserInfo user = new UserInfo();
			BeanUtils.copyProperties((UserInfo) subject.getPrincipal(), user);
			return user;
		}catch(Exception e){
			return null;
		}
	}
	
	/**
	 * 是否是普通用户
	 * @return
	 */
	public static final boolean isUser() {
		try{
			Subject subject = SecurityUtils.getSubject();
			return (!subject.hasRole(ItcsConstants.ROLE_ADMIN_USER) && !subject.hasRole(ItcsConstants.ROLE_CUSTOMER_MANAGER_USER) && !subject.hasRole(ItcsConstants.ROLE_SERVICE_MANAGER_USER));
		}catch(Exception e){
			return true;
		}
	}

	/**
	 * 获取用户工程师菜单集合
	 * @Methods Name getEngineerMenu
	 * @Create In 2014年12月19日 By lee
	 * @param user
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<ResourceNode> getEngineerMenu(UserInfo user){
		ResourceNodeService rns = ContextHolder.getBean(ResourceNodeService.class);
		List a= rns.findMenuForUser(user,ResourceNode.ROOT_ID);
		return a;
	}

	/**
	 * 是否是工程师(包含客户经理、服务工程师、管理员)
	 * @Methods Name isEngineer
	 * @Create In 2014年12月19日 By lee
	 * @return
	 */
	public static boolean isEngineer(){
		Subject subject = SecurityUtils.getSubject();
		if(subject.hasRole(ItcsConstants.ROLE_ADMIN_USER)){
			return true;
		}
		if(subject.hasRole(ItcsConstants.ROLE_CUSTOMER_MANAGER_USER)){
			return true;
		}
		if(subject.hasRole(ItcsConstants.ROLE_SERVICE_MANAGER_USER)){
			return true;
		}
		return false;
	}
	/**
	 * 是否拥有角色
	 * @Methods Name hasRole
	 * @Create In 2015年1月5日 By lee
	 * @param rid
	 * @return
	 */
	public static boolean hasRole(String rid){
		Subject subject = SecurityUtils.getSubject();
		return subject.hasRole(rid);
	}
}
