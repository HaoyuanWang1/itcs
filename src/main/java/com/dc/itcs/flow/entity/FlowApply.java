package com.dc.itcs.flow.entity;


import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.dc.flamingo.core.data.IdEntity;
import com.dc.flamingo.core.utils.StrUtils;
import com.dc.itcs.core.context.UserContext;
import com.dc.itcs.security.entity.UserInfo;
import com.google.common.collect.Sets;


/**
 * 申请单记录实体
 * @ClassName: FlowApply
 * @Description: TODO
 * @Create In 2014年11月18日 By lee
 */
@Entity
@Table(name = "ss_flow_apply") 
public class FlowApply extends IdEntity{
	private static final long serialVersionUID = -6333597268902055561L;
	public static final int PENDING_TRUE = 1;	//挂起状态
	public static final int PENDING_FALSE = 0;	//非挂起状态
	
	private Long applyId;		//关联实体ID
	private String applyClass;	//关联实体类
	private String applyNum;	//申请编号
	private String applyName;	//申请名称
	private String flowDesc;	//申请流程
	private Integer state;		//状态
	private String nodeTime;	//进入环节时间
	private String nodeName;	//环节名称
	private String nodeDesc;	//环节描述
	private String signers;		//待处理人
	private String signerIds;	//待处理人ID
	private String operators;	//已处理人ID
	private Long processId;		//对应流程实例ID
	private UserInfo createUser;//创建人
	private String createTime;	//创建时间
	private String applyUrl;	//申请单链接
	private String deleteUrl;	//删除链接
	private String slaKey;		//SLA设置KEY
	private String slaBeginTime;//SLA开始计算时间
	private String slaEndTime;	//SLA结束计算时间
	private Long pendingTime;	//SLA已挂起时长
	private String pendingStartTime;//挂起开始时间
	private Integer pendingFlag;	//挂起标志
	private Integer infoType;
	
	
	public Integer getInfoType() {
		return infoType;
	}
	public void setInfoType(Integer infoType) {
		this.infoType = infoType;
	}
	protected String followUserIds;	//关注人ID
	
	public Long getApplyId() {
		return applyId;
	}
	public void setApplyId(Long applyId) {
		this.applyId = applyId;
	}
	public String getApplyClass() {
		return applyClass;
	}
	public void setApplyClass(String applyClass) {
		this.applyClass = applyClass;
	}
	public String getApplyNum() {
		return applyNum;
	}
	public void setApplyNum(String applyNum) {
		this.applyNum = applyNum;
	}
	public String getApplyName() {
		return applyName;
	}
	public void setApplyName(String applyName) {
		this.applyName = applyName;
	}
	public String getFlowDesc() {
		return flowDesc;
	}
	public void setFlowDesc(String flowDesc) {
		this.flowDesc = flowDesc;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getNodeTime() {
		return nodeTime;
	}
	public void setNodeTime(String nodeTime) {
		this.nodeTime = nodeTime;
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
	public String getSigners() {
		return signers;
	}
	public void setSigners(String signers) {
		this.signers = signers;
	}
	public String getSignerIds() {
		return signerIds;
	}
	public void setSignerIds(String signerIds) {
		this.signerIds = signerIds;
	}
	public String getOperators() {
		return operators;
	}
	public void setOperators(String operators) {
		this.operators = operators;
	}
	public Long getProcessId() {
		return processId;
	}
	public void setProcessId(Long processId) {
		this.processId = processId;
	}
	@ManyToOne
	@JoinColumn
	public UserInfo getCreateUser() {
		return createUser;
	}
	public void setCreateUser(UserInfo createUser) {
		this.createUser = createUser;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getApplyUrl() {
		return applyUrl;
	}
	public void setApplyUrl(String applyUrl) {
		this.applyUrl = applyUrl;
	}
	public String getDeleteUrl() {
		return deleteUrl;
	}
	public void setDeleteUrl(String deleteUrl) {
		this.deleteUrl = deleteUrl;
	}
	public String getSlaKey() {
		return slaKey;
	}
	public void setSlaKey(String slaKey) {
		this.slaKey = slaKey;
	}
	public String getSlaBeginTime() {
		return slaBeginTime;
	}
	public void setSlaBeginTime(String slaBeginTime) {
		this.slaBeginTime = slaBeginTime;
	}
	public String getSlaEndTime() {
		return slaEndTime;
	}
	public void setSlaEndTime(String slaEndTime) {
		this.slaEndTime = slaEndTime;
	}
	public Long getPendingTime() {
		return pendingTime;
	}
	public void setPendingTime(Long pendingTime) {
		this.pendingTime = pendingTime;
	}
	public String getPendingStartTime() {
		return pendingStartTime;
	}
	public void setPendingStartTime(String pendingStartTime) {
		this.pendingStartTime = pendingStartTime;
	}
	public Integer getPendingFlag() {
		return pendingFlag;
	}
	public void setPendingFlag(Integer pendingFlag) {
		this.pendingFlag = pendingFlag;
	}
	public String getFollowUserIds() {
		return followUserIds;
	}
	public void setFollowUserIds(String followUserIds) {
		this.followUserIds = followUserIds;
	}
	@Transient
	public String getStateText(){
		if(state==null){
			return "";
		}
		switch(state){
			case 0 : return "草稿";
			case 1 : return "审批中";
			case 2 : return "完成";
			case 3 : return "驳回";
			case 4 : return "中止";
			case 5 : return "撤单";
			case -1 : return "删除";
		}
		return "";
	}
	/**
	 * 当前用户是否关注该申请
	 * @Methods Name getIsFollow
	 * @Create In 2014年11月19日 By lee
	 * @return
	 */
	@Transient
	public boolean getIsFollow() {
		UserInfo curUser = UserContext.getCurUser();
		if(curUser!=null&&StrUtils.isNotEmpty(followUserIds)){
			return followUserIds.indexOf("【"+curUser.getId()+"】")>-1;
		}else{
			return false;
		}
	}
	
	/**
	 * 该申请单有多少人关注
	 * @return
	 */
	@Transient
	public Integer getFollowCount() {
		return StrUtils.splitToList(followUserIds, ",", "【", "】").size();
		
	}

	public void addOperators(Long operatorIds) {
		List<Long> operators = StrUtils.splitToLongList(this.operators, ",", "【", "】");
		Set<Long> operatorSet = Sets.newHashSet(operators);
		operatorSet.add(operatorIds);
		this.operators = StrUtils.join(operatorSet, ",", "【", "】");
	}
}
