package com.dc.itcs.security.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.itcs.security.dao.RoleResourceDao;
import com.dc.itcs.security.entity.Resource;
import com.dc.itcs.security.entity.RoleResource;

@Service
@Transactional(readOnly=true)
public class RoleResourceService {
	@Autowired
	private RoleResourceDao roleResourceDao;
	public List<RoleResource> findAll() {
		return roleResourceDao.findAll();
	}

	public List<Resource> findResourceByRoleId(Long roleId) {
		List<RoleResource> rrs = roleResourceDao.findByRole_Id(roleId);
		List<Resource> list = new ArrayList<Resource>();
		for(RoleResource rr : rrs){
			list.add(rr.getResource());
		}
		return list;
	}

}
