package com.dc.itcs.security.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.dc.flamingo.core.data.BaseDao;
import com.dc.itcs.security.entity.RoleResource;

public interface RoleResourceDao extends BaseDao<RoleResource, Long>{

	@Modifying
    @Query("DELETE FROM RoleResource rr WHERE rr.resource.id = ?1 ")
	void deleteByResourceId(Long resourceId);
	
	@Modifying
    @Query("DELETE FROM RoleResource rr WHERE rr.role.id = ?1 ")
	void removeByRoleId(Long id);

	List<RoleResource> findByRole_Id(Long roleId);

}
