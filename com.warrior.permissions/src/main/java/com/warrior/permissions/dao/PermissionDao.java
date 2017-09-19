package com.warrior.permissions.dao;

import com.warrior.permissions.entity.Permission;
import com.warrior.permissions.entity.Resource;
import com.warrior.util.dao.WarriorBaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionDao extends WarriorBaseMapper<Permission> {

    List<Resource> getResourcesByRole(long userId);

    List<Resource> getResourcesByUser(long userId);

    List<Resource> getResourcess(Permission permission);
}