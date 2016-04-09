package com.dc.itcs.flow.aop;

import java.util.List;
import java.util.Set;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dc.flamingo.core.utils.DateUtils;
import com.dc.flamingo.core.utils.StrUtils;
import com.dc.flamingo.workflow.FlowContext;
import com.dc.flamingo.workflow.ProcessInstanceService;
import com.dc.flamingo.workflow.TaskService;
import com.dc.flamingo.workflow.core.Execution;
import com.dc.flamingo.workflow.entity.ProcessInstance;
import com.dc.flamingo.workflow.entity.Task;
import com.dc.flamingo.workflow.entity.Token;
import com.dc.itcs.core.base.entity.FlowEntity;
import com.dc.itcs.core.context.UserContext;
import com.dc.itcs.event.entity.Event;
import com.dc.itcs.flow.entity.FlowApply;
import com.dc.itcs.flow.service.FlowApplyService;
import com.dc.itcs.security.entity.UserInfo;
import com.dc.itcs.security.service.UserInfoService;
import com.google.common.collect.Lists;

@Component
@Aspect
@Transactional
public class FlowApplyAspect {
	@Autowired
	private FlowApplyService flowApplyService;
	@Autowired
	private ProcessInstanceService processInstanceService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private UserInfoService userInfoService;
	/**
	 * 监听保存流程表单实体
	 */
	@AfterReturning(value="execution(Object com.dc.flamingo.core.data.BaseDao.save(..))",
			argNames="entity", returning="entity")
	public void afterSaveEntity(Object entity) {
		if(entity instanceof FlowEntity){	//如果是申请单实体
			String applyClass = entity.getClass().getName();
			FlowEntity flowEntity = (FlowEntity) entity;
			Event event = (Event) entity;
			FlowApply flowApply = flowApplyService.findByClassAndId(applyClass,flowEntity.getId());
			if(flowApply==null){
				flowApply = new FlowApply();
				flowApply.setApplyClass(entity.getClass().getName());
				flowApply.setApplyId(flowEntity.getId());
			}
			if(flowApply.getCreateUser()==null){
				flowApply.setCreateUser(UserContext.getCurUser());
			}
			if(StrUtils.isEmpty(flowApply.getCreateTime())){
				flowApply.setCreateTime(DateUtils.getCurrentDateTimeStr());
			}
			if(flowApply.getState()==null){
				flowApply.setState(FlowEntity.STATE_DRAFT);
			}else if(flowEntity.getState()!=FlowEntity.STATE_DRAFT
					&&flowEntity.getState()!=FlowEntity.STATE_AUDIT){	//新提交或审批中由于内存对象更新不及时有问题
				flowApply.setState(flowEntity.getState());
			}
			flowApply.setApplyNum(flowEntity.getApplyNum());
			flowApply.setApplyName(flowEntity.getApplyName());
			flowApply.setFlowDesc(flowEntity.getFlowDesc());
			flowApply.setApplyUrl(flowEntity.getApplyUrl());
			flowApply.setDeleteUrl(flowEntity.getDeleteUrl());
			flowApply.setSlaBeginTime(event.getSubmitTime());
			flowApply.setSlaEndTime(event.getSolveTime());
			flowApplyService.save(flowApply);
		}
	}
	
