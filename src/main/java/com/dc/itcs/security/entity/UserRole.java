package com.dc.itcs.security.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.dc.flamingo.core.data.IdEntity;

@Entity
@Table(name = "ss_user_role") 
public class UserRole extends IdEntity{
	private static final long serialVersionUID = -730151278513769678L;
	private UserInfo user;	//用户
	private RoleInfo role;	//角色
	private String userRoleStr;    //传值
	@ManyToOne
	@JoinColumn(name="user_id")
	public UserInfo getUser() {
		return user;
	}
	public void setUser(UserInfo user) {
		this.user = user;
	}
	@ManyToOne
	@JoinColumn(name="role_id")
	public RoleInfo getRole() {
		return role;
	}
	public void setRole(RoleInfo role) {
		this.role = role;
	}
	@Transient
    public String getUserRoleStr() {
        return userRoleStr;
    }
    public void setUserRoleStr(String userRoleStr) {
        this.userRoleStr = userRoleStr;
    }
}
