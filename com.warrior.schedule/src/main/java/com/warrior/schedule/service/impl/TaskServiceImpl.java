package com.warrior.schedule.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.warrior.common.service.WarriorBaseServiceImpl;
import com.warrior.schedule.dao.TaskDao;
import com.warrior.schedule.entity.ScheduleJob;
import com.warrior.schedule.entity.Task;
import com.warrior.schedule.service.ScheduleService;
import com.warrior.schedule.service.TaskService;
import com.warrior.schedule.spring.PackscanUtil;
import com.warrior.schedule.task.JobTarget;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang.StringUtils;
import org.joor.Reflect;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Log4j
@Service
public class TaskServiceImpl extends WarriorBaseServiceImpl<TaskDao, Task> implements TaskService {

    @Autowired
    private ScheduleService scheduleService;

    @PostConstruct
    public void init(){
        try {
            EntityWrapper<Task> ew = new EntityWrapper<>();
            List<Task> list = baseMapper.selectList(ew);
            if (list != null && list.size() > 0){
                for (Task task : list){
                    scheduleService.addJob(new ScheduleJob(task.getTaskName(),task.getTaskGroup(),task.getCron(),task.getRemark(),task.getScriptName(),task.getClassName()));
                    if (task.getStatus() == TASK_STATUS_PAUSE){
                        scheduleService.pauseJob(task.getTaskName(),task.getTaskGroup());
                    }
                }
            }
            scheduleService.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public Page<Task> getPageList(Page<Task> page ,String taskName,String taskGroup,int status) {
        EntityWrapper<Task> ew = new EntityWrapper<>();
        if(!StringUtils.isBlank(taskName)){
            ew.eq("task_name",taskName);
        }
        if(!StringUtils.isBlank(taskGroup)){
            ew.eq("task_group",taskGroup);
        }
        if(status != -1){
            ew.eq("status",status);
        }
        page.setRecords(baseMapper.getPageList(page, ew));
        return page;
   }

   public List<Map<String,String>> scanPackage(){
        Set<String> classSet = PackscanUtil.findPackageClass("com.warrior");
        List<Map<String,String>> list = Lists.newArrayList();
        if(classSet != null && classSet.size() > 0){
            classSet.forEach(item->{
                Reflect ref = Reflect.on(item);
                Class cls = ref.get();
                JobTarget ann = (JobTarget) cls.getAnnotation(JobTarget.class);
                Map<String,String> data = Maps.newHashMap();
                data.put("label",ann.value());
                data.put("value",item);
                list.add(data);
            });
            classSet.clear();
        }
       return list;
   }

   @Override
   public boolean insertOrUpdate(Task task){
       try {
           Task current = baseMapper.selectById(task.getId());
           task.setUpdateTime(new Date());
           updateById(task);
           scheduleService.deleteJob(current.getTaskName(),current.getTaskGroup());
           scheduleService.addJob(new ScheduleJob(task.getTaskName(),task.getTaskGroup(),task.getCron(),task.getRemark(),task.getScriptName(),task.getClassName()));
           return true;
       } catch (SchedulerException e) {
           e.printStackTrace();
       }
       return false;
   }

    @Override
    public boolean deleteById(Serializable id) {
        Task task = baseMapper.selectById(id);
        try {
            scheduleService.deleteJob(task.getTaskName(),task.getTaskGroup());
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return super.deleteById(id);
    }

    @Override
    public boolean insert(Task task) {
        try {
            task.setCreateTime(new Date());
            scheduleService.addJob(new ScheduleJob(task.getTaskName(),task.getTaskGroup(),task.getCron(),task.getRemark(),task.getScriptName(),task.getClassName()));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return super.insert(task);
    }

    public boolean pauseOrResume(int id,int status){
        Task task = baseMapper.selectById(id);
        try {
            task.setStatus(status);
            task.setUpdateTime(new Date());
            baseMapper.updateById(task);
            if(status == TASK_STATUS_NORMAL){
                scheduleService.resumeJob(task.getTaskName(),task.getTaskGroup());
            }else{
                scheduleService.pauseJob(task.getTaskName(),task.getTaskGroup());
            }
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return false;
    }
}