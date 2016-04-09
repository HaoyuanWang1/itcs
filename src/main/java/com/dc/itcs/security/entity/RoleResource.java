package com.dc.itcs.security.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.dc.flamingo.core.data.IdEntity;

@Entity
@Table(name = "ss_role_resource")
public class RoleResource extends IdEntity{
	private static final long serialVersionUID = 7352229649267533262L;
	private RoleInfo role;	//角色
	private Resource resource;	//权限

	@ManyToOne
	@JoinColumn(name="role")
	public RoleInfo getRole() {
		return role;
	}
	public void setRole(RoleInfo role) {
		this.role = role;
	}
	@ManyToOne
	@JoinColumn(name="resource")
	public Resource getResource() {
		return resource;
	}
	public void setResource(Resource resource) {
		this.resource = resource;
	}
	
}
