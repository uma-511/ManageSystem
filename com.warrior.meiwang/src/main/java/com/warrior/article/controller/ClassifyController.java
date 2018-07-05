package com.warrior.article.controller;

import com.warrior.common.JSONMsg;
import com.warrior.common.annotation.SysLog;
import com.warrior.article.entity.Classify;
import com.warrior.article.service.ClassifyService;
import com.warrior.common.web.WarriorBaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value="ClassifyController",tags = "分类管理",description = "分类管理")
@RestController
@RequestMapping("/classify")
public class ClassifyController extends WarriorBaseController {

    @Autowired
    private ClassifyService classifyService;

    /**
    * 根据id获取分类管理
    *
    * @param id 
    * @return
    */
    @RequiresPermissions("admin:classify:view")
    @RequestMapping(value = "/{id}", method = {RequestMethod.GET})
    @ApiOperation(value = "获取分类管理",httpMethod = "GET",response = JSONMsg.class)
    public JSONMsg queryClassify(
        @ApiParam(name="id",value = "id",required = true)
        @PathVariable(value = "id") int id) {
        return buildMsg(classifyService.selectById(id));
    }

    /**
    * 新增分类管理
    *
    * @param classify
    * @return
    */
    @SysLog("新增分类管理")
    @RequiresPermissions("admin:classify:add")
    @RequestMapping(value = {""}, method = {RequestMethod.POST})
    @ApiOperation(value = "新增分类管理",httpMethod = "POST",response = JSONMsg.class)
    public JSONMsg addClassify(@ModelAttribute Classify classify) {
        return buildMsg(classifyService.insert(classify));
    }

    /**
    * 删除分类管理
    *
    * @param id
    * @return
    */
    @SysLog("删除分类管理")
    @RequiresPermissions("admin:classify:del")
    @RequestMapping(value = "/{id}", method = {RequestMethod.DELETE})
    @ApiOperation(value = "删除classify",httpMethod = "DELETE",response = JSONMsg.class)
    public JSONMsg delClassify(
        @ApiParam(name="id",value = "id",required = true)
        @PathVariable(value = "id") int id) {
        return buildMsg(classifyService.deleteById(id));
    }

    /**
    * 修改分类管理
    *
    * @param classify
    * @return
    */
    @SysLog("修改分类管理")
    @RequiresPermissions("admin:classify:update")
    @RequestMapping(value = "", method = {RequestMethod.PUT})
    @ApiOperation(value = "修改分类管理",httpMethod = "PUT",response = JSONMsg.class)
    public JSONMsg modifiedClassify(@ModelAttribute Classify classify) {
        return buildMsg(classifyService.insertOrUpdate(classify));
    }

    /**
    * 查询分类管理列表
    *
    */
    @RequiresPermissions("admin:classify:view")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value = "获取分类管理列表",httpMethod = "GET",response = JSONMsg.class)
    public JSONMsg getClassifyList(
        @ApiParam(name="name",value = "分类名称")
        @RequestParam(name = "name", defaultValue = "") String name,
        @ApiParam(name="state",value = "状态")
        @RequestParam(name = "state", defaultValue = "") String state,
        @ApiParam(name="page",value = "页码")
        @RequestParam(name="page",defaultValue = "1")int page,
        @ApiParam(name="rows",value = "分页大小")
        @RequestParam(name="rows",defaultValue = "10")int rows) {

        return buildMsg(classifyService.getPageList(new Page<Classify>(page,rows),name,state));
    }

    /**
     *
     */
    @RequiresPermissions("admin:classify:view")
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    @ApiOperation(value = "获取分类管理树形数据",httpMethod = "GET",response = JSONMsg.class)
    public JSONMsg getClassifyTree( @ApiParam(name="name",value = "分类名称")
                                        @RequestParam(name = "name", defaultValue = "") String name,
                                    @ApiParam(name="state",value = "状态")
                                        @RequestParam(name = "state", defaultValue = "") String state){
        return buildMsg(classifyService.getTree(name,state));
    }
}