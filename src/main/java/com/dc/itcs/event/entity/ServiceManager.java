package com.dc.itcs.event.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.dc.flamingo.core.data.IdEntity;
import com.dc.itcs.security.entity.UserInfo;

/**
 * 服务经理
 * @author Administrator
 *
 */
@Entity
@Table(name = "ss_service_manager") 
public class ServiceManager extends IdEntity {
	private static final long serialVersionUID = -2252663899756875610L;
	private ServiceType type;
	private UserInfo userInfo;
	
	@ManyToOne
	@JoinColumn
	public ServiceType getType() {
		return type;
	}

	public void setType(ServiceType type) {
		this.type = type;
	}
	
	@ManyToOne
	@JoinColumn
	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	
}