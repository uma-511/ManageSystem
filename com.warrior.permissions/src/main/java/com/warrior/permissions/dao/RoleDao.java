package com.warrior.permissions.dao;

import com.warrior.permissions.entity.Role;
import com.warrior.util.dao.WarriorBaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface RoleDao extends WarriorBaseMapper<Role> {

    List<Role> getRoleListByUser(long uid);

    List<Role> getRoleList(Map params);
}