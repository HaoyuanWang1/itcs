package com.dc.itcs.flow.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dc.flamingo.core.support.PageParam;
import com.dc.flamingo.core.utils.DateUtils;
import com.dc.flamingo.core.web.AjaxResult;
import com.dc.flamingo.core.web.WebParamInfo;
import com.dc.itcs.core.context.UserContext;
import com.dc.itcs.flow.entity.FlowAgent;
import com.dc.itcs.flow.service.FlowAgentService;

/**
 * 流程代办控制器
 * @author lee
 *
 */
@Controller
@RequestMapping("/flowAgent")
public class FlowAgentController {
	@Autowired
    private FlowAgentService flowAgentService;
	/**
	 * 用户流程代办分页查询列表
	 * @param pageParam
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/user/userPage")
	public ModelAndView page(WebParamInfo webParamInfo) {
		Map<String, Object> param = webParamInfo.getParamStartingWith("search_");
		PageParam pageParam = webParamInfo.getPageable();
		pageParam.setSort(new Sort(Direction.DESC, "startDate"));
		ModelAndView mav = new ModelAndView();
		Page<FlowAgent> page = flowAgentService.findForUserPage(param,pageParam);
		mav.addObject("page", page);
		mav.addObject("pageParam", new HashMap());
		mav.setViewName("/myit/myFlowAgent");
		return mav;
	}
	/**
	 * 保存代办信息
	 * @param flowAgent
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public AjaxResult save(FlowAgent flowAgent) {
		flowAgent.setAuther(UserContext.getCurUser());
		flowAgent.setCreateTime(DateUtils.getCurrentDateTimeStr());
		flowAgent.setStopTime(DateUtils.getTimeStr(DateUtils.convertStringToDate(flowAgent.getEndDate())));
		flowAgent = flowAgentService.saveFlowAgent(flowAgent);
		return AjaxResult.objectResult(flowAgent);
	}
	/**
	 * 删除代办信息
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public AjaxResult delete(Long id) {
		flowAgentService.removeFlowAgent(id);
		return AjaxResult.successResult();
	}
	/**
	 * 中止代办
	 * @param id
	 * @return
	 */
	@RequestMapping("/stop")
	@ResponseBody
	public AjaxResult stop(Long id) {
		flowAgentService.saveFlowAgentStop(id);
		return AjaxResult.successResult();
	}
}
