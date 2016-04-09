package com.dc.itcs.security.web;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dc.flamingo.core.web.AjaxResult;
import com.dc.flamingo.core.web.WebParamInfo;
import com.dc.itcs.core.base.support.ItcsConstants;
import com.dc.itcs.core.base.utils.Encryption;
import com.dc.itcs.core.context.UserContext;
import com.dc.itcs.security.entity.Tenant;
import com.dc.itcs.security.entity.UserInfo;
import com.dc.itcs.security.service.TenantService;
import com.dc.itcs.security.service.UserInfoService;

/**
 * 用户控制器
 * @author lee
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserInfoService userService;
	@Autowired
	private TenantService tenantService; 
	@Autowired
	private UserInfoService userinfoService;
	/**
	 * 用户分页查询
	 * @param webParamInfo
	 * @return
	 */
	@RequestMapping("/page")
	public ModelAndView page(WebParamInfo webParamInfo){
		Map<String, Object> param = webParamInfo.getParamStartingWith("search_");
		Pageable pageable = webParamInfo.getPageable();
		Page<UserInfo> page = userService.findByPage(pageable, param);
		ModelAndView mav = new ModelAndView();
		mav.addObject("page", page);
		mav.addObject("searchParam", webParamInfo.getParam());
		mav.setViewName("/security/user_list");
		return mav;
	}
	/**
	 *用户编辑弹出层-新增+编辑
	 * @param uid
	 * @return 
	 */	
	@RequestMapping("/panel/edit/{uid}")
	public ModelAndView panel(@PathVariable String uid){
		ModelAndView mav = new ModelAndView();
		UserInfo user = userService.findByUid(uid);
		if(user == null){
			user = new UserInfo();
		}
		mav.addObject("user",user);
		mav.setViewName("/security/user_edit");
		return mav;
	}

	/**
	 * 用户保存
	 * @Methods Name save
	 * @Create In 2015年8月13日 By luzm1
	 * @param userInfo
	 * @return AjaxResult 
	 */
	@RequestMapping("/save")
	@ResponseBody
	public AjaxResult save(UserInfo userInfo){
		UserInfo userByUid = userService.findByUid(userInfo.getUid());
		if(userInfo.getId() == null){/**新增*/
			if(userByUid != null) {/**用户UID唯一性校验*/
				return AjaxResult.objectResult("error");
			}
			/**增加默认的加密密码*/
			userInfo.setPassword(Encryption.encrypt(userInfo.getUid(), ItcsConstants.USER_PASSWORD));
		}
		userService.saveUserInfo(userInfo);
		return AjaxResult.successResult();
	}

	/**
	 * 密码重置--保存
	 * @Methods Name userInfoPwdResetSave
	 * @Create In 2015年8月13日 By luzm1
	 * @param uid
	 * @return AjaxResult
	 */
	@RequestMapping("/userInfoPwdResetSave/{uid}")
	@ResponseBody
	public AjaxResult userInfoPwdResetSave(@PathVariable String uid){
		UserInfo userInfo = userService.findByUid(uid);
		/**密码加密*/
		userInfo.setPassword(Encryption.encrypt(userInfo.getUid(), ItcsConstants.USER_PASSWORD));
		userService.saveUserInfo(userInfo);
		return AjaxResult.successResult();
	}
	

	/**
	 * 进入修改密码页面
	 * @Methods Name updatePwdPage
	 * @Create In 2015年8月13日 By luzm1
	 * @return ModelAndView
	 */
	@RequestMapping("/updatePwdPage")
	public ModelAndView updatePwdPage(){
		ModelAndView mav = new ModelAndView();
		UserInfo userInfo = UserContext.getCurUser();
		mav.addObject("userInfoId",userInfo.getId());
		mav.setViewName("/security/updatePwdPage");
		return mav;
	}
	

	/**
	 * 密码修改保存
	 * @Methods Name updatePwdPageSave
	 * @Create In 2015年8月13日 By luzm1
	 * @param userInfoId
	 * @param servletRequest
	 * @return AjaxResult
	 */
	@RequestMapping("/updatePwdPageSave/{userInfoId}")
	@ResponseBody
	public AjaxResult updatePwdPageSave(@PathVariable Long userInfoId,ServletRequest servletRequest){
		UserInfo userInfo = userService.findById(userInfoId);
		String oldPwdStr = servletRequest.getParameter("oldPassWord");
		String newPwdStr = servletRequest.getParameter("newPassWord");
		String newPwdAgainStr = servletRequest.getParameter("newPassWordAgain");
		
		/**旧密码加密*/
		String oldPwdStrAfterEncryption = Encryption.encrypt(userInfo.getUid(), oldPwdStr);
		/**新密码加密*/
		String newPwdStrAfterEncryption = Encryption.encrypt(userInfo.getUid(), newPwdStr);
		
		if(oldPwdStrAfterEncryption.equals(userInfo.getPassword())){
			if(!newPwdStr.equals(newPwdAgainStr) ){
				 return  AjaxResult.objectResult("different");
			}else{
				userInfo.setPassword(newPwdStrAfterEncryption);
				userService.saveUserInfo(userInfo);
			}
		}else{
			return  AjaxResult.objectResult("error");
		}
		return AjaxResult.successResult();
	}

	
	

	/**
	 * 查询所有客户下的账号信息
	 * @Methods Name findAccountMessOfTenants
	 * @Create In 2015年8月13日 By luzm1
	 * @param searchKey
	 * @param size
	 * @param tenantId
	 * @return AjaxResult
	 */
	@RequestMapping("/findAccountMessOfTenants")
	@ResponseBody
	public AjaxResult findAccountMessOfTenants(String searchKey,Integer size,Long tenantId){
		String code = tenantService.findTenantById(tenantId).getCode();
		List<UserInfo> tenantList= userinfoService.findUserOfTenantForSelect(searchKey,size,code);
		return AjaxResult.objectResult(tenantList);
	}
	
	
	/**
	 * 查询服务商下：客户经理和服务经理
	 * @Methods Name findManagerOfCustomerAndService
	 * @Create In 2015年8月13日 By luzm1
	 * @param searchKey
	 * @param size
	 * @return AjaxResult
	 */
	@RequestMapping("/findManagerOfCustomerAndService")
	@ResponseBody
	public AjaxResult findManagerOfCustomerAndService(String searchKey,Integer size){
		List<UserInfo> userInfoList = userinfoService.findUserForSelectByTenantType(searchKey,Tenant.TYPE_S,size);
		return AjaxResult.objectResult(userInfoList);
	}
	
	
	
}
