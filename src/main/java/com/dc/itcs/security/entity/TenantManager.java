package com.dc.itcs.security.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.dc.flamingo.core.data.IdEntity;

@Entity
@Table(name = "t_tenant_manager") 
public class TenantManager extends IdEntity{
	private static final long serialVersionUID = 564715426538358376L;
	private Tenant tenant;				// 客户
	private UserInfo tenantManager;		// 客户经理
	
	@ManyToOne
	@JoinColumn
	public Tenant getTenant() {
		return tenant;
	}
	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}
	
	@ManyToOne
	@JoinColumn
	public UserInfo getTenantManager() {
		return tenantManager;
	}
	public void setTenantManager(UserInfo tenantManager) {
		this.tenantManager = tenantManager;
	}

}
