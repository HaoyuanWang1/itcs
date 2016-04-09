package com.dc.itcs.security.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dc.flamingo.core.utils.StrUtils;
import com.dc.flamingo.core.web.AjaxResult;
import com.dc.flamingo.core.web.WebParamInfo;
import com.dc.itcs.security.entity.RoleInfo;
import com.dc.itcs.security.entity.UserInfo;
import com.dc.itcs.security.entity.UserRole;
import com.dc.itcs.security.service.RoleInfoService;
import com.dc.itcs.security.service.UserInfoService;
import com.dc.itcs.security.service.UserRoleService;

/**
 * 用户角色控制器
 * @ClassName: UserRoleController
 * @Create In 2014年12月18日 By lee
 */
@Controller
@RequestMapping("/userRole")
public class UserRoleController {
    @Autowired
	private UserRoleService userRoleService;
    @Autowired
    private UserInfoService userService;
    @Autowired
    private RoleInfoService roleInfoService;

    /**
     * 保存用户角色(用于保存用户的角色集合)
     * @Methods Name saveUserRoleForUser
     * @Create In 2014年12月18日 By lee
     * @param userId	用户ID
     * @param roleIds	角色ID字符串用,隔开
     * @return
     * 2015-05-13 ： itcs中应用
     */
    @RequestMapping("/saveUserRoleForUser")
    @ResponseBody
    public AjaxResult saveUserRoleForUser(Long userId,String roleIds) {
        List<Long> rids = StrUtils.splitToLongList(roleIds, ",");
        List<UserRole> userRoles = userRoleService.findByUser_Id(userId);
        for(UserRole ur : userRoles){
        	Long oldRid = ur.getRole().getId();
        	if(rids.contains(oldRid)){			//如果有，说明此次保存仍有该角色
        		rids.remove(oldRid);			//从记录中去掉，只剩余需新增的角色ID
        	}else{	//如果没有，说明删除
        		userRoleService.delete(ur);
        	}
        }
        UserInfo user = userService.findById(userId);
        for(Long rid : rids){	//保存新增的用户角色信息
        	UserRole ur = new UserRole();
        	ur.setUser(user);
        	RoleInfo role = new RoleInfo();
        	role.setId(rid);
        	ur.setRole(role);
        	userRoleService.saveUserRole(ur);
        }
        //UserContext.updateEngineerMenu(user);
        return AjaxResult.successResult();
    }
    
    /**
     * 载入用户对应角色集合
     * @Methods Name loadUserRole
     * @Create In 2014年12月18日 By lee
     * @param userId
     * @return
     * 2015-05-13 ： itcs中应用
     */
    @RequestMapping("/loadUserRole/panel/{userId}")
    public ModelAndView loadUserRole(@PathVariable Long userId) {
        List<UserRole> userRoles = userRoleService.findByUser_Id(userId);
        List<RoleInfo> allRoles = roleInfoService.findAll();
        StringBuffer roleIds = new StringBuffer("");
        for(UserRole ur:userRoles){
        	roleIds.append(ur.getRole().getId()+",");
        }
        if(roleIds.length()>0){
        	roleIds.deleteCharAt(roleIds.length()-1);
        }
        ModelAndView mav = new ModelAndView();
        mav.setViewName("security/userRole_edit");
        mav.addObject("roleIds", roleIds);
        mav.addObject("allRoles", allRoles);
        mav.addObject("userId",userId);
        return mav;
    }
    
    /**
     * 保存单个的UserRole对象
     * @Methods Name saveUserRole
     * @Create In 2014年12月18日 By lee
     * @param userRole
     * @return
     */
    @RequestMapping("/saveUserRole")
    @ResponseBody
    public AjaxResult saveUserRole(UserRole userRole) {
    	UserRole old = userRoleService.findUserRole(userRole.getUser().getId(),userRole.getRole().getId());
    	if(old==null){	//如果不存在保存
    		userRoleService.saveUserRole(userRole);
    		UserInfo user = userService.findById(userRole.getUser().getId());
    		//UserContext.updateEngineerMenu(user);
    		return AjaxResult.successResult();
    	}else{	//如果已存在，提示已存在
    		return AjaxResult.errorResult("改用户与角色对应关系已存在，不能重复添加");
    	}
    }
    /**
     * 删除用户角色
     * @Methods Name deleteUserRole
     * @Create In 2014年12月18日 By lee
     * @param id
     * @return
     */
    @RequestMapping("/deleteUserRole")
    @ResponseBody
    public AjaxResult deleteUserRole(Long id) {
    	UserRole old = userRoleService.findById(id);
    	//UserContext.updateEngineerMenu(old.getUser());
		userRoleService.delete(old);
		return AjaxResult.successResult();
    }
   
    
    /**
     * 载入角色对应用户页面
     * @Methods Name loadRoleUser
     * @Create In 2014年12月18日 By lee
     * @param roleId
     * @return
     */
    @RequestMapping("/loadRoleUser")
    public ModelAndView loadRoleUser(WebParamInfo webParamInfo, Long roleId){
        Map<String, Object> param = webParamInfo.getParamStartingWith("search_");
        Pageable pageable = webParamInfo.getPageable();
        Page<UserRole> page = userRoleService.findByPage(pageable, param, roleId);
        ModelAndView mav = new ModelAndView();
        mav.addObject("page", page);
        mav.addObject("role",roleInfoService.findById(roleId));
        mav.addObject("searchParam", webParamInfo.getParam());
        mav.setViewName("/admin/security/roleUser_edit");
        return mav;
    }

    /**
     * 为角色加人员
     * 1 如果是用户加的是外部人员
     * 2 如果是内部用户……
     * @param size
     * @param searchKey
     * @return
     */
	@RequestMapping("/select")
	@ResponseBody
	public AjaxResult select(Integer size, String searchKey){
		List<UserInfo> list = userService.findUserForSelect(searchKey,size);
		return AjaxResult.objectResult(list);
	}
    
}
