package com.dc.itcs.security.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dc.flamingo.core.web.AjaxResult;
import com.dc.itcs.core.base.support.ItcsConstants;
import com.dc.itcs.security.entity.TenantManager;
import com.dc.itcs.security.service.TenantManagerService;

/**
 * 客户经理控制器
 * @name TenantManagerController
 * @author hujxb
 *
 */
@Controller
@RequestMapping("/tenantManager")
public class TenantManagerController {
	@Autowired
	private TenantManagerService tenantManagerService;
	
	/**
  	 * 查找指定客户的客户经理
  	 * @param id
  	 * @return
  	 */
    @RequestMapping("/panel/list/{tenantId}")
    public ModelAndView list(@PathVariable Long tenantId) {
        ModelAndView mav = new ModelAndView();
        List<TenantManager> list = tenantManagerService.findByTenant_Id(tenantId);
        mav.addObject("tenantManagerList", list);
        mav.setViewName("/tenant/tenantManagerList");
        return mav;
    }
	
	/**
	 * 保存客户经理
	 * @param tenant
	 * @return
	 */
  	@RequestMapping("/save")
    @ResponseBody
    public AjaxResult save(TenantManager tenantManager) {
  		List<TenantManager> tenantManagerList = tenantManagerService.findByTenant_Id(tenantManager.getTenant().getId());
  		Long currentManagerId = tenantManager.getTenantManager().getId();
  		for(TenantManager tm : tenantManagerList) {
			if(tm.getTenantManager().getId() == currentManagerId){
				return AjaxResult.errorResult("客户经理已经存在!");
			}
  		}
  		
  		tenantManagerService.save(tenantManager);
        return AjaxResult.successResult();
    }
  	
  	/**
  	 * 删除客户经理--物理删除
  	 * @param id
  	 * @return
  	 */
  	@RequestMapping("/remove/{id}")
    @ResponseBody
  	public AjaxResult remove(@PathVariable Long id) {
  		TenantManager tenantManager = tenantManagerService.findById(id);
  		
  		//根据客户的ID，找到客户经理List信息
  		List<TenantManager> tenantManagerList = null;
  		if(tenantManager != null){
  			tenantManagerList = tenantManagerService.findByTenant_Id(tenantManager.getTenant().getId());
  		}
  		
  		//判断客户是否有效
  		if(tenantManager.getTenant().getState() == ItcsConstants.STATE_ON){//有效情况
  			//当客户经理人数大于1的情况下可以删除
  			//当客户经理人数等于1的情况下，提示您必须再增加一条，方可删除
  			if(tenantManagerList.size() > 1){
  				tenantManagerService.remove(tenantManager); 
  			}
  			if(tenantManagerList.size() == 1){
  				return AjaxResult.objectResult("error");
  			}
  			
  		}else{//无效情况
  			tenantManagerService.remove(tenantManager); 
  		}
  		return AjaxResult.objectResult("success");
  	}
	
}
