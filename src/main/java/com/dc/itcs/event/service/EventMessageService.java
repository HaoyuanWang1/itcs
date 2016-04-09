package com.dc.itcs.event.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.flamingo.core.service.BaseService;
import com.dc.flamingo.core.utils.DateUtils;
import com.dc.flamingo.core.utils.PropertiesUtils;
import com.dc.flamingo.tools.service.MailSendService;
import com.dc.itcs.core.context.UserContext;
import com.dc.itcs.event.entity.Event;
import com.dc.itcs.event.entity.EventLog;
import com.dc.itcs.event.entity.ServiceManager;
import com.dc.itcs.event.entity.ServiceType;
import com.dc.itcs.flow.entity.FlowApply;
import com.dc.itcs.flow.service.FlowApplyService;
import com.dc.itcs.security.entity.TenantManager;
import com.dc.itcs.security.entity.UserInfo;
import com.dc.itcs.security.service.TenantManagerService;
import com.dc.itcs.security.service.UserInfoService;


@Service
@Transactional(readOnly=true)
public class EventMessageService extends BaseService{

	@Autowired
	private MailSendService mailSendService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private EventLogService eventLogService;
	@Autowired
    private EventService eventApplyService;
	@Autowired
    private TenantManagerService tenantManagerService;
	@Autowired
	private ServiceManagerService serviceManagerService;
	@Autowired
	private FlowApplyService flowApplyService;

	/**
	 * 发送审批邮件
	 * @param apply
	 * @param actorIds
	 */
	public void sendAuditNoticeMail(Event apply,Iterable<Long> actorIds) {

		// 邮件主题MAP
		Map<String,String> titleParam = new HashMap<String,String>();
		// 正文MAP
		Map<String,String> contentParam = new HashMap<String,String>();
		// 申请人信息
		UserInfo applyUser = apply.getSubmitUser();
		//申请人所属客户
		String tenant = apply.getSubmitUser().getTenant().getName();
		//申请主题
		String topic=apply.getApplyName();
		//申请编号
		String code=apply.getCode();
		//申请主题
		String content=apply.getContent();
		// 邮件主题
		titleParam.put("applyUser", applyUser.getUserText());
		titleParam.put("tenant", tenant);
		titleParam.put("topic", topic);
		titleParam.put("code", code);
		// 邮件署名
		String msg1 = PropertiesUtils.getProperty("system.sendmail.mailMsg1");
		// URL
		String webUrl = PropertiesUtils.getProperty("system.web.url") + apply.getApplyUrl();
		String webUrls = "<a href = '" + PropertiesUtils.getProperty("system.web.url") + "'>IT服务中心</a>";
		String url = "<a href = '" + webUrl + "'>点此链接</a>";
		// 请点此链接处理
		// 邮件内容
		contentParam.put("applyUser", applyUser.getUserText());
		contentParam.put("tenant", tenant);
		contentParam.put("topic", topic); 
		contentParam.put("code", code); 
		contentParam.put("content", content); 
		contentParam.put("url", url);
		contentParam.put("webUrls", webUrls);
		contentParam.put("msg1", msg1);
		contentParam.put("date", DateUtils.getCurrentDateStr());
		// 邮件通知对象集合
		List<UserInfo> noticeUserList = userInfoService.findByIds(actorIds);
		// 遍历所有通知人员
		for(UserInfo auditUser : noticeUserList){
			String auditUserInfo = auditUser.getUserText();
			contentParam.put("auditUser", auditUserInfo);
			mailSendService.sendByTempate("eventAudit", auditUser.getEmail(), titleParam, contentParam);
		}
	
	}
	
	/**
	 * 发送结束邮件
	 * @param entityObject
	 */
	public void sendEndNoticeMail(Event apply) {
		String actionSlove = "已解决";
		EventLog eventLogSlove = eventLogService.findLastByEvent_IdAndAction(apply.getId(),actionSlove);
		//服务经理
		UserInfo serviceManager = eventLogSlove.getCreateUser();
		//发送回复内容条件
		String actionPass = "通过";
		EventLog eventLogPass = eventLogService.findLastByEvent_IdAndAction(apply.getId(),actionPass);
		//内容
		String context = eventLogPass.getContext();
		// 邮件主题MAP
		Map<String,String> titleParam = new HashMap<String,String>();
		// 正文MAP
		Map<String,String> contentParam = new HashMap<String,String>();
		//申请人所属客户
		String tenant = apply.getSubmitUser().getTenant().getName();
		//申请主题
		String topic=apply.getApplyName();
		//申请编号
		String code=apply.getCode();
		// 申请人信息
		UserInfo applyUser = apply.getSubmitUser();
		// 申请类型
		String flowName = apply.getFlowDesc();
		// 邮件主题
		titleParam.put("flowName", flowName);
		titleParam.put("applyUser", applyUser.getUserText());
		titleParam.put("tenant", tenant);
		titleParam.put("topic", topic);
		titleParam.put("code", code);
		// 邮件署名
		String msg1 = PropertiesUtils.getProperty("system.sendmail.mailMsg1");
		// URL
		String webUrl = PropertiesUtils.getProperty("system.web.url") + apply.getApplyUrl();
		String url = "<a href = '" + webUrl + "'>点此链接</a>";
		
		// 请点此链接处理
		// 邮件内容
		contentParam.put("serviceManager", serviceManager.getUserText());
		contentParam.put("flowName", flowName);
		contentParam.put("applyUser", applyUser.getUserText());
		contentParam.put("url", url);
		contentParam.put("context", context);
		contentParam.put("tenant", tenant);
		contentParam.put("topic", topic);
		contentParam.put("code", code);
		contentParam.put("msg1", msg1);
		contentParam.put("date", DateUtils.getCurrentDateStr());
		mailSendService.sendByTempate("eventEnd", serviceManager.getEmail(), titleParam, contentParam);
		
	}
	
	
	
