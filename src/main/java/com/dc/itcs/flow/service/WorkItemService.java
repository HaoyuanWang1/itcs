package com.dc.itcs.flow.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.flamingo.core.data.Criteria;
import com.dc.flamingo.core.data.Restrictions;
import com.dc.flamingo.core.service.BaseService;
import com.dc.flamingo.workflow.entity.Task;
import com.dc.itcs.core.base.entity.FlowEntity;
import com.dc.itcs.core.base.utils.MetadataManager;
import com.dc.itcs.flow.dao.WorkItemDao;
import com.dc.itcs.flow.entity.WorkItem;
import com.dc.itcs.security.entity.UserInfo;

@Service
@Transactional(readOnly = true)
public class WorkItemService extends BaseService{
	@Autowired
	private WorkItemDao workItemDao;
	@Autowired
	private MetadataManager metadataManager;

	public List<WorkItem> findForAuditUser(UserInfo user) {
		Criteria<WorkItem> c = super.getCriteria(WorkItem.class);
		c.add(Restrictions.like("actorIds", "【"+user.getId()+"】", false));
		c.add(Restrictions.eq("taskState", Task.STATE_OPEN, false));
		List<WorkItem> wis = workItemDao.findAll(c);
		return wis;
	}

	public WorkItem findForAuditUser(String entityClass, Long entityId,
			UserInfo user) {
		Criteria<WorkItem> c = super.getCriteria(WorkItem.class);
		c.add(Restrictions.eq("entityClass", entityClass, false));
		c.add(Restrictions.eq("entityId", entityId, false));
		c.add(Restrictions.like("actorIds", "【"+user.getId()+"】", false));
		c.add(Restrictions.eq("taskState", Task.STATE_OPEN, false));
		List<WorkItem> wis = workItemDao.findAll(c);
		if(wis.isEmpty()){
			return null;
		}else{
			return wis.get(0);
		}
	}

	public List<WorkItem> findByProcessId(Long processId) {
		Criteria<WorkItem> c = super.getCriteria(WorkItem.class);
		c.add(Restrictions.eq("processId", processId, false));
		return workItemDao.findAll(c);
	}

	public List<WorkItem> findForAuditUser(Long processId, UserInfo user) {
		Criteria<WorkItem> c = super.getCriteria(WorkItem.class);
		c.add(Restrictions.eq("processId", processId, false));
		c.add(Restrictions.like("actorIds", "【"+user.getId()+"】", false));
		c.add(Restrictions.eq("taskState", Task.STATE_OPEN, false));
		return workItemDao.findAll(c);
	}

	public WorkItem findByTaskId(Long taskId) {
		return workItemDao.findByTaskId(taskId);
	}

	public void save(WorkItem workItem) {
		workItemDao.save(workItem);
	}

	public Page<WorkItem> findForPage(Pageable pageable, Map<String, Object> param) {
		Criteria<WorkItem> c = super.getCriteria(param, WorkItem.class);
		return workItemDao.findAll(c, pageable);
	}

	public Page<WorkItem> findByPageForAdmin(Pageable pageable, Map<String, Object> param) {
		Criteria<WorkItem> c = super.getCriteria(param, WorkItem.class);
		Page<WorkItem> page = workItemDao.findAll(c, pageable);
		for(WorkItem wi : page.getContent()){
			wi.setEntity((FlowEntity) metadataManager.getEntity(wi.getEntityClass(), wi.getEntityId()));
		}
		return page;
	}

	public List<WorkItem> findByEntityClassForAuditUser(String entityClass, Long userId) {
		Criteria<WorkItem> c = super.getCriteria(WorkItem.class);
		c.add(Restrictions.eq("entityClass", entityClass, false));
		c.add(Restrictions.like("actorIds", "【"+userId+"】", false));
		c.add(Restrictions.eq("taskState", Task.STATE_OPEN, false));
		return workItemDao.findAll(c);
	}
}
