package com.warrior.base.controller;

import com.warrior.base.service.NotesService;
import com.warrior.common.JSONMsg;
import com.warrior.common.annotation.SysLog;
import com.warrior.common.web.WarriorBaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "NotesController", tags = "待办事项信息", description = "待办事项信息")
@RestController
@RequestMapping("/notes")
public class NotesController extends WarriorBaseController {

    @Autowired
    private NotesService notesService;

    /**
     * 新增待办事项信息
     *
     * @return
     */
    @SysLog("新增待办事项信息")
    @RequestMapping(value = {""}, method = {RequestMethod.POST})
    @ApiOperation(value = "新增待办事项信息", httpMethod = "POST", response = JSONMsg.class)
    public JSONMsg addNotes(
            @ApiParam(name = "note", value = "待办事项内容", required = true)
            @RequestParam(value = "note", defaultValue = "") String note) {
        return buildMsg(notesService.addNote(note));
    }

    /**
     * 删除待办事项信息
     *
     * @param id
     * @return
     */
    @SysLog("删除待办事项信息")
    @RequestMapping(value = "/{id}", method = {RequestMethod.DELETE})
    @ApiOperation(value = "删除notes", httpMethod = "DELETE", response = JSONMsg.class)
    public JSONMsg delNotes(
            @ApiParam(name = "id", value = "id", required = true)
            @PathVariable(value = "id") int id) {
        return buildMsg(notesService.deleteById(id));
    }

    /**
     * 修改待办事项信息
     *
     * @return
     */
    @SysLog("修改待办事项状态")
    @RequestMapping(value = "/{id}/{status}", method = {RequestMethod.PUT})
    @ApiOperation(value = "修改待办事项状态", httpMethod = "PUT", response = JSONMsg.class)
    public JSONMsg modifiedNotes(
            @ApiParam(name = "id", value = "编号", required = true)
            @PathVariable(value = "id") int id,
            @ApiParam(name = "status", value = "状态", required = true)
            @PathVariable(value = "status") int status) {
        return buildMsg(notesService.updateStatus(id, status));
    }

    @ApiOperation(value = "修改待办事项内容", httpMethod = "PUT", response = JSONMsg.class)
    @RequestMapping(value = "/{id}",method = {RequestMethod.PUT})
    public JSONMsg modifiedText(
            @ApiParam(name = "id", value = "编号", required = true)
            @PathVariable(value = "id") int id,
            @ApiParam(name = "note", value = "内容", required = true)
            @RequestParam(value = "note",defaultValue = "") String note) {
        return buildMsg(notesService.updateNote(id,note));
    }

    /**
     * 查询待办事项信息列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value = "获取待办事项信息列表", httpMethod = "GET", response = JSONMsg.class)
    public JSONMsg getNotesList() {
        return buildMsg(notesService.getNotesList());
    }
}