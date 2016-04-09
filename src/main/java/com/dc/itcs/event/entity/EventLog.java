package com.dc.itcs.event.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.dc.flamingo.core.data.IdEntity;
import com.dc.flamingo.tools.annotation.FileKeyColumn;
import com.dc.itcs.security.entity.UserInfo;

/**
 * 沟通日志
 * 用于记录客户和服务经理等人的沟通信息
 * @author Administrator
 *
 */
@Entity
@Table(name = "ss_event_log") 
public class EventLog extends IdEntity{

	private static final long serialVersionUID = -7855980864154441524L;
	
	public static final String SUBMIT_TYPE_SERVICE="转至服务经理";		//信息来源：转至服务经理
	public static final String SUBMIT_TYPE_REPLY="回复";		//信息来源：回复
	public static final String SUBMIT_TYPE_SOLVE="已解决";		//信息来源：已解决
	public static final String SUBMIT_TYPE_BACK="驳回";			//信息来源：驳回
	public static final String SUBMIT_TYPE_PASS="通过";			//信息来源：驳回
	
	private Event event;		//隶属事件
	private String context;		//沟通内容
	private String attachment;	//附件
	private UserInfo createUser;//创建人
	private String createTime;	//创建时间
	private String action;     //动作
	
	@ManyToOne
	@JoinColumn
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	@FileKeyColumn
	public String getAttachment() {
		return attachment;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	@ManyToOne
	@JoinColumn
	public UserInfo getCreateUser() {
		return createUser;
	}
	public void setCreateUser(UserInfo createUser) {
		this.createUser = createUser;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
	
}
