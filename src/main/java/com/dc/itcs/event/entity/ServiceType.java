package com.dc.itcs.event.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.dc.flamingo.core.data.IdEntity;
import com.dc.itcs.core.base.support.ItcsConstants;
import com.dc.itcs.security.entity.Tenant;

/**
 * 服务类型
 * @author luzm1
 */
@Entity
@Table(name = "ss_service_type") 
public class ServiceType extends IdEntity {
	private static final long serialVersionUID = -992465548626610741L;
	private Tenant tenant;				//隶属客户
	private String name;				//名称（中文）
	private Integer state;				//状态(是否可用)

	//获取文本信息
	@Transient
	public String getStateText() {
		if(getState() == ItcsConstants.STATE_OFF){
			return "无效";
		}else if(getState() == ItcsConstants.STATE_ON){
			return "有效";
		}
		return "";
	}
	
	@ManyToOne
	@JoinColumn
	public Tenant getTenant() {
		return tenant;
	}
	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}

}
