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
import com.dc.itcs.security.entity.UserInfo;
import com.dc.itcs.security.service.UserInfoService;

/**
 * 用户任务分派
 * 如果是动态指派，参数规则为例：{entity.createUser.id}
 * 如果直接指定用户uid，则参数规则为例: limingn,liuyingab
 * @author lee
 *
 */
@Handler(name="用户级(默认)")
@Transactional
@Component("userTaskAssignHandler")
public class UserTaskAssignHandler extends TaskAssignHandler{
	@Autowired
	private UserInfoService userService;
	
	public List<List<Long>> getActors(Execution execution, TaskNode taskNode) {
		String assignParam = taskNode.getAssignParam();
		List<List<Long>> actors = new ArrayList<List<Long>>();
		List<Long> userIds = new ArrayList<Long>();
		if(assignParam.indexOf("{")>-1){	//如果是实体属性参数
			ProcessInstance process = execution.getProcess();
			String entityClass = process.getEntityClass();
			Long entityId = process.getEntityId();
			assignParam = assignParam.replace("{", "");
			assignParam = assignParam.replace("}", "");
			assignParam = assignParam.replace("entity.", "");
			Long acotrId = (Long)this.getEntityProperty(entityClass, entityId, assignParam);
			userIds.add(acotrId);
			actors.add(userIds);
		}else{	//如果是直接指定的人员
			List<String> uids = StrUtils.splitToList(assignParam, ",");
			for(String uid : uids){
				UserInfo user = userService.findByUid(uid);
				if(user!=null){
					userIds.add(user.getId());
				}else{
					
				}
			}
			actors.add(userIds);
		}
		return actors;
	}

}
