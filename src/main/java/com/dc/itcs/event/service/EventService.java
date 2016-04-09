package com.dc.itcs.event.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.flamingo.core.data.Criteria;
import com.dc.flamingo.core.data.Criterion;
import com.dc.flamingo.core.data.Restrictions;
import com.dc.flamingo.core.service.BaseService;
import com.dc.flamingo.core.support.PageParam;
import com.dc.flamingo.core.utils.StrUtils;
import com.dc.itcs.core.base.support.IdGetter;
import com.dc.itcs.core.base.support.ItcsConstants;
import com.dc.itcs.event.dao.EventDao;
import com.dc.itcs.event.entity.Event;
import com.dc.itcs.security.entity.TenantManager;
import com.dc.itcs.security.entity.UserInfo;
import com.dc.itcs.security.service.TenantManagerService;
import com.dc.itcs.security.service.UserInfoService;

@Service("eventService")
@Transactional(readOnly=true)
public class EventService extends BaseService {
	@Autowired
	private EventDao eventDao;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private TenantManagerService tenantManagerService;
	
	
	/**
	 * 查询客户已提交的问题
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
    public Page<Event> findApplyPage(PageParam pageParam, Map<String, Object> paramMap) {
        return findApply(pageParam,paramMap,null);
    }
    
    /**
     * 查询客户待确认的问题
     * @param pageParam
     * @param paramMap
     * @return
     */
    public Page<Event> findApplyUserPage(PageParam pageParam, Map<String, Object> paramMap) {
        return findApply(pageParam,paramMap,ItcsConstants.STATE_ON);
    }
    /**
     * 查询客户解决中的问题
     * @param pageParam
     * @param paramMap
     * @return
     */
    public Page<Event> findApplyEventPage(PageParam pageParam, Map<String, Object> paramMap) {
       return findApply(pageParam,paramMap,ItcsConstants.STATE_OFF);
    }
    
	/**
	 * 精简分页查询方法
	 * @param pageParam
	 * @param paramMap
	 * @param state
	 * @return
	 */
	public Page<Event> findApply(PageParam pageParam, Map<String, Object> paramMap,Integer state)
	{
		/**公共部分*/
		Criteria<Event> c = super.getCriteria(paramMap, Event.class);
	    pageParam.setSort(new Sort(Direction.DESC, "id"));
	    /**不同区域*/
		if(state == null){
			return eventDao.findAll(c, pageParam);
		}
		if(state == ItcsConstants.STATE_OFF){
			c.add(Restrictions.eq("mainState", ItcsConstants.STATE_OFF, true));
			return eventDao.findAll(c, pageParam);
		}
		if(state == ItcsConstants.STATE_ON){
			c.add(Restrictions.eq("mainState", ItcsConstants.STATE_ON, true));
			return eventDao.findAll(c, pageParam);
		}
		return null;
	}
	
    /**
     * 查询客户待确认的问题 为定时任务准备数据
     * @return
     */
	public List<Event> findApplyUserList() {
		Criteria<Event> c = super.getCriteria(Event.class);
		c.add(Restrictions.eq("mainState", ItcsConstants.STATE_ON,  true));
		return eventDao.findAll(c);
	}
	
	/**
	 * 保存Event实体
	 * @param event
	 * @return
	 */
    @Transactional
    public Event eventSave(Event event){
    	if (StrUtils.isEmpty(event.getCode())) {
    		event.setCode(IdGetter.userApply.getNum());
		}
    	return eventDao.save(event);
    }
    
    /**
     * 用ID查找Event
     * @param id
     * @return
     */
    public Event findById(Long id){
    	return eventDao.findOne(id);
    }

    /**
     * 查找所有的Event
     * @return
     */
    public List<Event> findAll(){
    	return eventDao.findAll();
    }
    
    /**
     * 用客户ID和状态查找Event
     * @param tenantId
     * @param mainState
     * @return
     */
    public List<Event> findByTenant_idAndMainState(Long tenantId,Integer mainState){
    	return eventDao.findByTenant_idAndMainState(tenantId,mainState);
    }
    
    /**
     * 删除Event
     * @param id
     */
    public void deleteEvent(Long id){
    	eventDao.delete(id);
    }

    /**
     * 根据创建人查找Event
     * @param id
     * @return
     */
	public Event findByCreateUser_Id(Long id) {
		return eventDao.findByCreateUser_Id(id);
	}
	
