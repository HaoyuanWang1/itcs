package com.dc.itcs.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.flamingo.core.service.BaseService;
import com.dc.itcs.security.dao.TenantManagerDao;
import com.dc.itcs.security.entity.TenantManager;
import com.dc.itcs.security.entity.UserInfo;

@Service("tenantManagerService")
@Transactional(readOnly=true)
public class TenantManagerService extends BaseService {
	@Autowired
	private TenantManagerDao tenantManagerDao;
	
	public List<TenantManager> findByTenant_Id(Long id) {
		return tenantManagerDao.findByTenant_Id(id);
	}
	public TenantManager findById(Long id) {
		return tenantManagerDao.findOne(id);
	}
	
	@Transactional
	public TenantManager save(TenantManager tenantManager) {
		return tenantManagerDao.save(tenantManager);
	}
	
	@Transactional
	public void remove(TenantManager tenantManager) {
		tenantManagerDao.delete(tenantManager);
	}
	/**
	 * 根据用户获取对应客户经理集合
	 * @Methods Name findByManager
	 * @Create In 2015年8月17日 By lee
	 * @param user
	 * @return
	 */
	public List<TenantManager> findByManager(UserInfo user) {
		return tenantManagerDao.findByTenantManager(user);
	}

}
