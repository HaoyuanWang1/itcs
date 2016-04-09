package com.dc.itcs.security.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.dc.flamingo.core.data.IdEntity;

/**
 * 资源
 * @author lee
 *
 */
@Entity
@Table(name = "ss_resource") 
public class Resource extends IdEntity{
	private static final long serialVersionUID = -5064018692683391175L;
	public static final int TYPE_ANON = 0;	//无权限
	public static final int TYPE_USER = 1;	//需登录
	public static final int TYPE_ROLE = 2;	//需角色
	public static final String NOPAGE = "#";//此项无页面（为存菜单准备）
	private String name;		//名称
	private String pageUrl;		//链接地址
	private Long parentId;		//父级权限项
	private Integer menuItem;	//菜单项标识
	private Integer type;		//类型（公共、用户、角色）
	private Integer orderNum;	//排序号

	@Column(nullable=false,length=100)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(nullable=false,length=200)
	public String getPageUrl() {
		return pageUrl;
	}
	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	@Column(nullable=false)
	public Integer getMenuItem() {
		return menuItem;
	}
	public void setMenuItem(Integer menuItem) {
		this.menuItem = menuItem;
	}
	@Column(nullable=false)
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	@Transient
	public boolean isNoPage(){
		return NOPAGE.equals(pageUrl);
	}
}