	/**
	 * 发送驳回邮件
	 * @param entityObject
	 */
	public void sendbackNoticeMail(Event apply) {
		String action = "已解决";
		EventLog eventLog = eventLogService.findLastByEvent_IdAndAction(apply.getId(),action);
		/**服务经理*/
		UserInfo serviceManager = eventLog.getCreateUser();
		//申请人所属客户
		String tenant = apply.getSubmitUser().getTenant().getName();
		//申请主题
		String topic=apply.getApplyName();
		//申请主题
		String content=apply.getContent();
		//申请编号
		String code=apply.getCode();
		// 邮件主题MAP
		Map<String,String> titleParam = new HashMap<String,String>();
		// 正文MAP
		Map<String,String> contentParam = new HashMap<String,String>();
		// 申请人信息
		UserInfo applyUser = apply.getSubmitUser();
		// 申请类型
		String flowName = apply.getFlowDesc();
		// 邮件主题
		titleParam.put("flowName", flowName);
		titleParam.put("tenant", tenant);
		titleParam.put("topic", topic);
		titleParam.put("applyUser", applyUser.getUserText());
		titleParam.put("code", code);
		// 邮件署名
		String msg1 = PropertiesUtils.getProperty("system.sendmail.mailMsg1");
		// URL
		String webUrl = PropertiesUtils.getProperty("system.web.url") + apply.getApplyUrl();
		String url = "<a href = '" + webUrl + "'>点此链接</a>";
		// 请点此链接处理
		// 邮件内容
		contentParam.put("applyUser", applyUser.getUserText());
		contentParam.put("serviceManager", serviceManager.getUserText());
		contentParam.put("flowName", flowName);
		contentParam.put("url", url);
		contentParam.put("tenant", tenant);
		contentParam.put("topic", topic); 
		contentParam.put("content", content); 
		contentParam.put("code", code); 
		contentParam.put("msg1", msg1);
		contentParam.put("date", DateUtils.getCurrentDateStr());
		mailSendService.sendByTempate("eventBack", serviceManager.getEmail(), titleParam, contentParam);
		
	}
	
	/**
	 * 发送用户确认邮件
	 * @param entityObject
	 */
	public void sendUserPass(Event apply,Iterable<Long> actorIds) {
		String action = "已解决";
		EventLog eventLog = eventLogService.findLastByEvent_IdAndAction(apply.getId(),action);
		// 邮件主题MAP
		Map<String,String> titleParam = new HashMap<String,String>();
		// 正文MAP
		Map<String,String> contentParam = new HashMap<String,String>();
		//申请主题
		String topic=apply.getApplyName();
		//申请编号
		String code=apply.getCode();
		//回复内容
		String context=eventLog.getContext();
		// 申请人信息
		UserInfo applyUser = apply.getSubmitUser();
		// 申请类型
		String flowName = apply.getFlowDesc();
		// 邮件主题
		titleParam.put("flowName", flowName);
		titleParam.put("topic", topic);
		titleParam.put("code", code);
		// 邮件署名
		String msg1 = PropertiesUtils.getProperty("system.sendmail.mailMsg1");
		// URL
		String webUrl = PropertiesUtils.getProperty("system.web.url") + apply.getApplyUrl();
		String url = "<a href = '" + webUrl + "'>点此链接</a>";
		// 请点此链接处理
		// 邮件内容
		contentParam.put("applyUser", applyUser.getUserText());
		contentParam.put("flowName", flowName);
		contentParam.put("url", url);
		contentParam.put("topic", topic);
		contentParam.put("code", code);
		contentParam.put("context", context);
		contentParam.put("msg1", msg1);
		contentParam.put("date", DateUtils.getCurrentDateStr());
		mailSendService.sendByTempate("userPass", applyUser.getEmail(), titleParam, contentParam);
		
	}
	
