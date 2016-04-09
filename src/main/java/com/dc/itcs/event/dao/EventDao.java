package com.dc.itcs.event.dao;

import java.util.List;

import com.dc.flamingo.core.data.BaseDao;
import com.dc.itcs.event.entity.Event;

public interface EventDao extends BaseDao<Event,Long>{
	Event findByCreateUser_Id(Long id);
	Event findByCode(String code);
	List<Event> findByTenant_idAndMainState(Long tenantId, Integer mainState);
	/**
	 * 根据当前处理人和主状态查询事件集合
	 * @Methods Name findBySingerIdsContainingAndMainState
	 * @Create In 2015年8月17日 By lee
	 * @param singerIdStr
	 * @param mainState
	 * @return
	 */
	List<Event> findBySingerIdsContainingAndMainState(String singerIdStr, Integer mainState);
}
