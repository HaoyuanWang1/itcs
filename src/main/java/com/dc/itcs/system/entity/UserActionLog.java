package com.dc.itcs.system.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.dc.flamingo.core.data.IdEntity;
import com.dc.flamingo.core.utils.DateUtils;
import com.dc.itcs.security.entity.UserInfo;

@Entity
@Table(name = "system_log_useraction") 
public class UserActionLog extends IdEntity{
	private static final long serialVersionUID = -4082812338742931526L;
	private String uid;
	private String userName;
	private String actionUrl;
	private String actionTime;
	public UserActionLog(UserInfo userinfo, String url) {
		if(userinfo!=null){
			this.uid = userinfo.getUid();
			this.userName = userinfo.getUserName();
		}
		this.actionUrl = url;
		this.actionTime = DateUtils.getCurrentDateTimeStr();
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getActionUrl() {
		return actionUrl;
	}
	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}
	public String getActionTime() {
		return actionTime;
	}
	public void setActionTime(String actionTime) {
		this.actionTime = actionTime;
	}
	
}
