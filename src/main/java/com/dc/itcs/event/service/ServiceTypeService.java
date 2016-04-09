package com.dc.itcs.event.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.flamingo.core.service.BaseService;
import com.dc.itcs.event.dao.ServiceTypeDao;
import com.dc.itcs.event.entity.ServiceType;

@Service("serviceTypeService")
@Transactional(readOnly=true)
public class ServiceTypeService extends BaseService{

	@Autowired
	private ServiceTypeDao serviceTypeDao;
   
    public List<ServiceType> findByStateAndTenant_Id(Integer state,Long id){
    	return serviceTypeDao.findByStateAndTenant_Id(state,id);
    }
    
    public List<ServiceType> findByTenant_Id(Long id){
    	return serviceTypeDao.findByTenant_Id(id);
    }

    public ServiceType findById(Long id){
    	return serviceTypeDao.findOne(id);
    }
    
    @Transactional
    public ServiceType saveServiceType(ServiceType serviceType){
    	return serviceTypeDao.save(serviceType);
    }
}
