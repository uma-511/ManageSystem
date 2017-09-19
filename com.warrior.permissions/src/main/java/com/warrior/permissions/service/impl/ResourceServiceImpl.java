package com.warrior.permissions.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.warrior.permissions.dao.ResourcesDao;
import com.warrior.permissions.entity.Resource;
import com.warrior.permissions.service.ResourceService;
import com.warrior.user.dao.ParamsUtil;
import com.warrior.util.dao.WarriorBaseMapper;
import com.warrior.util.service.WarriorBaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ResourceServiceImpl extends WarriorBaseServiceImpl<Resource> implements ResourceService {

    @Autowired
    private ResourcesDao resourcesDao;

    @Override
    public WarriorBaseMapper<Resource> getBaseMapper() {
        return (WarriorBaseMapper<Resource>)resourcesDao;
    }

    /**
     * 根据资源父ID查找信息
     * @param parentId
     * @return
     */
    public List<Resource> getListByParentId(int parentId){
        return resourcesDao.getListByParentId(parentId);
    }


    @Override
    public PageInfo<Resource> getListByPage(String name, String url, int status, int isShow, int type,int page,int rows) {
        ParamsUtil params = new ParamsUtil();
        params.addStr("name",name)
                .addStr("url",url)
                .addInt("status",status,-1)
                .addInt("isShow",isShow,-1)
                .addInt("type",type,-1);
        PageHelper.startPage(page,rows);
        PageInfo<Resource> pageInfo = new PageInfo<>(resourcesDao.getListByPage(params));
        return pageInfo;
    }

    @Override
    public Resource insert(Resource resource) {
        resource.setCreateTime(new Date());
        resource.setUpdateTime(new Date());
        return super.insert(resource);
    }

    @Override
    public Resource modified(Resource resource) {
        resource.setUpdateTime(new Date());
        return super.modified(resource);
    }
}
