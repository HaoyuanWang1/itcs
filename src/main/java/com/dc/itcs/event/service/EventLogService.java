package com.dc.itcs.event.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.flamingo.core.service.BaseService;
import com.dc.itcs.event.dao.EventLogDao;
import com.dc.itcs.event.entity.EventLog;
@Service("eventLogService")
@Transactional(readOnly=true)
public class EventLogService extends BaseService {
	@Autowired
	private EventLogDao eventLogDao;

    @Transactional
    public EventLog eventLogSave(EventLog eventLog){
    	return eventLogDao.save(eventLog);
    }
    
    public EventLog findById(Long id){
    	return eventLogDao.findOne(id);
    }
    
    public List<EventLog> findByEvent_Id(Long id ){
    	return eventLogDao.findByEvent_Id(id);
    }
    
    public EventLog findLastByEvent_IdAndAction(Long id,String action){
    	List<EventLog> logs = eventLogDao.findByEvent_IdAndActionOrderByCreateTimeDesc(id,action);
    	if(!logs.isEmpty()){
    		return logs.get(0);
    	}
    	return null;
    }
  
}
