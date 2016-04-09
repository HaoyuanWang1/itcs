package com.dc.itcs.event.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dc.flamingo.core.utils.StrUtils;
import com.dc.flamingo.core.web.AjaxResult;
import com.dc.itcs.core.base.support.ItcsConstants;
import com.dc.itcs.event.entity.ServiceManager;
import com.dc.itcs.event.entity.ServiceType;
import com.dc.itcs.event.service.ServiceManagerService;
import com.dc.itcs.event.service.ServiceTypeService;
import com.dc.itcs.security.entity.Tenant;
import com.dc.itcs.security.entity.UserInfo;
import com.dc.itcs.security.service.TenantService;
import com.dc.itcs.security.service.UserInfoService;
/**
 * 服务类型控制器
 * @Class Name ServiceLevelController
 * @Author luzm1
 * @Create In 2015年8月13日
 */
@Controller
@RequestMapping("/serviceType")
public class ServiceTypeController {

	@Autowired
	private ServiceTypeService serviceTypeService;
	@Autowired
	private TenantService tenantService;
	@Autowired
	private ServiceManagerService serviceManagerService;
	@Autowired
	private UserInfoService userInfoService;
	
	/**
	 *  根据客户ID找到服务类型List信息
	 * @Methods Name findServiceTypesByTenantId
	 * @Create In 2015年8月13日 By luzm1
	 * @param tenantId
	 * @return AjaxResult
	 */
	@RequestMapping("/findServiceTypesByTenantId")
	@ResponseBody
	public AjaxResult findServiceTypesByTenantId(Long tenantId){
		List<ServiceType> typeList= serviceTypeService.findByStateAndTenant_Id(ItcsConstants.STATE_ON, tenantId);
		return AjaxResult.objectResult(typeList);
	}
	
	/**
	 * 新增或修改服务类型--保存
	 * @Methods Name serviceTypeSave
	 * @Create In 2015年8月13日 By luzm1
	 * @param tenantID
	 * @param ids
	 * @param serviceType
	 * @return AjaxResult
	 */
  	@RequestMapping("/serviceTypeSave/{tenantID}")
    @ResponseBody
    public AjaxResult serviceTypeSave(@PathVariable Long tenantID, String ids,ServiceType serviceType) {
    	/**最后一个编辑不能取消----开始*/
		if(serviceType.getId() == null){
 			Tenant tenant = tenantService.findTenantById(tenantID);
  			serviceType.setTenant(tenant);
		}else{
			if(serviceType.getId() == ItcsConstants.IS_NOT){
			}else{
				//根据客户的ID，找到服务类型List信息，并且找出有效的
				List<ServiceType> serviceTypeList = serviceTypeService.findByStateAndTenant_Id(ItcsConstants.IS_YES,tenantID);
				//首先判断客户的有效性
				Tenant tenantKu = tenantService.findTenantById(tenantID);
				if(tenantKu != null){
					if(tenantKu.getState() == ItcsConstants.IS_YES){//有效 ---最后一个编辑得有提示
						//编辑等于1的时候
						if(serviceTypeList.size() == ItcsConstants.IS_YES && serviceType.getState() == ItcsConstants.IS_NOT){
							return AjaxResult.objectResult("serviceTypeIsNULL");
						}
					}
				}
			}
		}
		/**最后一个编辑不能取消----结束*/
		
  		serviceType = serviceTypeService.saveServiceType(serviceType);
  		
  		List<ServiceManager> sm = serviceManagerService.findByType_Id(serviceType.getId());
  		for(ServiceManager s : sm){
  			serviceManagerService.delete(s);
  		}
  		if(ids.isEmpty()) {
  			return AjaxResult.objectResult("idsIsNUll");
  		}
  		String[] userids = ids.split(",");
  		for(String userId :userids){
  			UserInfo user = userInfoService.findById(Long.parseLong(userId));
  			ServiceManager serviceManager = new ServiceManager();
  			serviceManager.setType(serviceType);
  			serviceManager.setUserInfo(user);
  			serviceManagerService.saveServiceManager(serviceManager);
  		}
  		
        return AjaxResult.objectResult(tenantID);
    }
  	
 
  	/**
  	 * 新增或修改服务类型--页面
  	 * @Methods Name serviceTypeEdit
  	 * @Create In 2015年8月13日 By luzm1
  	 * @param serviceTypeID
  	 * @param tenantID
  	 * @return ModelAndView
  	 */
    @RequestMapping("/serviceTypeEdit/panel/{serviceTypeID}/{tenantID}")
    public ModelAndView serviceTypeEdit(@PathVariable Long serviceTypeID,@PathVariable Long tenantID) {
    	ModelAndView mav = new ModelAndView();
    	List<String> users = new ArrayList<String>();
    	ServiceType serviceType =  serviceTypeService.findById(serviceTypeID);
    	if(serviceType == null){serviceType = new ServiceType();}
    	List<ServiceManager> serviceManagers = serviceManagerService.findByType_Id(serviceTypeID);
    	if(serviceManagers.size() > 0){
			for(ServiceManager sm :serviceManagers){
				UserInfo user = userInfoService.findById(sm.getUserInfo().getId());
				users.add("" + user.getId());
			}
    	}
		mav.addObject("users", StrUtils.join(users, ","));
        mav.addObject("serviceType", serviceType);
        mav.addObject("tenantID", tenantID);
        mav.setViewName("/tenant/serviceType_edit");
        return mav;
    }
  	
 
    /**
     * 根据服务类型ID查询服务经理
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
  	
}
