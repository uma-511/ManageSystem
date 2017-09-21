package com.warrior.permissions.service;

import com.baomidou.mybatisplus.service.IService;
import com.warrior.permissions.entity.Role;

import java.util.List;

public interface RoleService extends IService<Role>{

    List<Role> getRoleList(String roleName, int status);

    List<Role> getRoleListByUser(long uid);

}
