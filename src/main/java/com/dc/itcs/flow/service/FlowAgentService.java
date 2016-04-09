package com.dc.itcs.flow.service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dc.flamingo.core.data.Criteria;
import com.dc.flamingo.core.data.Restrictions;
import com.dc.flamingo.core.service.BaseService;
import com.dc.flamingo.core.support.PageParam;
import com.dc.flamingo.core.utils.DateUtils;
import com.dc.itcs.core.context.UserContext;
import com.dc.itcs.flow.dao.FlowAgentDao;
import com.dc.itcs.flow.entity.FlowAgent;
import com.dc.itcs.security.entity.UserInfo;


/**
 * 流程代办
 * @Class Name FlowAgentService
 * @Author lee
 * @Create In Jul 27, 2011
 */
@Component
@Transactional(readOnly=true)
public class FlowAgentService extends BaseService{
    @Autowired
    private FlowAgentDao flowAgentDao;
	/**
	 * 保存代办信息
	 * @Methods Name saveFlowAgent
	 * @Create In Jul 27, 2011 By lee
	 * @param flowAgent FlowAgent
	 */
	public FlowAgent saveFlowAgent(FlowAgent flowAgent){
		flowAgent = flowAgentDao.save(flowAgent);
		flowAgent = flowAgentDao.findOne(flowAgent.getId());
		return flowAgent;
	}
	
	/**
	 * 删除代办信息
	 * @Methods Name removeFlowAgent
	 * @Create In Jul 27, 2011 By lee
	 * @param flowAgent void
	 */
	public void removeFlowAgent(Long flowAgentId){
		FlowAgent flowAgent = flowAgentDao.findOne(flowAgentId);
		flowAgentDao.delete(flowAgent);
	}

	/**
	 * 获取当前有效的代办
	 * @Methods Name getNowAgent
	 * @Create In Jul 27, 2011 By lee
	 * @return List<FlowAgent>
	 */
	public List<FlowAgent> getNowAgent(){
		String nowDate = DateUtils.getCurrentDateStr();
		return flowAgentDao.getNowAgent(nowDate, nowDate);
	}

	public Page<FlowAgent> findForUserPage(Map<String, Object> paramMap, PageParam pageParam) {
		Criteria<FlowAgent> c = super.getCriteria(paramMap, FlowAgent.class);
		UserInfo user = UserContext.getCurUser();
		c.add(Restrictions.eq("auther", user, false));
		return flowAgentDao.findAll(c,pageParam);
	}

	public Set<UserInfo> findAgenterForUser(UserInfo user) {
		Criteria<FlowAgent> c = new Criteria<FlowAgent>();
		c.add(Restrictions.eq("auther", user, false));
		String nowDate = DateUtils.getCurrentDateStr();
		String nowTime = DateUtils.getCurrentDateTimeStr();
		c.add(Restrictions.gte("startDate", nowDate, true));
		c.add(Restrictions.lte("stopTime", nowTime, true));
		List<FlowAgent> list = flowAgentDao.findAll(c);
		Set<UserInfo> users = new HashSet<UserInfo>();
		for(FlowAgent fa : list){
			users.add(fa.getAgenter());
		}
		return users;
	}

	public void saveFlowAgentStop(Long id) {
		FlowAgent flowAgent = flowAgentDao.findOne(id);
		flowAgent.setStopTime(DateUtils.getCurrentDateTimeStr());
		flowAgentDao.save(flowAgent);
	}

}
