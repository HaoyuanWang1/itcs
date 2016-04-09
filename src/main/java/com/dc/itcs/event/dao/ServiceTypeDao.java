package com.dc.itcs.event.dao;


import java.util.List;




import com.dc.flamingo.core.data.BaseDao;
import com.dc.itcs.event.entity.ServiceType;

public interface ServiceTypeDao extends BaseDao<ServiceType,Long>{

	List<ServiceType> findByStateAndTenant_Id(Integer state,Long id);

	List<ServiceType> findByTenant_Id(Long id);
}
