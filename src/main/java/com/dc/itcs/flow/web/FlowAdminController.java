package com.dc.itcs.flow.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



import org.springframework.web.servlet.ModelAndView;

import com.dc.flamingo.core.support.PageParam;
import com.dc.flamingo.core.utils.StrUtils;
import com.dc.flamingo.core.web.AjaxResult;
import com.dc.flamingo.core.web.WebParamInfo;
import com.dc.flamingo.workflow.FlowContext;
import com.dc.flamingo.workflow.element.Node;
import com.dc.flamingo.workflow.entity.ProcessDefinition;
import com.dc.itcs.flow.entity.FlowApply;
import com.dc.itcs.flow.entity.WorkItem;
import com.dc.itcs.flow.service.FlowApplyService;
import com.dc.itcs.flow.service.ItcsFlowEngine;
import com.dc.itcs.flow.service.WorkItemService;
import com.dc.itcs.security.entity.UserInfo;
import com.dc.itcs.security.service.UserInfoService;
import com.google.common.collect.Lists;

/**
 * 后台管理员流程处理
 * @author lee
 *
 */
@Controller
@RequestMapping("/flow/ctrl")
public class FlowAdminController {
	@Autowired
	private ItcsFlowEngine itilFlowEngine;
	@Autowired
	private WorkItemService workItemService;
	@Autowired
	private FlowApplyService flowApplyService;
	@Autowired
	private UserInfoService userService;

	/**
	 * 所有待办工作项查询
	 * @Methods Name workItemPage
	 * @Create In 2014年9月23日 By lee
	 * @param webParamInfo
	 * @return
	 */
	@RequestMapping("/workItemPage")
	public ModelAndView workItemPage(WebParamInfo webParamInfo){
		Map<String, Object> param = webParamInfo.getParamStartingWith("search_");
		PageParam pageable = webParamInfo.getPageable();
		pageable.setSort(new Sort(Direction.DESC, "createTime"));
		Page<WorkItem> page = workItemService.findForPage(pageable, param);
		ModelAndView mav = new ModelAndView();
		mav.addObject("page", page);
		mav.addObject("searchParam", webParamInfo.getParam());
		mav.setViewName("/flow/workItem_list");
		return mav;
	}
	
	/**
	 * 所有流程申请查询
	 * @Methods Name applyPage
	 * @Create In 2014年9月23日 By lee
	 * @param webParamInfo
	 * @return
	 */
	@RequestMapping("/applyPage")
	public ModelAndView applyPage(WebParamInfo webParamInfo){
		Map<String, Object> param = webParamInfo.getParamStartingWith("search_");
		PageParam pageable = webParamInfo.getPageable();
		pageable.setSort(new Sort(Direction.DESC, "createTime"));
		Page<FlowApply> page = flowApplyService.findByPage(pageable, param);
		ModelAndView mav = new ModelAndView();
		mav.addObject("page", page);
		mav.addObject("searchParam", webParamInfo.getParam());
		mav.setViewName("/flow/apply_list");
		return mav;
	}
	
	/**
	 * 任务处理人
	 * @param taskId
	 * @return
	 */
	@ResponseBody
    @RequestMapping("/taskActors")
    public AjaxResult taskActors(String actorIds) {
        List<String> actorIdList = StrUtils.splitToList(actorIds, ",", "【", "】");
        List<UserInfo> actors = Lists.newArrayList();
        for(String idStr : actorIdList){
        	actors.add(userService.findById(Long.valueOf(idStr)));
        }
        return AjaxResult.objectResult(actors);
    }
	
	/**
	 * 给前台管理员提供的任务管理页面
	 * @Methods Name taskPage
	 * @Create In 2014年12月3日 By lee
	 * @param webParamInfo
	 * @return
	 */
	@RequestMapping("/taskPage")
	public ModelAndView taskPage(WebParamInfo webParamInfo){
		Map<String, Object> param = webParamInfo.getParamStartingWith("search_");
		param.put("EQ_taskState", 1);
		PageParam pageable = webParamInfo.getPageable();
		pageable.setSort(new Sort(Direction.DESC, "createTime"));
		Page<WorkItem> page = workItemService.findByPageForAdmin(pageable, param);
		ModelAndView mav = new ModelAndView();
		mav.addObject("page", page);
		mav.addObject("searchParam", webParamInfo.getParam());
		mav.setViewName("/flow/admin_task_list");
		return mav;
	}
	
	/**
	 * 审批对话框
	 * @param taskId
	 * @return
	 */
	@RequestMapping("/panel/sign/{taskId}")
	public ModelAndView sign(@PathVariable Long taskId){
		ModelAndView mav = new ModelAndView();
		mav.addObject("taskId", taskId);
		mav.setViewName("/flow/sign_modal");
		return mav;
	}
	
	/**
	 * 驳回对话框
	 * @param taskId
	 * @return
	 */
	@RequestMapping("/panel/back/{taskId}")
	public ModelAndView back(@PathVariable Long taskId){
		ModelAndView mav = new ModelAndView();
		List<Node> backNodes = itilFlowEngine.taskBackNode(taskId);
		mav.addObject("backNodes", backNodes);
		mav.addObject("taskId", taskId);
		mav.setViewName("/flow/back_modal");
		return mav;
	}
	
	/**
	 * 撤单对话框
	 * @param taskId
	 * @return
	 */
	@RequestMapping("/panel/end/{taskId}")
	public ModelAndView end(@PathVariable Long taskId){
		ModelAndView mav = new ModelAndView();
		mav.addObject("taskId", taskId);
		mav.setViewName("/flow/end_modal");
		return mav;
	}
	
	/**
	 * 转办对话框
	 * @param taskId
	 * @return
	 */
	@RequestMapping("/panel/transfer/{taskId}")
	public ModelAndView transfer(@PathVariable Long taskId){
		ModelAndView mav = new ModelAndView();
		WorkItem workItem = workItemService.findByTaskId(taskId);
		List<String> actorIdList = StrUtils.splitToList(workItem.getActorIds(), ",", "【", "】");
		List<UserInfo> actors = Lists.newArrayList();
        for(String idStr : actorIdList){
        	actors.add(userService.findById(Long.valueOf(idStr)));
        }
        mav.addObject("actors", actors);
        mav.addObject("taskId", taskId);
		mav.setViewName("/flow/transfer_modal");
		return mav;
	}
	
	/**
	 * 自由对话框
	 * @param taskId
	 * @return
	 */
	@RequestMapping("/panel/free/{taskId}")
	public ModelAndView free(@PathVariable Long taskId){
		ModelAndView mav = new ModelAndView();
		WorkItem workItem = workItemService.findByTaskId(taskId);
		ProcessDefinition pd = FlowContext.getPd(workItem.getPdId());
		mav.addObject("jumpNodes", pd.getNodes().values());
		mav.addObject("taskId", taskId);
		mav.setViewName("/flow/free_modal");
		return mav;
	}
	
}
