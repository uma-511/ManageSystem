package com.warrior.article.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.warrior.article.entity.Classify;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClassifyDao extends BaseMapper<Classify> {

    List<Classify> getPageList(Pagination page, @Param("ew")Wrapper<Classify> wrapper);

    List<Classify> getList( @Param("ew")Wrapper<Classify> wrapper);

}