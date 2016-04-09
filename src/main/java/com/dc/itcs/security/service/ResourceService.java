package com.dc.itcs.security.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.flamingo.core.data.Criteria;
import com.dc.flamingo.core.service.BaseService;
import com.dc.itcs.security.dao.ResourceDao;
import com.dc.itcs.security.dao.RoleDao;
import com.dc.itcs.security.entity.Resource;
import com.google.common.collect.Lists;

@Service
@Transactional(readOnly=true)
public class ResourceService extends BaseService{
	@Autowired
	private ResourceDao resourceDao;
	@Autowired
	private RoleDao roleDao;
	public List<Resource> findAll(){
		return resourceDao.findAll();
	}
	public Map<String, Set<String>> findPermMap(){
		return null;
	}
	@Transactional
	public void save(Resource resource) {
		resourceDao.save(resource);
	}
	@Transactional
	public void removeResourceAndRelate(Long id) {
		List<Long> rids = this.findChildIdByParent(id);
		rids.add(id);
        for(Long rid : rids){
        	roleDao.deleteResource(rid);
        	resourceDao.delete(rid);
        }
	}
	private List<Long> findChildIdByParent(Long parentId) {
		List<Resource> childs = resourceDao.findByParentIdOrderByOrderNumAsc(parentId);
		if(childs==null){
			return new ArrayList<Long>();
		}
		List<Long> lists = Lists.newArrayList();
		for(Resource child : childs){
			lists.addAll(findChildIdByParent(child.getId()));
		}
		return lists;
	}

	public Resource findById(Long id) {
		return resourceDao.findOne(id);
	}

	public Page<Resource> findByPage(Pageable pageable, Map<String, Object> paramMap) {
		Criteria<Resource> c = super.getCriteria(paramMap, Resource.class);
		return resourceDao.findAll(c, pageable);
	}
}
