package com.dc.itcs.core.web.taglib;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.dc.flamingo.core.context.ContextHolder;
import com.dc.flamingo.workflow.ProcessProgressService;
import com.dc.flamingo.workflow.entity.ProcessProgress;

/**
 * 流程进度条taglib，css基于bootstrap
 * @author hujx
 *
 */
@SuppressWarnings("serial")
public class ProcessProgressTag extends TagSupport {

	private ProcessProgressService getProgressService() {
		return ContextHolder.getBean(ProcessProgressService.class);
	}

	private List<ProcessProgress> list;

	private Long processId; // 流程实例ID
	private String pdName; // 流程定义KEY

	public Long getProcessId() {
		return processId;
	}

	public void setProcessId(Long processId) {
		this.processId = processId;
	}

	public String getPdName() {
		return pdName;
	}

	public void setPdName(String pdName) {
		this.pdName = pdName;
	}

	private void setProgressList() {
		// 优先通过流程实例ID获取进度列表
		if (0 == processId) {
			list = getProgressService().findProgress(pdName);
			if (null != list && !list.isEmpty()) {
				list.get(0).setState(ProcessProgress.STATE_ACTIVE);
			}
		} else {
			list = getProgressService().findProgress(processId);
		}
	}
	
	/**
	 * 设置实际流程进度样式
	 * @param progress
	 */
	private void setRealStyle(StringBuffer progress) {
		double length = list.size();		// 进度点个数
		int index = 1;					// 状态为ACTIVE的点的序号
		for (ProcessProgress p : list) {
			if (ProcessProgress.STATE_ACTIVE.equals(p.getState())) {
				index = p.getSortNum();
				break;
			}
		}
		
		// 经过的流水线长度
		String streamWidth = ((100 * index) / (length + 1)) + "%";
		// 点阵位置左偏移，一个单元宽度的一半
		String dotMatrixLeft = (50 / (length + 1)) + "%";
		// 每个点单元的宽度
		String dotWidth = (100 / length) + "%";
		// 点阵列表的宽度，默认为总宽减去一个单元的宽度，通过左偏移形成水平居中
		String dotMatrixWidth = ((100 * length) / (length + 1)) + "%";
		
		progress.append("<style type=\"text/css\">");
		progress.append("	.flow-progress .stream { width: " + streamWidth + ";}");
		progress.append("	.flow-progress .dot-matrix { left: " + dotMatrixLeft + "; width: " + dotMatrixWidth + ";}");
		progress.append("	.flow-progress .dot-matrix .dot { width: " + dotWidth + ";}");
		progress.append("</style>");
		
	}
	
	/**
	 * 生成流程进度点阵
	 * @param progress
	 */
	private void generateProgressColumn(StringBuffer progress) {
		progress.append("<ul class=\"unstyled dot-matrix\">");
		
		for (ProcessProgress p : list) {
			String clsName = "dot";
			String dotState = p.getState();
			
			if (ProcessProgress.STATE_PASS.equals(dotState)) {
				clsName += " pass";
			} else if (ProcessProgress.STATE_ACTIVE.equals(dotState)) {
				clsName += " active";
			}
			progress.append("<li class=\"" + clsName + "\">");
			progress.append("	<div class=\"round\"></div>");
			progress.append("	<p class=\"dot-desc\">" + p.getName() + "</p>");
			progress.append("</li>");
		}
		
		progress.append("</ul>");
	}
	
	/**
	 * 绘制流程进度条
	 * @param progress
	 */
	private void drawProgress(StringBuffer progress) {
		String lastNodeState = list.get(list.size() - 1).getState();
		if (ProcessProgress.STATE_ACTIVE.equals(lastNodeState) || ProcessProgress.STATE_PASS.equals(lastNodeState)) {
			progress.append("<div class=\"flow-progress ended\">");
		} else {
			progress.append("<div class=\"flow-progress\">");
		}
		
		// 全路径，灰线框
		progress.append("<div class=\"line\"></div>");
		// 已经过路径，红色实线条
		progress.append("<div class=\"stream\"><div class=\"offset\"></div></div>");
		
		generateProgressColumn(progress);
		
		progress.append("</div>");
	}

	public int doStartTag() throws JspException {
		
		setProgressList();
		
		try {
			StringBuffer progress = new StringBuffer();
			JspWriter out = pageContext.getOut();
			
			if (null != list) {
				setRealStyle(progress);
				drawProgress(progress);
			}
		
			out.print(progress.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return super.doStartTag();
	}

	public void release() {
		processId = null;
		pdName = null;
		list = null;
		super.release();
	}
}
