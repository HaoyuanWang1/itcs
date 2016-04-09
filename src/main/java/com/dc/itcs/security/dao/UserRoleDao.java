package com.dc.itcs.security.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.dc.flamingo.core.data.BaseDao;
import com.dc.itcs.security.entity.UserRole;

public interface UserRoleDao extends BaseDao<UserRole, Long> {

    List<UserRole> findByUser_Id(Long userId);

    List<UserRole> findByRole_Id(Long roleId);

    @Modifying
    @Query(value = "delete UserRole ur where ur.user.id=?1 ")
    void deleteByUserId(Long userId);

    @Modifying
    @Query(value = "delete UserRole ur where ur.role.id=?1 ")
    void deleteByRoleId(Long roleId);
    UserRole findByUser_IdAndRole_Id(Long userId, Long roleId);
}
