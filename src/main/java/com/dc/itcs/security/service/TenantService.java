package com.dc.itcs.security.service;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.flamingo.core.data.Criteria;
import com.dc.flamingo.core.data.Restrictions;
import com.dc.flamingo.core.service.BaseService;
import com.dc.flamingo.core.support.PageParam;
import com.dc.flamingo.core.utils.StrUtils;
import com.dc.itcs.core.base.support.IdGetter;
import com.dc.itcs.core.base.support.ItcsConstants;
import com.dc.itcs.security.dao.TenantDao;
import com.dc.itcs.security.entity.Tenant;

@Service("tenantService")
@Transactional(readOnly=true)
public class TenantService extends BaseService {
	@Autowired
	private TenantDao tenantDao;
	
    public Page<Tenant> findApplyForPage(PageParam pageParam, Map<String, Object> paramMap) {
        pageParam.setSort(new Sort(Direction.ASC,"id"));
    	Criteria<Tenant> c = super.getCriteria(paramMap,Tenant.class);
        return tenantDao.findAll(c, pageParam);
    }
    
    @Transactional
    public Tenant saveApply(Tenant tenant) {
    	if (StrUtils.isEmpty(tenant.getCode())) {
    		tenant.setCode(IdGetter.userApply.getNum());
		}
        return tenantDao.save(tenant);
    }
    
    public Tenant findTenantById(Long id) {
        return tenantDao.findById(id);
    }
    
    public Tenant findTenantByName(String name) {
        return tenantDao.findByName(name);
    }
    
    
    /**
     * 查询客户信息
     * @Methods Name findAllTenantsMess
     * @Create In 2015年8月13日 By luzm1
     * @param searchKey
     * @param size
     * @return List<Tenant>
     */
	public List<Tenant> findAllTenantsMess(String searchKey,Integer size,String typeC) {
		Criteria<Tenant> c = super.getCriteria(Tenant.class);
		c.add(Restrictions.or(
				Restrictions.like("code", searchKey, true),
				Restrictions.like("name", searchKey, true)));
		c.add(Restrictions.eq("state", ItcsConstants.IS_YES, true));
		c.add(Restrictions.eq("type", typeC, true));
		return tenantDao.findAll(c, new PageParam(0,size)).getContent();
	}
    
}
