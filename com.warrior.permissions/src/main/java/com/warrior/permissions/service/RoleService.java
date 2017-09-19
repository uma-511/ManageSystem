package com.warrior.permissions.service;

import com.warrior.permissions.entity.Role;
import com.warrior.util.service.WarriorBaseService;

import java.util.List;

public interface RoleService extends WarriorBaseService<Role> {

    List<Role> getRoleList(String roleName, int status);

    List<Role> getRoleListByUser(long uid);

}
