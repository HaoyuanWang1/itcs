package com.dc.itcs.flow.aop;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dc.flamingo.core.utils.DateUtils;
import com.dc.flamingo.workflow.FlowContext;
import com.dc.flamingo.workflow.entity.ProcessInstance;
import com.dc.itcs.core.base.entity.FlowEntity;
import com.dc.itcs.core.base.utils.MetadataManager;
import com.dc.itcs.core.context.UserContext;


@Component
@Aspect
@Transactional
public class FlowEntityAspect {
	@Autowired
	private MetadataManager metadataManager;
	/**
	 * 创建流程后
	 * @throws ClassNotFoundException 
	 */
	@AfterReturning(value="execution(com.dc.flamingo.workflow.entity.ProcessInstance com.dc.flamingo.workflow.ProcessInstanceService.createProcess(..))",
			argNames="process", returning="process")
	public void afterStartProcess(ProcessInstance process){
		Long entityId = process.getEntityId();
		String entityClass = process.getEntityClass();
		
		Object obj = metadataManager.getEntity(entityClass, entityId);
		BeanWrapper bw = new BeanWrapperImpl(obj);
		bw.setPropertyValue("state", FlowEntity.STATE_AUDIT);
		bw.setPropertyValue("processId", process.getId());
		Object createUser = bw.getPropertyValue("createUser");
		if(createUser==null){
			bw.setPropertyValue("createUser", UserContext.getCurUser());
		}
		Object createTime = bw.getPropertyValue("createTime");
		if(createTime==null){
			bw.setPropertyValue("createTime", DateUtils.getCurrentDateTimeStr());
		}
		metadataManager.saveEntity(obj);
	}
	/**
	 * 流程实例关闭后
	 * @param process
	 * @param flowOperate
	 */
	@AfterReturning(value="execution(com.dc.flamingo.workflow.entity.ProcessInstance com.dc.flamingo.workflow.ProcessInstanceService.close(..))&&args(*,flowOperate)",
			argNames="process,flowOperate", returning="process")
	public void afterCloseProcess(ProcessInstance process,int flowOperate){
		Long entityId = process.getEntityId();
		String entityClass = process.getEntityClass();
		
		Object obj = metadataManager.getEntity(entityClass, entityId);
		BeanWrapper bw = new BeanWrapperImpl(obj);
		int state = FlowEntity.STATE_FINISH;
		switch(flowOperate){
			case FlowContext.OPERATE_AUDIT : state = FlowEntity.STATE_FINISH;break;
			case FlowContext.OPERATE_BACK : state = FlowEntity.STATE_BACK;break;
			case FlowContext.OPERATE_CANCEL : state = FlowEntity.STATE_CANCEL;break;
			case FlowContext.OPERATE_STOP : state = FlowEntity.STATE_STOP;break;
			case FlowContext.OPERATE_JUMP : state = FlowEntity.STATE_BACK;break;
		}
		bw.setPropertyValue("state", state);
		metadataManager.saveEntity(obj);
	}

}
