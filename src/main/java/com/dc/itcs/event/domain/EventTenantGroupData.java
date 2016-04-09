package com.dc.itcs.event.domain;

import java.util.List;

import com.dc.flamingo.core.utils.StrUtils;
import com.dc.itcs.security.entity.Tenant;
import com.google.common.collect.Lists;

/**
 * 待处理事件根据客户分组数据
 * @ClassName: EventTenantGroupData
 * @Description: TODO
 * @Create In 2015年8月17日 By lee
 */
public class EventTenantGroupData {
	private Tenant tenant;		//客户
	private Integer unSloveCount;	//未解决数量
	private List<Long> unSloveEventIds = Lists.newArrayList();
	public Tenant getTenant() {
		return tenant;
	}
	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
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