	/**
	 * 创建流程后
	 * @throws ClassNotFoundException 
	 */
	@AfterReturning(value="execution(com.dc.flamingo.workflow.entity.ProcessInstance com.dc.flamingo.workflow.ProcessInstanceService.createProcess(..))",
			argNames="process", returning="process")
	public void afterStartProcess(ProcessInstance process){
		Long entityId = process.getEntityId();
		String entityClass = process.getEntityClass();
		FlowApply flowApply = flowApplyService.findByClassAndId(entityClass,entityId);
		flowApply.setState(FlowEntity.STATE_AUDIT);
		flowApply.setNodeTime(DateUtils.getCurrentDateTimeStr());
		flowApply.setNodeName("start");
		flowApply.setNodeDesc("提交");
		flowApply.setProcessId(process.getId());
		if(flowApply.getCreateUser()==null){
			flowApply.setCreateUser(UserContext.getCurUser());
		}
		flowApply.setCreateTime(DateUtils.getCurrentDateTimeStr());
		flowApplyService.save(flowApply);
	}
	/**
	 * 流程实例关闭后
	 * @param process
	 * @param flowOperate
	 */
	@AfterReturning(value="execution(com.dc.flamingo.workflow.entity.ProcessInstance com.dc.flamingo.workflow.ProcessInstanceService.close(..))&&args(*,flowOperate)",
			argNames="process,flowOperate", returning="process")
	public void afterCloseProcess(ProcessInstance process,int flowOperate){
		Long entityId = process.getEntityId();
		String entityClass = process.getEntityClass();
		
		int state = 0;
		switch(flowOperate){
			case FlowContext.OPERATE_AUDIT : state = FlowEntity.STATE_FINISH; break;
			case FlowContext.OPERATE_BACK : state = FlowEntity.STATE_BACK; break;
			case FlowContext.OPERATE_CANCEL : state = FlowEntity.STATE_CANCEL; break;
			case FlowContext.OPERATE_STOP : state = FlowEntity.STATE_STOP; break;
			case FlowContext.OPERATE_JUMP : state = FlowEntity.STATE_BACK; break;
		}
		FlowApply flowApply = flowApplyService.findByClassAndId(entityClass,entityId);
		flowApply.setSigners(null);
		flowApply.setSignerIds(null);
		flowApply.setState(state);
		flowApplyService.save(flowApply);
	}
	/**
	 * 传递令牌后更新环节状态
	 * @param token
	 */
	@AfterReturning(value="execution(com.dc.flamingo.workflow.entity.Token com.dc.flamingo.workflow.TokenService.passToken(..))",
			argNames="token", returning="token")
	public void afterpassToken(Token token){
		ProcessInstance process = processInstanceService.findById(token.getProcessId());
		Long entityId = process.getEntityId();
		String entityClass = process.getEntityClass();

		FlowApply flowApply = flowApplyService.findByClassAndId(entityClass,entityId);
		flowApply.setNodeTime(DateUtils.getCurrentDateTimeStr());
		flowApply.setNodeName(token.getNodeName());
		flowApply.setNodeDesc(token.getNodeDesc());
		flowApplyService.save(flowApply);
	}
	@AfterReturning(value="execution(com.dc.flamingo.workflow.entity.Task com.dc.flamingo.workflow.TaskService.open(..))&&args(execution,*)",
			argNames="execution,task", returning="task")
	public void afterOpenTask(Execution execution,Task task){
		ProcessInstance process = execution.getProcess();
		Long entityId = process.getEntityId();
		String entityClass = process.getEntityClass();
		
		Set<Long> actorIds = taskService.findTaskActorsByProcessId(task.getProcessId());
		List<UserInfo> list = userInfoService.findByIds(actorIds);
		List<String> signerTextList = Lists.newArrayList();
		List<String> signerIdList = Lists.newArrayList();
		for(UserInfo user : list){
			signerTextList.add(user.getUserText());
			signerIdList.add(user.getId().toString());
		}
		FlowApply flowApply = flowApplyService.findByClassAndId(entityClass,entityId);
		flowApply.setNodeDesc(task.getNodeDesc());
		flowApply.setNodeTime(DateUtils.getCurrentDateTimeStr());
		flowApply.setSigners(StrUtils.join(signerTextList, ","));
		flowApply.setSignerIds(StrUtils.join(signerIdList, ",", "【", "】"));
		flowApplyService.save(flowApply);
	}
	@AfterReturning(value="execution(com.dc.flamingo.workflow.entity.Task com.dc.flamingo.workflow.TaskService.close(..))&&args(execution,*,*)",
			argNames="execution,task", returning="task")
	public void afterCloseTask(Execution execution,Task task){
		ProcessInstance process = execution.getProcess();
		Long entityId = process.getEntityId();
		String entityClass = process.getEntityClass();
		
		Set<Long> actorIds = taskService.findTaskActorsByProcessId(task.getProcessId());
		List<UserInfo> list = userInfoService.findByIds(actorIds);
		List<String> signerTextList = Lists.newArrayList();
		List<String> signerIdList = Lists.newArrayList();
		for(UserInfo user : list){
			signerTextList.add(user.getUserText());
			signerIdList.add(user.getId().toString());
		}
		FlowApply flowApply = flowApplyService.findByClassAndId(entityClass,entityId);
		flowApply.setSigners(StrUtils.join(signerTextList, ","));
		flowApply.setSignerIds(StrUtils.join(signerIdList, ",", "【", "】"));
		flowApply.addOperators(task.getOperatorId());
		flowApplyService.save(flowApply);
	}
	@AfterReturning(value="execution(* com.dc.flamingo.workflow.TaskService.transfer(..))&&args(execution,taskId,*,*)",
			argNames="execution,taskId")
	public void afterTransferTask(Execution execution,Long taskId){
		ProcessInstance process = execution.getProcess();
		Long entityId = process.getEntityId();
		String entityClass = process.getEntityClass();
		
		Set<Long> actorIds = taskService.findTaskActorsByProcessId(process.getId());
		List<UserInfo> list = userInfoService.findByIds(actorIds);
		List<String> signerTextList = Lists.newArrayList();
		List<String> signerIdList = Lists.newArrayList();
		for(UserInfo user : list){
			signerTextList.add(user.getUserText());
			signerIdList.add(user.getId().toString());
		}
		FlowApply flowApply = flowApplyService.findByClassAndId(entityClass,entityId);
		flowApply.setSigners(StrUtils.join(signerTextList, ","));
		flowApply.setSignerIds(StrUtils.join(signerIdList, ",", "【", "】"));
		flowApplyService.save(flowApply);
	}

}
