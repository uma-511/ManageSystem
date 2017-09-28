package com.warrior.base.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.warrior.base.entity.Role;
import com.warrior.common.model.RoleModel;

import java.util.List;

public interface RoleService extends IService<Role>{

    Page<Role> getRoleList(String roleName, int status, int page, int rows);

    List<RoleModel> getRoleByUser(long uid);

    boolean updateUserRole(long uid,String permissions);

    List<Role> getRoleListByUser(long uid);
}
