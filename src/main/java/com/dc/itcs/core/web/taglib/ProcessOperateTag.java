package com.dc.itcs.core.web.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.dc.flamingo.core.utils.StrUtils;
import com.dc.itcs.flow.FlowAuth;

/**
 * 流程操作taglib，包含按钮控制，审批框操作
 * @author hujx
 */
@SuppressWarnings("serial")
public class ProcessOperateTag extends TagSupport{
	
	private FlowAuth flowAuth;			// 流程权限
	private String className;			// 自定义class样式
	public FlowAuth getFlowAuth() {
		return flowAuth;
	}
	public void setFlowAuth(FlowAuth flowAuth) {
		this.flowAuth = flowAuth;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public int doStartTag() {
		
		try {
			JspWriter out = pageContext.getOut();
			if (StrUtils.isEmpty(className)) {
				out.print("<div class=\"flow-operate\" data-flow-operate>");
			} else {
				out.print("<div class=\"flow-operate " + className + "\" data-flow-operate>");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_BODY_INCLUDE;		// 执行容器标签主体
	}

	public int doEndTag() throws JspException {
		try {
			pageContext.getOut().print("</div>");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.doEndTag();
	}
	
	public void release() {
		flowAuth = null;
		className = null;
		super.release();
	}
}
