package com.warrior.base.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.warrior.base.entity.Permission;
import com.warrior.base.entity.Resources;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PermissionDao extends BaseMapper<Permission> {

    List<Resources> getResourcesByRole(@Param("roleId") long roleId,@Param("type") int type);

    List<Resources> getResourcesByUser(@Param("userId") long userId,@Param("type") int type);

    List<Integer> getPermission(@Param("ownId") long own_id,@Param("type") int type);

    int delPermission(@Param("ownId") long ownId,@Param("type") int type);

    String getPermissionStr(@Param("userId") long userId);
}