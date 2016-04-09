package com.dc.itcs.system.web;

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
import com.dc.itcs.system.entity.WorkDay;
import com.dc.itcs.system.service.WorkDayService;

/**
 * 工作日维护控制器
 * @ClassName: WorkDayController
 * @Create In 2014年11月5日 By lee
 */
@Controller
@RequestMapping("/workDay")
public class WorkDayController {
	@Autowired
	private WorkDayService workDayService;
	
	/**
	 * 分页查询
	 * @Methods Name page
	 * @Create In 2014年11月5日 By lee
	 * @param webParamInfo
	 * @return
	 */
	@RequestMapping
	public ModelAndView page(WebParamInfo webParamInfo){
		Map<String, Object> param = webParamInfo.getParamStartingWith("search_");
		param = this.handTime(param, "GT_targetDay", "LT_targetDay");
		Pageable pageable = webParamInfo.getPageable();
		Page<WorkDay> page = workDayService.findByPage(pageable, param);
		ModelAndView mav = new ModelAndView();
		mav.addObject("page", page);
		mav.addObject("searchParam", webParamInfo.getParam());
		mav.setViewName("/admin/tool/workDay_list");
		return mav;
	}
	
	@RequestMapping("/panel/edit/{id}")
	public ModelAndView edit(@PathVariable Long id){
		ModelAndView mav = new ModelAndView();
		WorkDay wd = workDayService.findById(id);
		if (null == wd) {
			wd = new WorkDay();
		}
		mav.addObject("wd", wd);
		mav.setViewName("/admin/tool/workDay_form");
		return mav;
	}
	
	/**
	 * 保存工作日设置
	 * @Methods Name save
	 * @Create In 2014年11月5日 By lee
	 * @param webParamInfo
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public AjaxResult save(WorkDay workDay){
		workDay = workDayService.save(workDay);
		return AjaxResult.objectResult(workDay);
	}
	
	public Map<String, Object> handTime(Map<String, Object> param, String gteKey, String lteKey) {
		String startDate = "";
    	String endDate = "";
    	if(param.get(gteKey)!=null){
    		startDate = param.get(gteKey) + "";
    		param.put(gteKey, startDate + " 00:00:00");
        }
    	
		if(param.get(lteKey)!=null){
			endDate = param.get(lteKey) + "";
			param.put(lteKey, endDate + " 23:59:59");
		}
		
		return param;
	}
}
