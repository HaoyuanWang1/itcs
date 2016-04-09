package com.dc.itcs.core.web.taglib;

import java.io.IOException;

import javax.servlet.jsp.tagext.TagSupport;

import com.dc.flamingo.core.utils.StrUtils;
import com.dc.itcs.flow.FlowAuth;

/**
 * 流程控制按钮
 * @author hujx
 *
 */
@SuppressWarnings("serial")
public class ProcessButtonTag extends TagSupport {
	
	private static final String TYPE_SIGN = "sign";						// 审批
	private static final String TYPE_BACK = "back";						// 驳回
	private static final String TYPE_TRANSFER = "transfer";				// 转办
	private static final String TYPE_ADDSIGN = "addSign";				// 加签
	private static final String TYPE_CANCELADDSIGN = "cancelAddSign";	// 取消加签
	private static final String TYPE_CANCEL = "cancel";					// 撤单
	private static final String TYPE_END = "end";						// 中止
	private static final String TYPE_FREEJUMP = "freeJump";				// 自由跳转

	// 2015-03-17 15:44:45新增两个操作按钮
	private static final String TYPE_ADDSIGNAGREE = "addSignAgree";		// 加签同意
	private static final String TYPE_ADDSIGNBACK = "addSignBack";		// 加签驳回
	
	private String type;			// 操作类型
	private String onclick;			// 按钮点击事件
	private String className;		// 按钮自定义样式
	private String target;			// 业务表单ID
	private Boolean judge = true;	// 自定义判断(权限控制+自定义判断==>按钮是否生成)
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOnclick() {
		return onclick;
	}
	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public Boolean getJudge() {
		return judge;
	}
	public void setJudge(Boolean judge) {
		this.judge = judge;
	}
	private void setButtonCommon(StringBuffer button) {
		button.append("<input type=\"button\"");
		if (StrUtils.isNotEmpty(className)) {
			button.append(" class=\"btn btn-diy flow-btn" + className + "\"");
		} else {
			button.append(" class=\"btn btn-diy flow-btn\"");
		}
		
		if (StrUtils.isNotEmpty(onclick)) {
			button.append(" data-event=\"" + onclick + "\"");
		}
		button.append(" data-listen=\"flowBtnClick\"");
		
		if (StrUtils.isNotEmpty(target)) {
			button.append(" data-target=\"" + target + "\"");
		}
	}
	
	private void setButton(StringBuffer button, FlowAuth flowAuth) {
		
		switch (type) {
		case TYPE_SIGN:
			if (flowAuth.isCanSign()) {
				setButtonCommon(button);
				button.append(" data-modal=\"#flowSignDialog\" value=\"通过\" />");
			}
			break;
		case TYPE_BACK:
			if (flowAuth.isCanBack()) {
				setButtonCommon(button);
				button.append(" data-modal=\"#flowBackDialog\" value=\"驳回\" />");
			}
			break;
		case TYPE_TRANSFER:
			if (flowAuth.isCanTransfer()) {
				setButtonCommon(button);
				button.append(" data-modal=\"#flowTransferDialog\" value=\"转办\" />");
			}
			break;
		case TYPE_ADDSIGN:
			if (flowAuth.isCanAddSign()) {
				setButtonCommon(button);
				button.append(" data-modal=\"#flowAddSignDialog\" value=\"加签\" />");
			}
			break;
		case TYPE_CANCELADDSIGN:
			if (flowAuth.isCanCancelAddSign()) {
				setButtonCommon(button);
				button.append(" data-modal=\"#flowCancelAddSignDialog\" value=\"取消加签\" />");
			}
			break;
		case TYPE_CANCEL:
			if (flowAuth.isCanCancel()) {
				setButtonCommon(button);
				button.append(" data-modal=\"#flowCancelDialog\" value=\"撤单\" />");
			}
			break;
		case TYPE_END:
			if (flowAuth.isCanEnd()) {
				button.append(" data-modal=\"#flowEndDialog\" value=\"中止\" />");
			}
			break;
		case TYPE_FREEJUMP:
			if (flowAuth.isCanFreeJump()) {
				setButtonCommon(button);
				button.append(" data-modal=\"#flowFreeJumpDialog\" value=\"自由跳转\" />");
			}
			break;
		case TYPE_ADDSIGNAGREE:
			if (flowAuth.getIsAddSignTask()) {
				setButtonCommon(button);
				button.append(" data-modal=\"#flowAddSignAgreeDialog\" value=\"加签同意\" />");
			}
			break;
		case TYPE_ADDSIGNBACK:
			if (flowAuth.getIsAddSignTask()) {
				setButtonCommon(button);
				button.append(" data-modal=\"#flowAddSignBackDialog\" value=\"加签驳回\" />");
			}
			break;
		default:
			break;
		}
	}
	
	public int doStartTag() {
		ProcessOperateTag parent = (ProcessOperateTag) this.getParent();
		if (null != parent && judge) {
			StringBuffer button = new StringBuffer();
			FlowAuth flowAuth = parent.getFlowAuth();
			setButton(button, flowAuth);
			
			try {
				pageContext.getOut().print(button.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			return EVAL_BODY_INCLUDE;
		} else {
			return SKIP_BODY;
		}
	}
	
	public void release() {
		type = null;
		onclick = null;
		className = null;
		judge = true;
		super.release();
	}
}
