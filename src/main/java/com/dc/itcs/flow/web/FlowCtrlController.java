package com.dc.itcs.flow.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dc.flamingo.core.web.AjaxResult;
import com.dc.itcs.core.context.UserContext;
import com.dc.itcs.flow.service.ItcsFlowEngine;
import com.dc.itcs.security.entity.UserInfo;
import com.dc.itcs.security.service.UserInfoService;

/**
 * 后台管理员流程处理
 * @author lee
 *
 */
@Controller
@RequestMapping("/userFlowCtrl")
public class FlowCtrlController {
	@Autowired
	private ItcsFlowEngine itilFlowEngine;
	@Autowired
	private UserInfoService userService;

    /**
     * 审批任务
     * @Methods Name executeTask
     */
    @RequestMapping(value = "/executeTask")
    @ResponseBody
    public AjaxResult executeTask(Long taskId, String comment) {
    	itilFlowEngine.executeTask(taskId, UserContext.getCurUser().getId(), "审批", comment);
        return AjaxResult.successResult();
    }

    /**
     * 撤单
     * @Methods Name cancelProcess
     */
    @RequestMapping(value = "/cancelProcess")
    @ResponseBody
    public AjaxResult cancelProcess(Long taskId, String comment) {
		itilFlowEngine.cancelProcess(taskId, UserContext.getCurUser().getId(),"撤单", comment);
		return AjaxResult.successResult();
    }

    /**
     * 驳回环节
     * @Methods Name backTask
     */
    @RequestMapping(value = "/backTask")
    @ResponseBody
    public AjaxResult backTask(Long taskId, String backNode, String comment) {
    	itilFlowEngine.backExecuteTask(taskId, backNode, UserContext.getCurUser().getId(),"驳回",comment);
        return AjaxResult.successResult();
    }

    /**
     * 任务转办
     * @Methods Name transferTask
     */
    @RequestMapping(value = "/transferTask")
    @ResponseBody
    public AjaxResult transferTask(Long taskId,Long sourceUser, Long transferUser, String comment) {
    	UserInfo sUser = userService.findById(sourceUser);
    	UserInfo tUser = userService.findById(transferUser);
    	itilFlowEngine.transfer(taskId, sUser, tUser,"转办", comment);
        return AjaxResult.successResult();
    }

    /**
     * 任务加签
     * @Methods Name addSignTask
     */
    @RequestMapping(value = "/addSignTask")
    @ResponseBody
    public AjaxResult addSignTask(Long taskId, Long addSignUser) {
    	UserInfo asUser = userService.findById(addSignUser);
    	itilFlowEngine.addSign(taskId, asUser, UserContext.getCurUser(),"加签","");
        return AjaxResult.successResult();
    }

    /**
     * 撤销加签任务
     * @Methods Name cancelAddSign
     */
    @RequestMapping(value = "/cancelAddSign")
    @ResponseBody
    public AjaxResult cancelAddSign(Long taskId) {
        return AjaxResult.successResult();
    }

    /**
     * 自由跳转
     * @Methods Name freeJump
     */
    @RequestMapping(value = "/freeJump")
    @ResponseBody
    public AjaxResult freeJump(Long taskId, String targetNode, String comment) {
    	itilFlowEngine.freeJump(taskId, targetNode, UserContext.getCurUser(),"跳转",comment);
        return AjaxResult.successResult();
    }

    /**
     * 催办
     * 
     * @Methods Name remind
     * @Create In Aug 30, 2011 By lee void
     */
    @RequestMapping(value = "/remind")
    @ResponseBody
    public AjaxResult remind(Long taskId) {
        return AjaxResult.successResult();
    }
}
