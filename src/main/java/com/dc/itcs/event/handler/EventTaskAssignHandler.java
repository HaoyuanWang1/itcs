package com.dc.itcs.event.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dc.flamingo.workflow.TaskAssignHandler;
import com.dc.flamingo.workflow.annotation.Handler;
import com.dc.flamingo.workflow.core.Execution;
import com.dc.flamingo.workflow.element.TaskNode;
import com.dc.itcs.event.entity.ServiceManager;
import com.dc.itcs.event.entity.ServiceType;
import com.dc.itcs.event.service.EventService;
import com.dc.itcs.event.service.ServiceManagerService;
import com.dc.itcs.security.entity.TenantManager;
import com.dc.itcs.security.entity.UserInfo;
import com.dc.itcs.security.service.TenantManagerService;
import com.dc.itcs.security.service.UserInfoService;
import com.dc.itcs.security.service.UserRoleService;
import com.google.common.collect.Lists;

/**
 * 审批人任务分派
 * 如果是动态指派，参数规则为例：{entity.createUser.id}
 * @author yizm
 *
 */
@Handler(name="审批人处理器")
@Transactional
@Component("EventTaskAssignHandler")
public class EventTaskAssignHandler extends TaskAssignHandler{
	@Autowired
    private EventService EventApplyService;
	@Autowired
    private ServiceManagerService serviceManagerService;
	@Autowired
    private TenantManagerService tenantManagerService;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private EventService eventService;  
	@Autowired
	private UserInfoService userInfoService;
	
	public List<List<Long>> getActors(Execution execution, TaskNode taskNode) {
		List<List<Long>> actors = new ArrayList<List<Long>>();
		Long id = execution.getProcess().getEntityId();
		/**
		 * 客户经理确认环节
		 */
		if("tenantManagerConfirm".equals(taskNode.getName())){
			//得到当前类型
			Long tenantId = EventApplyService.findById(id).getTenant().getId();
			List<TenantManager> managers = tenantManagerService.findByTenant_Id(tenantId);
			List<Long> list = Lists.newArrayList();
			for(TenantManager manager : managers){
				list.add(manager.getTenantManager().getId());
			}
			actors.add(list);
			//保存当前处理人信息
			eventService.saveCurrUserOperation(execution.getProcess().getEntityId(), list);
		}
		/**
		 * 服务经理确认环节
		 */
		if("serviceManagerConfirm".equals(taskNode.getName())){	//服务经理确认环节
			//得到当前类型
			ServiceType serviceType = EventApplyService.findById(id).getServiceType();
			List<ServiceManager> managers = serviceManagerService.findByType_Id(serviceType.getId());
			List<Long> list = Lists.newArrayList();
			for(ServiceManager manager : managers){
				list.add(manager.getUserInfo().getId());
			}
			actors.add(list);
			//保存当前处理人信息
			eventService.saveCurrUserOperation(execution.getProcess().getEntityId(), list);
		}
		
		if("userConfirm".equals(taskNode.getName())){	//客户确认环节
			//得到当前类型
			UserInfo user =eventService.findById(id).getSubmitUser();
			List<Long> list = Lists.newArrayList();			
				list.add(user.getId());		
			actors.add(list);
			//保存当前处理人信息
			eventService.saveCurrUserOperation(execution.getProcess().getEntityId(), list);
		}
		return actors;
	}

}
