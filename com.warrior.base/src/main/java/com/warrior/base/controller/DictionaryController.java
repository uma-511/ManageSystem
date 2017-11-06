package com.warrior.base.controller;

import com.warrior.base.entity.Dictionary;
import com.warrior.base.service.DictionaryService;
import com.warrior.common.JSONMsg;
import com.warrior.common.annotation.SysLog;
import com.warrior.common.web.WarriorBaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/dictionary")
@Api(value = "DictionaryController",tags = {"字典管理"},description = "字典管理")
public class DictionaryController extends WarriorBaseController {

    @Autowired
    private DictionaryService dictionaryService;

    @RequiresPermissions("admin:dict:view")
    @RequestMapping(value = "type/list",method = RequestMethod.GET)
    @ApiOperation(value = "获取字典类型列表",httpMethod = "GET",response = JSONMsg.class)
    public JSONMsg getDictType(){
        return buildMsg(dictionaryService.getDictTypeList());
    }
    /**
     * 根据字典类型获取列表(全部属性)
     * @return
     */
    @RequiresPermissions("admin:dict:view")
    @RequestMapping(value="/list",method = RequestMethod.GET)
    @ApiOperation(value = "获取字典列表",httpMethod = "GET",response = JSONMsg.class)
    public JSONMsg getListByType(
            @ApiParam(name = "dictType",value = "字典类型")
            @RequestParam(value = "dictType",defaultValue = "-1") int type,
            @ApiParam(name = "page",value = "页码")
            @RequestParam(value="page",defaultValue = "1") int page,
            @ApiParam(name = "rows",value = "分页大小")
            @RequestParam(value="rows",defaultValue = "10") int rows){
        return buildMsg(dictionaryService.getListByType(type,page,rows));
    }

    /**
     * 根据字典类型获取列表(部分属性)
     * @param dicType
     * @return
     */
    @RequiresPermissions("admin:dict:view")
    @RequestMapping(value = "/list/model/{type}",method = RequestMethod.GET)
    @ApiOperation(value = "根据字典类型获取字典列表",httpMethod = "GET",response = JSONMsg.class)
    public JSONMsg getModelListByType(
            @ApiParam(name="type",value = "字典类型")
            @PathVariable(value="type") int dicType){
        return buildMsg(dictionaryService.getModelListByType(dicType));
    }

    /**
     * 获取单个字典信息
     * @param id
     * @return
     */
    @RequiresPermissions("admin:dict:view")
    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    @ApiOperation(value = "根据ID获取字典信息",httpMethod = "GET",response = JSONMsg.class)
    public JSONMsg get(
            @ApiParam(name="id",value="字典编号")
            @PathVariable(value="id")long id){
        return buildMsg(dictionaryService.selectById(id));
    }

    /**
     * 新增字典信息
     * @param dictionary
     * @return
     */
    @SysLog("新增字典信息")
    @RequiresPermissions("admin:dict:add")
    @RequestMapping(value={""},method = RequestMethod.POST)
    @ApiOperation(value = "新增字典信息",httpMethod = "POST",response = JSONMsg.class)
    public JSONMsg addDictionary(
            @ModelAttribute Dictionary dictionary){
        dictionary.setCreateTime(new Date());
        return buildMsg(dictionaryService.insert(dictionary));
    }

    /**
     * 删除字典信息
     * @param id
     * @return
     */
    @SysLog("删除字典信息")
    @RequiresPermissions("admin:dict:del")
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    @ApiOperation(value = "删除字典信息",httpMethod = "DELETE",response = JSONMsg.class)
    public JSONMsg delete(
            @ApiParam(name="id",value="字典编号")
            @PathVariable(value = "id")long id){
        return buildMsg(dictionaryService.deleteById(id));
    }

    /**
     * 修改字典信息
     * @param dictionary
     * @return
     */
    @SysLog("修改字典信息")
    @RequiresPermissions("admin:dict:update")
    @RequestMapping(value="",method = RequestMethod.PUT)
    @ApiOperation(value = "修改字典信息",httpMethod = "PUT",response = JSONMsg.class)
    public JSONMsg modified(
            @ModelAttribute Dictionary dictionary){
        return buildMsg(dictionaryService.insertOrUpdate(dictionary));
    }
}