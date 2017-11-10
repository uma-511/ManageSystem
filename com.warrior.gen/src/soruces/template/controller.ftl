package ${packageName}.controller;

import com.warrior.common.JSONMsg;
import com.warrior.common.annotation.SysLog;
import ${packageName}.entity.${entityName};
import ${packageName}.service.${entityName}Service;
import com.warrior.common.web.WarriorBaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.plugins.Page;
<#if swagger>
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
</#if>
<#list imports as imp>
import ${imp};
</#list>

<#if swagger>
@Api(value="${className}",tags = "${remark}",description = "${remark}")
</#if>
@RestController
@RequestMapping("/${name}")
public class ${className} extends WarriorBaseController {

    @Autowired
    private ${entityName}Service ${name}Service;

    /**
    * 根据id获取${remark}
    *
    * @param id
    * @return
    */
    @RequiresPermissions("admin:${name}:view")
    @RequestMapping(value = "/{id}", method = {RequestMethod.GET})
    <#if swagger>
    @ApiOperation(value = "获取${remark}",httpMethod = "GET",response = JSONMsg.class)
    </#if>
    public JSONMsg query${entityName}(
        <#if swagger>
        @ApiParam(name="id",value = "ID",required = true)
        </#if>
        @PathVariable(value = "id") long id) {
        return buildMsg(${name}Service.selectById(id));
    }

    /**
    * 新增${remark}
    *
    * @param ${name}
    * @return
    */
    @SysLog("新增${remark}")
    @RequiresPermissions("admin:${name}:add")
    @RequestMapping(value = {""}, method = {RequestMethod.POST})
    <#if swagger>
    @ApiOperation(value = "新增${remark}",httpMethod = "POST",response = JSONMsg.class)
    </#if>
    public JSONMsg add${entityName}(<#if swagger>@ModelAttribute</#if> ${entityName} ${name}) {
        return buildMsg(${name}Service.insert(${name}));
    }

    /**
    * 删除${remark}
    *
    * @param id
    * @return
    */
    @SysLog("删除${remark}")
    @RequiresPermissions("admin:${name}:del")
    @RequestMapping(value = "/{id}", method = {RequestMethod.DELETE})
    <#if swagger>
    @ApiOperation(value = "删除${name}",httpMethod = "DELETE",response = JSONMsg.class)
    </#if>
    public JSONMsg del${entityName}(
        <#if swagger>@ApiParam(name="id",value = "ID",required = true)</#if>
        @PathVariable(value = "id") long id) {
        return buildMsg(${name}Service.deleteById(id));
    }

    /**
    * 修改${remark}
    *
    * @param ${name}
    * @return
    */
    @SysLog("修改${remark}")
    @RequiresPermissions("admin:${name}:update")
    @RequestMapping(value = "", method = {RequestMethod.PUT})
    <#if swagger>
    @ApiOperation(value = "修改${remark}",httpMethod = "PUT",response = JSONMsg.class)
    </#if>
    public JSONMsg modified${entityName}(<#if swagger>@ModelAttribute</#if> ${entityName} ${name}) {
        return buildMsg(${name}Service.insertOrUpdate(${name}));
    }

    /**
    * 查询${remark}列表
    *
    */
    @RequiresPermissions("admin:${name}:view")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    <#if swagger>
    @ApiOperation(value = "获取${remark}列表",httpMethod = "GET",response = JSONMsg.class)
    </#if>
    public JSONMsg get${entityName}List(
        <#list args as arg>
        <#if arg.type == "Date">
        <#if swagger>
        @ApiParam(name="${arg.name}_start",value = "${arg.remark}")
        </#if>
        @RequestParam(name = "${arg.name}_start", defaultValue = "") ${arg.type} ${arg.name}_start,
        <#if swagger>
        @ApiParam(name="${arg.name}_end",value = "${arg.remark}")
        </#if>
        @RequestParam(name = "${arg.name}_end", defaultValue = "") ${arg.type} ${arg.name}_end,
        <#else>
        <#if swagger>
        @ApiParam(name="${arg.name}",value = "${arg.remark}")
        </#if>
        @RequestParam(name = "${arg.name}", defaultValue = "") ${arg.type} ${arg.name},
        </#if>
        </#list>
        <#if swagger>@ApiParam(name="page",value = "页码")</#if>
        @RequestParam(name="page",defaultValue = "1")int page,
        <#if swagger>@ApiParam(name="rows",value = "分页大小")</#if>
        @RequestParam(name="rows",defaultValue = "10")int rows) {

        return buildMsg(${name}Service.getPageList(new Page<${entityName}>(page,rows)<#list args as arg><#if arg.type == "Date">,${arg.name}_start,${arg.name}_end<#else>,${arg.name}</#if></#list>));
    }
}