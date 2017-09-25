package com.warrior.permissions.service.impl;

import java.util.List;
import com.warrior.permissions.dao.UserRoleDao;
import com.warrior.permissions.entity.UserRole;
import com.warrior.permissions.service.UserRoleService;
import com.warrior.util.service.WarriorBaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl extends WarriorBaseServiceImpl<UserRoleDao,UserRole> implements UserRoleService {


    @Override
    public String getRoleByUser(long uid) {
        List<Integer> list = baseMapper.getRoleByUser(uid);
        String roleStr = "";
        for (Integer num:list) {
            roleStr +=num + "$";
        }
        return roleStr;
    }
}