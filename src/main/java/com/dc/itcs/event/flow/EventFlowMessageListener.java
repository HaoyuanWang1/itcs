package com.dc.itcs.event.flow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dc.flamingo.workflow.FlowMessageListener;
import com.dc.flamingo.workflow.core.Execution;
import com.dc.flamingo.workflow.element.Node;
import com.dc.flamingo.workflow.entity.ProcessDefinition;
import com.dc.flamingo.workflow.entity.ProcessInstance;
import com.dc.flamingo.workflow.entity.Task;
import com.dc.itcs.event.entity.Event;
import com.dc.itcs.event.service.EventMessageService;
import com.dc.itcs.event.service.EventService;

@Component("eventFlowMessageListener")
@Transactional(readOnly=true)
public class EventFlowMessageListener implements FlowMessageListener{
	private static final Logger log = LoggerFactory.getLogger(EventFlowMessageListener.class);
	@Autowired
	private EventService eventService;
	@Autowired
	private EventMessageService eventMessageService;
	@Override
	public void enter(ProcessDefinition pd, ProcessInstance process, Node node) {
		log.info("=====进入环节【"+node.getDesc()+"】=====");
	}

	@Override
	public void exit(ProcessDefinition pd, ProcessInstance process, Node node) {
		log.info("=====离开环节【"+node.getDesc()+"】=====");
	}

	@Override
	public void taskOpen(ProcessDefinition pd, ProcessInstance process,
			Task task, Iterable<Long> actorIds) {
		log.info("=====打开任务【"+task.getId()+"】=====");
		//如果是用户确认环节 发送用户确认邮件模版
		if(task.getNodeName().equals("userConfirm")){
			eventMessageService.sendUserPass((Event)process.getEntityObject(),actorIds);
		}
		else
			eventMessageService.sendAuditNoticeMail((Event)process.getEntityObject(),actorIds);
	}

	@Override
	public void taskClose(ProcessDefinition pd, ProcessInstance process,
			Task task) {
		log.info("=====关闭任务【"+task.getId()+"】=====");
	}

	@Override
	public void taskTranfer(Execution execution, Task task, Long sourceUserId,
			Long targetUserId) {
		log.info("=====转办任务【"+task.getId()+"】=====");
	}

}
