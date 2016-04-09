package com.dc.itcs.event.service;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.flamingo.core.service.BaseService;
import com.dc.flamingo.core.utils.DateUtils;
import com.dc.flamingo.core.utils.PropertiesUtils;
import com.dc.flamingo.core.utils.StrUtils;
import com.dc.flamingo.tools.service.MailSendService;
import com.dc.itcs.core.base.support.ItcsConstants;
import com.dc.itcs.event.dao.ServiceLevelDao;
import com.dc.itcs.event.entity.Event;
import com.dc.itcs.event.entity.ServiceLevel;
import com.dc.itcs.security.entity.UserInfo;
import com.dc.itcs.security.entity.UserRole;
import com.dc.itcs.security.service.UserInfoService;
import com.dc.itcs.security.service.UserRoleService;

@Service("serviceLevelService")
@Transactional(readOnly=true)
public class ServiceLevelService extends BaseService{

	@Autowired
	private ServiceLevelDao serviceLevelDao;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private MailSendService mailSendService;
	
    public List<ServiceLevel> findByStateAndTenant_Id(Integer state,Long id){
    	return serviceLevelDao.findByStateAndTenant_Id(state,id);
    }
    public List<ServiceLevel> findByTenant_Id(Long id){
    	return serviceLevelDao.findByTenant_Id(id);
    }
    public ServiceLevel findById(Long id){
    	return serviceLevelDao.findOne(id);
    }
    @Transactional
    public ServiceLevel saveServiceLevl(ServiceLevel serviceLevel){
    	return serviceLevelDao.save(serviceLevel);
    }
  
    /**
     * 发送超期/预警邮件
     * @Methods Name sendOverWarningTimeMail
     * @Create In 2015年8月13日 By luzm1
     * @param apply
     * @param flag void
     */
	@Transactional
	public void sendOverWarningTimeMail(Event apply,String flag) {
			/**准备当前处理人Long--ids集合*/
			List<String> sbStrSplit= null;
			List<Long> managerIds = null;
			if(!StrUtils.isEmpty(apply.getSingerIds())){
				sbStrSplit= StrUtils.splitToList(apply.getSingerIds(), ",", ItcsConstants.STR_JOIN_PREFIX, ItcsConstants.STR_JOIN_SUFFIX);
				managerIds = new ArrayList<Long>();
				for(String s: sbStrSplit){
					Number id = (Number)Integer.valueOf(s);
					Long idLong = id.longValue();
					if(!managerIds.contains(idLong)){
						managerIds.add(idLong);
					}
				}
				/**发送邮件给当前处理人*/
				sendNoticeMailToManager(apply,managerIds,flag);
			}
	}


	/**
	 * 发送给经理
	 * @Methods Name sendNoticeMailToManager
	 * @Create In 2015年8月13日 By luzm1
	 * @param apply
	 * @param ids
	 * @param flag void
	 */
	private void sendNoticeMailToManager(Event apply,Collection<Long> ids,String flag) {
		// 邮件主题MAP
		Map<String,String> titleParam = new HashMap<String,String>();
		// 正文MAP
		Map<String,String> contentParam = new HashMap<String,String>();
		// 邮件主题
		String code = apply.getCode();
		String topic = apply.getTopic();
		// 审批历史
		// 邮件署名
		String msg1 = PropertiesUtils.getProperty("system.sendmail.mailMsg1");
		// URL
		String webUrl = PropertiesUtils.getProperty("system.web.url") + apply.getApplyUrl();
		String webUrls = "<a href = '" + PropertiesUtils.getProperty("system.web.url") + "'>IT服务中心</a>";
		String url = "请<a href = '" + webUrl + "'>点此链接</a>查看详情";
		
		// 邮件内容
		titleParam.put("code", code);
		titleParam.put("topic", topic);
		contentParam.put("url", url);
		// 邮件内容
		contentParam.put("topic", topic); 
		contentParam.put("code", code); 
		contentParam.put("url", url);
		contentParam.put("webUrls", webUrls);
		contentParam.put("msg1", msg1);
		contentParam.put("date", DateUtils.getCurrentDateStr());
		
		/**如果有当前处理人 则：发送邮件
		        如果没有当前处理人 则：不发送邮件*/
		List<UserInfo> managers = null;
		if(ids.size() > 0){
			managers = userInfoService.findByIds(ids);
			String roleName = "";
			for(UserInfo manager : managers){
				List<UserRole> userRole = userRoleService.findByUser_Id(manager.getId());
				if(userRole.size() > 0){ roleName = userRole.get(0).getRole().getName();}
				contentParam.put("roleName", roleName);
				contentParam.put("applyUser", manager.getUserText());
				mailSendService.sendByTempate(flag, manager.getEmail(), titleParam, contentParam);
			}
		}
	}
	
	
	
}
