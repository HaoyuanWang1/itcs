package com.dc.itcs.security.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.dc.flamingo.core.data.IdEntity;

@Entity
@Table(name = "ss_role") 
public class RoleInfo extends IdEntity{
	private static final long serialVersionUID = -3040190054032341166L;
	private String rid;
	private String name;

	@Column(length=100 ,nullable=false)
	public String getRid() {
		return rid;
	}
	public void setRid(String rid) {
		this.rid = rid;
	}
	@Column(length=100 ,nullable=false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
