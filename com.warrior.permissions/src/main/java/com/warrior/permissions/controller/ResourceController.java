package com.warrior.permissions.controller;

import com.warrior.permissions.entity.Resource;
import com.warrior.permissions.service.ResourceService;
import com.warrior.util.common.JSONMsg;
import com.warrior.util.web.WarriorBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/***
 * 资源管理
 *
 */
@RestController
@RequestMapping("/resource")
public class ResourceController extends WarriorBaseController {

    @Autowired
    private ResourceService resourceService;


    /**
     * 获取单个资源信息
     * @param resId
     * @return
     */
    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public JSONMsg get(@PathVariable(value = "id")long resId){
        return buildMsg(resourceService.get(resId));
    }

    /**
     * 获取资源列表
     * @return
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    public JSONMsg getList(@RequestParam(value = "name",defaultValue = "") String name,
                           @RequestParam(value = "url",defaultValue = "") String url,
                           @RequestParam(value = "status",defaultValue = "-1") int status,
                           @RequestParam(value = "isShow",defaultValue = "-1") int isShow,
                           @RequestParam(value = "type",defaultValue = "-1") int type,
                           @RequestParam(value = "page",defaultValue = "1") int page,
                           @RequestParam(value = "rows",defaultValue = "10") int rows){
        return buildMsg(resourceService.getListByPage(name,url,status,isShow,type,page,rows));
    }

    /**
     * 新增资源
     * @param resource
     * @return
     */
    @RequestMapping(value={"/",""},method = RequestMethod.POST)
    public JSONMsg addResource(Resource resource){
        return buildMsg(resourceService.insert(resource));
    }

    /**
     * 修改资源
     * @param resId
     * @param resource
     * @return
     */
    @RequestMapping(value="/{id}",method = RequestMethod.PUT)
    public JSONMsg modified(@PathVariable(value = "id")long resId,Resource resource){
        return buildMsg(resourceService.modified(resource));
    }

    /**
     * 删除资源
     * @param resId
     * @return
     */
    @RequestMapping(value="/{id}",method = RequestMethod.DELETE)
    public JSONMsg delete(@PathVariable(value = "id")long resId){
        return buildMsg(resourceService.delete(resId));
    }
}