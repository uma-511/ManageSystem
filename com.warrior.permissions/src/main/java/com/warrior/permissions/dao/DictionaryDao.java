package com.warrior.permissions.dao;

import com.warrior.permissions.entity.Dictionary;
import com.warrior.util.dao.WarriorBaseMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Repository
public interface DictionaryDao extends WarriorBaseMapper<Dictionary> {

    public List<Dictionary> getListByType(int dicType);

    public Dictionary getByAttribute(Map params);

}
