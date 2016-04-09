package com.dc.itcs.system.entity;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.dc.flamingo.core.data.IdEntity;

/**
 * 工作日设定实体
 * @ClassName: WorkDay
 * @Create In 2014年11月5日 By lee
 */
@Entity
@Table(name = "system_work_day") 
public class WorkDay extends IdEntity {
	private static final long serialVersionUID = 4016520649969288103L;
	public static final int WORK_TRUE = 1;	//工作日
	public static final int WORK_FALSE = 0;	//非工作日
	private String targetDay;	//目标日期
	private Integer workFlag;	//是否工作日
	public String getTargetDay() {
		return targetDay;
	}
	public void setTargetDay(String targetDay) {
		this.targetDay = targetDay;
	}
	public Integer getWorkFlag() {
		return workFlag;
	}
	public void setWorkFlag(Integer workFlag) {
		this.workFlag = workFlag;
	}
	
	@Transient
	public Map<String, String> getTrueOrFalse() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("1", "是");
		map.put("0", "否");
		return map;
	}
	@Transient
	public String getWorkFlagText() {
		if(WORK_TRUE==workFlag){
			return "是";
		}
		if(WORK_FALSE==workFlag){
			return "否";
		}
		return "";
	}
}
