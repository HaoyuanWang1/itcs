package com.dc.itcs.core.base.entity;

import com.dc.itcs.security.entity.UserInfo;

/**
 * 角色参与实体接口
 * 用于统计那些实体与类似角色配置有关，统计用户在系统中参与权限及配置信息
 * @author lee
 *
 */
public interface RoleActor {
	/**
	 * 获取角色参与描述
	 * @return
	 */
	String getRoleActorInfo(UserInfo userInfo);
}
