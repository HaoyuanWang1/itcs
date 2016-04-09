package com.dc.itcs.system.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.dc.flamingo.core.data.IdEntity;

/**
 * 用户消息
 * @author lee
 *
 */
@Entity
@Table(name = "message_user") 
public class UserMessage  extends IdEntity{
	private static final long serialVersionUID = 1470397636382523617L;
	/** 通知类型 */
	public static final int TYPE_NOTICE = 1;
	/** 待办任务类型 */
	public static final int TYPE_TASK_OPEN = 2;
	/** 已办任务类型 */
	public static final int TYPE_TASK_CLOSE = 3;
	/** 未读状态 */
	public static final int READFLAG_TRUE = 1;
	/** 已读状态 */
	public static final int READFLAG_FLASE = 0;
	
	private String content;	//内容
	private String url;		//链接
	private Integer type;	//类型（通知/未办任务/已办任务）
	private Integer readFlag;	//已读标识（0：未读，1：已读）
	private String createTime;	//创建日期
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getReadFlag() {
		return readFlag;
	}
	public void setReadFlag(Integer readFlag) {
		this.readFlag = readFlag;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
}
