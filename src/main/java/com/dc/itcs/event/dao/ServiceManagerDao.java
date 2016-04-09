package com.dc.itcs.event.dao;


import java.util.List;
import com.dc.flamingo.core.data.BaseDao;
import com.dc.itcs.event.entity.ServiceManager;

public interface ServiceManagerDao extends BaseDao<ServiceManager,Long>{
	
	List<ServiceManager> findByType_Id(Long id);

	ServiceManager findByType_IdAndUserInfo_Id(Long typeId, Long userId);
}
