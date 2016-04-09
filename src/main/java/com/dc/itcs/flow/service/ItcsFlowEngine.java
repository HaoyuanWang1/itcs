package com.dc.itcs.flow.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.flamingo.core.helper.AssertHelper;
import com.dc.flamingo.core.utils.DateUtils;
import com.dc.flamingo.workflow.FlowContext;
import com.dc.flamingo.workflow.FlowEngine;
import com.dc.flamingo.workflow.ProcessInstanceService;
import com.dc.flamingo.workflow.TaskService;
import com.dc.flamingo.workflow.element.Node;
import com.dc.flamingo.workflow.element.TaskNode;
import com.dc.flamingo.workflow.entity.ProcessInstance;
import com.dc.flamingo.workflow.entity.ProcessDefinition;
import com.dc.flamingo.workflow.entity.Task;
import com.dc.itcs.core.base.entity.FlowEntity;
import com.dc.itcs.core.context.UserContext;
import com.dc.itcs.flow.FlowAuth;
import com.dc.itcs.flow.entity.WorkItem;
import com.dc.itcs.security.entity.UserInfo;

@Service
@Transactional(readOnly=true)
public class ItcsFlowEngine {
	@Autowired
	private FlowEngine flowEngine;
	@Autowired
	private ProcessInstanceService processInstanceService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private WorkItemService workItemService;
	@Autowired
	private FlowOperateLogService flowOperateLogService;
	/**
	 * 启动流程
	 * @param flowEntity
	 * @param creator
	 * @param args
	 * @return
	 */
	@Transactional
	public <T> ProcessInstance startProcess(FlowEntity flowEntity, Long creator, Map<String, Object> args) {
		if(args == null) args = new HashMap<String, Object>();
		AssertHelper.notNull(flowEntity, "启动流程传入申请实体不能为NULL");
		ProcessInstance process = flowEngine.startProcess(flowEntity.getPdName(), creator, 
				flowEntity.getClass(), flowEntity.getId(), args, "提交", "");
		flowOperateLogService.saveOperateLog(process.getId(), "提交申请", process.getStartTime(), new UserInfo(creator), "提交", "");
		return process;
	}
	
	
	
