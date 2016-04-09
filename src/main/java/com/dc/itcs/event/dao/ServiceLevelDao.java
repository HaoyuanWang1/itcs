package com.dc.itcs.event.dao;

import java.util.List;



import com.dc.flamingo.core.data.BaseDao;
import com.dc.itcs.event.entity.ServiceLevel;

public interface ServiceLevelDao extends BaseDao<ServiceLevel,Long>{

	List<ServiceLevel> findByStateAndTenant_Id(Integer state,Long id);
	List<ServiceLevel> findByTenant_Id(Long id);
}
