package com.warrior.permissions.dao;

import com.warrior.permissions.entity.Resource;
import com.warrior.util.dao.WarriorBaseMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Repository
public interface ResourcesDao extends WarriorBaseMapper<Resource> {

    public List<Resource> getListByParentId(int parentId);

    public List<Resource> getListByPage(Map params);
}
