package com.warrior.user.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.warrior.user.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserDao extends BaseMapper<User> {

    List<User> getUserList(Pagination page, @Param("ew")Wrapper<User> wrapper);

    User getByUserName(String userName);

}
