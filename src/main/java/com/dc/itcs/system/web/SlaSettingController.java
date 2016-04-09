package com.dc.itcs.system.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dc.flamingo.core.web.AjaxResult;
import com.dc.itcs.event.entity.ServiceLevel;
import com.dc.itcs.system.service.SlaSettingService;

/**
 * SLA设置控制器
 * @ClassName: SlaSettingController
 * @Description: TODO
 * @Create In 2014年11月17日 By lee
 */
@Controller
@RequestMapping("/slaSetting")
public class SlaSettingController {
	@Autowired
	private SlaSettingService slaSettingService;
	
	/**
	 * 查询所有SLA设置
	 * @Methods Name index
	 * @Create In 2014年11月17日 By lee
	 * @return
	 */
	@RequestMapping
	public ModelAndView index(){
		List<ServiceLevel> list = slaSettingService.findAll();
		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		mav.setViewName("/admin/tool/sla_list");
		return mav;
	}
	
	/**
	 * 编辑SLA设置
	 * @Methods Name edit
	 * @Create In 2014年11月17日 By lee
	 * @param key
	 * @return
	 */
	@RequestMapping("/panel/edit/{key}")
	public ModelAndView edit(@PathVariable Long key){
		ModelAndView mav = new ModelAndView();
		ServiceLevel slaSetting = slaSettingService.findByKey(key);
		mav.addObject("slaSetting", slaSetting);
		mav.setViewName("/admin/tool/sla_form");
		return mav;
	}
	
	/**
	 * 保存SLA设置
	 * @Methods Name save
	 * @Create In 2014年11月17日 By lee
	 * @param slaSetting
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public AjaxResult save(ServiceLevel serviceLevel){
		slaSettingService.save(serviceLevel);
		return AjaxResult.successResult();
	}
}
