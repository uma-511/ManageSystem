package com.warrior.permissions.service;

import com.warrior.permissions.entity.Dictionary;
import com.warrior.permissions.model.DictModel;
import com.warrior.util.service.WarriorBaseService;

import java.util.List;

public interface DictionaryService extends WarriorBaseService<Dictionary> {

    List<Dictionary> getListByType(int dicType);

    Dictionary getByAttribute(String key,Object value);

    List<DictModel> getModelListByType(int dicType);

}
