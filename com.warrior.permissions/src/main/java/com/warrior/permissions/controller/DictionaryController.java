package com.warrior.permissions.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.warrior.permissions.entity.Dictionary;
import com.warrior.permissions.service.DictionaryService;
import com.warrior.util.common.JSONMsg;
import com.warrior.util.web.WarriorBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/dictionary")
public class DictionaryController extends WarriorBaseController {

    @Autowired
    private DictionaryService dictionaryService;

    @RequestMapping(value = "type/list",method = RequestMethod.GET)
    public JSONMsg getDictType(){
        return buildMsg(dictionaryService.getDictTypeList());
    }
    /**
     * 根据字典类型获取列表(全部属性)
     * @return
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    public JSONMsg getListByType(
            @RequestParam(value = "dictType",defaultValue = "-1") int type,
            @RequestParam(value="page",defaultValue = "1") int page,
            @RequestParam(value="rows",defaultValue = "10") int rows){
        return buildMsg(dictionaryService.getListByType(type,page,rows));
    }

    /**
     * 根据字典类型获取列表(部分属性)
     * @param dicType
     * @return
     */
    @RequestMapping(value = "/list/model/{type}",method = RequestMethod.GET)
    public JSONMsg getModelListByType(@PathVariable(value="type") int dicType){
        return buildMsg(dictionaryService.getModelListByType(dicType));
    }

    /**
     * 获取单个字典信息
     * @param id
     * @return
     */
    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public JSONMsg get(@PathVariable(value="id")long id){
        return buildMsg(dictionaryService.selectById(id));
    }

    /**
     * 新增字典信息
     * @param dictionary
     * @return
     */
    @RequestMapping(value={"","/"},method = RequestMethod.POST)
    public JSONMsg addDictionary(Dictionary dictionary){
        dictionary.setCreateTime(new Date());
        return buildMsg(dictionaryService.insert(dictionary));
    }

    /**
     * 删除字典信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public JSONMsg delete(@PathVariable(value = "id")long id){
        return buildMsg(dictionaryService.deleteById(id));
    }

    /**
     * 修改字典信息
     * @param id
     * @param dictionary
     * @return
     */
    @RequestMapping(value="/{id}",method = RequestMethod.PUT)
    public JSONMsg modified(@PathVariable(value = "id")long id,Dictionary dictionary){
        dictionary.setId(id);
        return buildMsg(dictionaryService.insertOrUpdate(dictionary));
    }
}