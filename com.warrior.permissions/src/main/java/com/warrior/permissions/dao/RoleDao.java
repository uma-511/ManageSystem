package com.warrior.permissions.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.warrior.permissions.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleDao extends BaseMapper<Role> {

    List<Role> getRoleListByUser(long uid);

    List<Role> getRoleList(@Param("ew")Wrapper<Role> wrapper);
}