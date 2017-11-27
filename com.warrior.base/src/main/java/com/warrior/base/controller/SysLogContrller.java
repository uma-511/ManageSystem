package com.warrior.base.controller;

import com.warrior.common.JSONMsg;
import com.warrior.common.annotation.SysLog;
import com.warrior.base.service.SysLogService;
import com.warrior.common.cache.PushCache;
import com.warrior.common.web.WarriorBaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value="系统日志",tags = {"系统日志管理"},description = "系统日志")
@RestController
@RequestMapping("/syslog")
public class SysLogContrller extends WarriorBaseController {

    @Autowired
    private SysLogService sysLogService;

    @RequestMapping(value = "/client",method = RequestMethod.GET)
    public JSONMsg getClientList(){
        return buildMsg(PushCache.getAllClient());
    }

    @RequiresPermissions("admin:log:view")
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ApiOperation(value = "系统日志查询",httpMethod = "GET",response = JSONMsg.class)
    public JSONMsg getListByPage(
            @ApiParam(name="keyWord",value = "关键字")
            @RequestParam(value = "keyWord",defaultValue = "") String keyWord,
            @ApiParam(name="page",value = "页码",required = true)
            @RequestParam(value = "page",defaultValue = "1") int page,
            @ApiParam(name="rows",value = "每页大小",required = true)
            @RequestParam(value = "rows",defaultValue = "10") int rows){
        return buildMsg(sysLogService.getListByPage(keyWord,page,rows));
    }

    @SysLog("删除系统日志")
    @RequiresPermissions("admin:log:del")
    @RequestMapping(value="/{id}",method = RequestMethod.DELETE)
    @ApiOperation(value="删除单个系统日志",httpMethod = "DELETE",response = JSONMsg.class)
    public JSONMsg delByIds(
            @ApiParam(name="id",value = "ID",required = true)
            @PathVariable(value = "id") String ids){
        return buildMsg(sysLogService.delById(ids));
    }

    @SysLog("删除系统日志")
    @RequiresPermissions("admin:log:del")
    @RequestMapping(value="/all",method = RequestMethod.DELETE)
    @ApiOperation(value="删除所有系统日志",httpMethod = "DELETE",response = JSONMsg.class)
    public JSONMsg delAll(){
        return buildMsg(sysLogService.delAll());
    }
}