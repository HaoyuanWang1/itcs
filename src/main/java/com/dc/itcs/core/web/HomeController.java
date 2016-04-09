package com.dc.itcs.core.web;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dc.itcs.core.base.support.ItcsConstants;
import com.dc.itcs.core.context.UserContext;
import com.dc.itcs.event.domain.EventGroupData;
import com.dc.itcs.event.entity.ServiceLevel;
import com.dc.itcs.event.service.EventGroupDataService;
import com.dc.itcs.event.service.ServiceLevelService;
import com.dc.itcs.event.service.ServiceManagerService;
import com.dc.itcs.flow.service.FlowApplyService;
import com.dc.itcs.security.entity.Tenant;
import com.dc.itcs.security.entity.TenantManager;
import com.dc.itcs.security.entity.UserInfo;
import com.dc.itcs.security.service.TenantManagerService;

@Controller
@RequestMapping
public class HomeController {
    @Autowired
    private TenantManagerService tenantManagerService;
    @Autowired
    private ServiceLevelService serviceLevelService;  
    @Autowired
    private EventGroupDataService eventGroupDataService;   
	@Autowired
	private FlowApplyService flowApplyService;
	@Autowired
	private ServiceManagerService serviceManagerService;
	
	
    /**
     * 用户首页
     * @return
     */
	@RequestMapping
	public ModelAndView index(){
		ModelAndView mav = new ModelAndView();
		UserInfo curUser = UserContext.getCurUser();
		// 如果是客户用户登入
		if(!curUser.isSpUser()){
			Tenant tenant = UserContext.getCurUser().getTenant();  
			List<ServiceLevel> serviceLevelList = serviceLevelService.findByStateAndTenant_Id(ItcsConstants.STATE_ON,tenant.getId());	
			List<TenantManager> tenantManagerlist = tenantManagerService.findByTenant_Id(tenant.getId());
			mav.addObject("tenantManagerlist",tenantManagerlist);
			mav.addObject("serviceLevelList",serviceLevelList);
			mav.setViewName("userIndex");
		}else{	//如果是服务商用户登入
			EventGroupData egd = eventGroupDataService.findEventGroupDataByAuth(curUser);
			//第一个柱状图
			mav.addObject("tenantGroupDatas",egd.getTenantGroupDatas());
			//第二个柱状图
			mav.addObject("userGroupDatas",egd.getUserGroupDatas());
			mav.setViewName("spUserIndex");
		}
		
		return mav;
	}
	@RequestMapping(value="/login")
	public ModelAndView login(){
		ModelAndView mav = new ModelAndView();
		if(UserContext.getCurUser()!=null){
			mav.setViewName("redirect:/");
		}else{
			mav.setViewName("/login");
		}
		return mav;
	}
	
	@RequestMapping(value="/authError")
	public ModelAndView authError(){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/authError");
		return mav;
	}
}
