package com.warrior.schedule.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.warrior.common.service.WarriorBaseServiceImpl;
import com.warrior.schedule.entity.ScheduleJob;
import com.warrior.schedule.service.ScheduleService;
import com.warrior.schedule.spring.PackscanUtil;
import lombok.extern.log4j.Log4j;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.warrior.schedule.service.TaskService;
import com.warrior.schedule.dao.TaskDao;
import com.warrior.schedule.entity.Task;
import org.apache.commons.lang.StringUtils;
import javax.annotation.PostConstruct;
import java.util.List;
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
            ew.eq("status",TASK_STATUS_NORMAL);

            List<Task> list = baseMapper.selectList(ew);
            if (list != null && list.size() > 0){
                for (Task task : list){
                    scheduleService.addJob(new ScheduleJob(task.getTaskName(),task.getTaskGroup(),task.getCron(),task.getRemark(),task.getScriptName(),task.getClassName()));
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

   public Set<String> scanPackage(){
       return PackscanUtil.findPackageClass("com.warrior");
   }
}