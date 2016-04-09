package com.dc.itcs.security.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.dc.flamingo.core.data.IdEntity;
import com.dc.flamingo.core.utils.StrUtils;
import com.dc.itcs.core.base.support.ItcsConstants;

/**
 * 用户信息
 * @author lee
 *
 */
@Entity
@Table(name = "ss_user") 
public class UserInfo extends IdEntity {
	private static final long serialVersionUID = 7624258509869173918L;
	private String uid;			//用户ID(用户唯一标识)(客户账号)
	private String userName;	//用户名（客户名称）
	private String password;	//密码
	private Tenant tenant;		//所属租户
	private Integer enabled;	//是否可用
	private String email; 		//邮箱
	private String tel; 		//座机
	private String mobile; 		//手机

	public UserInfo() {
	}
	public UserInfo(Long userId) {
		this.id = userId;
	}
	@Column(length=100 ,nullable=false)
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	@Column(length=100 ,nullable=false)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@ManyToOne
	@JoinColumn(name="tenant")
	public Tenant getTenant() {
		return tenant;
	}
	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}
	@Column(nullable=false)
	public Integer getEnabled() {
		return enabled;
	}
	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Override
	public int hashCode() {

		int result = 17;

		result = 31 * result + (null == uid ? 0 : uid.hashCode());
		result = 31 * result + (null == userName ? 0 : userName.hashCode());
		result = 31 * result + (null == password ? 0 : password.hashCode());

		return result;
	}
	
	/**
	 * userInfo比较
	 * @Methods Name equals
	 * @Create In 2014年2月19日 By liaogaosong
	 * @param userInfo
	 * @return boolean
	 */
	public boolean equals(Object obj) {
		if(obj==null){
			return false;
		}
		if(obj instanceof UserInfo){
			UserInfo userObj = (UserInfo) obj;
			if(StrUtils.isNotEmpty(userObj.getUid())){
				if(StrUtils.isNotEmpty(this.uid) && this.uid.equals(userObj.getUid())){
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return String.format("UserInfo [userId: %s, userName： %s, enable: %s]", uid, userName,enabled);
	}
	
	@Transient	
	public String getEnabledText() {
		String enabled = "";
		if(this.enabled==ItcsConstants.STATE_ON){
			enabled= "是";
		}else if(this.enabled==ItcsConstants.STATE_OFF){
			enabled= "否";
		}
		return enabled;
	}
	
	/**
	 * 格式化人员信息
	 * @return
	 */
	@Transient
	public String getUserText() {
		String userText = "";
		if (StrUtils.isNotEmpty(this.userName)) {
			userText += this.userName + "/";
		}
		
		if (StrUtils.isNotEmpty(this.uid)) {
			userText += this.uid;
		}
		
		while (userText.endsWith("/")) {
			userText = userText.substring(0, userText.length() - 1);
		}
		return userText;
	}
	
	/**
	 * 是否域控用户
	 * @return
	 */
	@Transient
	public Boolean getIsAdUser() {
		String code = this.tenant.getCode();
		return ItcsConstants.TENANT_DC.equals(code) || ItcsConstants.TENANT_ITS.equals(code);
	}
	/**
	 * 是否是服务商用户，服务商用户即服务工程师或客户经理
	 * @Methods Name isSpUser
	 * @Create In 2015年8月17日 By lee
	 * @return
	 */
	@Transient
	public boolean isSpUser() {
		if(Tenant.TYPE_S.equals(this.tenant.getType())){
			return true;
		}
		return false;
	}
	
}
