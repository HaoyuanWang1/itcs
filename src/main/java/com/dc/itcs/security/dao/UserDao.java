package com.dc.itcs.security.dao;

import java.util.List;
import com.dc.flamingo.core.data.BaseDao;
import com.dc.itcs.security.entity.UserInfo;

public interface UserDao extends BaseDao<UserInfo, Long> {

	UserInfo findByUid(String uid);

	List<UserInfo> findByTenant_CodeOrTenant_CodeAndTenant_StateAndEnabled(
			String codeITS, String codeDC, Integer tenantState, Integer enabled);

	List<UserInfo> findByTenant_IdAndTenant_StateAndEnabled(Long tenantId,
			Integer tenantState, Integer enabled);

	UserInfo findByUidAndEnabled(String uid,Integer enabled);

	List<UserInfo> findByTenant_id(Long id);

}
