package com.dc.itcs.event.web;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dc.flamingo.core.support.PageParam;
import com.dc.flamingo.core.utils.DateUtils;
import com.dc.flamingo.core.utils.StrUtils;
import com.dc.flamingo.core.web.AjaxResult;
import com.dc.flamingo.core.web.WebParamInfo;
import com.dc.itcs.core.base.entity.FlowEntity;
import com.dc.itcs.core.base.support.ItcsConstants;
import com.dc.itcs.core.context.UserContext;
import com.dc.itcs.event.entity.Event;
import com.dc.itcs.event.entity.EventLog;
import com.dc.itcs.event.entity.ServiceLevel;
import com.dc.itcs.event.entity.ServiceManager;
import com.dc.itcs.event.entity.ServiceType;
import com.dc.itcs.event.excel.EventExcelView;
import com.dc.itcs.event.service.EventLogService;
import com.dc.itcs.event.service.EventMessageService;
import com.dc.itcs.event.service.EventService;
import com.dc.itcs.event.service.ServiceLevelService;
import com.dc.itcs.event.service.ServiceManagerService;
import com.dc.itcs.event.service.ServiceTypeService;
import com.dc.itcs.flow.FlowAuth;
import com.dc.itcs.flow.entity.FlowApply;
import com.dc.itcs.flow.service.FlowApplyService;
import com.dc.itcs.flow.service.ItcsFlowEngine;
import com.dc.itcs.security.entity.UserInfo;
import com.dc.itcs.security.entity.UserRole;
import com.dc.itcs.security.service.UserInfoService;
import com.dc.itcs.security.service.UserRoleService;
/**
 * 问题控制器
 */
@Controller
@RequestMapping("/event")
public class EventController {

	@Autowired
	private EventService eventService;
	@Autowired
	private ServiceTypeService serviceTypeService;
	@Autowired
	private ServiceLevelService serviceLevelService;
	@Autowired
	private ItcsFlowEngine itcsFlowEngine;
	@Autowired
	private FlowApplyService flowApplyService;
	@Autowired
	private UserInfoService userinfoService;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private EventLogService eventLogService;
	@Autowired
	private EventMessageService eventMessageService;   
	@Autowired
	private ServiceManagerService serviceManagerService;
	
	
	/**
	 * 申请页面总控制器
	 * @Methods Name page
	 * @Create In 2015年4月3日 By yizm
	 * @param entityId
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/applyPage/{entityId}")
    public ModelAndView applyPage(@PathVariable Long entityId) {
		ModelAndView mav = new ModelAndView();
		// 跳转URL 默认结束页面
		String url = "/event/event_end";
		// 当前用户
        UserInfo curUser = UserContext.getCurUser();
        mav.addObject("curUser", curUser);
        // 根据ID查询申请单
        Event apply = eventService.findById(entityId);
        mav.addObject("apply",apply);
        //得到当前服务类型的服务经理
        ServiceType serviceType = apply.getServiceType();
        Long serviceTypeID = null;
        if(serviceType != null){
        	serviceTypeID = serviceType.getId();
        }
        List<ServiceManager>  serviceManagerList =null;
        if(serviceTypeID != null){
        	serviceManagerList = serviceManagerService.findByType_Id(serviceTypeID);
        	mav.addObject("serviceManagerList", serviceManagerList);
        }
        
    	List<ServiceType> serviceTypeList= serviceTypeService.findByStateAndTenant_Id(ItcsConstants.STATE_ON, apply.getTenant().getId());
        //准备serviceType
    	List<ServiceLevel> serviceLevelList = serviceLevelService.findByStateAndTenant_Id(ItcsConstants.STATE_ON, apply.getTenant().getId());
    	
    	mav.addObject("serviceTypeList", serviceTypeList);
    	mav.addObject("serviceLevelList", serviceLevelList);
        if ( (FlowEntity.STATE_DRAFT==apply.getState()) && curUser.getUid().equals(apply.getCreateUser().getUid())) {
			// 转到申请页面
        	url = "redirect:/event/applyEdit/" + entityId;
		}else if ( FlowEntity.STATE_AUDIT ==apply.getState()) {	// 审批中
			FlowAuth flowAuth = itcsFlowEngine.findFlowAuth(apply.getProcessId(), UserContext.getCurUser());
			mav.addObject("flowAuth", flowAuth);
			if (flowAuth.isFlowSinger()) {
				String nodeName = flowAuth.getNodeName();
				if (ItcsConstants.EVENT_NODE_CLIENTMANAGER_CONFIRM.equals(nodeName)) {	// 客户经理审批页面
					url = "/event/event_tenantAudit";
				} else if (ItcsConstants.EVENT_NODE_SERVICEMANAGER_CONFIRM.equals(nodeName)) {	// 服务经理审批页面
					url = "/event/event_serviceAudit";
				}else if(ItcsConstants.EVENT_NODE_USER_CONFIRM.equals(nodeName)){	//用户确认页面
					url = "/event/event_userAudit";
				}
			}
		}
		mav.setViewName(url);
		return mav;
	}
	
	/**
	 * 进入新增申请页面
	 * @Methods Name applyEdit
	 * @param entityId
	 * @return ModelAndView
	 */
	@RequestMapping("/applyEdit/{entityId}")
	public ModelAndView applyEdit(@PathVariable Long entityId) {
		ModelAndView mav = new ModelAndView();
		Event apply = eventService.findById(entityId);
		if(null != apply){
			apply.setSubmitTime(DateUtils.getTimeStr(new Date()));
		}else{
			apply = Event.getInstance();
		}
		//根据登录用户进行判断
		if(!UserContext.isEngineer()){
			//自提
			//保存最近更新人
			apply.setRecentUser("用户");
			//保存最近更新状态
			apply.setRecentAction("提交");
			apply.setIsInstead(ItcsConstants.SUBMIT_OF_MINE);
			apply.setSubmitMode(Event.SUBMIT_MODE_MENTION);
			mav.addObject("apply", apply);
			mav.setViewName("/event/event_edit_user");
		}else{
			//代提
			//保存最近更新人
			apply.setRecentUser("客户经理");
			//保存最近更新状态
			apply.setRecentAction("代提");
			apply.setIsInstead(ItcsConstants.SUBMIT_OF_INSTEAD);
			apply.setSubmitMode(Event.SUBMIT_MODE_GENERATION);
			mav.addObject("apply", apply);
			mav.setViewName("/event/event_edit_engineer");
		}
		return mav;
	}
	
