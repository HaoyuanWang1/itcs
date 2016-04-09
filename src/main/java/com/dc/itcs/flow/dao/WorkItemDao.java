package com.dc.itcs.flow.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.dc.flamingo.core.data.BaseDao;
import com.dc.itcs.flow.entity.WorkItem;

public interface WorkItemDao extends BaseDao<WorkItem, Long>{
	@Modifying
	@Query(value="delete WorkItem wi where wi.taskId=?1")
	void deleteByTaskId(Long taskId);

	WorkItem findByTaskId(Long taskId);

	List<WorkItem> findByActorIdsLike(String string);
}
