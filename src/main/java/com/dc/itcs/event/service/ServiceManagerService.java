package com.dc.itcs.event.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.flamingo.core.service.BaseService;
import com.dc.itcs.event.dao.ServiceManagerDao;
import com.dc.itcs.event.entity.ServiceManager;
import com.dc.itcs.flow.service.FlowApplyService;
import com.dc.itcs.security.service.UserInfoService;

@Service("serviceManagerService")
@Transactional(readOnly=true)
public class ServiceManagerService extends BaseService{
	@Autowired
	private ServiceManagerDao serviceManagerDao;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private FlowApplyService flowApplyService;
	
	
	public List<ServiceManager> findByType_Id(Long id) {
		return serviceManagerDao.findByType_Id(id);
	}

    @Transactional
    public ServiceManager saveServiceManager(ServiceManager serviceManager){
    	return serviceManagerDao.save(serviceManager);
    }
	
	@Transactional
	public void delete(ServiceManager sm) {
		serviceManagerDao.delete(sm);
	}

}
