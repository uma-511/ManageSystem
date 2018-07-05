package com.warrior.article.controller;

import com.warrior.common.JSONMsg;
import com.warrior.common.annotation.SysLog;
import com.warrior.article.entity.Report;
import com.warrior.article.service.ReportService;
import com.warrior.common.web.WarriorBaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.Date;

@Api(value="ReportController",tags = "投诉管理",description = "投诉管理")
@RestController
@RequestMapping("/report")
public class ReportController extends WarriorBaseController {

    @Autowired
    private ReportService reportService;

    /**
    * 根据id获取投诉管理
    *
    * @param id 
    * @return
    */
    @RequiresPermissions("admin:report:view")
    @RequestMapping(value = "/{id}", method = {RequestMethod.GET})
    @ApiOperation(value = "获取投诉管理",httpMethod = "GET",response = JSONMsg.class)
    public JSONMsg queryReport(
        @ApiParam(name="id",value = "id",required = true)
        @PathVariable(value = "id") int id) {
        return buildMsg(reportService.getDetail(id));
    }

    /**
    * 新增投诉管理
    *
    * @param report
    * @return
    */
    @SysLog("新增投诉管理")
    @RequiresPermissions("admin:report:add")
    @RequestMapping(value = {""}, method = {RequestMethod.POST})
    @ApiOperation(value = "新增投诉管理",httpMethod = "POST",response = JSONMsg.class)
    public JSONMsg addReport(@ModelAttribute Report report) {
        return buildMsg(reportService.insert(report));
    }

    /**
    * 删除投诉管理
    *
    * @param id
    * @return
    */
    @SysLog("删除投诉管理")
    @RequiresPermissions("admin:report:del")
    @RequestMapping(value = "/{id}", method = {RequestMethod.DELETE})
    @ApiOperation(value = "删除report",httpMethod = "DELETE",response = JSONMsg.class)
    public JSONMsg delReport(
        @ApiParam(name="id",value = "id",required = true)
        @PathVariable(value = "id") int id) {
        return buildMsg(reportService.deleteById(id));
    }

    /**
    * 修改投诉管理
    *
    * @param report
    * @return
    */
    @SysLog("修改投诉管理")
    @RequiresPermissions("admin:report:update")
    @RequestMapping(value = "", method = {RequestMethod.PUT})
    @ApiOperation(value = "修改投诉管理",httpMethod = "PUT",response = JSONMsg.class)
    public JSONMsg modifiedReport(@ModelAttribute Report report) {
        return buildMsg(reportService.insertOrUpdate(report));
    }

    /**
    * 查询投诉管理列表
    *
    */
    @RequiresPermissions("admin:report:view")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value = "获取投诉管理列表",httpMethod = "GET",response = JSONMsg.class)
    public JSONMsg getReportList(
        @ApiParam(name="reportTime_start",value = "投诉时间")
        @RequestParam(name = "reportTime_start", defaultValue = "") Date reportTime_start,
        @ApiParam(name="reportTime_end",value = "投诉时间")
        @RequestParam(name = "reportTime_end", defaultValue = "") Date reportTime_end,
        @ApiParam(name="status",value = "处理状态")
        @RequestParam(name = "status", defaultValue = "") String status,
        @ApiParam(name="page",value = "页码")
        @RequestParam(name="page",defaultValue = "1")int page,
        @ApiParam(name="rows",value = "分页大小")
        @RequestParam(name="rows",defaultValue = "10")int rows) {

        return buildMsg(reportService.getPageList(new Page<Report>(page,rows),reportTime_start,reportTime_end,status));
    }

    /**
     * 更新投诉状态
     *
     */
    @RequiresPermissions("admin:report:update")
    @RequestMapping(value = "/updateStatus", method = RequestMethod.GET)
    @ApiOperation(value = "获取投诉管理列表",httpMethod = "PUT",response = JSONMsg.class)
    public JSONMsg getReportList(
            @ApiParam(name="id",value = "id",required = true)
            @RequestParam(value = "id") int id,

            @ApiParam(name="status",value = "status",required = true)
            @RequestParam(value = "status") int status,
            @ApiParam(name="processing_mode",value = "processing_mode",required = true)
            @RequestParam(value = "processing_mode") String processing_mode,
            @ApiParam(name="result",value = "result",required = true)
            @RequestParam(value = "result") String result) {

        return buildMsg(reportService.updateStatus(id,status,processing_mode,result));
    }
}