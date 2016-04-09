package com.dc.itcs.security.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dc.itcs.security.entity.Resource;

@SuppressWarnings("serial")
public class ResourceNode implements Serializable{
	public static final long ROOT_ID = 0;
	private Long id;				//ID
	private String text;			//节点名称
	private String url;				//对应URL
	private Long parentItem;		//父菜单项
	private int orderNum=0;			//排序
	private int parentFlag=0; 		//是否是父级目录菜单
	private int publicFlag=0;		//是否是共用菜单
	private Integer menuItem;		//菜单项标识
	private boolean open=false;     //是否展开
	private String iconSkin = "";		//图标
	private List<ResourceNode> children = new ArrayList<ResourceNode>();
	public ResourceNode() {
	}
	public ResourceNode(Resource r) {
		this.id = r.getId();
		this.url = r.getPageUrl();
		this.parentItem = r.getParentId();
		this.orderNum = r.getOrderNum();
		this.text = r.getName();
		if(r.getMenuItem()==1){
			this.iconSkin = "menuIcon";
		}else{
			this.iconSkin = "linkIcon";
		}
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Long getParentItem() {
		return parentItem;
	}
	public void setParentItem(Long parentItem) {
		this.parentItem = parentItem;
	}
	public int getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}
	public int getParentFlag() {
		return parentFlag;
	}
	public void setParentFlag(int parentFlag) {
		this.parentFlag = parentFlag;
	}
	public int getPublicFlag() {
		return publicFlag;
	}
	public void setPublicFlag(int publicFlag) {
		this.publicFlag = publicFlag;
	}
	public Integer getMenuItem() {
		return menuItem;
	}
	public void setMenuItem(Integer menuItem) {
		this.menuItem = menuItem;
	}
	public boolean isOpen() {
		return open;
	}
	public void setOpen(boolean open) {
		this.open = open;
	}
	public List<ResourceNode> getChildren() {
		return children;
	}
	public void setChildren(List<ResourceNode> children) {
		this.children = children;
	}
	public String getIconSkin() {
		return iconSkin;
	}
	public void setIconSkin(String iconSkin) {
		this.iconSkin = iconSkin;
	}
	public String getFullName(){
		return this.text+"["+this.url+"]";
	}
	public static ResourceNode getRootNode() {
		ResourceNode root = new ResourceNode();
    	root.setId(ROOT_ID);
    	root.setOpen(true);
    	root.setText("根权限");
    	root.setParentFlag(1);
    	root.setIconSkin("menuIcon");
		return root;
	}
}
