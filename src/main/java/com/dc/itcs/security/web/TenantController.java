package com.dc.itcs.security.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dc.flamingo.core.support.PageParam;
import com.dc.flamingo.core.web.AjaxResult;
import com.dc.flamingo.core.web.WebParamInfo;
import com.dc.itcs.core.base.support.ItcsConstants;
import com.dc.itcs.event.entity.ServiceLevel;
import com.dc.itcs.event.entity.ServiceManager;
import com.dc.itcs.event.entity.ServiceType;
import com.dc.itcs.event.service.ServiceLevelService;
import com.dc.itcs.event.service.ServiceManagerService;
import com.dc.itcs.event.service.ServiceTypeService;
import com.dc.itcs.security.entity.Tenant;
import com.dc.itcs.security.entity.TenantManager;
import com.dc.itcs.security.entity.UserInfo;
import com.dc.itcs.security.service.TenantManagerService;
import com.dc.itcs.security.service.TenantService;
import com.dc.itcs.security.service.UserInfoService;

/**
 * 客户控制器
 *
 */
@Controller
@RequestMapping("/tenant")
public class TenantController {
	
	@Autowired
	private TenantService tenantService;
	@Autowired
	private TenantManagerService tenantManagerService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private ServiceTypeService serviceTypeService;
	@Autowired
	private ServiceLevelService serviceLevelService;
	@Autowired
	private ServiceManagerService serviceManagerService;    

	/**
	 * 客户信息管理
	 * @param webParamInfo
	 * @return
	 */
	@RequestMapping("/tenantPage")
	public ModelAndView tenantPage(WebParamInfo webParamInfo){
		Map<String, Object> param = webParamInfo.getParamStartingWith("search_");
		PageParam pageable = webParamInfo.getPageable();
		Page<Tenant> page = tenantService.findApplyForPage(pageable, param);
		ModelAndView mav = new ModelAndView();
		mav.addObject("page", page);
		mav.addObject("searchParam", webParamInfo.getParam());
		mav.setViewName("/tenant/tenant_list");
		return mav;
	}
	
	
	/**
	 * 编辑客户信息
	 * @Methods Name tenantEdit
	 * @Create In 2015年8月13日 By luzm1
	 * @param tenantId
	 * @return ModelAndView
	 */
	@RequestMapping("/tenantEdit/{tenantId}")
	public ModelAndView tenantEdit(@PathVariable Long tenantId){
		ModelAndView mav = new ModelAndView(); 
		Tenant tenant = tenantService.findTenantById(tenantId);
		
		if (tenant == null) {
			tenant = new Tenant();
		} else {
			List<ServiceType> serviceTypeList = serviceTypeService.findByTenant_Id(tenantId);
			List<ServiceLevel> serviceLevelList = serviceLevelService.findByTenant_Id(tenant.getId());
			List<TenantManager> tenantManagerList = tenantManagerService.findByTenant_Id(tenantId);
			mav.addObject("serviceTypeList", serviceTypeList);
			mav.addObject("tenantManagerList", tenantManagerList);
			mav.addObject("serviceLevelList", serviceLevelList);
		}
		mav.addObject("tenant", tenant);
		mav.setViewName("/tenant/tenant_edit");
		return mav;
	}
	
	
	 /**
	  * 选择用户是隶属于客户还是服务商
	  * @Methods Name organizationSelect
	  * @Create In 2015年8月13日 By luzm1
	  * @param size
	  * @param searchKey
	  * @return AjaxResult
	  */
	@RequestMapping("/organizationSelect")
	@ResponseBody
	public AjaxResult organizationSelect(Integer size, String searchKey){
		List<Tenant> list = tenantService.findAllTenantsMess(searchKey,size,null);
		return AjaxResult.objectResult(list);
	}
	
	/**
	 * 客户--保存
	 * @Methods Name tanantSave
	 * @Create In 2015年8月13日 By luzm1
	 * @param tenant
	 * @return AjaxResult
	 */
	@RequestMapping("/tenantSave")
    @ResponseBody
    public AjaxResult tanantSave(Tenant tenant) {
		/**客户名称必须全局唯一*/
		Tenant tenantAfter = tenantService.findTenantByName(tenant.getName());
		if(tenantAfter != null){
			if(tenant.getId() == null){ /**新增情况*/
				if(tenantAfter != null){
					return AjaxResult.objectResult("exist");
				}
			}else{/**编辑情况*/
				if(tenant.getId() != tenantAfter.getId()){
					return AjaxResult.objectResult("exist");
				}
			}
		}
		tenant.setState(ItcsConstants.STATE_OFF);
  		tenant = tenantService.saveApply(tenant);
        return AjaxResult.objectResult(tenant.getId());
    }
	
	//启用停用按钮
	@RequestMapping("/startTenant/{tenantId}/{state}")
	@ResponseBody
	public AjaxResult startTenant(@PathVariable Long tenantId,@PathVariable Integer state){
		Tenant tenant=tenantService.findTenantById(tenantId);
		/**
		 * 如果启用并且组织类型为客户，需检查客户经理、服务类型等设置
		 */
		if(ItcsConstants.STATE_ON == state && Tenant.TYPE_C==tenant.getType()){
			List<ServiceType> serviceTypeList = serviceTypeService.findByStateAndTenant_Id(ItcsConstants.STATE_ON,tenantId);
			List<ServiceLevel> serviceLevelList = serviceLevelService.findByStateAndTenant_Id(ItcsConstants.STATE_ON,tenant.getId());
			List<TenantManager> tenantManagerList = tenantManagerService.findByTenant_Id(tenantId);
			if(serviceTypeList.size()==0||serviceLevelList.size()==0||tenantManagerList.size()==0){
				return AjaxResult.objectResult("error");
			}
		}
		tenant.setState(state);
		tenant = tenantService.saveApply(tenant);
		return AjaxResult.successResult();
	}

	/**
	 * 根据服务类型ID查询服务经理信息
	 * @Methods Name getServiceManagerByServiceTypeId
	 * @Create In 2015年8月13日 By luzm1
	 * @param serviceTypeId
	 * @return AjaxResult
	 */
	@RequestMapping("/getServiceManagerByServiceTypeId/{serviceTypeId}")
	@ResponseBody
	public AjaxResult getServiceManagerByServiceTypeId(@PathVariable Long serviceTypeId){
		List<UserInfo> users = new ArrayList<UserInfo>();
		ServiceType serviceType = serviceTypeService.findById(serviceTypeId);
		List<ServiceManager> serviceManagers = serviceManagerService.findByType_Id(serviceTypeId);
		for(ServiceManager sm :serviceManagers){
			UserInfo user = userInfoService.findById(sm.getUserInfo().getId());
			users.add(user);
		}
		AjaxResult ajaxResult = AjaxResult.successResult();
		
		ajaxResult.put("users", users);
		ajaxResult.put("serviceType", serviceType);
		return ajaxResult;
	}
	 	
  
  	/**
  	 * 查询所有客户信息
  	 * @Methods Name findAllTenants
  	 * @Create In 2015年8月13日 By luzm1
  	 * @param searchKey
  	 * @param size
  	 * @return AjaxResult
  	 */
  	@RequestMapping("/findAllTenantsMess")
	@ResponseBody
	public AjaxResult findAllTenantsMess(String searchKey,Integer size){
		List<Tenant> tenantList= tenantService.findAllTenantsMess(searchKey,size,Tenant.TYPE_C);
		return AjaxResult.objectResult(tenantList);
	}
  	
}
