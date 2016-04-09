package com.dc.itcs.flow.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dc.flamingo.core.utils.StrUtils;
import com.dc.flamingo.workflow.TaskAssignHandler;
import com.dc.flamingo.workflow.entity.ProcessInstance;
import com.dc.flamingo.workflow.annotation.Handler;
import com.dc.flamingo.workflow.core.Execution;
import com.dc.flamingo.workflow.element.TaskNode;
import com.dc.itcs.security.entity.RoleInfo;
import com.dc.itcs.security.entity.UserRole;
import com.dc.itcs.security.service.RoleInfoService;
import com.dc.itcs.security.service.UserInfoService;
import com.dc.itcs.security.service.UserRoleService;
import com.google.common.collect.Lists;

/**
 * 角色任务分派
 * 如果是动态指派，参数规则为例：{entity.auditRole.id}
 * 如果直接指定角色KEY，则参数规则为例: ROLE_CI_MANAGER
 * @author lee
 *
 */
@Handler(name="角色级(默认)")
@Transactional
@Component("roleTaskAssignHandler")
public class RoleTaskAssignHandler extends TaskAssignHandler{
	@Autowired
	private UserInfoService userService;
	@Autowired
	private RoleInfoService roleService;
	@Autowired
	private UserRoleService userRoleService;
	
	public List<List<Long>> getActors(Execution execution, TaskNode taskNode) {
		String assignParam = taskNode.getAssignParam();
		List<List<Long>> actors = new ArrayList<List<Long>>();
		if(assignParam.indexOf("{")>0){	//如果是实体属性参数
			ProcessInstance process = execution.getProcess();
			String entityClass = process.getEntityClass();
			Long entityId = process.getEntityId();
			assignParam = assignParam.replace("{", "");
			assignParam = assignParam.replace("}", "");
			assignParam = assignParam.replace("entity.", "");
			Long roleId = (Long)this.getEntityProperty(entityClass, entityId, assignParam);
			actors.add(getActorIds(roleId));
		}else{	//如果是直接指定的角色
			List<String> rids = StrUtils.splitToList(assignParam, ",");
			for(String rid : rids){
				rid = rid.replace("[", "");
				rid = rid.replace("]", "");
				RoleInfo role = roleService.findByRid(rid);
				if(role!=null){
					actors.add(getActorIds(role.getId()));
				}
			}
		}
		return actors;
	}

	private List<Long> getActorIds(Long roleId){
		List<UserRole> urs = userRoleService.findByRole_Id(roleId);
		List<Long> list = Lists.newArrayList();
		for(UserRole ur : urs){
			list.add(ur.getUser().getId());
		}
		return list;
	}
}
