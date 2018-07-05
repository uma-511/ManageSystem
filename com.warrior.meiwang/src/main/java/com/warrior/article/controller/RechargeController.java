package com.warrior.article.controller;

import com.warrior.common.JSONMsg;
import com.warrior.common.annotation.SysLog;
import com.warrior.article.entity.Recharge;
import com.warrior.article.service.RechargeService;
import com.warrior.common.web.WarriorBaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.Date;

@Api(value = "RechargeController", tags = "充值管理", description = "充值管理")
@RestController
@RequestMapping("/recharge")
public class RechargeController extends WarriorBaseController {

    @Autowired
    private RechargeService rechargeService;

    /**
     * 根据id获取充值管理
     *
     * @param id
     * @return
     */
    @RequiresPermissions("admin:recharge:view")
    @RequestMapping(value = "/{id}", method = {RequestMethod.GET})
    @ApiOperation(value = "获取充值管理", httpMethod = "GET", response = JSONMsg.class)
    public JSONMsg queryRecharge(
            @ApiParam(name = "id", value = "id", required = true)
            @PathVariable(value = "id") int id) {
        return buildMsg(rechargeService.selectById(id));
    }

    /**
     * 新增充值管理
     *
     * @param recharge
     * @return
     */
    @SysLog("新增充值管理")
    @RequiresPermissions("admin:recharge:add")
    @RequestMapping(value = {""}, method = {RequestMethod.POST})
    @ApiOperation(value = "新增充值管理", httpMethod = "POST", response = JSONMsg.class)
    public JSONMsg addRecharge(@ModelAttribute Recharge recharge) {
        return buildMsg(rechargeService.insert(recharge));
    }

    /**
     * 删除充值管理
     *
     * @param id
     * @return
     */
    @SysLog("删除充值管理")
    @RequiresPermissions("admin:recharge:del")
    @RequestMapping(value = "/{id}", method = {RequestMethod.DELETE})
    @ApiOperation(value = "删除recharge", httpMethod = "DELETE", response = JSONMsg.class)
    public JSONMsg delRecharge(
            @ApiParam(name = "id", value = "id", required = true)
            @PathVariable(value = "id") int id) {
        return buildMsg(rechargeService.deleteById(id));
    }

    /**
     * 修改充值管理
     *
     * @param recharge
     * @return
     */
    @SysLog("修改充值管理")
    @RequiresPermissions("admin:recharge:update")
    @RequestMapping(value = "", method = {RequestMethod.PUT})
    @ApiOperation(value = "修改充值管理", httpMethod = "PUT", response = JSONMsg.class)
    public JSONMsg modifiedRecharge(@ModelAttribute Recharge recharge) {
        return buildMsg(rechargeService.insertOrUpdate(recharge));
    }

    /**
     * 查询充值管理列表
     */
    @RequiresPermissions("admin:recharge:view")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value = "获取充值管理列表", httpMethod = "GET", response = JSONMsg.class)
    public JSONMsg getRechargeList(
            @ApiParam(name = "time_start", value = "充值时间_开始")
            @RequestParam(name = "time_start", defaultValue = "") Date time_start,
            @ApiParam(name = "time_end", value = "充值时间_结束")
            @RequestParam(name = "time_end", defaultValue = "") Date time_end,
            @ApiParam(name = "uid", value = "用户")
            @RequestParam(name = "uid", defaultValue = "") String uid,
            @ApiParam(name = "channel", value = "充值渠道")
            @RequestParam(name = "channel", defaultValue = "") String channel,
            @ApiParam(name = "status", value = "订单状态")
            @RequestParam(name = "status", defaultValue = "") String status,
            @ApiParam(name = "outtradeno", value = "订单号")
            @RequestParam(name = "outtradeno", defaultValue = "") String outtradeno,
            @ApiParam(name = "page", value = "页码")
            @RequestParam(name = "page", defaultValue = "1") int page,
            @ApiParam(name = "rows", value = "分页大小")
            @RequestParam(name = "rows", defaultValue = "10") int rows) {

        return buildMsg(rechargeService.getPageList(new Page<Recharge>(page, rows), time_start, time_end, uid, channel, status, outtradeno));
    }
}