	/**
	 * 用户发给
	 */
	@Transactional
	public void sendBackMail(Event event){
		String action = "回复";
		EventLog eventLog = eventLogService.findLastByEvent_IdAndAction(event.getId(),action);
		// 邮件主题MAP
		Map<String,String> titleParam = new HashMap<String,String>();
		// 正文MAP
		Map<String,String> contentParam = new HashMap<String,String>();
		//申请人所属客户
		String tenant = event.getSubmitUser().getTenant().getName();
		//申请主题
		String topic=event.getTopic();
		//得到当前申请人
		UserInfo applyUser = event.getSubmitUser();
		//得到当前登录用户
		 UserInfo curUser = UserContext.getCurUser();
		//申请编号
		String code=event.getCode();
		//回复内容
		String context=eventLog.getContext();
		//保存主题信息
		titleParam.put("applyUser", applyUser.getUserText());
		titleParam.put("code", code);
		titleParam.put("topic", topic);
		titleParam.put("tenant", tenant);
		// 邮件署名
		String msg1 = PropertiesUtils.getProperty("system.sendmail.mailMsg1");
		// URL
		String webUrl = PropertiesUtils.getProperty("system.web.url") + event.getApplyUrl();
		String url = "<a href = '" + webUrl + "'>点此链接</a>";
		contentParam.put("url", url);
		contentParam.put("topic", topic);
		contentParam.put("code", code);
		contentParam.put("applyUser", applyUser.getUserText());
		contentParam.put("tenant", tenant);
		contentParam.put("context", context);
		contentParam.put("msg1", msg1);
		contentParam.put("date", DateUtils.getCurrentDateStr());
		FlowApply flowApply = flowApplyService.findByClassAndId(Event.class.getName(), event.getId());
			//1客户经理审批环节			
		if(flowApply.getNodeName().equals("tenantManagerConfirm")){
			 	//a.当前登录人是提交人，给客户经理发
			if(applyUser.getId() == curUser.getId()){	
				Long tenantId = eventApplyService.findById(event.getId()).getTenant().getId();
				List<TenantManager> managers = tenantManagerService.findByTenant_Id(tenantId);
				// 遍历所有通知人员
				for(TenantManager manager : managers){
					String auditUserInfo = manager.getTenantManager().getUserText();
					contentParam.put("auditUser", auditUserInfo);
					mailSendService.sendByTempate("eventAdmin", manager.getTenantManager().getEmail(), titleParam, contentParam);
					
				}
			}
			
		}
		
		// b.当前登录人是客户经理，给提交人发   
		/**
		 * 3服务经理审批环节、用户确认环节（和服务经理环节一样）
		 * a.当前登录人是提交人，给最后一个处理的服务经理发（没满足 群发）
		 * b.当前登录人是服务经理，给提交人发
		 */
		if(flowApply.getNodeName().equals("serviceManagerConfirm")||flowApply.getNodeName().equals("userConfirm")){
						// a.当前登录人是提交人，给所有对应的服务经理发
			if(applyUser.getId() == curUser.getId()){	
				//	ii.服务经理未回复过，给所有支持该服务类型的服务经理发
				ServiceType serviceType = eventApplyService.findById(event.getId()).getServiceType();
				List<ServiceManager> managers = serviceManagerService.findByType_Id(serviceType.getId());
				// 遍历所有通知人员
				for(ServiceManager manager : managers){
					String auditUserInfo = manager.getUserInfo().getUserText();
					contentParam.put("auditUser", auditUserInfo);
					mailSendService.sendByTempate("eventAdmin", manager.getUserInfo().getEmail(), titleParam, contentParam);
				}
			}
					//i.服务经理回复过，给最近一次回复的服务经理发(不要的了)
					//b.当前登录人是服务经理，给提交人发
			else{
				mailSendService.sendByTempate("eventMessage", applyUser.getEmail(), titleParam, contentParam);
			}
				
		}
				
	}
	
	
	/**
	 * 用户两天没有解决，就自动关闭
	 * @param entityObject
	 */
	@Transactional
	public void twoDaysAutoOver(Event apply) {
		// 邮件主题MAP
		Map<String,String> titleParam = new HashMap<String,String>();
		// 正文MAP
		Map<String,String> contentParam = new HashMap<String,String>();
		//申请主题
		String topic=apply.getApplyName();
		//申请编号
		String code=apply.getCode();
		// 申请人信息
		UserInfo applyUser = apply.getSubmitUser();
		// 邮件署名
		String msg1 = PropertiesUtils.getProperty("system.sendmail.mailMsg1");
		// URL
		String webUrl = PropertiesUtils.getProperty("system.web.url") + apply.getApplyUrl();
		// 请点此链接处理
		String url = "<a href = '" + webUrl + "'>点此链接</a>";
		
		titleParam.put("topic", topic);
		titleParam.put("code", code);
		// 邮件内容
		contentParam.put("applyUser", applyUser.getUserText());
		contentParam.put("url", url);
		contentParam.put("topic", topic);
		contentParam.put("code", code);
		contentParam.put("msg1", msg1);
		contentParam.put("date", DateUtils.getCurrentDateStr());
		mailSendService.sendByTempate("twoDaysAutoOver", applyUser.getEmail(), titleParam, contentParam);
		
	}
	
	

}
