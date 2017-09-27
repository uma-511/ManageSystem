package com.warrior.permissions.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.warrior.permissions.entity.Role;
import com.warrior.permissions.model.RoleModel;

import java.util.List;

public interface RoleService extends IService<Role>{

    Page<Role> getRoleList(String roleName, int status, int page, int rows);

    List<RoleModel> getRoleByUser(long uid);

    boolean updateUserRole(long uid,String permissions);

    List<Role> getRoleListByUser(long uid);
}
