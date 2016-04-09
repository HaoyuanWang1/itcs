package com.dc.itcs.security.dao;


import java.util.List;

import com.dc.flamingo.core.data.BaseDao;
import com.dc.itcs.security.entity.Tenant;

public interface TenantDao extends BaseDao<Tenant, Long>{

	List<Tenant> findByState(Integer id);

	Tenant findByIdAndState(Long id, Integer state);

	Tenant findById(Long id);

	Tenant findByName(String name);
}
