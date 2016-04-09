package com.dc.itcs.flow;

import java.io.Serializable;
import java.util.List;

import com.dc.flamingo.workflow.element.Node;
import com.google.common.collect.Lists;

public class FlowAuth implements Serializable{
	private static final long serialVersionUID = 8277709640451445653L;
	private Long processId;		//流程实例ID
	private Long tokenId;		//令牌Id
	private Long taskId;		//任务ID
	private String nodeName;	//环节名称
	private String nodeDesc;	//环节描述
	private List<Node> backNodes = Lists.newArrayList();
	private boolean flowAdmin = false;
	private boolean flowReader = false;
	private boolean flowCreator = false;
	private boolean flowSinger = false;
	
	private boolean canSign = false;
	private boolean canBack = false;
	private boolean canTransfer = false;
	private boolean canAddSign = false;
	private boolean canCancelAddSign = false;
	private boolean canCancel = false;
	private boolean canEnd = false;
	private boolean canFreeJump = false;
	private boolean isAddSignTask = false;

	public Long getProcessId() {
		return processId;
	}
	public void setProcessId(Long processId) {
		this.processId = processId;
	}
	public Long getTokenId() {
		return tokenId;
	}
	public void setTokenId(Long tokenId) {
		this.tokenId = tokenId;
	}
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getNodeDesc() {
		return nodeDesc;
	}
	public void setNodeDesc(String nodeDesc) {
		this.nodeDesc = nodeDesc;
	}
	public List<Node> getBackNodes() {
		return backNodes;
	}
	public void setBackNodes(List<Node> backNodes) {
		this.backNodes = backNodes;
	}
	public boolean isFlowAdmin() {
		return flowAdmin;
	}
	public void setFlowAdmin(boolean flowAdmin) {
		this.flowAdmin = flowAdmin;
	}
	public boolean isFlowReader() {
		return flowReader;
	}
	public void setFlowReader(boolean flowReader) {
		this.flowReader = flowReader;
	}
	public boolean isFlowCreator() {
		return flowCreator;
	}
	public void setFlowCreator(boolean flowCreator) {
		this.flowCreator = flowCreator;
	}
	public boolean isFlowSinger() {
		return flowSinger;
	}
	public void setFlowSinger(boolean flowSinger) {
		this.flowSinger = flowSinger;
	}
	public boolean isCanSign() {
		return canSign;
	}
	public void setCanSign(boolean canSign) {
		this.canSign = canSign;
	}
	public boolean isCanBack() {
		return canBack;
	}
	public void setCanBack(boolean canBack) {
		this.canBack = canBack;
	}
	public boolean isCanTransfer() {
		return canTransfer;
	}
	public void setCanTransfer(boolean canTransfer) {
		this.canTransfer = canTransfer;
	}
	public boolean isCanAddSign() {
		return canAddSign;
	}
	public void setCanAddSign(boolean canAddSign) {
		this.canAddSign = canAddSign;
	}
	public boolean isCanCancelAddSign() {
		return canCancelAddSign;
	}
	public void setCanCancelAddSign(boolean canCancelAddSign) {
		this.canCancelAddSign = canCancelAddSign;
	}
	public boolean isCanCancel() {
		return canCancel;
	}
	public void setCanCancel(boolean canCancel) {
		this.canCancel = canCancel;
	}
	public boolean isCanEnd() {
		return canEnd;
	}
	public void setCanEnd(boolean canEnd) {
		this.canEnd = canEnd;
	}
	public boolean isCanFreeJump() {
		return canFreeJump;
	}
	public void setCanFreeJump(boolean canFreeJump) {
		this.canFreeJump = canFreeJump;
	}
	public boolean getIsAddSignTask() {
		return isAddSignTask;
	}
	public void setIsAddSignTask(boolean isAddSignTask) {
		this.isAddSignTask = isAddSignTask;
	}
	
}
