package com.dc.itcs.event.dao;


import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.dc.flamingo.core.data.BaseDao;
import com.dc.itcs.event.entity.EventSloveData;

public interface EventSloveDataDao extends BaseDao<EventSloveData,Long>{
	
	@Query(value="SELECT new EventSloveData(event.tenant.id,event.tenant,"
			+ " count(event.tenant.id) as dclCount)"
			+ " FROM Event event"
			+ " where event.mainState = ?1"
			+ " group by event.tenant order by dclCount ASC")
	List<EventSloveData> findEventSloveDataByTenant(int mainState);
	
	//该服务商下的所有的客户经理和服务经理
	@Query(value="SELECT new EventSloveData(event.tenant.id,event.tenant,count(event.tenant.id) as dclCount)"
			+ " FROM Event event"
			+ " WHERE event.mainState = ?1"
			+ " AND event.singerIds like %?2"
			+ " group by event.tenant order by dclCount ASC")
	List<EventSloveData> findEventSloveDataByTenantAndType(int mainState,String code);
	
}