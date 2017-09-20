package com.warrior.permissions.service.impl;

import com.warrior.permissions.dao.RoleDao;
import com.warrior.permissions.entity.Role;
import com.warrior.permissions.service.RoleService;
import com.warrior.util.common.QueryParams;
import com.warrior.util.dao.WarriorBaseMapper;
import com.warrior.util.service.WarriorBaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * The type Role service.
 */
@Service
public class RoleServiceImpl extends WarriorBaseServiceImpl<Role> implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public WarriorBaseMapper<Role> getBaseMapper() {
        return (WarriorBaseMapper<Role>)roleDao;
    }

    public List<Role> getRoleList(String roleName, int status){
        return roleDao.getRoleList(new QueryParams()
            .addStr("roleName",roleName)
            .addNum("status",status,-1));
    }

    public List<Role> getRoleListByUser(long uid){
        return roleDao.getRoleListByUser(uid);
    }

}