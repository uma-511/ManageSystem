package com.warrior.base.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.warrior.base.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleDao extends BaseMapper<Role> {

    List<Role> getRoleList(Pagination page, @Param("ew")Wrapper<Role> wrapper);

}