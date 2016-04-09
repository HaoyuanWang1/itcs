package com.dc.itcs.event.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dc.flamingo.core.utils.StrUtils;
import com.dc.itcs.event.dao.EventDao;
import com.dc.itcs.event.domain.EventGroupData;
import com.dc.itcs.event.domain.EventTenantGroupData;
import com.dc.itcs.event.domain.EventUserGroupData;
import com.dc.itcs.event.entity.Event;
import com.dc.itcs.security.entity.TenantManager;
import com.dc.itcs.security.entity.UserInfo;
import com.dc.itcs.security.service.TenantManagerService;
import com.dc.itcs.security.service.UserInfoService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * 事件分组查询服务层
 * @ClassName: EventGroupDataService
 * @Description: TODO
 * @Create In 2015年8月17日 By lee
 */
@Service
public class EventGroupDataService {
	@Autowired
	private EventDao eventDao;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private TenantManagerService tenantManagerService;
	/**
	 * 根据用户权限获取可视待解决事件集合信息
	 * 客户经理可以查看其管理所有客户的事件对应集合
	 * 服务经理可以查看其所属服务商组织下所有服务经理正处理的事件对应集合
	 * @Methods Name findEventGroupDataByAuth
	 * @Create In 2015年8月17日 By lee
	 * @param user
	 * @return
	 */
	public EventGroupData findEventGroupDataByAuth(UserInfo user) {
		List<TenantManager> tenantManagers = tenantManagerService.findByManager(user);
		EventGroupData egd = new EventGroupData();
		Set<Event> events = Sets.newHashSet();
		//首先将同服务商所有待解决问题查询
		List<UserInfo> spUsers = userInfoService.findByTenant_id(user.getTenant().getId());
		for(UserInfo spUser : spUsers){
			events.addAll(eventDao.findBySingerIdsContainingAndMainState("【"+spUser.getId()+"】", Event.MAIN_STATE_AUDIT));
		}
		//如果用户是客户经理，增加客户经理权限数据
		if(tenantManagers!=null&&!tenantManagers.isEmpty()){//如果用户是客户经理
			for(TenantManager tm : tenantManagers){
				events.addAll(eventDao.findByTenant_idAndMainState(tm.getTenant().getId(), Event.MAIN_STATE_AUDIT));
			}
			egd.setUserGroupDatas(this.getUserGroupData(events));
		}else{
			egd.setUserGroupDatas(this.getUserGroupData(events,spUsers));
		}
		egd.setTenantGroupDatas(Lists.newArrayList(this.getTenantGroupData(events)));
		return egd;
	}
	
	/**
	 * 根据事件集合组装按客户分组信息
	 * @Methods Name getTenantGroupData
	 * @Create In 2015年8月17日 By lee
	 * @param events
	 * @return
	 */
	private List<EventTenantGroupData> getTenantGroupData(Set<Event> events) {
		Map<Long,EventTenantGroupData> tgdMap = Maps.newHashMap();
		for(Event event : events){
			EventTenantGroupData tgd = tgdMap.get(event.getTenant().getId());
			if(tgd==null){
				tgd = new EventTenantGroupData();
				tgd.setTenant(event.getTenant());
				tgd.setUnSloveCount(0);
				tgdMap.put(event.getTenant().getId(), tgd);
			}
			tgd.setUnSloveCount(tgd.getUnSloveCount()+1);
			tgd.getUnSloveEventIds().add(event.getId());
		}
		return Lists.newArrayList(tgdMap.values());
	}
	/**
	 * 根据事件集合及制定用户组装按处理用户分组信息，为服务经理提供
	 * @Methods Name getUserGroupData
	 * @Create In 2015年8月17日 By lee
	 * @param events
	 * @param spUsers
	 * @return
	 */
	private List<EventUserGroupData> getUserGroupData(Set<Event> events,List<UserInfo> spUsers) {
		Map<Long,EventUserGroupData> ugdMap = Maps.newHashMap();
		List<Long> spUserIds = Lists.newArrayList();
		for(UserInfo spUser : spUsers){
			spUserIds.add(spUser.getId());
		}
		for(Event event : events){
			List<Long> signerIds = StrUtils.splitToLongList(event.getSingerIds(), ",", "【", "】");
			for(Long signerId : signerIds){
				if(spUserIds.contains(signerId)){
					EventUserGroupData ugd = ugdMap.get(signerId);
					if(ugd==null){
						UserInfo spUser = userInfoService.findById(signerId);
						ugd = new EventUserGroupData(spUser);
						ugdMap.put(signerId, ugd);
					}
					ugd.setUnSloveCount(ugd.getUnSloveCount()+1);
					ugd.getUnSloveEventIds().add(event.getId());
				}
				
			}
		}
		return Lists.newArrayList(ugdMap.values());
	}
	/**
	 * 根据事件集合装按处理用户分组信息,为客户经理提供
	 * @Methods Name getUserGroupData
	 * @Create In 2015年8月17日 By lee
	 * @param events
	 * @return
	 */
	private List<EventUserGroupData> getUserGroupData(Set<Event> events) {
		Map<Long,EventUserGroupData> ugdMap = Maps.newHashMap();
		for(Event event : events){
			List<Long> signerIds = StrUtils.splitToLongList(event.getSingerIds(), ",", "【", "】");
			for(Long signerId : signerIds){
				EventUserGroupData ugd = ugdMap.get(signerId);
				if(ugd==null){
					UserInfo spUser = userInfoService.findById(signerId);
					ugd = new EventUserGroupData(spUser);
					ugdMap.put(signerId, ugd);
				}
				ugd.setUnSloveCount(ugd.getUnSloveCount()+1);
				ugd.getUnSloveEventIds().add(event.getId());
			}
		}
		return Lists.newArrayList(ugdMap.values());
	}

}
