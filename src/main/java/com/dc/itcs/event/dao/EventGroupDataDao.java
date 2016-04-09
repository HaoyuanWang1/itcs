package com.dc.itcs.event.dao;

import java.util.List;
import java.util.Set;

import com.dc.itcs.event.domain.EventTenantGroupData;

/**
 * 事件分组查询Dao层，用于没有实体映射的分组对象数据查询
 * @ClassName: EventGroupDataDao
 * @Description: TODO
 * @Create In 2015年8月17日 By lee
 */
public interface EventGroupDataDao {

	List<EventTenantGroupData> findTenantGroupDataByTenantIds(Set<Long> tenantIds);

}
