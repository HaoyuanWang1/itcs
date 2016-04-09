package com.dc.itcs.security.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.flamingo.core.data.Criteria;
import com.dc.flamingo.core.data.Restrictions;
import com.dc.flamingo.core.service.BaseService;
import com.dc.itcs.security.dao.UserRoleDao;
import com.dc.itcs.security.entity.UserRole;

@Service
@Transactional(readOnly = true)
public class UserRoleService extends BaseService {
    @Autowired
    private UserRoleDao userRoleDao;

    public List<UserRole> findByRole_Id(Long roleId) {
        return userRoleDao.findByRole_Id(roleId);
    }
    
    public List<UserRole> findByRole_Rid(String rid) {
    	Criteria<UserRole> c = new Criteria<UserRole>();
    	c.add(Restrictions.eq("role.rid", rid, false));
    	return userRoleDao.findAll(c);
    }

    public List<UserRole> findByUser_Id(Long userId) {
        return userRoleDao.findByUser_Id(userId);
    }
    @Transactional
    public void saveUserRole(UserRole userRole) {
        userRoleDao.save(userRole);
    }
    
	public UserRole findById(Long id) {
		return userRoleDao.findOne(id);
	}

    @Transactional
    public void saveBatchUserRoleByUser(List<UserRole> list) {
        if (list != null && list.size() > 0) {
            userRoleDao.deleteByUserId(list.get(0).getUser().getId());
            userRoleDao.save(list);
        }

    }
    
    @Transactional
 	public void delete(UserRole userRole) {
 		userRoleDao.delete(userRole);
 		//UserContext.updateEngineerMenu(userRole.getUser());
 	}

    public UserRole findUserRole(Long userId, Long roleId) {
		return userRoleDao.findByUser_IdAndRole_Id(userId,roleId);
	}
    @Transactional
    public void saveBatchUserRoleByRole(List<UserRole> list) {
        if (list != null && list.size() > 0) {
            userRoleDao.deleteByRoleId(list.get(0).getRole().getId());
            userRoleDao.save(list);
        }

    }
    
    public Page<UserRole> findByPage(Pageable pageable, Map<String, Object> paramMap, Long roleId) {
		Criteria<UserRole> c = super.getCriteria(paramMap, UserRole.class);
		c.add(Restrictions.eq("role.id", roleId, false));
		return userRoleDao.findAll(c, pageable);
	}

}
