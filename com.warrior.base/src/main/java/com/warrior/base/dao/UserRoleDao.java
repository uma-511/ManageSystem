package com.warrior.base.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import com.warrior.base.entity.UserRole;
import org.apache.ibatis.annotations.Param;

public interface UserRoleDao extends BaseMapper<UserRole> {

    List<Integer> getRoleByUser(@Param("uid")long uid);

}
