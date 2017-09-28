package com.warrior.common.controller;

import com.warrior.common.JSONMsg;
import com.warrior.common.annotation.SysLog;
import com.warrior.common.service.SysLogService;
import com.warrior.common.web.WarriorBaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/syslog")
public class SysLogContrller extends WarriorBaseController {

    @Autowired
    private SysLogService sysLogService;

    @SysLog("系统日志查询")
    @RequiresPermissions("admin:log:view")
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public JSONMsg getListByPage(
            @RequestParam(value = "keyWord",defaultValue = "") String keyWord,
            @RequestParam(value = "page",defaultValue = "1") int page,
            @RequestParam(value = "rows",defaultValue = "10") int rows){
        return buildMsg(sysLogService.getListByPage(keyWord,page,rows));
    }

    @SysLog("删除系统日志")
    @RequiresPermissions("admin:log:del")
    @RequestMapping(value="/{id}",method = RequestMethod.DELETE)
    public JSONMsg delByIds(@PathVariable(value = "id") String ids){
        return buildMsg(sysLogService.delById(ids));
    }

    @SysLog("删除系统日志")
    @RequiresPermissions("admin:log:del")
    @RequestMapping(value="/all",method = RequestMethod.DELETE)
    public JSONMsg delAll(){
        return buildMsg(sysLogService.delAll());
    }
}