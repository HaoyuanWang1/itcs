package com.dc.itcs.flow.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.dc.flamingo.core.data.IdEntity;
import com.dc.itcs.security.entity.UserInfo;

/**
 * 流程实例操作记录
 * @author lee
 *
 */
@Entity
@Table(name = "ss_flow_operate_log") 
public class FlowOperateLog extends IdEntity{
	public static String OPERATE_TYPE_AUDIT = "审批";
	public static String OPERATE_TYPE_CANCEL = "撤单";
	public static String OPERATE_TYPE_BACK = "驳回";
	public static String OPERATE_TYPE_TRANSFER = "转办";
	public static String OPERATE_TYPE_ADDSIGN = "加签";
	public static String OPERATE_TYPE_JUMP = "跳转";
	
	private static final long serialVersionUID = -4330423377518658548L;
	private Long processId;		//流程实例ID
	private String nodeDesc;	//节点描述
	private String operateTime;	//记录时间
	private UserInfo operator;	//操作人
	private String operateType;	//操作类型
	private String comment;		//审批意见
	private String action;		//动作
	public Long getProcessId() {
		return processId;
	}
	public void setProcessId(Long processId) {
		this.processId = processId;
	}
	public String getNodeDesc() {
		return nodeDesc;
	}
	public void setNodeDesc(String nodeDesc) {
		this.nodeDesc = nodeDesc;
	}
	public String getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}
	@ManyToOne
	@JoinColumn(name="operator")
	public UserInfo getOperator() {
		return operator;
	}
	public void setOperator(UserInfo operator) {
		this.operator = operator;
	}
	public String getOperateType() {
		return operateType;
	}
	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getAction() {
		return this.action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
}
