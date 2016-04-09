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

/**
 * 事件检查任务
 * 若客户超过2天未关闭，系统自动关闭问题，视为已解决。
 * @ClassName: EventCheckJob
 * @Description: TODO
 * @Create In 2015年5月14日 By yizm
 */
@Component("userPassJob")
public class UserPassJob implements SchedulerJobBean{
	private static final Logger log = LoggerFactory.getLogger(UserPassJob.class);
	@Autowired
	private EventService eventService;
	@Autowired
	private EventMessageService eventMessageService;
	
	@Override
	public void execute(JobExecutionContext context) {
		log.debug("自动关闭提醒：在自动关闭前发送===============go");
		//获取未关闭且未被检查出超时的事件
		List<Event> alertEvents = eventService.findApplyUserList();
		for(Event event : alertEvents){
			if(event.isMoreThanTwoDays() == true){
				event.setIsSlove(ItcsConstants.SRVICE_MANAGER_PASS);
				event.setMainState(ItcsConstants.EVENT_SLA_OVERDUE);
				eventService.eventSave(event);
				log.info("自动关闭["+event.getCode()+"]提醒");
				/**发送 自动关闭邮件*/
				eventMessageService.twoDaysAutoOver(event);
			}
			
		}
	
	}

}
