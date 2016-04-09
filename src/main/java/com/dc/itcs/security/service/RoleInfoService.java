package com.dc.itcs.security.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.flamingo.core.data.Criteria;
import com.dc.flamingo.core.service.BaseService;
import com.dc.flamingo.core.utils.StrUtils;
import com.dc.itcs.security.dao.RoleDao;
import com.dc.itcs.security.dao.RoleResourceDao;
import com.dc.itcs.security.entity.Resource;
import com.dc.itcs.security.entity.RoleInfo;
import com.dc.itcs.security.entity.RoleResource;

@Service("roleService")
@Transactional(readOnly=true)
public class RoleInfoService extends BaseService {
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private RoleResourceDao roleResourceDao;
	
	public Page<RoleInfo> findByPage(Pageable pageable, Map<String, Object> paramMap) {
		Criteria<RoleInfo> c = super.getCriteria(paramMap, RoleInfo.class);
		return roleDao.findAll(c, pageable);
	}

	public RoleInfo findById(Long id) {
		return roleDao.findOne(id);
	}
	@Transactional
	public RoleInfo saveRoleAndResource(RoleInfo roleInfo, String resources) {
		roleInfo = roleDao.save(roleInfo);
		List<String> resourceIdStrs = StrUtils.splitToList(resources, ",");
		roleResourceDao.removeByRoleId(roleInfo.getId());
		List<RoleResource> rrs = new ArrayList<RoleResource>();
		for(String resourceIdStr : resourceIdStrs){
			Long resourceId = Long.valueOf(resourceIdStr);
			if(0!=resourceId){
				RoleResource rr = new RoleResource();
				rr.setRole(roleInfo);
				Resource resource = new Resource();
				resource.setId(resourceId);
				rr.setResource(resource);
				rrs.add(rr);
			}
		}
		roleResourceDao.save(rrs);
		return roleInfo;
	}

	public RoleInfo findByRid(String rid) {
		return roleDao.findByRid(rid);
	}
	public List<RoleInfo> findAll(){
	    return roleDao.findAll();
	}
}
