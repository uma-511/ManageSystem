package com.warrior.base.service;

import com.baomidou.mybatisplus.service.IService;
import com.warrior.base.entity.Permission;
import com.warrior.base.model.ResourceModel;

import java.util.LinkedList;
import java.util.List;

public interface PermissionService extends IService<Permission>{

    boolean updatePermission(long userId,int type,String permission);

    List<ResourceModel> getPermission(long ownId, int type);

    LinkedList<ResourceModel> getUserPermission(long uid);

    String getPermissionStr(long uid);
}