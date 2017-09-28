package com.warrior.common.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.warrior.common.entity.SysLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysLogDao extends BaseMapper<SysLog>{

    List<SysLog> getListByPage(Pagination page, @Param("ew")Wrapper<SysLog> wrapper);

}