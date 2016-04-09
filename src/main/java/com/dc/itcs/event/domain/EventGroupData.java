package com.dc.itcs.event.domain;

import java.util.List;

/**
 * 事件查询分组数据
 * @ClassName: EventGroupData
 * @Description: TODO
 * @Create In 2015年8月17日 By lee
 */
public class EventGroupData {
	private List<EventTenantGroupData> tenantGroupDatas;
	private List<EventUserGroupData> userGroupDatas;
	public List<EventTenantGroupData> getTenantGroupDatas() {
		return tenantGroupDatas;
	}
	public void setTenantGroupDatas(List<EventTenantGroupData> tenantGroupDatas) {
		this.tenantGroupDatas = tenantGroupDatas;
	}
	public List<EventUserGroupData> getUserGroupDatas() {
		return userGroupDatas;
	}
	public void setUserGroupDatas(List<EventUserGroupData> userGroupDatas) {
		this.userGroupDatas = userGroupDatas;
	}

}
