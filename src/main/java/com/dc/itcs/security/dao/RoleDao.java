package com.dc.itcs.security.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.dc.flamingo.core.data.BaseDao;
import com.dc.itcs.security.entity.RoleInfo;

public interface RoleDao extends BaseDao<RoleInfo, Long>{
	@Modifying
    @Query("DELETE FROM RoleResource rr WHERE rr.resource.id = ?1 ")
	public void deleteResource(Long rid);

	public RoleInfo findByRid(String rid);

}
