package com.dc.itcs.core.base.entity;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.dc.flamingo.core.data.IdEntity;
import com.dc.flamingo.core.utils.DateUtils;
import com.dc.itcs.core.context.UserContext;
import com.dc.itcs.security.entity.UserInfo;


/**
 * 流程申请单顶级基类
 * @author lee
 *
 */
@MappedSuperclass
public abstract class FlowEntity extends IdEntity{
	private static final long serialVersionUID = -3696291169338605300L;
	public static final int STATE_DRAFT = 0;	//草稿状态
	public static final int STATE_AUDIT = 1;	//审批状态
	public static final int STATE_FINISH = 2;	//完成状态
	public static final int STATE_BACK = 3;		//驳回状态
	public static final int STATE_STOP = 4;		//中止状态
	public static final int STATE_CANCEL = 5;	//撤单状态
	public static final int STATE_DELETE = -1;	//删除状态
	protected Integer state;		//状态
	protected Long processId;		//对应流程实例ID
	protected UserInfo createUser;	//创建人
	protected String createTime;	//创建时间
	
	public FlowEntity(){
		this.state = STATE_DRAFT;
		this.createUser = UserContext.getCurUser();
		this.createTime = DateUtils.getCurrentDateTimeStr();
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
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
	public Long getProcessId() {
		return processId;
	}
	public void setProcessId(Long processId) {
		this.processId = processId;
	}
	@ManyToOne
    @JoinColumn(name="createUser")
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
	/**
	 * 获取申请单编号
	 * @return
	 */
	@Transient
	public abstract String getApplyNum();
	/**
	 * 获取申请单名称
	 * @return
	 */
	@Transient
	public abstract String getApplyName();
	
	/**
	 * 获取申请用户
	 * @Methods Name getSubmitUser
	 * @Create In 2015年3月5日 By lee
	 * @return
	 */
	@Transient
	public abstract UserInfo getSubmitUser();
	/**
	 * 获取申请类型
	 * @return
	 */
	@Transient
	public abstract String getFlowDesc();
	/**
	 * 获取流程定义
	 * @return
	 */
	@Transient
	public abstract String getPdName();
	/**
	 * 获取申请单链接
	 * @return
	 */
	@Transient
	public abstract String getApplyUrl();
	/**
	 * 获取删除链接
	 * @return
	 */
	@Transient
	public abstract String getDeleteUrl();
	
}

