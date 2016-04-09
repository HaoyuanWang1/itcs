package com.dc.itcs.flow.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.dc.flamingo.core.data.IdEntity;
import com.dc.flamingo.core.utils.DateUtils;
import com.dc.itcs.security.entity.UserInfo;

/**
 * 流程代办人设置
 * @author lee
 *
 */
@Entity
@Table(name = "ss_flow_agent") 
public class FlowAgent extends IdEntity{
    private static final long serialVersionUID = -3977848163249278667L;
	private UserInfo auther;	//授权人
	private UserInfo agenter;	//代理人
	private String startDate;	//开始日期
	private String endDate;		//结束日期
	private String createTime;	//创建日期
	private String stopTime;	//中止日期

	@ManyToOne
    @JoinColumn(name="auther")
	public UserInfo getAuther() {
		return auther;
	}
	public void setAuther(UserInfo auther) {
		this.auther = auther;
	}
	@ManyToOne
    @JoinColumn(name="agenter")
	public UserInfo getAgenter() {
		return agenter;
	}
	public void setAgenter(UserInfo agenter) {
		this.agenter = agenter;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getStopTime() {
		return stopTime;
	}
	public void setStopTime(String stopTime) {
		this.stopTime = stopTime;
	}
	@Transient
	public boolean isCanDelete(){
		long stopTime1 = DateUtils.convertStringToDate(endDate).getTime();
		long nowTime = new Date().getTime();
		if(nowTime>stopTime1){
			return true;
		}
		return false;
	}
	@Transient
	public boolean isCanStop(){
		long startTime = DateUtils.convertStringToDate(startDate).getTime();
		long stopTime1 = DateUtils.convertStringToDate(stopTime).getTime();
		long nowTime = new Date().getTime();
		if(nowTime>startTime&&stopTime1>nowTime){
			return true;
		}
		return false;
	}
}
