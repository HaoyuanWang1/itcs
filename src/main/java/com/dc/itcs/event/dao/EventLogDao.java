package com.dc.itcs.event.dao;


import java.util.List;



import com.dc.flamingo.core.data.BaseDao;
import com.dc.itcs.event.entity.EventLog;

public interface EventLogDao extends BaseDao<EventLog,Long>{

	List<EventLog> findByEvent_Id(Long id);

	List<EventLog> findByEvent_IdAndActionOrderByCreateTimeDesc(Long id, String action);

}
