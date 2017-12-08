package com.warrior.schedule.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.warrior.schedule.entity.Task;

import java.util.List;
import java.util.Map;

public interface TaskService extends IService<Task>{

    Page<Task> getPageList(Page<Task> page,String taskName,String taskGroup,int status);

    List<Map<String,String>> scanPackage();

    boolean pauseOrResume(int id,int status);
}