package com.warrior.permissions.service.impl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Lists;
import com.warrior.permissions.dao.DictionaryDao;
import com.warrior.permissions.entity.Dictionary;
import com.warrior.permissions.model.DictModel;
import com.warrior.permissions.service.DictionaryService;
import com.warrior.util.exception.WarriorException;
import com.warrior.util.service.WarriorBaseServiceImpl;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Service
public class DictionaryServiceImpl extends WarriorBaseServiceImpl<DictionaryDao,Dictionary> implements DictionaryService {

    /**
     * 根据字典类型查找
     * @param dicType
     * @return
     */
    public List<Dictionary> getListByType(int dicType){
        return baseMapper.getListByType(dicType);
    }

    /**
     * 根据字典类型查找
     * @param dicType
     * @return
     */
    public List<DictModel> getModelListByType(int dicType){
        List<Dictionary> list = getListByType(dicType);
        List<DictModel> modelList = Lists.newArrayList();
        try {
            for (Dictionary dict : list){
               DictModel model = new DictModel();
               BeanUtils.copyProperties(model,dict);
               modelList.add(model);
            }
        } catch (IllegalAccessException e) {
            throw new WarriorException("服务端错误！",e);
        } catch (InvocationTargetException e) {
            throw new WarriorException("服务端错误！",e);
        }
        return modelList;
    }

    /**
     * 根据字典属性查找单个信息
     * @param
     * @return
     */
    public Dictionary getByAttribute(String key,Object value){
        EntityWrapper<Dictionary> entity = new EntityWrapper<>();
        entity.eq(key,value);
        return baseMapper.getByAttribute(entity);
    }
}
