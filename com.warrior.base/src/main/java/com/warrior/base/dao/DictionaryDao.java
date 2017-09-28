package com.warrior.base.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.warrior.base.entity.Dictionary;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DictionaryDao extends BaseMapper<Dictionary> {

    List<Dictionary> getListByType(int dicType);

    Dictionary getByAttribute(@Param("ew")Wrapper<Dictionary> wrapper);

    List<Dictionary> getList(Pagination page,@Param("ew")Wrapper<Dictionary> wrapper);

    List<Dictionary> getTypeList();
}
