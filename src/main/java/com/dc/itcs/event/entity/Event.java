package com.dc.itcs.event.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.dc.flamingo.core.utils.DateUtils;
import com.dc.flamingo.core.utils.StrUtils;
import com.dc.flamingo.tools.annotation.FileKeyColumn;
import com.dc.itcs.core.base.entity.FlowEntity;
import com.dc.itcs.core.base.support.ItcsConstants;
import com.dc.itcs.core.base.utils.SLAUtils;
import com.dc.itcs.core.context.UserContext;
import com.dc.itcs.security.entity.Tenant;
import com.dc.itcs.security.entity.UserInfo;
import com.google.common.collect.Lists;

/**
 * 问题申请主实体
 * @author Administrator
 *
 */
@Entity
@Table(name = "ss_event") 
public class Event extends FlowEntity {
	private static final long serialVersionUID = 5716227589480306850L;
	
	public static final int MAIN_TYPE_FAULT = 0;		//主类型：故障
	public static final int MAIN_TYPE_CONSULTATION=1;	//主类型：咨询
	public static final int MAIN_TYPE_ADVISE=2;			//主类型：建议
	public static final int MAIN_TYPE_DEMAND=3;			//主类型：需求
	public static final int MAIN_TYPE_COMPLAINT=4;		//主类型：投诉
	
	public static final int MAIN_STATE_AUDIT=0;			//主状态：解决中
	public static final int MAIN_STATE_CONFIRMED=1;		//主状态：待确认
	public static final int MAIN_STATE_FINISH=2;		//主状态：已解决
	
	public static final int SUBMIT_TYPE_EMAIL=0;		//信息来源：邮件
	public static final int SUBMIT_TYPE_PHONE=1;		//信息来源：电话
	public static final int SUBMIT_TYPE_OTHER=2;		//信息来源：其他
	
	public static final int SUBMIT_MODE_MENTION=0;		//提交模式：自提
	public static final int SUBMIT_MODE_GENERATION=1;	//提交模式：代提
	
	private String code;				//编号
	private String  topic;				//主题
	private Tenant tenant;				//所属组织
	private UserInfo  submitUser;		//组织方提交问题的人
	private String tel;					//电话
	private String email;				//邮箱
	private Integer  mainType;			//主类型：故障/咨询/建议/需求/投诉; 0/1/2/3/4
	private String  submitTime;			//提交时间
	private Integer mainState;			//状态：解决中/待确认/已解决 ;0/1/2
	private String  singerIds;			//当前处理人ID
	private List<UserInfo> singerList = Lists.newArrayList();
	private String responseTime;		//响应时间
	private String  solveTime;			//解决时间
	
	private String content;				//描述
	private String attachment;			//附件
	private Integer submitType;			//信息来源：邮件/电话/其他
	private ServiceType serviceType;	//服务类型
	private ServiceLevel serviceLevel;	//服务级别
	private Integer submitMode;			//提交模式：自提0，代提1
	//private UserInfo createUser;		//创建人，自提情况与submitUser相同，代提与submitUser不同
	private Integer useState;			//状态 有效  无效
	
	private Integer isWarningFlag;		//是否预警标示（1已预警、0未预警）
	private Integer isOverduFlag;		//是否超期标示（1已超期、0未超期）
	
	private String isInstead;			//判断的条件(客户经理还是用户发起的)--- YES：指代提    NO：自提   
	private String isSlove;				//判断的条件(服务经理解决与否)-----解决：isSlove 未解决：otherManager
	private String linkMan;				//联系人
	private String recentUser;		//最新更新人
	private String recentAction;		//最新更新状态
	
	@Transient	
	public String getMainTypeText() {
		String mainType = "";
		if(this.mainType==MAIN_TYPE_FAULT){
			mainType= "故障";
		}else if(this.mainType == MAIN_TYPE_CONSULTATION){
			mainType= "咨询";
		}else if(this.mainType == MAIN_TYPE_ADVISE){
			mainType= "建议";
		}else if(this.mainType == MAIN_TYPE_DEMAND){
			mainType= "需求";
		}
		else if(this.mainType == MAIN_TYPE_COMPLAINT){
			mainType= "投诉";
		}
		return mainType;
	}
	
	
	@Transient	
	public String getMainStateText() {
		String mainState = "";
		if(this.mainState==MAIN_STATE_AUDIT){
			mainState= "受理中";
		}else if(this.mainState == MAIN_STATE_CONFIRMED){
			mainState= "待客户确认";
		}else if(this.mainState == MAIN_STATE_FINISH){
			mainState= "已关闭";
		}
		return mainState;
	}
	
