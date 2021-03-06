package com.warrior.base.service.impl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import com.warrior.base.dao.DictionaryDao;
import com.warrior.base.entity.Dictionary;
import com.warrior.base.model.DictModel;
import com.warrior.base.service.DictionaryService;
import com.warrior.common.Contacts;
import com.warrior.common.exception.WarriorException;
import com.warrior.common.service.WarriorBaseServiceImpl;
import lombok.extern.log4j.Log4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Log4j
@Service
public class DictionaryServiceImpl extends WarriorBaseServiceImpl<DictionaryDao,Dictionary> implements DictionaryService {


    @Override
    public Page<Dictionary> getListByType(int dicType, int page, int rows) {
        Page<Dictionary> paging = new Page<>(page,rows);
        EntityWrapper<Dictionary> ew = new EntityWrapper<>();
        if (dicType != -1){
            ew.eq("dic_type",dicType);
        }
        paging.setRecords(baseMapper.getList(paging,ew));
        return paging;
    }

    public List<DictModel> getDictTypeList(){
        List<Dictionary> list = baseMapper.getTypeList();
        List<DictModel> modelList = Lists.newArrayList();
        for (Dictionary dict : list) {
            modelList.add(new DictModel(dict.getDicType(),dict.getTypeValue()));
        }
        return modelList;
    }
    /**
     * 根据字典类型查找
     * @param dicType
     * @return
     */
    private List<Dictionary> getListByType(int dicType){
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
            throw new WarriorException(Contacts.CODE_FAIL,"服务端错误！",e);
        } catch (InvocationTargetException e) {
            throw new WarriorException(Contacts.CODE_FAIL,"服务端错误！",e);
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
