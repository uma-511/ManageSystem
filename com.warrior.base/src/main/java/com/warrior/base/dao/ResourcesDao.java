package com.warrior.base.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.warrior.base.entity.Resources;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ResourcesDao extends BaseMapper<Resources> {

    List<Resources> getListByParentId(int parentId);

    List<Resources> getListByPage(Pagination page, @Param("ew")Wrapper<Resources> wrapper);
}
