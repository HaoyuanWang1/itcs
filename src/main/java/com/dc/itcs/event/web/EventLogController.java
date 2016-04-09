package com.dc.itcs.event.web;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dc.flamingo.core.web.AjaxResult;
import com.dc.flamingo.core.web.WebParamInfo;
import com.dc.itcs.event.entity.EventLog;
import com.dc.itcs.event.service.EventLogService;
import com.dc.itcs.security.entity.UserRole;
import com.dc.itcs.security.service.UserRoleService;

/**
 * 问题日志控制器
 * @author luzm1
 */
@Controller
@RequestMapping("/eventLog")
public class EventLogController {
	
	@Autowired
	EventLogService eventLogService;
	@Autowired
	UserRoleService userRoleService;
    /**
     * 保存
     * @param event
     * @return
     */
    @RequestMapping("/applySave")
    @ResponseBody
    public AjaxResult applySave(EventLog eventLog) {
        eventLog = eventLogService.eventLogSave(eventLog);
        return AjaxResult.objectResult(eventLog);
    }

    /**
     * 根据申请单显示沟通记录
     * @param event
     * @return
     */
    @RequestMapping("/panel/eventlogList/{eventId}")
    public ModelAndView eventlogList(WebParamInfo webParamInfo,@PathVariable Long eventId) {
    	ModelAndView mav = new ModelAndView();
    	List<EventLog> page = eventLogService.findByEvent_Id(eventId);
		Map<EventLog,List<UserRole>> newEventLog = new LinkedHashMap<EventLog,List<UserRole>>();
		for(EventLog el : page){
			List<UserRole> userRoleList = userRoleService.findByUser_Id(el.getCreateUser().getId());
			if(userRoleList.size() > 0){
				newEventLog.put(el, userRoleList);
			}
		}
    	mav.addObject("eventLogEntrySet",newEventLog.entrySet());
    	mav.setViewName("/event/eventLogList");
		return mav;
    }
}
