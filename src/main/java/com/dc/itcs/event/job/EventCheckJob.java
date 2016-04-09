package com.dc.itcs.event.job;

import java.util.List;

import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dc.flamingo.tools.quartz.SchedulerJobBean;
import com.dc.itcs.core.base.support.ItcsConstants;
import com.dc.itcs.event.entity.Event;
import com.dc.itcs.event.service.EventMessageService;
import com.dc.itcs.event.service.EventService;
import com.dc.itcs.event.service.ServiceLevelService;

/**
 * 事件检查任务
 * 定时检查是否有超期事件，并做相应提醒
 * @ClassName: EventCheckJob
 * @Description: TODO
 * @Create In 2015年5月12日 By lee
 */
@Component("eventCheckJob")
public class EventCheckJob implements SchedulerJobBean{
	private static final Logger log = LoggerFactory.getLogger(EventCheckJob.class);
	@Autowired
	private EventService eventService;
	@Autowired
	private ServiceLevelService serviceLevelService;
	@Autowired
	private EventMessageService eventMessageService;
	
	@Override
	public void execute(JobExecutionContext context) {
		log.debug("事件提醒：事件预警在SLA标准2小时前发送===============go");
		//获取未关闭且未被检查出超时的事件
		List<Event> alertEvents = eventService.findEventForWarning();
		for(Event event : alertEvents){
			if(event.getIsAlert() == true){
				event.setIsWarningFlag(ItcsConstants.IS_YES);
				eventService.eventSave(event);
				log.info("事件["+event.getCode()+"]预警提醒");
				/**预警标志*/
				String OverModel = "warningModel";
				/**发送邮件*/
				serviceLevelService.sendOverWarningTimeMail(event,OverModel);
			}
		}
		//获取未关闭且未被检查出需要提醒的事件
		List<Event> overduEvents = eventService.findEventForOverdue();
		for(Event event : overduEvents){
			if(event.getIsOverdue() == true){
				event.setIsOverduFlag(ItcsConstants.IS_YES);
				event.setIsWarningFlag(ItcsConstants.IS_NOT);
				eventService.eventSave(event);
				log.info("事件["+event.getCode()+"]超期提醒");
				/**超期标志*/
				String OverModel = "OverModel";
				/**发送邮件*/
				serviceLevelService.sendOverWarningTimeMail(event,OverModel);
			}
		}
		log.debug("事件提醒：事件预警在SLA标准2小时前发送===============end");
	}

}
