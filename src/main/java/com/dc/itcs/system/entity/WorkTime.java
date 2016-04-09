package com.dc.itcs.system.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.dc.flamingo.core.data.IdEntity;

/**
 * 工作时间设定
 * @ClassName: WorkTime
 * @Description: TODO
 * @Create In 2014年12月19日 By lee
 */
@Entity
@Table(name = "system_work_time") 
public class WorkTime extends IdEntity {
	private static final long serialVersionUID = 5573580379890771787L;
	private String amBeginTime;		//上午开始工作时间
	private String amEndTime;		//上午结束工作时间
	private String pmBeginTime;		//下午开始工作时间
	private String pmEndTime;		//下午结束工作时间
	private Long oneDayWorkTime;	//单天工作时间（毫秒）
	public String getAmBeginTime() {
		return amBeginTime;
	}
	public void setAmBeginTime(String amBeginTime) {
		this.amBeginTime = amBeginTime;
	}
	public String getAmEndTime() {
		return amEndTime;
	}
	public void setAmEndTime(String amEndTime) {
		this.amEndTime = amEndTime;
	}
	public String getPmBeginTime() {
		return pmBeginTime;
	}
	public void setPmBeginTime(String pmBeginTime) {
		this.pmBeginTime = pmBeginTime;
	}
	public String getPmEndTime() {
		return pmEndTime;
	}
	public void setPmEndTime(String pmEndTime) {
		this.pmEndTime = pmEndTime;
	}
	public Long getOneDayWorkTime() {
		return oneDayWorkTime;
	}
	public void setOneDayWorkTime(Long oneDayWorkTime) {
		this.oneDayWorkTime = oneDayWorkTime;
	}
	
}
