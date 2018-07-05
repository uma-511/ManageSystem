package com.warrior.article.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.warrior.article.dao.ReportDao;
import com.warrior.article.entity.Report;
import com.warrior.article.service.ReportService;
import com.warrior.common.service.WarriorBaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class ReportServiceImpl extends WarriorBaseServiceImpl<ReportDao, Report> implements ReportService {

    @Override
    public Page<Report> getPageList(Page<Report> page , Date reportTime_start, Date reportTime_end,String status) {
        EntityWrapper<Report> ew = new EntityWrapper<>();
        if(reportTime_start != null){
            ew.ge("report_time",reportTime_start);
        }
        if(reportTime_end != null){
            ew.le("report_time",reportTime_end);
        }
        if(!status.equals("all")){
            ew.eq("sr.status",status);
        }
        page.setRecords(baseMapper.getPageList(page, ew));
        return page;
   }

    @Override
    public Map<String, Object> getDetail(int id) {
        Map<String, Object> report=baseMapper.getDetail(id);
        return report;
    }

    @Override
    public boolean updateStatus(int id,int status,String processing_mode,String result) {
        Report report=super.selectById(id);
        report.setStatus(status);
        return  baseMapper.updateStatus(report.getId(),report.getStatus(),report.getProcessing_mode(),report.getResult());
    }

}