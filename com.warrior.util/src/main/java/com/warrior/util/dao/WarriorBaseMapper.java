package com.warrior.util.dao;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface WarriorBaseMapper<T> extends Mapper<T>,MySqlMapper<T> {

}