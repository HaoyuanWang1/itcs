package com.dc.itcs.core.base.utils;

import java.text.ParseException;
import java.util.Date;

import com.dc.flamingo.core.context.ContextHolder;
import com.dc.itcs.event.entity.ServiceLevel;
import com.dc.itcs.system.service.SlaSettingService;

/**
 * SLA工具类
 * @ClassName: SLAUtils
 * @Description: TODO
 * @Create In 2014年11月17日 By lee
 */
public class SLAUtils {
	/**
	 * 是否超期
	 * @Methods Name isOverdue
	 * @Create In 2014年11月17日 By lee
	* @param slaKey		SLA设置KEY
	 * @param beginDate	SLA计算开始时间
	 * @param endDate	SLA计算结束时间
	 * @param pendingTime	挂起时长
	 * @return
	 * @throws ParseException 
	 */
	public static boolean isOverdue(Long id, Date beginTime, Date endTime, long pendingTime) {
		if(getServiceLevel(id)==null){
			return false;
		}
		try {
			Long getOverdueSlaTime = (long) (getServiceLevel(id).getOverdueTime()*60*60*1000);
			Long getSolveTime = (WorkDayUtils.getWorkTime(beginTime, endTime)-pendingTime);
			if(getOverdueSlaTime < getSolveTime){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 是否预警
	 * @Methods Name isAlert
	 * @Create In 2014年11月17日 By lee
	 * @param slaKey	SLA设置KEY
	 * @param beginDate	SLA计算开始时间
	 * @param endDate	SLA计算结束时间
	 * @param pendingTime	挂起时长
	 * @return
	 */
	public static boolean isAlert(Long id, Date beginTime, Date endTime, long pendingTime) {
		if(getServiceLevel(id)==null){
			return false;
		}
		try {
			Long getAlertSlaTime = (long) (getServiceLevel(id).getAlertTime()*60*60*1000);
			Long getSolveTime = (WorkDayUtils.getWorkTime(beginTime, endTime)-pendingTime);
			Long getOverdueSlaTime = (long) (getServiceLevel(id).getOverdueTime()*60*60*1000);
			if(getAlertSlaTime < getSolveTime && getSolveTime < getOverdueSlaTime){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 根据关键字获取对应SLA设置
	 * @Methods Name getSlaSetting
	 * @Create In 2014年11月18日 By lee
	 * @param key
	 * @return
	 */
	public static ServiceLevel getServiceLevel(Long id){
		return ContextHolder.getBean(SlaSettingService.class).findByKey(id);
	}
}
