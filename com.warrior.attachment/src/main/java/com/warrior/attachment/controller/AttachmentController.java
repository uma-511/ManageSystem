package com.warrior.attachment.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.warrior.attachment.entity.Attachment;
import com.warrior.attachment.service.AttachmentService;
import com.warrior.common.JSONMsg;
import com.warrior.common.annotation.SysLog;
import com.warrior.common.web.WarriorBaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value="AttachmentController",tags = "附件信息",description = "附件信息")
@RestController
@RequestMapping("/attachment")
@Log4j
public class AttachmentController extends WarriorBaseController {

    @Autowired
    private AttachmentService attachmentService;

    /**
     * 文件下载
     * @param aid
     * @param response
     */
    @RequestMapping(value = "file/{aid}",method = RequestMethod.GET)
    public void downloadFile(@PathVariable(value = "aid") String aid, HttpServletResponse response){
        attachmentService.downloadFile(aid,response);
    }

    /**
    * 根据id获取附件信息
    *
    * @param aid 
    * @return
    */
    @RequiresPermissions("admin:attachment:view")
    @RequestMapping(value = "/{aid}", method = {RequestMethod.GET})
    @ApiOperation(value = "获取附件信息",httpMethod = "GET",response = JSONMsg.class)
    public JSONMsg queryAttachment(
        @ApiParam(name="aid",value = "aid",required = true)
        @PathVariable(value = "aid") String aid) {
        return buildMsg(attachmentService.selectById(aid));
    }

    /**
    * 新增附件信息
    *
    * @return
    */
    @SysLog("上传附件")
    @RequestMapping(value = {"/upload/{type}"}, method = {RequestMethod.POST})
    @ApiOperation(value = "上传附件",httpMethod = "POST",response = JSONMsg.class)
    public JSONMsg uploadAttachment(
            @ApiParam(name="type",value = "文件类型",required = true)
            @PathVariable(value = "type") int type,HttpServletRequest request) {
        return buildMsg(attachmentService.uploadFile(request, type),"");
    }

    /**
    * 删除附件信息
    *
    * @param aid
    * @return
    */
    @SysLog("删除附件信息")
    @RequiresPermissions("admin:attachment:del")
    @RequestMapping(value = "/{aid}", method = {RequestMethod.DELETE})
    @ApiOperation(value = "删除attachment",httpMethod = "DELETE",response = JSONMsg.class)
    public JSONMsg delAttachment(
        @ApiParam(name="aid",value = "aid",required = true)
        @PathVariable(value = "aid") String aid) {
        return buildMsg(attachmentService.deleteById(aid));
    }

    /**
    * 查询附件信息列表
    *
    */
    @RequiresPermissions("admin:attachment:view")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value = "获取附件信息列表",httpMethod = "GET",response = JSONMsg.class)
    public JSONMsg getAttachmentList(
        @ApiParam(name="page",value = "页码")
        @RequestParam(name="page",defaultValue = "1")int page,
        @ApiParam(name="rows",value = "分页大小")
        @RequestParam(name="rows",defaultValue = "10")int rows) {

        return buildMsg(attachmentService.getPageList(new Page<Attachment>(page,rows)));
    }
}