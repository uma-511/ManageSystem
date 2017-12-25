package com.warrior.base.controller;

import com.warrior.base.service.SettingService;
import com.warrior.common.JSONMsg;
import com.warrior.common.web.WarriorBaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "SettingController", tags = "系统设置", description = "系统设置")
@RestController
@RequestMapping("/setting")
public class SettingController extends WarriorBaseController {

    @Autowired
    private SettingService settingService;

    /**
     * 根据id获取系统设置
     *
     * @param key
     * @return
     */
    @RequestMapping(value = "/{key}", method = {RequestMethod.GET})
    @ApiOperation(value = "获取系统设置", httpMethod = "GET", response = JSONMsg.class)
    public JSONMsg querySetting(
            @ApiParam(name = "key", value = "key", required = true)
            @PathVariable(value = "key") String key) {
        return buildMsg(settingService.getSettingByKey(key));
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ApiOperation(value = "修改系统设置", httpMethod = "PUT", response = JSONMsg.class)
    public JSONMsg updateSetting(
            @ApiParam(name = "key", value = "key", required = true)
            @RequestParam(value = "key", defaultValue = "")
                    String key,
            @ApiParam(name = "value", value = "value", required = true)
            @RequestParam(value = "value", defaultValue = "")
                    String value) {
        return buildMsg(settingService.updateSetting(key, value));
    }
}