	/**
	 * 保存申请
	 * @Methods Name saveTelApply
	 * @param telApply
	 * @return AjaxResult
	 */
	@RequestMapping("/saveEventApply")
	@ResponseBody
	public AjaxResult saveEventApply(Event apply,String context,String appendix){
		EventLog eventlog=new EventLog();
		eventlog.setEvent(apply);
		eventlog.setContext(context);
		eventlog.setAttachment(appendix);
		eventlog.setCreateTime(DateUtils.getTimeStr(new Date()));
		eventlog.setCreateUser(UserContext.getCurUser());
		eventlog.setAction(EventLog.SUBMIT_TYPE_SERVICE);
		//保存最近更新人
		apply.setRecentUser("客户经理");
		//保存最近更新状态
		apply.setRecentAction(EventLog.SUBMIT_TYPE_SERVICE);
		apply = eventService.eventSave(apply);
		eventLogService.eventLogSave(eventlog);
		return AjaxResult.objectResult(apply.getId());
	}
	
	/**
	 * 保存并启动流程
	 * @param apply
	 * @return
	 */
	@RequestMapping("/submitApply")
	@ResponseBody
	public AjaxResult submitApply(Event apply){
		//启动流程之前给超期预警设置默认值
		apply.setIsOverduFlag(ItcsConstants.IS_NOT);
		apply.setIsWarningFlag(ItcsConstants.IS_NOT);
		// 保存数据
		apply = eventService.eventSave(apply);
		// 启动流程
		itcsFlowEngine.startProcess(apply, apply.getCreateUser().getId(), null);
		// 返回数据
		return AjaxResult.objectResult(apply.getId());
	}
	
	/**
	 * 服务经理已经解决 状态赋值 已经解决
	 * @param apply
	 * @return
	 */
	@RequestMapping("/serviceSlove")
	@ResponseBody
	public AjaxResult serviceSlove(Event apply,String context,String appendix){
		//1获取原事件
		Event oldEvent = eventService.findById(apply.getId());
		//2查看事件服务级别是否变更
		boolean isChange = apply.getServiceType().getId().equals(oldEvent.getServiceType().getId());
		if(!isChange){
			return AjaxResult.objectResult("exist");
		}
		EventLog eventlog=new EventLog();
		eventlog.setEvent(apply);
		eventlog.setContext(context);
		eventlog.setAttachment(appendix);
		eventlog.setCreateTime(DateUtils.getTimeStr(new Date()));
		eventlog.setCreateUser(UserContext.getCurUser());
		eventlog.setAction(EventLog.SUBMIT_TYPE_SOLVE);
		apply.setIsSlove(ItcsConstants.SRVICE_MANAGER_OK);
		//保存最近更新人
		apply.setRecentUser("服务经理");
		//保存最近更新状态
		apply.setRecentAction(EventLog.SUBMIT_TYPE_SOLVE);
		apply = eventService.eventSave(apply);
		eventLogService.eventLogSave(eventlog);
		return AjaxResult.objectResult(apply.getId());
	}
	
