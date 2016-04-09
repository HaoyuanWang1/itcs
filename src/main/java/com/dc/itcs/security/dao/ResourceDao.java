package com.dc.itcs.security.dao;

import java.util.List;

import com.dc.flamingo.core.data.BaseDao;
import com.dc.itcs.security.entity.Resource;

public interface ResourceDao extends BaseDao<Resource, Long>{

	List<Resource> findByParentIdOrderByOrderNumAsc(Long parentId);

	List<Resource> findByParentIdAndMenuItemOrderByOrderNumAsc(Long parentId, Integer menuItem);
	
	

}
