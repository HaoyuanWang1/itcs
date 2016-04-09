package com.dc.itcs.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.itcs.event.entity.ServiceLevel;
import com.dc.itcs.system.dao.SlaSettingDao;

/**
 * SLA设置服务层
 * @ClassName: SlaSettingService
 * @Description: TODO
 * @Create In 2014年11月17日 By lee
 */
@Service
@Transactional(readOnly=true)
public class SlaSettingService {
	@Autowired
	private SlaSettingDao slaSettingDao;
	
	/**
	 * 保存SLA设置
	 * @Methods Name save
	 * @Create In 2014年11月17日 By lee
	 * @param slaSetting
	 * @return
	 */
	@Transactional
	public ServiceLevel save(ServiceLevel slaSetting){
		return slaSettingDao.save(slaSetting);
	}
	/**
	 * 获取所有SLA设置
	 * @Methods Name findAll
	 * @Create In 2014年11月17日 By lee
	 * @return
	 */
	public List<ServiceLevel> findAll(){
		return slaSettingDao.findAll();
	}
	
	/**
	 * 根据关键字获取SLA设置
	 * @Methods Name findByKey
	 * @Create In 2014年11月17日 By lee
	 * @param key
	 * @return
	 */
	public ServiceLevel findByKey(Long id){
		return slaSettingDao.findById(id);
	}
}
