package com.warrior.permissions.service;

import com.baomidou.mybatisplus.service.IService;
import com.warrior.permissions.entity.Permission;
import com.warrior.permissions.entity.Resources;
import com.warrior.permissions.entity.UserRole;
import com.warrior.permissions.model.ResourceModel;

import java.util.LinkedList;
import java.util.List;

public interface PermissionService extends IService<Permission>{

    boolean updatePermission(long userId,int type,String permission);

    List<ResourceModel> getPermission(long ownId, int type);

    LinkedList<ResourceModel> getUserPermission();

}
