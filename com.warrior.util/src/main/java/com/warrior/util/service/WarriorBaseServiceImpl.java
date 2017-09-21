package com.warrior.util.service;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

public abstract class WarriorBaseServiceImpl<M extends BaseMapper<T>,T> extends ServiceImpl<M,T> {

}
