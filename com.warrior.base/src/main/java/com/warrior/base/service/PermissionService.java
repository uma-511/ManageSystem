package com.warrior.base.service;

import com.baomidou.mybatisplus.service.IService;
import com.warrior.base.entity.Permission;
import com.warrior.base.entity.Resources;
import com.warrior.common.model.ResourceModel;

import java.util.LinkedList;
import java.util.List;

public interface PermissionService extends IService<Permission>{

    boolean updatePermission(long userId,int type,String permission);

    List<ResourceModel> getPermission(long ownId, int type);

    LinkedList<ResourceModel> getUserPermission(long uid);

    List<Resources> getPermissionByType(long ownId,int type);

    String getPermissionStr(long uid);
}