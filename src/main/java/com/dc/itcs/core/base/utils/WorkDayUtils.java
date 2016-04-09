package com.dc.itcs.core.base.utils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import com.dc.flamingo.core.context.ContextHolder;
import com.dc.flamingo.core.utils.DateUtils;
import com.dc.itcs.system.entity.WorkTime;
import com.dc.itcs.system.service.WorkTimeService;

/**
 * 工作日工具类
 * @ClassName: WorkDayUtils
 * @Description: TODO
 * @Create In 2014年11月5日 By lee
 */
public final class WorkDayUtils {
	
	/**
	 * 是否是工作日
	 * @Methods Name isWorkDay
	 * @Create In 2014年11月5日 By lee
	 * @param dateStr
	 * @return
	 */
	public static boolean isWorkDay(String dateStr){
		return true;
	}
	/**
	 * 是否是默认的工作日
	 * @Methods Name isDefaultWorkDay
	 * @Create In 2014年11月5日 By lee
	 * @param dateStr
	 * @return
	 */
	public static boolean isDefaultWorkDay(String dateStr){
		Date date = DateUtils.convertStringToDate(dateStr);
		Calendar cDay = Calendar.getInstance();   
        cDay.setTime(date);  
        int weekDay = cDay.get(Calendar.DAY_OF_WEEK);
        if(1==weekDay||7==weekDay){	//默认周六周日为休息日
        	return false;
        }
		return true;
	}
	
	/**
	 * 获取工时，排除工作日
	 * @Methods Name getWorkTime
	 * @Create In 2014年11月17日 By lee
	 * @param beginTime
	 * @param endTime
	 * @return 毫秒
	 * @throws ParseException 
	 */
	public static long getWorkTime(Date beginTime, Date endTime){
		try {
			Date beginDay;
			beginDay = DateUtils.convertStringToDate(DateUtils.getDateStr(beginTime));
			Date endDay = DateUtils.convertStringToDate(DateUtils.getDateStr(endTime));
			if(endDay.equals(beginDay)){	//如果是同一天
				return curDayWorkTime(beginTime, endTime);
			}else{
				long tempTime = 0;
				Date curDay = beginDay;
				while(!endDay.before(curDay)){
					if(isWorkDay(DateUtils.convertDateToString(curDay))){
						if(curDay.equals(beginDay)){	//开始所在天
							tempTime += curDayWorkTime(beginTime, DateUtils.convertStringToDate(DateUtils.getDateStr(beginTime)+" "+getWorkTime().getPmEndTime()));
						}else if(curDay.equals(endDay)){//结束所在天
							tempTime += curDayWorkTime(DateUtils.convertStringToDate(DateUtils.getDateStr(endTime)+" "+getWorkTime().getAmBeginTime()),endTime);
						}else{
							tempTime += getWorkTime().getOneDayWorkTime();
						}
					}
					curDay = DateUtils.addDays(curDay, 1);
				}
				return tempTime;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * 当前工时
	 * @Methods Name curDayWorkTime
	 * @Create In 2014年11月18日 By lee
	 * @param beginTime
	 * @param endTime
	 * @return
	 * @throws ParseException
	 */
	public static long curDayWorkTime(Date beginTime, Date endTime) throws ParseException{
		String curDayStr = DateUtils.getDateStr(beginTime);
		Date amBeginTime = DateUtils.convertStringToDate(curDayStr+" "+getWorkTime().getAmBeginTime());
		Date amEndTime = DateUtils.convertStringToDate(curDayStr+" "+getWorkTime().getAmEndTime());
		Date pmBeginTime = DateUtils.convertStringToDate(curDayStr+" "+getWorkTime().getPmBeginTime());
		Date pmEndTime = DateUtils.convertStringToDate(curDayStr+" "+getWorkTime().getPmEndTime());

		//如果开始时间未到上班时间,按上班时间开始计算
		if(beginTime.before(amBeginTime)||amBeginTime.equals(beginTime)){
			//如果午休前结束
			if(endTime.before(amEndTime)){
				return endTime.getTime()-amBeginTime.getTime();//上午结束时间-开始时间
			//如果是午休时间结束
			}else if(endTime.after(amEndTime)&&endTime.before(pmBeginTime)){
				return amEndTime.getTime()-amBeginTime.getTime();//上午结束时间-开始时间
			//如果是下午上班时间结束
			}else if(endTime.after(pmBeginTime)&&endTime.before(pmEndTime)){
				return endTime.getTime()-amBeginTime.getTime()-(pmBeginTime.getTime()-amEndTime.getTime()); //结束时间-上午上班时间-午休时间
			//如果是下班后结束
			}else{
				return getWorkTime().getOneDayWorkTime();
			}
		}
		//如果开始时间是上午上班时间
		else if(beginTime.after(amBeginTime)&&beginTime.before(amEndTime)){
			//如果午休前结束
			if(endTime.before(amEndTime)){
				return endTime.getTime()-beginTime.getTime();//结束时间-上午上班时间
			//如果是午休时间结束
			}else if(endTime.after(amEndTime)&&endTime.before(pmBeginTime)){
				return amEndTime.getTime()-beginTime.getTime();//上午下班时间-上午上班时间
			//如果是下午上班时间结束
			}else if(endTime.after(pmBeginTime)&&endTime.before(pmEndTime)){
				return endTime.getTime()-beginTime.getTime()-(pmBeginTime.getTime()-amEndTime.getTime());//结束时间-上午开始-午休时间
			//如果是下班后结束
			}else{
				return pmEndTime.getTime()-beginTime.getTime()-(pmBeginTime.getTime()-amEndTime.getTime());//下午下班时间-上午上班时间-午休时间
			}
		}
		//如果开始时间是午休时间
		else if(beginTime.after(amEndTime)&&beginTime.before(pmBeginTime)){
			//如果是午休时间结束
			if(endTime.after(amEndTime)&&endTime.before(pmBeginTime)){
				return 0;
			//如果是下午上班时间结束
			}else if(endTime.after(pmBeginTime)&&endTime.before(pmEndTime)){
				return endTime.getTime()-pmBeginTime.getTime();//结束时间-下午上班时间
			//如果是下班后结束
			}else{
				return pmEndTime.getTime()-pmBeginTime.getTime();//下午下班时间-下午上班时间
			}
		}
		//如果开始时间是下午上班时间
		else if(beginTime.after(pmBeginTime)&&beginTime.before(pmEndTime)){
			//如果是下午上班时间结束
			if(endTime.after(pmBeginTime)&&endTime.before(pmEndTime)){
				return endTime.getTime()-beginTime.getTime();
			//如果是下班后结束
			}else{
				return pmEndTime.getTime()-beginTime.getTime();
			}
		}
		//如果开始时间是下午下班时间
		else{
			return 0;
		}
	}
	
	public static WorkTime getWorkTime(){
		WorkTimeService workTimeService = ContextHolder.getBean(WorkTimeService.class);
		return workTimeService.findWorkTime();
	}

	public static void main(String[] args){
		WorkDayUtils.isDefaultWorkDay("2014-11-02");
	}
}
