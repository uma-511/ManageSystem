package com.warrior.schedule.controller;

import com.warrior.common.JSONMsg;
import com.warrior.common.annotation.SysLog;
import com.warrior.schedule.entity.Task;
import com.warrior.schedule.service.TaskService;
import com.warrior.common.web.WarriorBaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.Date;

@Api(value="TaskController",tags = "定时作业",description = "定时作业")
@RestController
@RequestMapping("/task")
public class TaskController extends WarriorBaseController {

    @Autowired
    private TaskService taskService;

    /**
    * 根据id获取定时作业
    *
    * @param id 
    * @return
    */
    @RequiresPermissions("admin:task:view")
    @RequestMapping(value = "/{id}", method = {RequestMethod.GET})
    @ApiOperation(value = "获取定时作业",httpMethod = "GET",response = JSONMsg.class)
    public JSONMsg queryTask(
        @ApiParam(name="id",value = "id",required = true)
        @PathVariable(value = "id") int id) {
        return buildMsg(taskService.selectById(id));
    }

    /**
    * 新增定时作业
    *
    * @param task
    * @return
    */
    @SysLog("新增定时作业")
    @RequiresPermissions("admin:task:add")
    @RequestMapping(value = {""}, method = {RequestMethod.POST})
    @ApiOperation(value = "新增定时作业",httpMethod = "POST",response = JSONMsg.class)
    public JSONMsg addTask(@ModelAttribute Task task) {
        task.setCreateTime(new Date());
        return buildMsg(taskService.insert(task));
    }

    /**
    * 删除定时作业
    *
    * @param id
    * @return
    */
    @SysLog("删除定时作业")
    @RequiresPermissions("admin:task:del")
    @RequestMapping(value = "/{id}", method = {RequestMethod.DELETE})
    @ApiOperation(value = "删除task",httpMethod = "DELETE",response = JSONMsg.class)
    public JSONMsg delTask(
        @ApiParam(name="id",value = "id",required = true)
        @PathVariable(value = "id") int id) {
        return buildMsg(taskService.deleteById(id));
    }

    /**
    * 修改定时作业
    *
    * @param task
    * @return
    */
    @SysLog("修改定时作业")
    @RequiresPermissions("admin:task:update")
    @RequestMapping(value = "", method = {RequestMethod.PUT})
    @ApiOperation(value = "修改定时作业",httpMethod = "PUT",response = JSONMsg.class)
    public JSONMsg modifiedTask(@ModelAttribute Task task) {
        task.setUpdateTime(new Date());
        return buildMsg(taskService.insertOrUpdate(task));
    }

    /**
    * 查询定时作业列表
    *
    */
    @RequiresPermissions("admin:task:view")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value = "获取定时作业列表",httpMethod = "GET",response = JSONMsg.class)
    public JSONMsg getTaskList(
        @ApiParam(name="taskName",value = "任务名称")
        @RequestParam(name = "taskName", defaultValue = "") String taskName,
        @ApiParam(name="taskGroup",value = "任务分组")
        @RequestParam(name = "taskGroup", defaultValue = "") String taskGroup,
        @ApiParam(name="status",value = "任务状态")
        @RequestParam(name = "status", defaultValue = "") int status,
        @ApiParam(name="page",value = "页码")
        @RequestParam(name="page",defaultValue = "1")int page,
        @ApiParam(name="rows",value = "分页大小")
        @RequestParam(name="rows",defaultValue = "10")int rows) {

        return buildMsg(taskService.getPageList(new Page<Task>(page,rows),taskName,taskGroup,status));
    }

    /**
     * 获取所有的定时作业执行类信息
     * @return
     */
    @RequiresPermissions("admin:task:view")
    @RequestMapping(value = "/jobClass",method = RequestMethod.GET)
    @ApiOperation(value = "获取所有的定时作业执行类信息",httpMethod = "GET",response = JSONMsg.class)
    public JSONMsg getJobClassList(){
        return buildMsg(taskService.scanPackage());
    }
}