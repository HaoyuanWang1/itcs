package com.dc.itcs.security.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dc.flamingo.core.context.ContextHolder;
import com.dc.itcs.core.base.entity.RoleActor;
import com.dc.itcs.core.base.service.RoleActorService;
import com.dc.itcs.security.entity.UserInfo;
import com.dc.itcs.security.service.UserInfoService;
import com.google.common.collect.Lists;

/**
 * 角色参与者控制器
 * @author lee
 *
 */
@Controller
@RequestMapping("/roleActor")
public class RoleActorController {
	@Autowired
	private UserInfoService userService;
	@RequestMapping("/findForUser")
	public ModelAndView findForUser(Long userId){
		UserInfo userInfo = userService.findById(userId);
		List<RoleActor> list = Lists.newArrayList();
		Map<String,RoleActorService> services = ContextHolder.getBeansOfType(RoleActorService.class);
		for(RoleActorService service : services.values()){
			list.addAll(service.getRoleActorByUser(userInfo));
		}
		ModelAndView mav = new ModelAndView();
		mav.addObject("roleActors", list);
		mav.setViewName("/admin/security/role_actor");
		return mav;
	}
	@RequestMapping("/change")
	public ModelAndView change(Long sourceUserId,Long targetUserId){
		UserInfo sourceUser = userService.findById(sourceUserId);
		UserInfo targetUser = userService.findById(targetUserId);
		Map<String,RoleActorService> services = ContextHolder.getBeansOfType(RoleActorService.class);
		for(RoleActorService service : services.values()){
			service.changeRoleActor(sourceUser, targetUser);
		}
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/security/role_actor");
		return mav;
	}
	@RequestMapping("/delete")
	public ModelAndView delete(Long userId){
		UserInfo userInfo = userService.findById(userId);
		Map<String,RoleActorService> services = ContextHolder.getBeansOfType(RoleActorService.class);
		for(RoleActorService service : services.values()){
			service.deleteRoleActor(userInfo);
		}
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/security/role_actor");
		return mav;
	}
}
