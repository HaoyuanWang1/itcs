package com.dc.itcs.core.web.taglib;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.dc.flamingo.core.context.ContextHolder;
import com.dc.flamingo.core.utils.StrUtils;
import com.dc.flamingo.workflow.TaskService;
import com.dc.flamingo.workflow.TokenService;
import com.dc.flamingo.workflow.entity.Token;
import com.dc.itcs.flow.entity.FlowOperateLog;
import com.dc.itcs.flow.service.FlowOperateLogService;
import com.dc.itcs.security.entity.UserInfo;
import com.dc.itcs.security.service.UserInfoService;
import com.google.common.collect.Lists;

/**
 * 流程审批历史taglib
 * @author hujx
 *
 */
@SuppressWarnings("serial")
public class ProcessHistoryTag extends TagSupport {
	
	private FlowOperateLogService getflowOperateLogService() {
		return ContextHolder.getBean(FlowOperateLogService.class);
	}
	private List<FlowOperateLog> list;
	
	private List<UserInfo> curAuditUserList;		// 当前环节审批人列表
	private String curNode;							// 当前环节列表
	private Boolean isEnd;							// 流程是否结束
	
	private Long processId;			// 流程实例ID
	private Boolean asc = true;			// 排序方式
	public Long getProcessId() {
		return processId;
	}
	public void setProcessId(Long processId) {
		this.processId = processId;
	}
	public Boolean getAsc() {
		return asc;
	}
	public void setAsc(Boolean asc) {
		this.asc = asc;
	}
	
	private void setHistoryList() {
		list = getflowOperateLogService().findByProcessId(processId, asc);
	}
	
	private void setCurNode() {
		isEnd = "结束".equals(list.get(list.size() - 1).getNodeDesc());
		
		if (!isEnd) {
			TaskService taskService = ContextHolder.getBean(TaskService.class);
			TokenService tokenService = ContextHolder.getBean(TokenService.class);
			
			UserInfoService userService = ContextHolder.getBean(UserInfoService.class);
			Set<Long> actorIds = taskService.findOpenTaskActorByProcessId(processId);
			curAuditUserList = userService.findByIds(actorIds);
			List<Token> tokens = tokenService.findActiveToken(processId);
			List<String> nodeDescs = Lists.newArrayList();
			for(Token t : tokens){
				nodeDescs.add(t.getNodeDesc());
			}
			curNode = StrUtils.join(nodeDescs, ",");
		}
	}
	
	/**
	 * 采用bootstrap的Collapse插件，可自动收起
	 * @param history
	 */
	private void generateHtml(StringBuffer l) {
		String logId = "operateLog" + (new Date()).getTime();
		l.append("<div class=\"accordion operate-log\" id=\"" + logId + "\">");
		l.append("  <div class=\"accordion-group\">");
		l.append("    <div class=\"accordion-heading\">");
		l.append("      <div class=\"accordion-toggle\" data-toggle=\"collapse\" data-parent=\"#" + logId + "\" href=\"#" + logId + "One\">");
		l.append("<i class=\"icon-angle-up operate-open\"></i><i class=\"icon-angle-down operate-closed\"></i> 审批历史</div>");
		l.append("    </div>");
		l.append("    <div class=\"accordion-body collapse in\" id=\"" + logId + "One\">");
		l.append("      <div class=\"accordion-inner\">");
		l.append("        <table class=\"table table-hover table-condensed operate-log-list\">");
	
		for (FlowOperateLog h : list) {
			l.append("<tr>");
			l.append("<td class=\"log-time\">" + h.getOperateTime() + "</td>");
			l.append("<td class=\"log-node\">" + h.getNodeDesc() + "</td>");
			if (null != h.getOperator()) {
				l.append("<td class=\"log-user\">" + formatUser(h.getOperator()) + "</td>");
			} else {
				l.append("<td class=\"log-user\"></td>");
			}
			l.append(getOperateType(h));
			l.append(getOperateComment(h));
			l.append("</tr>");
		}
		
		if (!isEnd) {
			l.append("<tr>");
			l.append("<td>当前环节：" + curNode + "</td>");
			String auditUsers = "";
			for (UserInfo u : curAuditUserList) {
				auditUsers += "，" + formatUser(u);
			}
			if (auditUsers.length() > 0) {
				auditUsers = auditUsers.substring(1);
			}
			l.append("<td colspan=\"4\" class=\"log-cur-users\">" + auditUsers + "</td>");
			l.append("</tr>");
		}
		
		l.append("        </table>");
		l.append("      </div>");
		l.append("    </div>");
		l.append("  </div>");
		l.append("</div>");
	}
	
	private String formatUser(UserInfo userInfo) {
		return userInfo.getUserText();
	}
	
	private String getOperateType(FlowOperateLog log) {
		String type = log.getAction();
		if(StrUtils.isEmpty(type)){
			return "<td class=\"log-type\"></td>";
		}else{
			return "<td class=\"log-type log-sign\">"+type+"</td>";
		}
	}
	
	private String getOperateComment(FlowOperateLog log) {
		String comment = log.getComment();
		if (StrUtils.isEmpty(comment)) {
			return "<td class=\"log-comment\">无审批意见</td>";
		}
		return "<td class=\"log-comment\"><b>审批意见</b>：“" + StrUtils.html2Text(comment) + "”</td>";
	}
	
	@Override
	public int doStartTag() throws JspException {
		setHistoryList();
		try {
			JspWriter out = pageContext.getOut();
			StringBuffer history = new StringBuffer();
			if (null != list && list.size() > 0) {
				setCurNode();
				generateHtml(history);
			}
			
			out.print(history.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return super.doStartTag();
	}
	
	@Override
	public void release() {
		processId = null;
		asc = null;
		list = null;
		curAuditUserList = null;
		curNode = null;
		isEnd = null;
		super.release();
	}
	
}