	@Transient	
	public Integer getSlaFlag() {
		if(this.getIsOverdue()){
			return ItcsConstants.EVENT_SLA_OVERDUE;
		}
		if(this.getIsAlert()){
			return ItcsConstants.EVENT_SLA_WARNING;
		}
		return ItcsConstants.EVENT_SLA_NORMAL;
	}
	/**
	 * SLA状态文本
	 * @Methods Name getSlaFlagText
	 * @Create In 2015年5月12日 By lee
	 * @return
	 */
	@Transient	
	public String getSlaFlagText() {
		if(this.getIsOverdue()){
			return "超期";
		}
		if(this.getIsAlert()){
			return "预警";
		}
		return "";
	}
	
	
	@Transient	
	public String getUseStateText() {
		String useState = "";
		if(this.useState==ItcsConstants.STATE_ON){
			useState= "有效";
		}else if(this.useState==ItcsConstants.STATE_OFF){
			useState= "无效";
		}
		return useState;
	}
	
	@Transient	
	public String getSubmitModeText() {
		String submitMode = "";
		if(this.submitMode==SUBMIT_MODE_MENTION){
			submitMode= "自提";
		}else if(this.submitMode == SUBMIT_MODE_GENERATION){
			submitMode= "代提";
		}
		return submitMode;
	}
	
	@Transient	
	public String getSubmitTypeText() {
		String submitType = "";
		if(this.submitType==SUBMIT_TYPE_EMAIL){
			submitType= "邮件";
		}else if(this.submitType == SUBMIT_TYPE_PHONE){
			submitType= "电话";
		}else if(this.submitType == SUBMIT_TYPE_OTHER){
			submitType= "其他";
		}
		return submitType;
	}
	
	/**
	 * 重写equals方法
	 * @data 2015-08-19
	 * @author liulx
	 */
	public boolean equals(Object obj){
		if(this == obj){
			return true;
		}
		if(obj instanceof Event){ //
			Event p = (Event)obj;
			return(code.equals(p.code) &&  id== p.id);
		}
		return super.equals(obj);
	}
	public int hashCode(){
		return (int) (code.hashCode()+id); //使用字符串哈希值与Integer的哈希值的组合
	                                       //这样会产生重码，实际上重码率很高
	}

	public String getRecentUser() {
		return recentUser;
	}


	public void setRecentUser(String recentUser) {
		this.recentUser = recentUser;
	}


	public String getRecentAction() {
		return recentAction;
	}


	public void setRecentAction(String recentAction) {
		this.recentAction = recentAction;
	}


