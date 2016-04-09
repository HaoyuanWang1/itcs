package com.dc.itcs.system.service;

import java.util.Calendar;
import java.util.Date;
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
import com.dc.flamingo.core.utils.DateUtils;
import com.dc.itcs.system.dao.WorkDayDao;
import com.dc.itcs.system.entity.WorkDay;

/**
 * 工作日维护服务层
 * @ClassName: WorkDayService
 * @Create In 2014年11月5日 By lee
 */
@Service
@Transactional(readOnly=true)
public class WorkDayService extends BaseService{
	@Autowired
	private WorkDayDao workDayDao;

	/**
	 * 分页查询
	 * @Methods Name findByPage
	 * @Create In 2014年11月5日 By lee
	 * @param pageable
	 * @param paramMap
	 * @return
	 */
	public Page<WorkDay> findByPage(Pageable pageable, Map<String, Object> paramMap) {
		Criteria<WorkDay> c = super.getCriteria(paramMap, WorkDay.class);
		return workDayDao.findAll(c, pageable);
	}
	
	/**
	 * 保存工作日设定
	 * @Methods Name save
	 * @Create In 2014年11月5日 By lee
	 * @param workDay
	 * @return
	 */
	@Transactional
	public WorkDay save(WorkDay workDay) {
		return workDayDao.save(workDay);
	}

	public WorkDay findById(Long id) {
		return workDayDao.findOne(id);
	}
	
	/**
	 * 根据某个日期查询
	 * @Methods Name findByTargetDay
	 * @Create In 2014年11月12日 By liaogaosong
	 * @param targetDay
	 * @return WorkDay
	 */
	public WorkDay findByTargetDay(String targetDay){
		Criteria<WorkDay> c = super.getCriteria(WorkDay.class);
		c.add(Restrictions.eq("targetDay",targetDay, false));
		return workDayDao.findOne(c);
	}
	
	/**
	 * 检查是否是工作日
	 * @Methods Name isWorkDay
	 * @Create In 2014年11月12日 By liaogaosong
	 * @param date
	 * @return boolean
	 */
	public boolean isWorkDay(Date date){
		boolean isTrue = false;
		// 1. 查出工作计划表中数据
		WorkDay work = findByTargetDay(DateUtils.convertDateToString(date));
		// 2. 检查今天是周几，如果是周末则检查work信息中是否上班
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		// 今天是周几
		int curWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
		// 3. 如果是周一-周五，则检查work信息中是否放假
		if(curWeek >= 1 && curWeek <=5){
			if(null != work){
				int workFlag = work.getWorkFlag();
				if(WorkDay.WORK_FALSE == workFlag){
					isTrue = false;
				}else{
					isTrue = true;
				}
			}else{
				isTrue = true;
			}
		}else{
			// 周末，则检查当前是否上班
			if(null != work){
				int workFlag = work.getWorkFlag();
				if(WorkDay.WORK_TRUE == workFlag){
					isTrue = true;
				}else{
					isTrue = false;
				}
			}else{
				isTrue = false;
			}
		}
		return isTrue;
	}

	/**
	 * 获取所有工作日设定
	 * @Methods Name findAll
	 * @Create In 2014年11月18日 By lee
	 * @return
	 */
	public List<WorkDay> findAll() {
		return workDayDao.findAll();
	}
}
