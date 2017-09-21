package com.warrior.permissions.service;

import com.baomidou.mybatisplus.service.IService;
import com.warrior.permissions.entity.Dictionary;
import com.warrior.permissions.model.DictModel;

import java.util.List;

public interface DictionaryService extends IService<Dictionary>{

    List<Dictionary> getListByType(int dicType);

    Dictionary getByAttribute(String key,Object value);

    List<DictModel> getModelListByType(int dicType);

}