	/**
	 * 用户已经通过 状态赋值 已通过
	 * @param apply
	 * @return
	 */
	@RequestMapping("/userPass")
	@ResponseBody
	public AjaxResult userPass(Event apply,String context,String appendix){
		
		EventLog eventlog=new EventLog();
		eventlog.setEvent(apply);
		eventlog.setContext(context);
		eventlog.setAttachment(appendix);
		eventlog.setCreateTime(DateUtils.getTimeStr(new Date()));
		eventlog.setCreateUser(UserContext.getCurUser());
		eventlog.setAction(EventLog.SUBMIT_TYPE_PASS);
		apply.setIsSlove(ItcsConstants.SRVICE_MANAGER_PASS);
		//保存最近更新人
		apply.setRecentUser("用户");
		//保存最近更新状态
		apply.setRecentAction(EventLog.SUBMIT_TYPE_PASS);
		apply = eventService.eventSave(apply);
		eventLogService.eventLogSave(eventlog);
		return AjaxResult.objectResult(apply.getId());
	}
	/**
	 * 用户驳回  状态赋值 已通过
	 * @param apply
	 * @return
	 */
	@RequestMapping("/userBack")
	@ResponseBody
	public AjaxResult userBack(Event apply,String context,String appendix){
		
		EventLog eventlog=new EventLog();
		eventlog.setEvent(apply);
		eventlog.setContext(context);
		eventlog.setAttachment(appendix);
		eventlog.setCreateTime(DateUtils.getTimeStr(new Date()));
		eventlog.setCreateUser(UserContext.getCurUser());
		eventlog.setAction(EventLog.SUBMIT_TYPE_BACK);
		/*apply.setIsSlove(ItcsConstants.SRVICE_MANAGER_OK);*/
		//保存最近更新人
		apply.setRecentUser("用户");
		//保存最近更新状态
		apply.setRecentAction(EventLog.SUBMIT_TYPE_BACK);
		apply = eventService.eventSave(apply);
		eventLogService.eventLogSave(eventlog);
		return AjaxResult.objectResult(apply.getId());
	}
	
