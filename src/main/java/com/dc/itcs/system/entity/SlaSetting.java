/*package com.dc.itcs.system.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.dc.flamingo.core.data.IdEntity;
import com.dc.itcs.customer.entity.InsteadQuestiion;

*//**
 * SLA设置
 * @ClassName: SlaSetting
 * @Description: TODO
 * @Create In 2014年11月17日 By lee
 *//*
@Entity
@Table(name = "system_sla_setting") 
public class SlaSetting extends IdEntity {
	private static final long serialVersionUID = -3102152709208576952L;
	private String slaKey;			//SLA关键字
	private String slaName;			//SLA服务级别
	private Double overdueTime;		//超期时长
	private Double alertTime;		//预警时长
	private Double replayTime;  	//响应时间
	private Integer	state;	 		//状态
	private InsteadQuestiion insteadQuestiion;//代提
	
	@ManyToOne
	@JoinColumn
	public InsteadQuestiion getInsteadQuestiion() {
		return insteadQuestiion;
	}
	public void setInsteadQuestiion(InsteadQuestiion insteadQuestiion) {
		this.insteadQuestiion = insteadQuestiion;
	}

	//获取文本信息
	@Transient
	public String getStateText() {
		if(getState() == 0){
			return "有效";
		}else if(getState() == 1){
			return "无效";
		}
		return "";
	}
	
	
	public Double getReplayTime() {
		return replayTime;
	}

	public void setReplayTime(Double replayTime) {
		this.replayTime = replayTime;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	
	public String getSlaKey() {
		return slaKey;
	}
	public void setSlaKey(String slaKey) {
		this.slaKey = slaKey;
	}
	public String getSlaName() {
		return slaName;
	}
	public void setSlaName(String slaName) {
		this.slaName = slaName;
	}
	public Double getOverdueTime() {
		return overdueTime;
	}
	public void setOverdueTime(Double overdueTime) {
		this.overdueTime = overdueTime;
	}
	public Double getAlertTime() {
		return alertTime;
	}
	public void setAlertTime(Double alertTime) {
		this.alertTime = alertTime;
	}

}
*/