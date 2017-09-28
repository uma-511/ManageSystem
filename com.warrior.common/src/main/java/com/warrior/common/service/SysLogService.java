package com.warrior.common.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.warrior.common.entity.SysLog;

public interface SysLogService extends IService<SysLog>{

    Page<SysLog> getListByPage(String keyWord, int page, int rows);

    boolean delById(String ids);

    boolean delAll();
}