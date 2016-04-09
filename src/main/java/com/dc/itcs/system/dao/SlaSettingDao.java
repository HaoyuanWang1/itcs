package com.dc.itcs.system.dao;

import com.dc.flamingo.core.data.BaseDao;
import com.dc.itcs.event.entity.ServiceLevel;

public interface SlaSettingDao extends BaseDao<ServiceLevel, Long>{

	ServiceLevel findByName(String name);
	ServiceLevel findById(Long id);

}
