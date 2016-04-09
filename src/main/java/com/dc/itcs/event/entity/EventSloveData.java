package com.dc.itcs.event.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.dc.flamingo.core.data.IdEntity;
import com.dc.itcs.security.entity.Tenant;

/**
 * 待解决信息实体记录
 * @author Administrator
 *
 */
@Entity
public class EventSloveData extends IdEntity {
	private static final long serialVersionUID = 5716227589480306850L;
	private Tenant tenant;	//客户
	private Long dclCount;	//待解决条数
	
	public EventSloveData(){}

	public EventSloveData(Long id,Tenant tenant,Long dclCount){
		this.id = id;
		this.tenant = tenant;
		this.dclCount = dclCount; 
	}

	@ManyToOne
	@JoinColumn
	public Tenant getTenant() {
		return tenant;
	}
	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}
	
	public Long getDclCount() {
		return dclCount;
	}
	public void setDclCount(Long dclCount) {
		this.dclCount = dclCount;
	}
	
	//获取客户名称
	@Transient
	public String getDealTenantNameText() {
		return tenant.getName();
	}

	
}