	/**
	 * 服务经理未解决 仅回复信息 状态赋值
	 * @param apply
	 * @return
	 */
	@RequestMapping("/serviceOther")
	@ResponseBody
	public AjaxResult serviceOther(Event apply,String context,String appendix,Long taskId){
		//1获取原事件
		Event oldEvent = eventService.findById(apply.getId());
		//2查看事件服务级别是否变更
		boolean isChange = apply.getServiceType().getId().equals(oldEvent.getServiceType().getId());
		EventLog eventlog=new EventLog();
		eventlog.setEvent(apply);
		eventlog.setContext(context);
		eventlog.setAttachment(appendix);
		eventlog.setCreateTime(DateUtils.getTimeStr(new Date()));
		eventlog.setCreateUser(UserContext.getCurUser());
		eventlog.setAction(EventLog.SUBMIT_TYPE_REPLY);
		//保存最近更新人
		apply.setRecentUser("服务经理");
		//保存最近更新状态
		apply.setRecentAction(EventLog.SUBMIT_TYPE_REPLY);
		eventLogService.eventLogSave(eventlog);
		apply = eventService.eventSave(apply);
		if(isChange){
			eventMessageService.sendBackMail(apply);
		}
		//变更了服务类型
		if(!isChange){
			apply.setIsSlove(ItcsConstants.SRVICE_MANAGER_NO_OK);
			apply = eventService.eventSave(apply);
			//发送邮件
			itcsFlowEngine.executeTask(taskId, UserContext.getCurUser().getId(), "回复", context);			
		}
		return AjaxResult.objectResult(apply.getId());
	}
	
	
	/**
	 *  结束页面 通用回复 按钮点击事件
	 * @param apply
	 * @return
	 */
	@RequestMapping("/userMessage")
	@ResponseBody
	public AjaxResult userMessage( Event apply,String context,String appendix){
		EventLog eventlog=new EventLog();
		eventlog.setEvent(apply);
		eventlog.setContext(context);
		eventlog.setAttachment(appendix);
		eventlog.setCreateTime(DateUtils.getTimeStr(new Date()));
		eventlog.setCreateUser(UserContext.getCurUser());
		eventlog.setAction(EventLog.SUBMIT_TYPE_REPLY);
		/*apply.setIsSlove(ItcsConstants.SRVICE_MANAGER_OK);*/
		//保存最近更新人 如果当前登陆人等于提交人， 则为用户 。否则为 服务经理
		if(UserContext.getCurUser().getId().equals(apply.getSubmitUser().getId()))	{
			apply.setRecentUser("用户");
		}
		else{
			apply.setRecentUser("服务经理");
		}
		//保存最近更新状态
		apply.setRecentAction(EventLog.SUBMIT_TYPE_REPLY);
		apply = eventService.eventSave(apply);
		eventLogService.eventLogSave(eventlog);
		eventMessageService.sendBackMail(apply);
		return AjaxResult.objectResult(apply.getId());
	}
	/**
	 * 待我审批的页面
	 * @param webParamInfo
	 * @return
	 */
	@RequestMapping("/panel/signerPage")
	public ModelAndView signerPage(WebParamInfo webParamInfo){
		Map<String, Object> param = webParamInfo.getParamStartingWith("search_");
		param = this.handTime(param, "GTE_createTime", "LTE_createTime");
		UserInfo curUser = UserContext.getCurUser();
		param.put("LIKE_signerIds", "【"+curUser.getId()+"】");
		PageParam pageable = webParamInfo.getPageable();
		pageable.setSort(new Sort(Direction.DESC,"createTime"));
		Page<FlowApply> page = flowApplyService.findByPage(pageable, param);
		
		/**调整创建人信息*/
		for(FlowApply fa : page.getContent()){
			Event event = eventService.findById(fa.getApplyId());
			if(event.getSubmitMode() != null){
				if(event.getSubmitMode() == 1){
					fa.setCreateUser(event.getSubmitUser());
				}
			}
		}
		/**因为flowApply中没有 信息类型
		       现在 要重新装配数据*/
		Map<FlowApply,Event> newFlowApply = new LinkedHashMap<FlowApply,Event>();
		
		for(FlowApply fa : page.getContent()){
			Event event = eventService.findById(fa.getApplyId());
			newFlowApply.put(fa, event);
		}
	
		ModelAndView mav = new ModelAndView();
		mav.addObject("page", page);
		mav.addObject("newFlowApplyMap", newFlowApply.entrySet());
		mav.addObject("searchParam", webParamInfo.getParam());
		mav.setViewName("/event/task_signer_SignerPage");
		return mav;
	}
	/**
	 * 根据事件ID获取待处理事件分页查询
	 * @Methods Name unSloveEvents
	 * @Create In 2015年8月18日 By lee
	 * @param webParamInfo
	 * @param eventIds
	 * @return
	 */
	@RequestMapping("/panel/unSloveEvents/{eventIds}")
	public ModelAndView unSloveEvents(WebParamInfo webParamInfo,@PathVariable String eventIds){
		PageParam pageable = webParamInfo.getPageable();
		pageable.setSort(new Sort(Direction.DESC,"createTime"));
		Page<Event> page = eventService.findByIdsForPage(pageable,StrUtils.splitToLongList(eventIds, ","));
		
		/**注入UserInfoList对象*/
	    for(Event event : page.getContent()){
        	event.setSingerList(userinfoService.findByIds(event.getSingerIdList()));
        }
		ModelAndView mav = new ModelAndView();
		mav.addObject("page", page);
		mav.addObject("eventIds", eventIds);
		mav.setViewName("/event/event_panel_unslove_list");
		return mav;
	}
	
	/**
	 * 带您确认的问题
	 * @param webParamInfo
	 * @return
	 */
	@RequestMapping("/panel/UserPage")
	public ModelAndView UserConfirm(WebParamInfo webParamInfo){
		Map<String, Object> param = webParamInfo.getParamStartingWith("search_");
        PageParam pageParam = webParamInfo.getPageable();
        UserInfo curUser = UserContext.getCurUser();
    	param.put("EQ_submitUser", +curUser.getId());
        ModelAndView mav = new ModelAndView();
        Page<Event> page = eventService.findApplyUserPage(pageParam,param);
        mav.addObject("page", page);
        mav.addObject("searchParam", webParamInfo.getParam());
        mav.setViewName("/event/task_signer_UserPage");
        return mav;
	}
	
	/**
	 * 查询条件起始日期修改
	 * @param param
	 * @param gteKey
	 * @param lteKey
	 * @return
	 */
	public Map<String, Object> handTime(Map<String, Object> param, String gteKey, String lteKey) {
		String startDate = "";
    	String endDate = "";
    	if(param.get(gteKey)!=null){
    		startDate = param.get(gteKey) + "";
    		param.put(gteKey, startDate + " 00:00:00");
        }
		if(param.get(lteKey)!=null){
			endDate = param.get(lteKey) + "";
			param.put(lteKey, endDate + " 23:59:59");
		}
		return param;
	}
    
