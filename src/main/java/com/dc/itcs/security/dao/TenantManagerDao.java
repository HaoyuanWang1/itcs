package com.dc.itcs.security.dao;


import java.util.List;




import com.dc.flamingo.core.data.BaseDao;
import com.dc.itcs.security.entity.TenantManager;
import com.dc.itcs.security.entity.UserInfo;

public interface TenantManagerDao extends BaseDao<TenantManager, Long>{
	List<TenantManager> findByTenant_Name(String name);
	List<TenantManager> findByTenant_Id(Long id);
	/**
	 * 根据用户查询对应的客户经理集合
	 * @Methods Name findByTenantManager
	 * @Create In 2015年8月17日 By lee
	 * @param user
	 * @return
	 */
	List<TenantManager> findByTenantManager(UserInfo user);
}
