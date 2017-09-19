package com.warrior.permissions.controller;

import com.warrior.permissions.entity.Dictionary;
import com.warrior.permissions.service.DictionaryService;
import com.warrior.util.common.JSONMsg;
import com.warrior.util.web.WarriorBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dictionary")
public class DictionaryController extends WarriorBaseController {

    @Autowired
    private DictionaryService dictionaryService;

    /**
     * 根据字典类型获取列表(全部属性)
     * @param type
     * @return
     */
    @RequestMapping(value="/list/{type}",method = RequestMethod.GET)
    public JSONMsg getListByType(@PathVariable(value = "type") int type){
        return buildMsg(dictionaryService.getListByType(type));
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
     * 获取字典列表
     * @return
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    public JSONMsg getList(){
        return buildMsg(dictionaryService.getList());
    }
    /**
     * 获取单个字典信息
     * @param id
     * @return
     */
    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public JSONMsg get(@PathVariable(value="id")long id){
        return buildMsg(dictionaryService.get(id));
    }

    /**
     * 新增字典信息
     * @param dictionary
     * @return
     */
    @RequestMapping(value={"","/"},method = RequestMethod.POST)
    public JSONMsg addDictionary(Dictionary dictionary){
        return buildMsg(dictionaryService.insert(dictionary));
    }

    /**
     * 删除字典信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public JSONMsg delete(@PathVariable(value = "id")long id){
        return buildMsg(dictionaryService.delete(id));
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
        return buildMsg(dictionaryService.modified(dictionary));
    }
}