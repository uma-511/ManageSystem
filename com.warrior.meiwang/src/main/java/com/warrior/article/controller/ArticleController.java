package com.warrior.article.controller;

import com.warrior.common.JSONMsg;
import com.warrior.common.annotation.SysLog;
import com.warrior.article.entity.Article;
import com.warrior.article.service.ArticleService;
import com.warrior.common.web.WarriorBaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.Date;

@Api(value="ArticleController",tags = "文章管理",description = "文章管理")
@RestController
@RequestMapping("/article")
public class ArticleController extends WarriorBaseController {

    @Autowired
    private ArticleService articleService;

    /**
    * 根据id获取文章管理
    *
    * @param id 
    * @return
    */
    @RequiresPermissions("admin:article:view")
    @RequestMapping(value = "/{id}", method = {RequestMethod.GET})
    @ApiOperation(value = "获取文章管理",httpMethod = "GET",response = JSONMsg.class)
    public JSONMsg queryArticle(
        @ApiParam(name="id",value = "id",required = true)
        @PathVariable(value = "id") int id) {
        return buildMsg(articleService.selectById(id));
    }

    /**
    * 新增文章管理
    *
    * @param article
    * @return
    */
    @SysLog("新增文章管理")
    @RequiresPermissions("admin:article:add")
    @RequestMapping(value = {""}, method = {RequestMethod.POST})
    @ApiOperation(value = "新增文章管理",httpMethod = "POST",response = JSONMsg.class)
    public JSONMsg addArticle(@ModelAttribute Article article) {
        return buildMsg(articleService.insert(article));
    }

    /**
    * 删除文章管理
    *
    * @param id
    * @return
    */
    @SysLog("删除文章管理")
    @RequiresPermissions("admin:article:del")
    @RequestMapping(value = "/{id}", method = {RequestMethod.DELETE})
    @ApiOperation(value = "删除article",httpMethod = "DELETE",response = JSONMsg.class)
    public JSONMsg delArticle(
        @ApiParam(name="id",value = "id",required = true)
        @PathVariable(value = "id") int id) {
        return buildMsg(articleService.deleteById(id));
    }

    /**
    * 修改文章管理
    *
    * @param article
    * @return
    */
    @SysLog("修改文章管理")
    @RequiresPermissions("admin:article:update")
    @RequestMapping(value = "", method = {RequestMethod.PUT})
    @ApiOperation(value = "修改文章管理",httpMethod = "PUT",response = JSONMsg.class)
    public JSONMsg modifiedArticle(@ModelAttribute Article article) {
        return buildMsg(articleService.insertOrUpdate(article));
    }

    /**
    * 查询文章管理列表
    *
    */
    @RequiresPermissions("admin:article:view")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value = "获取文章管理列表",httpMethod = "GET",response = JSONMsg.class)
    public JSONMsg getArticleList(
        @ApiParam(name="createTime_start",value = "创建时间")
        @RequestParam(name = "createTime_start", defaultValue = "") Date createTime_start,
        @ApiParam(name="createTime_end",value = "创建时间")
        @RequestParam(name = "createTime_end", defaultValue = "") Date createTime_end,
        @ApiParam(name="typeId",value = "类型")
        @RequestParam(name = "typeId", defaultValue = "") int typeId,
        @ApiParam(name="isHot",value = "热门")
        @RequestParam(name = "isHot", defaultValue = "") String isHot,
        @ApiParam(name="page",value = "页码")
        @RequestParam(name="page",defaultValue = "1")int page,
        @ApiParam(name="rows",value = "分页大小")
        @RequestParam(name="rows",defaultValue = "10")int rows) {

        return buildMsg(articleService.getPageList(new Page<Article>(page,rows),createTime_start,createTime_end,typeId,isHot));
    }
}