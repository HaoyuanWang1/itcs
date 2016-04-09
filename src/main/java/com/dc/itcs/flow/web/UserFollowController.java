package com.dc.itcs.flow.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dc.flamingo.core.web.AjaxResult;
import com.dc.itcs.core.context.UserContext;
import com.dc.itcs.flow.entity.FlowApply;
import com.dc.itcs.flow.service.FlowApplyService;

/**
 * 用户关注控制器
 * @ClassName: UserFollowController
 * @Description: TODO
 * @Create In 2014年11月19日 By lee
 */
@Controller
@RequestMapping("/userFollow")
public class UserFollowController {
	@Autowired
	private FlowApplyService flowApplyService;
	
	/**
	 * 关注
	 * @Methods Name add
	 * @Create In 2014年11月19日 By lee
	 * @param entityClass
	 * @param entityId
	 * @return
	 */
	@RequestMapping(value = "/panel/add")
    public ModelAndView add(String entityClass,Long entityId) {
		ModelAndView mav = new ModelAndView();
		flowApplyService.addFollow(UserContext.getCurUser(),entityClass,entityId);
		FlowApply fa = flowApplyService.findByClassAndId(entityClass, entityId);
		mav.addObject("flowApply", fa);
		mav.setViewName("/userFollow");
        return mav;
    }
	/**
	 * 取消关注
	 * @Methods Name remove
	 * @Create In 2014年11月19日 By lee
	 * @param entityClass
	 * @param entityId
	 * @return
	 */
	@RequestMapping(value = "/panel/remove")
    public ModelAndView remove(String entityClass,Long entityId) {
		ModelAndView mav = new ModelAndView();
		flowApplyService.removeFollow(UserContext.getCurUser(),entityClass,entityId);
		FlowApply fa = flowApplyService.findByClassAndId(entityClass, entityId);
		mav.addObject("flowApply", fa);
		mav.setViewName("/userFollow");
        return mav;
    }
	
	@RequestMapping(value = "/remove")
	@ResponseBody
    public AjaxResult cancelFollow(String entityClass,Long entityId) {
		flowApplyService.removeFollow(UserContext.getCurUser(),entityClass,entityId);
        return AjaxResult.successResult();
    }
}
