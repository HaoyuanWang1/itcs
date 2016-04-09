package com.dc.itcs.flow.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.flamingo.core.data.Criteria;
import com.dc.flamingo.core.data.Restrictions;
import com.dc.flamingo.core.service.BaseService;
import com.dc.flamingo.workflow.FlowEngine;
import com.dc.itcs.flow.dao.FlowOperateLogDao;
import com.dc.itcs.flow.entity.FlowOperateLog;
import com.dc.itcs.security.entity.UserInfo;

/**
 * 流程操作记录
 * @ClassName: FlowOperateLogService
 * @Description: TODO
 * @Create In 2014年12月23日 By lee
 */
@Service
@Transactional(readOnly = true)
public class FlowOperateLogService extends BaseService{
	@Autowired
	private FlowEngine flowEngine;
	@Autowired
	private FlowOperateLogDao flowOperateLogDao;
	@Transactional
	public void save(FlowOperateLog flowOperateLog) {
		flowOperateLogDao.save(flowOperateLog);
	}
	/**
	 * 保存审批历史
	 * @Methods Name saveOperateLog
	 * @Create In 2015年1月7日 By lee
	 * @param processId
	 * @param nodeDesc
	 * @param operateTime
	 * @param operator
	 * @param action
	 * @param comment
	 */
	@Transactional
	public void saveOperateLog(Long processId,String nodeDesc,String operateTime,UserInfo operator,String operateType,String comment) {
		FlowOperateLog fol = new FlowOperateLog();
		fol.setProcessId(processId);
		fol.setNodeDesc(nodeDesc);
		fol.setOperateTime(operateTime);
		fol.setOperateType(operateType);
		fol.setOperator(operator);
		fol.setComment(comment);
		flowOperateLogDao.save(fol);
	}

	public List<FlowOperateLog> findByProcessId(Long processId, boolean asc) {
		Criteria<FlowOperateLog> c = super.getCriteria(FlowOperateLog.class);
		c.add(Restrictions.eq("processId", processId, false));
		return flowOperateLogDao.findAll(c);
	}
	
}
