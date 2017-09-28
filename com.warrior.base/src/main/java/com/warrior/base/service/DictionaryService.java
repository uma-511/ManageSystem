package com.warrior.base.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.warrior.base.entity.Dictionary;
import com.warrior.common.model.DictModel;

import java.util.List;

public interface DictionaryService extends IService<Dictionary>{

    Page<Dictionary> getListByType(int dicType,int page,int rows);

    Dictionary getByAttribute(String key,Object value);

    List<DictModel> getModelListByType(int dicType);

    List<DictModel> getDictTypeList();
}
