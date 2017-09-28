package com.warrior.base.service.impl;

import com.warrior.base.dao.UserRoleDao;
import com.warrior.base.entity.UserRole;
import com.warrior.base.service.UserRoleService;
import com.warrior.common.service.impl.WarriorBaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

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