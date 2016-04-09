package com.dc.itcs.flow.aop;

import java.util.List;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dc.flamingo.core.utils.DateUtils;
import com.dc.flamingo.core.utils.StrUtils;
import com.dc.flamingo.workflow.FlowContext;
import com.dc.flamingo.workflow.dao.ProcessInstanceDao;
import com.dc.flamingo.workflow.entity.ProcessDefinition;
import com.dc.flamingo.workflow.entity.ProcessInstance;
import com.dc.flamingo.workflow.entity.Task;
import com.dc.itcs.core.base.entity.FlowEntity;
import com.dc.itcs.core.context.UserContext;
import com.dc.itcs.flow.dao.WorkItemDao;
import com.dc.itcs.flow.entity.WorkItem;

/**
 * 工作项处理
 * @author lee
 *
 */
@Component
@Aspect
public class WorkItemAspect {
	@Autowired
	private ProcessInstanceDao processInstanceDao;
	@Autowired
	private WorkItemDao workItemDao;
	
	/**
	 * 打开任务后
	 */
	@AfterReturning(value="execution(com.dc.flamingo.workflow.entity.Task com.dc.flamingo.workflow.TaskService.open(..))",
			argNames="task", returning="task")
	public void afterOpenTask(Task task) {
		WorkItem wi = new WorkItem();
		ProcessInstance process = processInstanceDao.findOne(task.getProcessId());
		ProcessDefinition pd = FlowContext.getPd(process.getPdId());
		FlowEntity flowEntity = (FlowEntity) process.getEntityObject();
		wi.setPdId(process.getPdId());
		wi.setPdName(pd.getName());
		wi.setProcessId(process.getId());
		wi.setEntityClass(process.getEntityClass());
		wi.setEntityId(process.getEntityId());
		wi.setTokenId(task.getTokenId());
		wi.setTaskId(task.getId());
		wi.setTaskType(task.getType());
		wi.setTaskState(task.getState());
		wi.setActionUrl(flowEntity.getApplyUrl());
		wi.setCreateTime(DateUtils.getCurrentDateTimeStr());
		wi.setNodeName(task.getNodeName());
		wi.setNodeDesc(task.getNodeDesc());
		List<Long> actorIdList = task.getActorIdList();
		wi.setActorIds(StrUtils.join(actorIdList, ",","【","】"));
		workItemDao.save(wi);
	}
	/**
	 * 完成任务后
	 */
	@AfterReturning(value="execution(com.dc.flamingo.workflow.entity.Task com.dc.flamingo.workflow.TaskService.close(..))",
			argNames="task", returning="task")
	public void afterCompleteTask(Task task) {
		WorkItem wi = workItemDao.findByTaskId(task.getId());
		wi.setTaskState(task.getState());
		wi.setOperator(UserContext.getCurUser());
		wi.setEndTime(DateUtils.getCurrentDateTimeStr());
		workItemDao.save(wi);
	}

	/**
	 * 转办任务后
	 */
	@AfterReturning(value="execution(* com.dc.flamingo.workflow.TaskService.transfer(..))&&args(taskId,sourceActorId,targetActorId)",
			argNames="taskId,sourceActorId,targetActorId")
	public void afterTransferTask(Long taskId, Long sourceActorId, Long targetActorId) {
		WorkItem wi = workItemDao.findByTaskId(taskId);
		wi.transfer(sourceActorId, targetActorId);
		workItemDao.save(wi);
	}
}
