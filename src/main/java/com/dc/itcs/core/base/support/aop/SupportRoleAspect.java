package com.dc.itcs.core.base.support.aop;

import java.util.List;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dc.itcs.core.base.support.ItcsConstants;
import com.dc.itcs.event.entity.ServiceManager;
import com.dc.itcs.security.entity.RoleInfo;
import com.dc.itcs.security.entity.TenantManager;
import com.dc.itcs.security.entity.UserInfo;
import com.dc.itcs.security.entity.UserRole;
import com.dc.itcs.security.service.RoleInfoService;
import com.dc.itcs.security.service.UserInfoService;
import com.dc.itcs.security.service.UserRoleService;

@Component
@Aspect
@Transactional
public class SupportRoleAspect {
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private RoleInfoService roleInfoService;
	@Autowired
	private UserRoleService userRoleService;
	
	/**
	 * 保存客户经理后自动给客户经理增加角色
	 * @Methods Name afterSaveSupportEngineer
	 * @Create In 2014年12月23日 By lee
	 * @param supportEngineer
	 */
	@AfterReturning(value="execution(com.dc.itcs.security.entity.TenantManager com.dc.itcs.security.service.TenantManagerService.save(..))",
			argNames="tenantManager", returning="tenantManager")
	public void afterSaveTenantManager(TenantManager tenantManager) {
		List<UserRole> list= userRoleService.findByUser_Id(tenantManager.getTenantManager().getId());
		boolean isTenantManager = false; // 是否已经是客户经理
		String tenantManagerRid = ItcsConstants.ROLE_CUSTOMER_MANAGER_USER;
		for(UserRole ur : list){
			if(tenantManagerRid.equals(ur.getRole().getRid())){
				isTenantManager = true;
				break;
			}
		}
		
		// 如果没有客户经理角色，新增
		if(!isTenantManager){
			UserRole ur = new UserRole();
			RoleInfo role = roleInfoService.findByRid(tenantManagerRid);
			ur.setRole(role);
			ur.setUser(tenantManager.getTenantManager());
			userRoleService.saveUserRole(ur);
		}
	}
	
	/**
	 * 删除客户经理后，如果该客户没有其他角色，直接删除改用户角色
	 * @Methods Name afterSaveSupportEngineer
	 * @Create In 2014年12月23日 By lee
	 * @param supportEngineer
	 */
	@AfterReturning(value="execution(com.dc.itcs.security.entity.TenantManager com.dc.itcs.security.service.TenantManagerService.remove(..))",
			argNames="tenantManager", returning="tenantManager")
	public void afterDeleteTenantManager(TenantManager tenantManager) {
		List<UserRole> list= userRoleService.findByUser_Id(tenantManager.getTenantManager().getId());
		
		String tenantManagerRid = ItcsConstants.ROLE_CUSTOMER_MANAGER_USER;
		
		UserRole userRole = list.get(0);
		
		// 该客户除去客户经理外、没有其他角色，直接删除该用户角色
		if (list.size() == 1 && tenantManagerRid.equals(userRole.getRole().getRid())) {
			userRoleService.delete(userRole);
		}
	}
	
	
	
	/**
	 * 保存普通账号自动给普通账号增加角色
	 * @Methods Name afterSaveSupportEngineer
	 * @Create In 2014年12月23日 By lee
	 * @param supportEngineer
	 */
	@AfterReturning(value="execution(com.dc.itcs.security.entity.UserInfo com.dc.itcs.security.service.UserInfoService.saveUserInfo(..))",
			argNames="userInfo", returning="userInfo")
	public void afterSaveUser(UserInfo userInfo) {
		List<UserRole> list= userRoleService.findByUser_Id(userInfo.getId());
		boolean isUserInfo = false; // 是否已经是普通账号
		String userInfoRid = ItcsConstants.ROLE_COMMON_USER;
		for(UserRole ur : list){
			if(userInfoRid.equals(ur.getRole().getRid())){
				isUserInfo = true;
				break;
			}
		}
		
		// 如果没有普通账号角色，新增
		if(!isUserInfo){
			UserRole ur = new UserRole();
			RoleInfo role = roleInfoService.findByRid(userInfoRid);
			ur.setRole(role);
			ur.setUser(userInfo);
			userRoleService.saveUserRole(ur);
		}
	}
	
	/**
	 * 删除普通账号后，如果该客户没有其他角色，直接删除改用户角色
	 * @Methods Name afterSaveSupportEngineer
	 * @Create In 2014年12月23日 By lee
	 * @param supportEngineer
	 */
	@AfterReturning(value="execution(com.dc.itcs.security.entity.UserInfo com.dc.itcs.security.service.UserInfoService.remove(..))",
			argNames="userInfo", returning="userInfo")
	public void afterDeleteUser(UserInfo userInfo) {
		List<UserRole> list= userRoleService.findByUser_Id(userInfo.getId());
		
		String userInfoRid = ItcsConstants.ROLE_COMMON_USER;
		
		UserRole userRole = list.get(0);
		
		// 该客户除去普通账号外、没有其他角色，直接删除该用户角色
		if (list.size() == 1 && userInfoRid.equals(userRole.getRole().getRid())) {
			userRoleService.delete(userRole);
		}
	}
	
	
	
	/**
	 * 保存服务经理账号自动给服务经理账号增加角色
	 * @Methods Name afterSaveSupportEngineer
	 * @Create In 2014年12月23日 By lee
	 * @param supportEngineer
	 */									                                     
	@AfterReturning(value="execution(com.dc.itcs.event.entity.ServiceManager com.dc.itcs.event.service.ServiceManagerService.saveServiceManager(..))",
			argNames="serviceManager", returning="serviceManager")
	public void afterSaveServiceLevel(ServiceManager serviceManager) {
		List<UserRole> list= userRoleService.findByUser_Id(serviceManager.getUserInfo().getId());
		boolean isServiceManager = false; // 是否已经是服务经理
		String serviceManagerRid = ItcsConstants.ROLE_SERVICE_MANAGER_USER;
		for(UserRole ur : list){
			if(serviceManagerRid.equals(ur.getRole().getRid())){
				isServiceManager = true;
				break;
			}
		}
		
		// 如果没有服务经理账号角色，新增
		if(!isServiceManager){
			UserRole ur = new UserRole();
			RoleInfo role = roleInfoService.findByRid(serviceManagerRid);
			ur.setRole(role);
			ur.setUser(serviceManager.getUserInfo());
			userRoleService.saveUserRole(ur);
		}
	}
	
	/**
	 * 删除服务经理后，如果该客户没有其他角色，直接删除改用户角色
	 * @Methods Name afterSaveSupportEngineer
	 * @Create In 2014年12月23日 By lee
	 * @param supportEngineer	
	 */																			
	@AfterReturning(value="execution(com.dc.itcs.event.entity.ServiceManager com.dc.itcs.event.service.ServiceManagerService.delete(..))",
			argNames="serviceManager", returning="serviceManager")
	public void afterDeleteServiceLevel(ServiceManager serviceManager) {
		List<UserRole> list= userRoleService.findByUser_Id(serviceManager.getUserInfo().getId());
		
		String serviceManagerRid = ItcsConstants.ROLE_SERVICE_MANAGER_USER;
		
		UserRole userRole = list.get(0);
		
		// 该客户除去服务经理外、没有其他角色，直接删除该用户角色
		if (list.size() == 1 && serviceManagerRid.equals(userRole.getRole().getRid())) {
			userRoleService.delete(userRole);
		}
	}
}
