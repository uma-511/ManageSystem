package com.warrior.permissions.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.warrior.permissions.entity.Permission;
import com.warrior.permissions.entity.Resources;

import java.util.List;

public interface PermissionDao extends BaseMapper<Permission> {

    List<Resources> getResourcesByRole(long userId);

    List<Resources> getResourcesByUser(long userId);

    List<Resources> getResourcess(Permission permission);
}