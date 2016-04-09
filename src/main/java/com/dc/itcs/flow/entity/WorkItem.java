package com.dc.itcs.flow.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.dc.flamingo.core.data.IdEntity;
import com.dc.flamingo.core.utils.StrUtils;
import com.dc.flamingo.workflow.entity.Task;
import com.dc.itcs.security.entity.UserInfo;
import com.google.common.collect.Lists;

/**
 * 流程工作项
 * @author lee
 *
 */
@Entity
@Table(name = "ss_flow_work_item") 
public class WorkItem extends IdEntity{
	private static final long serialVersionUID = 5229161079995074309L;
	
	private Long pdId;			//流程定义ID
	private String pdName;		//流程实例名称
	private Long processId;		//流程实例ID
	private Long entityId;		//关联实体ID
	private String entityClass;	//关联实体类
	private Long tokenId;		//令牌Id
	private Long taskId;		//任务ID
	private String actionUrl;	//任务对应URL
	private String createTime;	//创建时间
	private String endTime;		//关闭时间
	private String nodeName;	//节点名称
	private String nodeDesc;	//节点描述
	private String actorIds;	//任务参与者ID
	private List<UserInfo> actors;	//任务参与者
	private Object entity;		//业务实体
	private UserInfo operator;	//审批人
	private Integer taskType;	//类型
	private Integer taskState;	//状态
	public Long getPdId() {
		return pdId;
	}
	public void setPdId(Long pdId) {
		this.pdId = pdId;
	}
	public String getPdName() {
		return pdName;
	}
	public void setPdName(String pdName) {
		this.pdName = pdName;
	}
	public Long getProcessId() {
		return processId;
	}
	public void setProcessId(Long processId) {
		this.processId = processId;
	}
	public Long getTokenId() {
		return tokenId;
	}
	public void setTokenId(Long tokenId) {
		this.tokenId = tokenId;
	}
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public String getActionUrl() {
		return actionUrl;
	}
	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getNodeDesc() {
		return nodeDesc;
	}
	public void setNodeDesc(String nodeDesc) {
		this.nodeDesc = nodeDesc;
	}
	@Transient
	public List<UserInfo> getActors() {
		return actors;
	}
	public void setActors(List<UserInfo> actors) {
		this.actors = actors;
	}
	public String getActorIds() {
		return actorIds;
	}
	@Transient
	public List<Long> getActorIdList(){
		List<String> list = StrUtils.splitToList(actorIds, ",", "【", "】");
		List<Long> actorIdList = Lists.newArrayList();
		for(String actorIdStr : list){
			actorIdList.add(Long.valueOf(actorIdStr));
		}
		return actorIdList;
	}
	public void setActorIds(String actorIds) {
		this.actorIds = actorIds;
	}
	public void setActorIdList(List<Long> actorIdList){
		this.actorIds = StrUtils.join(actorIdList, ",", "【", "】");
	}
	public Long getEntityId() {
		return entityId;
	}
	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}
	public String getEntityClass() {
		return entityClass;
	}
	public void setEntityClass(String entityClass) {
		this.entityClass = entityClass;
	}
	@Transient
	public Object getEntity() {
		return entity;
	}
	public void setEntity(Object entity) {
		this.entity = entity;
	}
	public Integer getTaskType() {
		return taskType;
	}
	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}
	public Integer getTaskState() {
		return taskState;
	}
	public void setTaskState(Integer taskState) {
		this.taskState = taskState;
	}
	@ManyToOne
	@JoinColumn(name="operator")
	public UserInfo getOperator() {
		return operator;
	}
	public void setOperator(UserInfo operator) {
		this.operator = operator;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	@Transient
	public String getStateText() {
		if(taskState==Task.STATE_OPEN){
			return "待处理";
		}
		if(taskState==Task.STATE_WAIT){
			return "待加签响应";
		}
		if(taskState==Task.STATE_CLOSE){
			return "已处理";
		}
		return "";
	}
	public void transfer(Long sourceActorId, Long targetActorId){
		this.actorIds = actorIds.replaceAll("【"+sourceActorId+"】", "【"+targetActorId+"】");
	}
}