	/**
	 * 客户查询已提交的问题
	 * @param webParamInfo
	 * @return
	 */
	@RequestMapping("/page")
	public ModelAndView page(WebParamInfo webParamInfo){
		Map<String, Object> param = webParamInfo.getParamStartingWith("search_");
        PageParam pageParam = webParamInfo.getPageable();
        UserInfo curUser = UserContext.getCurUser();
    	param.put("EQ_submitUser", curUser.getId());
        ModelAndView mav = new ModelAndView();
        Page<Event> page = eventService.findApplyPage(pageParam,param);
        mav.addObject("page", page);
        mav.addObject("searchParam", webParamInfo.getParam());
        mav.setViewName("/event/event_list_user");
        return mav;
	}
	
	/**
	 * 客户反馈查询
	 * @param webParamInfo
	 * @return
	 */
	@RequestMapping("/feedBack")
	public ModelAndView FeedBack(WebParamInfo webParamInfo){
		ModelAndView mav = new ModelAndView();
		//得到当前用户角色
        UserInfo curUser = UserContext.getCurUser();
        List<UserRole> userRole=userRoleService.findByUser_Id(curUser.getId());
        mav.addObject("userRole",userRole);
        mav.setViewName("/event/event_list_engineer");
        return mav;
	}
	
	@RequestMapping("/panel/engineerPage")
	public ModelAndView engineerPage(WebParamInfo webParamInfo){
		Map<String, Object> param = webParamInfo.getParamStartingWith("search_");
        PageParam pageParam = webParamInfo.getPageable();
        
        /**防止处理人带出其他人*/
        String singerIds = (String)param.get("LIKE_singerIds");
        if(StrUtils.isNotEmpty(singerIds)){
        	param.put("LIKE_singerIds", "【"+ param.get("LIKE_singerIds")+"】");
        }else{
        	param.remove("LIKE_singerIds");
        }
        
        /**超期预警查询条件的处理 ----开始*/
        String warningOverFlag = (String) param.get("EQ_overAndWarningData");
        String accept = warningOverFlag;/**暂时接受*/
        Number warningOverNum = null;
        Long warningOver = null;
        if(warningOverFlag != null){
	         warningOverNum = (Number)Integer.valueOf(warningOverFlag);
			 warningOver = warningOverNum.longValue();
        }
        if(warningOverFlag != null){
        	if(warningOver == ItcsConstants.EVENT_SLA_WARNING){
        		param.put("EQ_isWarningFlag", ItcsConstants.IS_YES);
        	}else if(warningOver == ItcsConstants.EVENT_SLA_OVERDUE){
        		param.put("EQ_isOverduFlag", ItcsConstants.IS_YES);
        	}
        }
        param.remove("EQ_overAndWarningData");
        /**超期预警查询条件的处理 ----结束*/
        
        //获取当前用的角色
        UserInfo curUser = UserContext.getCurUser();
        ModelAndView mav = new ModelAndView();
        
        Page<Event> page=null;
        //判断是否为客户用户
        if(!curUser.isSpUser()){
        	page = eventService.findApplyPage(pageParam,param);
        }else{
        	page = eventService.findSpEventPage(curUser,pageParam,param);
        }
        for(Event event : page.getContent()){
        	event.setSingerList(userinfoService.findByIds(event.getSingerIdList()));
        }
    	param.put("EQ_overAndWarningData", accept);/**用户页面回显*/
        mav.addObject("page", page);
        mav.addObject("searchParam", webParamInfo.getParam());
        mav.setViewName("/event/event_list_engineer_panel");
        return mav;
	}
	

	
    /**
   	 * 导出客户反馈清单
   	 * @param webParamInfo
   	 * @param manager
   	 * @return
   	 */
   	@RequestMapping(value="/exportEvent")
   	public ModelAndView exportEvent(WebParamInfo webParamInfo){
   		Map<String, Object> param = webParamInfo.getParamStartingWith("search_");
   	    param.remove("EQ_overAndWarningData");
   	    List<Event> list = eventService.exportUserItem(param);	    
   	    for(Event event : list){
        	event.setSingerList(userinfoService.findByIds(event.getSingerIdList()));
        }
   		Map<String,Object> map = new HashMap<String,Object>();
   		map.put("info", list);
   		return new ModelAndView(new EventExcelView(), map);  
   	}
}
