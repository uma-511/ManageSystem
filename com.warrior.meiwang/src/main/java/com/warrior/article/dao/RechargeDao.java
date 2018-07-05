package com.warrior.article.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import com.warrior.article.entity.Recharge;

public interface RechargeDao extends BaseMapper<Recharge> {

    List<Recharge> getPageList(Pagination page, @Param("ew")Wrapper<Recharge> wrapper);

}