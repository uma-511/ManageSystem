package com.warrior.article.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.warrior.article.entity.Report;

import java.util.Date;
import java.util.Map;

public interface ReportService extends IService<Report>{

    Page<Report> getPageList(Page<Report> page,Date reportTime_start,Date reportTime_end,String status);

    Map<String, Object> getDetail(int id);

    boolean updateStatus(int id,int status,String processing_mode,String result);
}
