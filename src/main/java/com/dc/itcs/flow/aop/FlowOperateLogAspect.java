package com.dc.itcs.flow.aop;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dc.flamingo.core.utils.DateUtils;
import com.dc.flamingo.workflow.entity.ProcessInstance;
import com.dc.itcs.flow.service.FlowOperateLogService;

@Component
@Aspect
@Transactional
public class FlowOperateLogAspect {
	@Autowired
	private FlowOperateLogService flowOperateLogService;

	/**
	 * 流程实例关闭后
	 * @param process
	 * @param flowOperate
	 */
	@AfterReturning(value="execution(com.dc.flamingo.workflow.entity.ProcessInstance com.dc.flamingo.workflow.ProcessInstanceService.close(..))&&args(*,flowOperate)",
			argNames="process,flowOperate", returning="process")
	public void afterCloseProcess(ProcessInstance process,int flowOperate){
		flowOperateLogService.saveOperateLog(process.getId(), "结束", DateUtils.getCurrentDateTimeStr(), null, "结束", "");
	}
}