	/**
	 * 启动流程
	 * @param flowEntity
	 * @param creator
	 * @param args
	 * @return
	 */
	@Transactional
	public <T> ProcessInstance startProcess(FlowEntity flowEntity, Long creator, Map<String, Object> args, String action,String comment) {
		if(args == null) args = new HashMap<String, Object>();
		AssertHelper.notNull(flowEntity, "启动流程传入申请实体不能为NULL");
		ProcessInstance process = flowEngine.startProcess(flowEntity.getPdName(), creator, 
				flowEntity.getClass(), flowEntity.getId(), args, action, comment);
		flowOperateLogService.saveOperateLog(process.getId(), "提交申请", process.getStartTime(), new UserInfo(creator), "提交", "");
		return process;
	}
	/**
	 * 启动流程,并跳转至目标环节
	 * @Methods Name startProcess
	 * @Create In 2014年11月6日 By lee
	 * @param flowEntity
	 * @param creator
	 * @param args
	 * @param node
	 * @return
	 */
	@Transactional
	public <T> ProcessInstance startProcess(FlowEntity flowEntity, Long creator, Map<String, Object> args, String node, String action,String comment) {
		if(args == null) args = new HashMap<String, Object>();
		AssertHelper.notNull(flowEntity, "启动流程传入申请实体不能为NULL");
		ProcessInstance process = flowEngine.startProcess(flowEntity.getPdName(), creator, 
				flowEntity.getClass(), flowEntity.getId(), args, node, action, comment);
		flowOperateLogService.saveOperateLog(process.getId(), "提交申请", process.getStartTime(), new UserInfo(creator), "提交", "");
		return process;
	}
	/**
	 * 获取流程权限
	 * @param processId
	 * @param user
	 * @return
	 */
	public FlowAuth findFlowAuth(Long processId, UserInfo user) {
		FlowAuth flowAuth = new FlowAuth();
		ProcessInstance process = processInstanceService.findById(processId);
		if(user.getId().equals(process.getCreatorId())){
			flowAuth.setFlowCreator(true);
		}
		List<WorkItem> workItems = workItemService.findForAuditUser(processId, user);
		if(!workItems.isEmpty()){
			WorkItem workItem = workItems.get(0);
			flowAuth.setFlowSinger(true);
			
			ProcessDefinition pd = FlowContext.getPd(process.getPdId());
//			flowAuth.setBackNodes(flowEngine.taskBackNode(workItem.getTaskId()));
			TaskNode taskNode = (TaskNode) pd.getNode(workItem.getNodeName());
			flowAuth.setProcessId(processId);
			flowAuth.setTokenId(workItem.getTokenId());
			flowAuth.setTaskId(workItem.getTaskId());
			flowAuth.setNodeName(taskNode.getName());
			flowAuth.setNodeDesc(taskNode.getDesc());
			
			flowAuth.setCanSign(true);
			flowAuth.setCanBack(taskNode.getBack()==1);
//			if(Task.TYPE_ADD==workItem.getTaskType()){	//如果是加签任务
//				flowAuth.setCanAddSign(false);	//加签任务不允许再加签
//			/*	flowAuth.setIsAddSignTask(true);*/
//			}else{	//如果不是加签任务
				flowAuth.setCanAddSign(taskNode.getAddSign()==1);
//			}
			flowAuth.setCanTransfer(taskNode.getTransfer()==1);
		}
		return flowAuth;
	}
	/**
	 * 审批任务
	 * @param taskId
	 * @param operatorId
	 * @param comment
	 */
	@Transactional
	public void executeTask(Long taskId, Long operatorId, String action,String comment) {
		Task task = taskService.findById(taskId);
		flowOperateLogService.saveOperateLog(task.getProcessId(), task.getNodeDesc(), DateUtils.getCurrentDateTimeStr(), new UserInfo(operatorId), action, comment);
		flowEngine.executeTask(taskId, operatorId, action, comment);
	}
	
	
	/**
	 * 根据任务撤单
	 * @param taskId
	 * @param operatorId
	 * @param comment
	 */
	@Transactional
	public void cancelProcess(Long taskId, Long operatorId, String action,String comment) {
		Task task = taskService.findById(taskId);
		flowOperateLogService.saveOperateLog(task.getProcessId(), task.getNodeDesc(), DateUtils.getCurrentDateTimeStr(), new UserInfo(operatorId), action, comment);
		flowEngine.cancelProcess(task.getProcessId(), UserContext.getCurUser().getId(), action, comment);
	}
	/**
	 * 驳回任务
	 * @param taskId
	 * @param backNode
	 * @param operatorId
	 * @param comment
	 */
	@Transactional
	public void backExecuteTask(Long taskId, String backNode, Long operatorId, String action,String comment) {
		Task task = taskService.findById(taskId);
		flowOperateLogService.saveOperateLog(task.getProcessId(), task.getNodeDesc(), DateUtils.getCurrentDateTimeStr(), new UserInfo(operatorId), action, comment);
        flowEngine.backExecuteTask(taskId, backNode, UserContext.getCurUser().getId(), action, comment);
	}
	/**
	 * 任务转办
	 * @param taskId
	 * @param sourceUser
	 * @param transferUser
	 */
	@Transactional
	public void transfer(Long taskId, UserInfo sourceUser, UserInfo transferUser, String action,String comment) {
		Task task = taskService.findById(taskId);
		flowOperateLogService.saveOperateLog(task.getProcessId(), task.getNodeDesc(), DateUtils.getCurrentDateTimeStr(), UserContext.getCurUser(), action, comment);
		flowEngine.transfer(taskId, UserContext.getCurUser().getId(),sourceUser.getId(), transferUser.getId());
	}
	/**
	 * 加签
	 * @param taskId
	 * @param addSignType
	 * @param addSignUser
	 * @param operator
	 */
	@Transactional
	public void addSign(Long taskId, UserInfo addSignUser, UserInfo operator, String action,String comment) {
		Task task = taskService.findById(taskId);
		flowOperateLogService.saveOperateLog(task.getProcessId(), task.getNodeDesc(), DateUtils.getCurrentDateTimeStr(), UserContext.getCurUser(), action, comment);
		flowEngine.addSign(taskId, UserContext.getCurUser().getId(), addSignUser.getId());
	}
	/**
	 * 自由跳转
	 * @param taskId
	 * @param targetNode
	 * @param operator
	 * @param comment
	 */
	@Transactional
	public void freeJump(Long taskId, String targetNode, UserInfo operator, String action,String comment) {
		Task task = taskService.findById(taskId);
		flowOperateLogService.saveOperateLog(task.getProcessId(), task.getNodeDesc(), DateUtils.getCurrentDateTimeStr(), UserContext.getCurUser(), action, comment);
		flowEngine.freeJump(taskId, targetNode, operator.getId(), action, comment);
	}
	/**
	 * 根据流程实例ID获取当前活动任务集合
	 * @Methods Name findCurProcessActiveTasks
	 * @Create In 2014年11月19日 By lee
	 * @param processId
	 * @return
	 */
	public List<Task> findCurProcessActiveTasks(Long processId) {
		return taskService.findActiveTaskByProcessId(processId);
	}
	/**
	 * 驳回环节
	 * @Methods Name taskBackNode
	 * @Create In 2014年12月16日 By lee
	 * @param taskId
	 * @return
	 */
	public List<Node> taskBackNode(Long taskId){
		return flowEngine.taskBackNode(taskId);
	}
}
