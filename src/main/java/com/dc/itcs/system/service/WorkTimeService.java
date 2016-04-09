package com.dc.itcs.system.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.flamingo.core.service.BaseService;
import com.dc.flamingo.core.utils.DateUtils;
import com.dc.itcs.system.dao.WorkTimeDao;
import com.dc.itcs.system.entity.WorkTime;

@Service
@Transactional(readOnly=true)
public class WorkTimeService extends BaseService{
	@Autowired
	private WorkTimeDao workTimeDao;

	public WorkTime findWorkTime() {
		List<WorkTime> list = workTimeDao.findAll();
		if(list.isEmpty()){
			return null;
		}else{
			return list.get(0);
		}
	}
	
	@Transactional
	public WorkTime save(WorkTime workTime) {
		workTime = workTimeDao.save(workTime);
		String curDayStr = DateUtils.getCurrentDateStr();
		Date amBeginTime = DateUtils.convertStringToDate(curDayStr+" "+workTime.getAmBeginTime());
		Date amEndTime = DateUtils.convertStringToDate(curDayStr+" "+workTime.getAmEndTime());
		Date pmBeginTime = DateUtils.convertStringToDate(curDayStr+" "+workTime.getPmBeginTime());
		Date pmEndTime = DateUtils.convertStringToDate(curDayStr+" "+workTime.getPmEndTime());
		Long oneDayWorkTime = amEndTime.getTime()-amBeginTime.getTime()+pmEndTime.getTime()-pmBeginTime.getTime();
		workTime.setOneDayWorkTime(oneDayWorkTime);
		return workTime;
	}
	
}