	public String getLinkMan() {
		return linkMan;
	}
	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getMainType() {
		return mainType;
	}
	public void setMainType(Integer mainType) {
		this.mainType = mainType;
	}
	public String getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}
	public Integer getMainState() {
		return mainState;
	}
	public void setMainState(Integer mainState) {
		this.mainState = mainState;
	}

	public String getResponseTime() {
		return responseTime;
	}
	public void setResponseTime(String responseTime) {
		this.responseTime = responseTime;
	}
	public String getSolveTime() {
		return solveTime;
	}
	public void setSolveTime(String solveTime) {
		this.solveTime = solveTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@FileKeyColumn
	public String getAttachment() {
		return attachment;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	public Integer getSubmitType() {
		return submitType;
	}
	public void setSubmitType(Integer submitType) {
		this.submitType = submitType;
	}

	public Integer getIsWarningFlag() {
		return isWarningFlag;
	}

	public void setIsWarningFlag(Integer isWarningFlag) {
		this.isWarningFlag = isWarningFlag;
	}

	public Integer getIsOverduFlag() {
		return isOverduFlag;
	}

	public void setIsOverduFlag(Integer isOverduFlag) {
		this.isOverduFlag = isOverduFlag;
	}

	public Integer getSubmitMode() {
		return submitMode;
	}
	public void setSubmitMode(Integer submitMode) {
		this.submitMode = submitMode;
	}
	public Integer getUseState() {
		return useState;
	}
	public void setUseState(Integer useState) {
		this.useState = useState;
	}
	public String getIsInstead() {
		return isInstead;
	}
	public void setIsInstead(String isInstead) {
		this.isInstead = isInstead;
	}
	public String getIsSlove() {
		return isSlove;
	}
	public void setIsSlove(String isSlove) {
		this.isSlove = isSlove;
	}
	public String getSingerIds() {
		return singerIds;
	}
	public void setSingerIds(String singerIds) {
		this.singerIds = singerIds;
	}
	@Transient
	public List<UserInfo> getSingerList() {
		return singerList;
	}
	public void setSingerList(List<UserInfo> singerList) {
		this.singerList = singerList;
	}

	@ManyToOne
	@JoinColumn
	public ServiceType getServiceType() {
		return serviceType;
	}
	public void setServiceType(ServiceType serviceType) {
		this.serviceType = serviceType;
	}
	@ManyToOne
	@JoinColumn(name="submitUser")
	public UserInfo getSubmitUser() {
		return submitUser;
	}
	public void setSubmitUser(UserInfo submitUser) {
		this.submitUser =submitUser;
	}
	@ManyToOne
	@JoinColumn
	public ServiceLevel getServiceLevel() {
		return serviceLevel;
	}
	public void setServiceLevel(ServiceLevel serviceLevel) {
		this.serviceLevel = serviceLevel;
	}
	@ManyToOne
	@JoinColumn
	public Tenant getTenant() {
		return tenant;
	}
	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}
	@Override
	@Transient	
	public String getApplyNum() {
		return this.code;
	}
	@Override
	@Transient	
	public String getApplyName() {
		return this.topic;
	}
	@Override
	@Transient	
	public String getFlowDesc() {
		return "客户支持流程";
	}
	@Override
	@Transient	
	public String getPdName() {
		return "event";
	}
	@Override
	@Transient	
	public String getApplyUrl() {
		return "/event/applyPage/"+this.id;
	}
	@Override
	@Transient	
	public String getDeleteUrl() {
		return null;
	}

	/***得到当前处理人对象 信息----格式化用于页面显示*/
	@Transient
	public String getSingerIdsText(){
		if(singerList.size() > 0){
			StringBuffer sb = new StringBuffer();
			for(UserInfo singer : singerList){
				sb.append(","+singer.getUid()+"/"+singer.getUserName());
			}
			/**去掉最前面的逗号*/	
			return sb.substring(ItcsConstants.IS_YES);
		}
		return "";
	}
	
	public static Event getInstance(){
		Event apply = new Event();
		UserInfo curUser = UserContext.getCurUser();
		apply.setSubmitUser(curUser);
		apply.setSubmitTime(DateUtils.getCurrentDateTimeStr());
		return apply;
	}
	/**
	 * 是否超期
	 * @Methods Name isOverdue
	 * @Create In 2014年11月18日 By lee
	 * @return
	 */
	@Transient
	public Boolean getIsOverdue(){
		if(StrUtils.isEmpty(this.submitTime)){
			return false;
		}else{
			if(this.isOverduFlag!=ItcsConstants.IS_NOT){
				return this.isOverduFlag==ItcsConstants.IS_YES;
			}else{
				String slaBeginTime = this.submitTime;
				String slaEndTime = this.solveTime;
				Date curTime = DateUtils.getCurrentDateTime();
				Boolean isOverdueTemp = false;
				if(this.serviceLevel==null||this.serviceLevel.getOverdueTime()==null)
				{
					return false;
				}
				if(StrUtils.isEmpty(slaEndTime)){
					isOverdueTemp = SLAUtils.isOverdue(this.serviceLevel.getId(), DateUtils.convertStringToDate(slaBeginTime), curTime, 0);
				}else{
					isOverdueTemp = SLAUtils.isOverdue(this.serviceLevel.getId(), DateUtils.convertStringToDate(slaBeginTime), DateUtils.convertStringToDate(slaEndTime), 0);
				}
				return isOverdueTemp;
			}
		}
	}
	/**
	 * 是否预警
	 * @Methods Name isAlert
	 * @Create In 2014年11月18日 By lee
	 * @return
	 */
	@Transient
	public Boolean getIsAlert(){
		String slaBeginTime = this.submitTime;
		String slaEndTime = this.solveTime;
		if(StrUtils.isEmpty(slaBeginTime)||StrUtils.isNotEmpty(slaEndTime)){
			return false;
		}else{
			if(this.isWarningFlag!=ItcsConstants.IS_NOT){
				return this.isWarningFlag==ItcsConstants.IS_YES;
			}else{
				long pendingTimes = 0;
				Date curTime = DateUtils.getCurrentDateTime();
				Boolean isAlertTemp = false;
				if(this.serviceLevel==null||this.serviceLevel.getAlertTime()==null)
				{
					return false;
				}
				if(!this.getIsOverdue()){
					isAlertTemp = SLAUtils.isAlert(this.serviceLevel.getId(), DateUtils.convertStringToDate(slaBeginTime), curTime, pendingTimes);
				}else{
					isAlertTemp = false;
				}
				return isAlertTemp;
			}
		}
	}
	
	/**
	 * 问题两天没有 关闭就自动关闭
	 * 判断是否大于两天
	 * @return
	 */
	@Transient
	public Boolean isMoreThanTwoDays(){
		if(this.mainState != null){
			if(this.solveTime != null){
				if(this.mainState == MAIN_STATE_CONFIRMED){
					String slaBeginTime = this.solveTime;
					Date beginTime = DateUtils.convertStringToDate(slaBeginTime);
					Date endTime = DateUtils.getCurrentDateTime();
					Long resultTime = endTime.getTime()-beginTime.getTime();
					Long twoDaysTime = (long) (48*60*60*1000);
					if(twoDaysTime-resultTime > 0){
						return false;/**如果在两天之内*/
					}else{
						return true;/**如果超过两天*/
					}
				}
			}
		}
		return false;
	}

	/**
	 * 获取当前处理人ID集合
	 * @return
	 */
	@Transient
	public Iterable<Long> getSingerIdList() {
		return StrUtils.splitToLongList(this.singerIds, ",", "【", "】");
	}
	
	
}
