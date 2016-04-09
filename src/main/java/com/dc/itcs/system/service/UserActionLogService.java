package com.dc.itcs.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.itcs.system.dao.UserActionLogDao;
import com.dc.itcs.system.entity.UserActionLog;

@Service
@Transactional(readOnly=true)
public class UserActionLogService {
	@Autowired
	private UserActionLogDao userActionLogDao;
	@Transactional
	public void saveLog(UserActionLog userActionLog){
		userActionLogDao.save(userActionLog);
	}
}
