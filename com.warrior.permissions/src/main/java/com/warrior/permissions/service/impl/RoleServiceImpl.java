package com.warrior.permissions.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.warrior.permissions.dao.RoleDao;
import com.warrior.permissions.entity.Role;
import com.warrior.permissions.service.RoleService;
import com.warrior.util.service.WarriorBaseServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * The type Role service.
 */
@Service
public class RoleServiceImpl extends WarriorBaseServiceImpl<RoleDao,Role> implements RoleService {

    public List<Role> getRoleList(String roleName, int status){
        EntityWrapper<Role> ew = new EntityWrapper<>();
        if (!StringUtils.isBlank(roleName)){
            ew.eq("role_name",roleName);
        }
        if (status != -1){
            ew.eq("status",status);
        }
        return baseMapper.getRoleList(ew);
    }

    public List<Role> getRoleListByUser(long uid){
        return baseMapper.getRoleListByUser(uid);
    }

}