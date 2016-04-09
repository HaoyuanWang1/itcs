package com.dc.itcs.security.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.itcs.security.dao.ResourceDao;
import com.dc.itcs.security.dao.RoleResourceDao;
import com.dc.itcs.security.dao.UserRoleDao;
import com.dc.itcs.security.domain.ResourceNode;
import com.dc.itcs.security.entity.Resource;
import com.dc.itcs.security.entity.RoleResource;
import com.dc.itcs.security.entity.UserInfo;
import com.dc.itcs.security.entity.UserRole;
import com.google.common.collect.Sets;

@Service
@Transactional(readOnly=true)
public class ResourceNodeService {
	@Autowired
	private ResourceDao resourceDao;
	@Autowired
	private UserRoleDao userRoleDao;
	@Autowired
	private RoleResourceDao roleResourceDao;
	/**
	 * 根据父ID获取子节点
	 * @param parentId
	 * @return
	 */
	public List<ResourceNode> findByParent(long parentId) {
		List<Resource> rList = resourceDao.findByParentIdOrderByOrderNumAsc(parentId);
        if (rList==null) {
            return new ArrayList<ResourceNode>();
        }
        List<ResourceNode> rnList = new ArrayList<ResourceNode>();
        for(Resource r : rList){
        	ResourceNode rn = new ResourceNode(r);
        	rn.setChildren(findByParent(r.getId()));
        	rnList.add(rn);
        }
        return rnList;
	}
	public List<ResourceNode> findMenuByParent(long parentId) {
		List<Resource> rList = resourceDao.findByParentIdAndMenuItemOrderByOrderNumAsc(parentId, 1);
        if (rList==null) {
            return new ArrayList<ResourceNode>();
        }
        List<ResourceNode> rnList = new ArrayList<ResourceNode>();
        for(Resource r : rList){
        	ResourceNode rn = new ResourceNode(r);
        	rn.setChildren(findByParent(r.getId()));
        	rnList.add(rn);
        }
        return rnList;
	}

	public List<ResourceNode> findMenuForUser(UserInfo userInfo,Long parentId) {
		List<UserRole> userRoles = userRoleDao.findByUser_Id(userInfo.getId());
		Set<Resource> allRrs = Sets.newHashSet();
		for(UserRole ur : userRoles){
			List<RoleResource> rrs = roleResourceDao.findByRole_Id(ur.getRole().getId());
			for(RoleResource rr : rrs){
				allRrs.add(rr.getResource());
			}
		}
		return findUserMenuNode(allRrs,parentId);
	}
	private List<ResourceNode> findUserMenuNode(Set<Resource> allRrs,Long parentId){
		List<Resource> rList = resourceDao.findByParentIdAndMenuItemOrderByOrderNumAsc(parentId, 1);
        if (rList==null) {
            return new ArrayList<ResourceNode>();
        }
        List<ResourceNode> rnList = new ArrayList<ResourceNode>();
        for(Resource r : rList){
        	if(allRrs.contains(r)){
        		ResourceNode rn = new ResourceNode(r);
            	rn.setChildren(findUserMenuNode(allRrs, r.getId()));
            	rnList.add(rn);
        	}else{
        		if(Resource.TYPE_ROLE!=r.getType()){
        			ResourceNode rn = new ResourceNode(r);
                	rn.setChildren(findUserMenuNode(allRrs, r.getId()));
                	rnList.add(rn);
        		}
        	}
        }
        return rnList;
	}
}
