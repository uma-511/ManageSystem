package com.warrior.permissions.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.warrior.permissions.entity.Permission;
import com.warrior.permissions.entity.Resources;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PermissionDao extends BaseMapper<Permission> {

    List<Resources> getResourcesByRole(long userId);

    List<Resources> getResourcesByUser(long userId);

    List<Integer> getPermission(Permission permission);

    int delPermission(@Param("ownId") long ownId,@Param("type") int type);

}