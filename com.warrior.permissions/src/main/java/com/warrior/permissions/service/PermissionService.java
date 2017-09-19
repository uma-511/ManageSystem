package com.warrior.permissions.service;

import com.warrior.permissions.entity.Permission;
import com.warrior.permissions.entity.Resource;
import com.warrior.permissions.entity.UserRole;
import com.warrior.permissions.model.ResourceModel;
import com.warrior.util.service.WarriorBaseService;

import java.util.LinkedList;
import java.util.List;

public interface PermissionService extends WarriorBaseService<Permission> {

    UserRole addUserRole(long userId, long roleId);

    boolean delUserRole(long id);

    Permission addUserPermission(long userId,long resId);

    boolean delUserPermission(long id);

    Permission addRolePermission(long roleId,long resId);

    boolean delRolePermission(long id);

    List<Resource> getPermission(long ownId, int type);

    LinkedList<ResourceModel> getUserPermission();

}
