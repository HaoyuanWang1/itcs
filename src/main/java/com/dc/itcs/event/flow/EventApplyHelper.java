package com.dc.itcs.event.flow;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dc.flamingo.core.utils.DateUtils;
import com.dc.flamingo.workflow.FlowEventListener;
import com.dc.flamingo.workflow.core.Execution;
import com.dc.flamingo.workflow.element.Node;
import com.dc.flamingo.workflow.entity.ProcessInstance;
import com.dc.flamingo.workflow.entity.Token;
import com.dc.itcs.core.base.support.ItcsConstants;
import com.dc.itcs.event.entity.Event;
import com.dc.itcs.event.service.EventMessageService;
import com.dc.itcs.event.service.EventService;


@Component("eventApplyHelper")
public class EventApplyHelper implements FlowEventListener{
    @Autowired
    private EventService eventService;
    @Autowired
    private EventMessageService eventMessageService;
    /**
     * 进入环节事件
     */
	@Override
	public void enter(Execution execution) {
		Token token = execution.getToken();
		if(ItcsConstants.EVENT_NODE_USER_CONFIRM.equals(token.getNodeName())){	//进入用户确认
			Event apply = (Event) execution.getProcesEntity();
			apply.setMainState(Event.MAIN_STATE_CONFIRMED);
			eventService.eventSave(apply);
		}
		if(ItcsConstants.EVENT_NODE_SERVICEMANAGER_CONFIRM.equals(token.getNodeName())){
			Event apply = (Event) execution.getProcesEntity();
			apply.setMainState(Event.MAIN_STATE_AUDIT);
			apply.setResponseTime(DateUtils.getCurrentDateTimeStr());
			eventService.eventSave(apply);
		}
		if(ItcsConstants.EVENT_NODE_CLIENTMANAGER_CONFIRM.equals(token.getNodeName())){
			Event apply = (Event) execution.getProcesEntity();
			apply.setMainState(Event.MAIN_STATE_AUDIT);
			apply.setResponseTime(DateUtils.getCurrentDateTimeStr());
			eventService.eventSave(apply);
		}
		if(ItcsConstants.EVENT_NODE_END.equals(token.getNodeName())){	//结束
			Event apply = (Event) execution.getProcesEntity();
			apply.setMainState(Event.MAIN_STATE_FINISH);
			apply.setSingerIds("");/**结束时清空当前处理人*/
			eventService.eventSave(apply);
		}
	}
	/**
	 * 离开环节事件
	 */
	@Override
	public String exit(Execution execution) {
		// 得到申请单
		Event apply = (Event) execution.getProcesEntity();
		// 当前环节ID
		String nodeName = execution.getToken().getNodeName();
		if (ItcsConstants.EVENT_NODE_START.equals(nodeName)) { // 开始环节
			apply.setMainState(Event.MAIN_STATE_AUDIT);
			eventService.eventSave(apply);
		}
		// 判断是否代提
		if (ItcsConstants.IS_REPLACE.equals(nodeName)) {
			if (ItcsConstants.SUBMIT_OF_INSTEAD.equals(apply.getIsInstead())) {
				return "yes";
			} else {
				return "no";
			}
		}
		// 服务经理操作
		if (ItcsConstants.EVENT_NODE_SERVICEMANAGER_OPT.equals(nodeName)) {
			if (ItcsConstants.SRVICE_MANAGER_OK.equals(apply.getIsSlove())) { // 解决了
				apply.setSolveTime(DateUtils.getCurrentDateTimeStr());
				eventService.eventSave(apply);
				return "slove";
			} else {
				return "otherManager";
			}
		}
		if ("end".equals(nodeName)) { // 申请结束
			eventMessageService.sendEndNoticeMail(apply);
		}
		eventService.eventSave(apply);
		return null;
	}

	@Override
	public void back(Execution execution, Node targetNode, Long operator) {
		
	}
	@Override
	public void cancel(ProcessInstance process, Long operator) {
		
	}
	@Override
	public void stop(ProcessInstance process, Long operator) {
		
	}


}
