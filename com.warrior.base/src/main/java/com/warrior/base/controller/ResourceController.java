package com.warrior.base.controller;

import com.warrior.common.JSONMsg;
import com.warrior.base.entity.Resources;
import com.warrior.base.service.ResourceService;
import com.warrior.common.annotation.SysLog;
import com.warrior.common.web.WarriorBaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/***
 * 资源管理
 *
 */
@Api(value="ResourceController",tags = "资源管理",description = "资源管理")
@RestController
@RequestMapping("/resource")
public class ResourceController extends WarriorBaseController {

    @Autowired
    private ResourceService resourceService;


    @RequiresPermissions("admin:resource:view")
    @RequestMapping(value = "/parent/list",method = RequestMethod.GET)
    @ApiOperation(value = "获取资源父级列表",httpMethod = "GET",response = JSONMsg.class)
    public JSONMsg getParentList(){
        return buildMsg(resourceService.getListByParentId(0));
    }
    /**
     * 获取单个资源信息
     * @param resId
     * @return
     */
    @RequiresPermissions("admin:resource:view")
    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    @ApiOperation(value = "获取单个资源信息",httpMethod = "GET",response = JSONMsg.class)
    public JSONMsg get(
            @ApiParam(name = "id",value = "资源ID",required = true)
            @PathVariable(value = "id")long resId){
        return buildMsg(resourceService.selectById(resId));
    }

    /**
     * 获取资源列表
     * @return
     */
    @RequiresPermissions("admin:resource:view")
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @ApiOperation(value = "获取资源列表",httpMethod = "GET",response = JSONMsg.class)
    public JSONMsg getList(
            @ApiParam(name="name",value = "资源名称")
            @RequestParam(value = "name",defaultValue = "") String name,
            @ApiParam(name="url",value = "URL")
            @RequestParam(value = "url",defaultValue = "") String url,
            @ApiParam(name="status",value = "状态")
            @RequestParam(value = "status",defaultValue = "-1") int status,
            @ApiParam(name="isShow",value = "是否显示")
            @RequestParam(value = "isShow",defaultValue = "-1") int isShow,
            @ApiParam(name="type",value = "资源类型")
            @RequestParam(value = "type",defaultValue = "-1") int type,
            @ApiParam(name="page",value = "页码")
            @RequestParam(value = "page",defaultValue = "1") int page,
            @ApiParam(name="rows",value = "分页大小")
            @RequestParam(value = "rows",defaultValue = "10") int rows){
        return buildMsg(resourceService.getListByPage(name,url,status,isShow,type,page,rows));
    }

    /**
     * 新增资源
     * @param resource
     * @return
     */
    @SysLog("新增资源信息")
    @RequiresPermissions("admin:resource:add")
    @RequestMapping(value={""},method = RequestMethod.POST)
    @ApiOperation(value = "新增资源",httpMethod = "POST",response = JSONMsg.class)
    public JSONMsg addResource(
            @ModelAttribute Resources resource){
        return buildMsg(resourceService.insert(resource));
    }

    /**
     * 修改资源
     * @param resource
     * @return
     */
    @SysLog("修改资源信息")
    @RequiresPermissions("admin:resource:update")
    @RequestMapping(value="",method = RequestMethod.PUT)
    @ApiOperation(value = "修改资源",httpMethod = "PUT",response = JSONMsg.class)
    public JSONMsg modified(@ModelAttribute Resources resource){
        return buildMsg(resourceService.modified(resource));
    }

    /**
     * 删除资源
     * @param resId
     * @return
     */
    @SysLog("删除资源信息")
    @RequiresPermissions("admin:resource:del")
    @RequestMapping(value="/{id}",method = RequestMethod.DELETE)
    @ApiOperation(value = "删除资源",httpMethod = "DELETE",response = JSONMsg.class)
    public JSONMsg delete(
            @ApiParam(name="id",value = "资源ID",required = true)
            @PathVariable(value = "id")long resId){
        return buildMsg(resourceService.deleteById(resId));
    }
}