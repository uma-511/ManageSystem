package com.warrior.base.service;

import com.baomidou.mybatisplus.service.IService;
import com.warrior.base.entity.UserRole;

public interface UserRoleService extends IService<UserRole>{

    String getRoleByUser(long uid);

    String getRoleNameByUser(long uid);

}
