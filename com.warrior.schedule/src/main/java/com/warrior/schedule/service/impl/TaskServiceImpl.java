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
import com.warrior.schedule.task.Job;
import com.warrior.schedule.task.JobExecute;
import com.warrior.schedule.task.TaskRun;
import com.warrior.util.common.ClassPathScanHandler;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang.StringUtils;
import org.joor.Reflect;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

@Log4j
@Service
public class TaskServiceImpl extends WarriorBaseServiceImpl<TaskDao, Task> implements TaskService {

    @Autowired
    private ScheduleService scheduleService;

    @PostConstruct
    public void init() {
        try {
            EntityWrapper<Task> ew = new EntityWrapper<>();
            List<Task> list = baseMapper.selectList(ew);
            if (list != null && list.size() > 0) {
                for (Task task : list) {
                    scheduleService.addJob(getScheduleJob(task));
                }
            }
            scheduleService.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public Page<Task> getPageList(Page<Task> page, String taskName, String taskGroup, int status) {
        EntityWrapper<Task> ew = new EntityWrapper<>();
        if (!StringUtils.isBlank(taskName)) {
            ew.eq("task_name", taskName);
        }
        if (!StringUtils.isBlank(taskGroup)) {
            ew.eq("task_group", taskGroup);
        }
        if (status != -1) {
            ew.eq("status", status);
        }
        page.setRecords(baseMapper.getPageList(page, ew));
        return page;
    }

    public List<Map<String, String>> scanPackage() {
        List<Map<String, String>> list = Lists.newArrayList();

        ClassPathScanHandler scanHandler = new ClassPathScanHandler();
        Set<Class<?>> cls = scanHandler.getPackageAllClasses("com.warrior",true);
        cls.forEach(item->{
            Annotation annotation = item.getAnnotation(Job.class);
            if (annotation != null){
                Method [] methods = item.getMethods();
                Arrays.stream(methods).forEach(m ->{
                    JobExecute jobExecute = m.getAnnotation(JobExecute.class);
                    if(jobExecute != null){
                        Map<String, String> data = Maps.newHashMap();
                        data.put("label",jobExecute.value());
                        data.put("value",item.getName()+"."+m.getName());
                        list.add(data);
                    }
                });
            }
        });
        cls.clear();
        return list;
    }

    @Override
    public boolean insertOrUpdate(Task task) {
        try {
            Task current = baseMapper.selectById(task.getId());
            task.setUpdateTime(new Date());
            updateById(task);
            scheduleService.deleteJob(current.getTaskName(), current.getTaskGroup());
            scheduleService.addJob(getScheduleJob(task));
            if (task.getStatus() == ScheduleJob.TASK_STATUS_PAUSE){
                scheduleService.pauseJob(task.getTaskName(),task.getTaskGroup());
            }
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
            scheduleService.deleteJob(task.getTaskName(), task.getTaskGroup());
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return super.deleteById(id);
    }

    @Override
    public boolean insert(Task task) {
        try {
            task.setCreateTime(new Date());
            scheduleService.addJob(getScheduleJob(task));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return super.insert(task);
    }

    public boolean pauseOrResume(int id, int status) {
        Task task = baseMapper.selectById(id);
        try {
            task.setStatus(status);
            task.setUpdateTime(new Date());
            baseMapper.updateById(task);
            if (status == ScheduleJob.TASK_STATUS_NORMAL) {
                scheduleService.resumeJob(task.getTaskName(), task.getTaskGroup());
            } else {
                scheduleService.pauseJob(task.getTaskName(), task.getTaskGroup());
            }
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return false;
    }

    private ScheduleJob getScheduleJob(Task task){
        ScheduleJob job = new ScheduleJob(task.getTaskName(), task.getTaskGroup(), task.getCron(), task.getRemark(), task.getScriptName(), StringUtils.isEmpty(task.getScriptName()) ? TaskRun.class.getName() : "",task.getStatus());
        if (StringUtils.isEmpty(task.getScriptName())) {
            String className = task.getClassName().substring(0, task.getClassName().lastIndexOf('.'));
            job.addParam(ScheduleService.QUARTZ_CLASS, Reflect.on(className));
            job.addParam(ScheduleService.QUARTZ_METHOD, task.getClassName().substring(task.getClassName().lastIndexOf('.'), task.getClassName().length()));
        }
        return job;
    }
}