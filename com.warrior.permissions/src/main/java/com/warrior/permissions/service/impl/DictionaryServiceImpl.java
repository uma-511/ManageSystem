package com.warrior.permissions.service.impl;


import com.google.common.collect.Lists;
import com.warrior.permissions.dao.DictionaryDao;
import com.warrior.permissions.entity.Dictionary;
import com.warrior.permissions.model.DictModel;
import com.warrior.permissions.service.DictionaryService;
import com.warrior.user.dao.ParamsUtil;
import com.warrior.util.dao.WarriorBaseMapper;
import com.warrior.util.exception.WarriorException;
import com.warrior.util.service.WarriorBaseServiceImpl;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

@Service
public class DictionaryServiceImpl extends WarriorBaseServiceImpl<Dictionary> implements DictionaryService {

    @Autowired
    @Qualifier("dictionaryDao")
    private DictionaryDao dictionaryDao;

    @Override
    public WarriorBaseMapper<Dictionary> getBaseMapper() {
        return (WarriorBaseMapper<Dictionary>)dictionaryDao;
    }

    /**
     * 根据字典类型查找
     * @param dicType
     * @return
     */
    public List<Dictionary> getListByType(int dicType){
        return dictionaryDao.getListByType(dicType);
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
        return dictionaryDao.getByAttribute(new ParamsUtil().addStr("key",key)
        .addObjct("value",value));
    }
}
