package com.dc.itcs.event.domain;

import java.util.List;

import com.dc.flamingo.core.utils.StrUtils;
import com.dc.itcs.security.entity.UserInfo;
import com.google.common.collect.Lists;

/**
 * 待处理事件根据用户分组数据
 * @ClassName: EventUserGroupData
 * @Description: TODO
 * @Create In 2015年8月17日 By lee
 */
public class EventUserGroupData {
	private UserInfo spUser;		//用户
	private Integer unSloveCount;	//未解决数量
	private List<Long> unSloveEventIds = Lists.newArrayList();
	public EventUserGroupData(UserInfo spUser) {
		this.spUser = spUser;
		this.unSloveCount = 0;
	}
	public UserInfo getSpUser() {
		return spUser;
	}
	public void setSpUser(UserInfo spUser) {
		this.spUser = spUser;
	}
	public Integer getUnSloveCount() {
		return unSloveCount;
	}
	public void setUnSloveCount(Integer unSloveCount) {
		this.unSloveCount = unSloveCount;
	}
	public List<Long> getUnSloveEventIds() {
		return unSloveEventIds;
	}
	public void setUnSloveEventIds(List<Long> unSloveEventIds) {
		this.unSloveEventIds = unSloveEventIds;
	}
	public String getUnSloveEventIdStr(){
		return StrUtils.join(unSloveEventIds, ",");
	}
}
