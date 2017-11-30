package com.warrior.schedule.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import com.warrior.schedule.entity.Task;

public interface TaskDao extends BaseMapper<Task> {

    List<Task> getPageList(Pagination page, @Param("ew")Wrapper<Task> wrapper);

}