package com.warrior.article.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.warrior.article.entity.AppUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AppUserDao extends BaseMapper<AppUser> {

    List<AppUser> getPageList(Pagination page, @Param("ew")Wrapper<AppUser> wrapper);

    boolean cancelUserById(@Param("id") int id);

    boolean disableUserById(@Param("id") int id);

    boolean enableUserById(@Param("id") int id);
}