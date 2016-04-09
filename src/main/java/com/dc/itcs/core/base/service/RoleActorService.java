package com.dc.itcs.core.base.service;

import java.util.List;

import com.dc.itcs.core.base.entity.RoleActor;
import com.dc.itcs.security.entity.UserInfo;


/**
 * 角色参与服务接口
 * @author lee
 *
 */
public interface RoleActorService {

	/**
	 * 根据用户获取角色
	 * @param userInfo
	 * @return
	 */
	List<RoleActor> getRoleActorByUser(UserInfo userInfo);
	
	/**
	 * 更换角色
	 * @param sourceUser
	 * @param targetUser
	 */
	void changeRoleActor(UserInfo sourceUser, UserInfo targetUser);
	
	/**
	 * 删除角色
	 * @param userInfo
	 */
	void deleteRoleActor(UserInfo userInfo);
}
