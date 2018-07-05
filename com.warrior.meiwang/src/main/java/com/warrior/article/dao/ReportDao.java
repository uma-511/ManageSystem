package com.warrior.article.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.warrior.article.entity.Report;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ReportDao extends BaseMapper<Report> {

    List<Report> getPageList(Pagination page, @Param("ew")Wrapper<Report> wrapper);

    Map<String, Object> getDetail(@Param("id")int id);

    boolean updateStatus(@Param("id")int id,@Param("status")int status,@Param("processing_mode")String processing_mode,@Param("result")String result);
}