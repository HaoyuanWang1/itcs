package com.dc.itcs.security.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.dc.flamingo.core.data.Criteria;
import com.dc.flamingo.core.data.Criterion;
import com.dc.flamingo.core.data.Restrictions;
import com.dc.flamingo.core.service.BaseService;
import com.dc.flamingo.core.support.PageParam;
import com.dc.flamingo.core.utils.StrUtils;
import com.dc.itcs.core.base.support.ItcsConstants;
import com.dc.itcs.security.dao.UserDao;
import com.dc.itcs.security.dao.UserRoleDao;
import com.dc.itcs.security.entity.UserInfo;
import com.dc.itcs.security.entity.UserRole;

@Service("userInfoService")
@Transactional(readOnly=true)
public class UserInfoService extends BaseService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private UserRoleDao userRoleDao;
	
	public UserInfo findById(Long id) {
		return userDao.findOne(id);
	}
	
	public UserInfo findByUid(String uid) {
		return userDao.findByUid(uid);
	}
	
	public UserInfo findByUidAndEnabled(String uid) {
		return userDao.findByUidAndEnabled(uid,ItcsConstants.STATE_ON);
	}
	
	public List<UserInfo> findEnabledUserByTenantId(Long tenantId) {
		return userDao.findByTenant_IdAndTenant_StateAndEnabled(tenantId,ItcsConstants.IS_YES,ItcsConstants.IS_YES);
	}

	public List<String> findRidByUserId(Long userId) {
		List<UserRole> urs = userRoleDao.findByUser_Id(userId);
		List<String> roles = new ArrayList<String>();
		for(UserRole ur : urs){
			roles.add(ur.getRole().getRid());
		}
		return roles;
	}

	public Page<UserInfo> findByPage(Pageable pageable, Map<String, Object> paramMap) {
		Criteria<UserInfo> c = super.getCriteria(paramMap, UserInfo.class);
		return userDao.findAll(c, pageable);
	}
	
	@Transactional
	public void remove(UserInfo userInfo) {
		userDao.delete(userInfo);
	}
	
	@Transactional
	public UserInfo saveUserInfo(UserInfo userInfo) {
		return userDao.save(userInfo);
	}

	public List<UserInfo> findUserForSelect(String searchKey,Integer size) {
		return this.findUserForSelectByTenantType(searchKey, null, size);
	}

	/**
	 * 根据uid或名字及所属组织类型查询用户集合
	 * @Methods Name findUserForSelect
	 * @Create In 2015年8月13日 By lee
	 * @param searchKey	uid或用户名查询条件
	 * @param tenantType	组织类型
	 * @param size	返回数据条数
	 * @return
	 */
	public List<UserInfo> findUserForSelectByTenantType(String searchKey, String tenantType, Integer size) {
		Criteria<UserInfo> c = super.getCriteria(UserInfo.class);
		//只获取可用用户
		c.add(Restrictions.eq("enabled", ItcsConstants.IS_YES, true));
		//加入模糊查询条件
		if(StrUtils.isNotEmpty(searchKey)){
			c.add(Restrictions.or(
					Restrictions.like("uid", searchKey, true),
					Restrictions.like("userName", searchKey, true)
					));
		}
		c.add(Restrictions.eq("tenant.type", tenantType, true));
		return userDao.findAll(c, new PageParam(0,size)).getContent();
	}
	
	public List<UserInfo> findUserOfTenantForSelect(String searchKey,Integer size,String code) {
		return this.findForSelectByTenantCode(searchKey, size, code);
	}
	
	/**
	 * 根据用户uid或中文名模糊匹配+客户检索用户集合
	 * @param searchKey	模糊匹配名字
	 * @param size	条目数
	 * @return
	 */
	public List<UserInfo> findForSelectByTenantCode(String searchKey, Integer size, String... tenantCodes) {
		Criteria<UserInfo> c = super.getCriteria(UserInfo.class);
		
		c.add(Restrictions.eq("enabled", ItcsConstants.IS_YES, true));/**共同部分*/
		if(StrUtils.isNotEmpty(searchKey)){
			c.add(Restrictions.or(
					Restrictions.like("uid", searchKey, true),
					Restrictions.like("userName", searchKey, true)
					));
		}
		if(tenantCodes!=null&&tenantCodes.length>0){
			Criterion[] tanantExpressions = new Criterion[tenantCodes.length];
			for(int i=0;i<tenantCodes.length; i++ ){
				tanantExpressions[i] = Restrictions.like("tenant.code", tenantCodes[i], true);
			}
			c.add(Restrictions.or(tanantExpressions));
		}
		
		return userDao.findAll(c, new PageParam(0,size)).getContent();
	}
	
	public List<UserInfo> findByIds(Iterable<Long> ids) {
		return userDao.findAll(ids);
	}
	
	/**
	 * 根据组户id查询该组户下的所有人员
	 * @Methods Name findByTenant_id
	 * @Create In 2015年8月14日 By liulx
	 * @param id
	 * @return List<UserInfo>
	 */
	public List<UserInfo> findByTenant_id(Long id) {
		return userDao.findByTenant_id(id);
	}

}