	/**
	 * 根据编码查找Event
	 * @param code
	 * @return
	 */
	public Event findByCode(String code) {
		return eventDao.findByCode(code);
	}
	
	/**
	 * 导出客户反馈清单
	 * @param 
	 * @return
	 */
	public List<Event> exportUserItem(Map<String, Object> param) {
		Criteria<Event> c = super.getCriteria(param,Event.class);
		return eventDao.findAll(c);
	}

	/**
	 * 查找审批状态下的非预警的所有的Event
	 * @return
	 */
	public List<Event> findEventForWarning() {
		Criteria<Event> c = super.getCriteria(Event.class);
		c.add(Restrictions.eq("state", Event.STATE_AUDIT, true));
		c.add(Restrictions.ne("isWarningFlag", ItcsConstants.IS_YES, true));
		return eventDao.findAll(c);
	}

	/**
	 * 查找审批状态下的非超期的所有的Event
	 * @return
	 */
	public List<Event> findEventForOverdue() {
		Criteria<Event> c = super.getCriteria(Event.class);
		c.add(Restrictions.eq("state", Event.STATE_AUDIT, true));
		c.add(Restrictions.ne("isOverduFlag", ItcsConstants.IS_YES, true));
		return eventDao.findAll(c);
	}

    /**
     * 保存当前处理人信息的操作
     * @param eventID
     * @param coll
     */
    public void saveCurrUserOperation(Long eventID,Collection<?> coll){
		/**保存当前处理人*/
		Event event = eventDao.findOne(eventID);
		String currUserStr = StrUtils.join(coll, ",",ItcsConstants.STR_JOIN_PREFIX,ItcsConstants.STR_JOIN_SUFFIX);
		event.setSingerIds(currUserStr);
		eventDao.save(event);
    }

	/**
	 * 根据服务商下对应的服务经理获取事件的分页数据
	 * @author liulx
	 * @data 2015-08-18
	 * @param pageable
	 * @param pageParam
	 * @return
	 */
	public Page<Event> findSpEventPage(UserInfo user,PageParam pageable,Map<String, Object> param){
		//公共部分*/
		Criteria<Event> c = super.getCriteria(param,Event.class);
		//查询服务商下的问题
		List<UserInfo> serviceManagers = userInfoService.findByTenant_id(user.getTenant().getId());
		if(serviceManagers.isEmpty()){
			return null;
		}else{
			Criterion[] serviceManagerCriterion = new Criterion[serviceManagers.size()];
			for(int i=0;i<serviceManagers.size();i++){
				serviceManagerCriterion[i] = Restrictions.like("singerIds", "【"+serviceManagers.get(i).getId()+"】", false);
			}
			c.add(Restrictions.or(serviceManagerCriterion));//加入服务商所有服务经理查询项
		}
		//如果登录人是客户经理，查询所负责客户下的所有问题
		List<TenantManager> tenantManagers = tenantManagerService.findByManager(user);
		if(tenantManagers!=null && !tenantManagers.isEmpty()){//客户经理
			Criterion[] tenantManagerCriterion = new Criterion[tenantManagers.size()];
			for(int i=0;i<tenantManagers.size();i++){
				tenantManagerCriterion[i] = Restrictions.eq("tenant.id", tenantManagers.get(i).getTenant().getId(), true);
			}
			c.add(Restrictions.or(tenantManagerCriterion));
		}
		pageable.setSort(new Sort(Direction.DESC, "id"));
		return eventDao.findAll(c, pageable);
	}
	
	/**
	 * 根据事件ID获取事件分页集合
	 * 超过1000个ID不可使用该方法
	 * @Methods Name findByIdsForPage
	 * @Create In 2015年8月18日 By lee
	 * @param splitToLongList
	 * @return
	 */
	public Page<Event> findByIdsForPage(PageParam pageable, List<Long> eventIds) {
		Criteria<Event> c = super.getCriteria(Event.class);
		if(eventIds==null||eventIds.isEmpty()){
			return null;
		}else{
			c.add(Restrictions.in("id", eventIds, false));
			return eventDao.findAll(c, pageable);
		}
	}
	
	/*//**
	 * 保存申请单
	 * @param apply
	 * @return
	 *//*
	@Transactional
	public Event saveEventApply(Event apply) {
		// 申请单编号
		if(apply.getCode().equals(apply.getApplyNum())){
			apply.setCode(IdGetter.releaseApply.getNum());
		}
		return eventDao.save(apply);
	}*/

}
