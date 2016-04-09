package com.dc.itcs.system.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dc.flamingo.core.web.AjaxResult;
import com.dc.itcs.system.entity.WorkTime;
import com.dc.itcs.system.service.WorkTimeService;

/**
 * 工作时间设置控制器
 * @ClassName: WorkTimeController
 * @Description: TODO
 * @Create In 2014年12月19日 By lee
 */
@Controller
@RequestMapping("/workTime")
public class WorkTimeController {
	@Autowired
	private WorkTimeService workTimeService;
	
	/**
	 * 工作时间展示页
	 * @Methods Name page
	 * @Create In 2014年12月19日 By lee
	 * @return
	 */
	@RequestMapping
	public ModelAndView page(){
		WorkTime workTime = workTimeService.findWorkTime();
		if(workTime==null){
			workTime = new WorkTime();
		}
		ModelAndView mav = new ModelAndView();
		mav.addObject("workTime", workTime);
		mav.setViewName("/admin/tool/workTime");
		return mav;
	}
	/**
	 * 保存工作时间
	 * @Methods Name save
	 * @Create In 2014年12月19日 By lee
	 * @param workTime
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public AjaxResult save(WorkTime workTime){
		workTime = workTimeService.save(workTime);
		return AjaxResult.objectResult(workTime.getId());
	}
}
