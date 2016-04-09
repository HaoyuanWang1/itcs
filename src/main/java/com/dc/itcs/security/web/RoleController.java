package com.dc.itcs.security.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dc.flamingo.core.web.AjaxResult;
import com.dc.flamingo.core.web.WebParamInfo;
import com.dc.itcs.security.entity.Resource;
import com.dc.itcs.security.entity.RoleInfo;
import com.dc.itcs.security.service.RoleInfoService;
import com.dc.itcs.security.service.RoleResourceService;

/**
 * 角色控制器
 * @author lee
 *
 */
@Controller
@RequestMapping("/role")
public class RoleController {
	@Autowired
	private RoleInfoService roleService;
	@Autowired
	private RoleResourceService roleResourceService;
	
	@RequestMapping("/page")
	public ModelAndView page(WebParamInfo webParamInfo){
		Map<String, Object> param = webParamInfo.getParamStartingWith("search_");
		Pageable pageable = webParamInfo.getPageable();
		Page<RoleInfo> page = roleService.findByPage(pageable, param);
		ModelAndView mav = new ModelAndView();
		mav.addObject("page", page);
		mav.addObject("searchParam", webParamInfo.getParam());
		mav.setViewName("/security/role_list");
		return mav;
	}
	@RequestMapping("/edit/{id}")
	public ModelAndView edit(@PathVariable Long id){
		ModelAndView mav = new ModelAndView();
		RoleInfo role = roleService.findById(id);
		List<Resource> resourceList = roleResourceService.findResourceByRoleId(id);
		mav.addObject("role", role);
		mav.addObject("resources", resourceList);
		mav.setViewName("/security/role_edit");
		return mav;
	}
	@RequestMapping("/save")
	@ResponseBody
	public AjaxResult save(RoleInfo roleInfo,String resources){
		roleInfo = roleService.saveRoleAndResource(roleInfo,resources);
		return AjaxResult.objectResult(roleInfo);
	}
}
