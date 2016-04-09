package com.dc.itcs.security.entity;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.dc.flamingo.core.data.IdEntity;
import com.dc.itcs.core.base.support.ItcsConstants;

/**
 * 客户
 * @author lee
 *
 */
@Entity
@Table(name = "t_tenant") 
public class Tenant extends IdEntity{
	private static final long serialVersionUID = -8126200091881255473L;
	/** 客户 类型*/
	public static final String TYPE_C = "C";
	/** 服务商类型 */
	public static final String TYPE_S = "S";
	
	private String code;		//编号
	private String name;		//名称
	private String type;		//类型（客户、服务商）
	private Integer state;		//状态
	
	public Tenant() {
		this.state = ItcsConstants.STATE_ON;
	}

	@Column(length=40,unique=true,nullable=false)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	@Transient	
	public String getStateText() {
		String state = "";
		if(this.state==ItcsConstants.STATE_ON){
			state= "有效";
		}else if(this.state==ItcsConstants.STATE_OFF){
			state= "无效";
		}
		return state;
	}
	
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
	/**
	 * 状态列表
	 * @return
	 */
	@Transient
	public Map<String, String> getStateMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put(ItcsConstants.STATE_ON + "", ItcsConstants.IS_YESS);
		map.put(ItcsConstants.STATE_OFF + "", ItcsConstants.IS_NO);
		return map;
	}
	
}
