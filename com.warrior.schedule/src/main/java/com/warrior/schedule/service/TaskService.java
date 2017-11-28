package com.warrior.schedule.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.warrior.schedule.entity.Task;

import java.util.Set;

public interface TaskService extends IService<Task>{

    int TASK_STATUS_NORMAL = 0;
    int TASK_STATUS_PAUSE = 1;

    Page<Task> getPageList(Page<Task> page,String taskName,String taskGroup,int status);

    Set<String> scanPackage();

}