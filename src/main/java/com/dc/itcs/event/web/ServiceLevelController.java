package com.dc.itcs.event.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dc.flamingo.core.web.AjaxResult;
import com.dc.itcs.core.base.support.ItcsConstants;
import com.dc.itcs.event.entity.ServiceLevel;
import com.dc.itcs.event.service.ServiceLevelService;
import com.dc.itcs.security.entity.Tenant;
import com.dc.itcs.security.service.TenantService;
/**
 * 服务级别控制器
 * @Class Name ServiceLevelController
 * @Author luzm1
 * @Create In 2015年8月13日
 */
@Controller
@RequestMapping("/serviceLevel")
public class ServiceLevelController {

	@Autowired
	private ServiceLevelService serviceLevelService;
	@Autowired
	private TenantService tenantService; 
	
	//@RequestMapping("/serviceLevel")
	/**
	 * 根据客户ID找到服务级别List信息
	 * @Methods Name findServiceLevelsByTenantId
	 * @Create In 2015年8月13日 By luzm1
	 * @param tenantId
	 * @return AjaxResult
	 */
	@RequestMapping("/findServiceLevelsByTenantId")
	@ResponseBody
	public AjaxResult findServiceLevelsByTenantId(Long tenantId){
		List<ServiceLevel> levelList= serviceLevelService.findByStateAndTenant_Id(ItcsConstants.STATE_ON, tenantId);
		return AjaxResult.objectResult(levelList);
	}
	
	
	/**
	 * 新增或修改服务级别--保存
	 * @Methods Name serviceLevelSave
	 * @Create In 2015年8月13日 By luzm1
	 * @param tenantID
	 * @param serviceLevel
	 * @return AjaxResult
	 */
  	@RequestMapping("/serviceLevelSave/{tenantID}")
    @ResponseBody
    public AjaxResult serviceLevelSave(@PathVariable Long tenantID,ServiceLevel serviceLevel) {
  		if(serviceLevel.getId() == null){
  			Tenant tenant = tenantService.findTenantById(tenantID);
  			serviceLevel.setTenant(tenant);
  		}else{
			if(serviceLevel.getId() == ItcsConstants.IS_NOT){
			}else{
				//根据客户的ID，找到服务类型List信息，并且找出有效的
				List<ServiceLevel> serviceLevelList = serviceLevelService.findByStateAndTenant_Id(ItcsConstants.IS_YES,tenantID);
				//首先判断客户的有效性
				Tenant tenantKu = tenantService.findTenantById(tenantID);
				if(tenantKu != null){
					if(tenantKu.getState() == ItcsConstants.IS_YES){//有效 ---最后一个编辑得有提示
						//编辑等于1的时候
						if(serviceLevelList.size() == ItcsConstants.IS_YES && serviceLevel.getState() == ItcsConstants.IS_NOT){
							return AjaxResult.objectResult("error");
						}
					}
				}
			}
  		}
  		serviceLevelService.saveServiceLevl(serviceLevel);
        return AjaxResult.objectResult(tenantID);
    }
	
  	/**
  	 * 新增或修改服务级别--页面
  	 * @Methods Name serviceLevelEdit
  	 * @Create In 2015年8月13日 By luzm1
  	 * @param serviceLevelID
  	 * @param tenantID
  	 * @return ModelAndView
  	 */
    @RequestMapping("/serviceLevelEdit/panel/{serviceLevelID}/{tenantID}")
    public ModelAndView serviceLevelEdit(@PathVariable Long serviceLevelID,@PathVariable Long tenantID) {
    	ModelAndView mav = new ModelAndView();
    	ServiceLevel serviceLevel = serviceLevelService.findById(serviceLevelID);
    	if(serviceLevel == null){
    		serviceLevel = new ServiceLevel();
    	}
        mav.addObject("serviceLevel", serviceLevel);
        mav.addObject("tenantID", tenantID);
        mav.setViewName("/tenant/serviceLevel_edit");
        return mav;
    }
	
}
