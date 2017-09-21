package com.warrior.permissions.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.warrior.permissions.dao.ResourcesDao;
import com.warrior.permissions.entity.Resources;
import com.warrior.permissions.service.ResourceService;
import com.warrior.util.service.WarriorBaseServiceImpl;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Log4j
@Service
public class ResourceServiceImpl extends WarriorBaseServiceImpl<ResourcesDao,Resources> implements ResourceService {

    /**
     * 根据资源父ID查找信息
     * @param parentId
     * @return
     */
    public List<Resources> getListByParentId(int parentId){
        return baseMapper.getListByParentId(parentId);
    }


    public Page<Resources> getListByPage(String name, String url, int status, int isShow, int type, int page, int rows) {
        EntityWrapper<Resources> ew = new EntityWrapper<>();
        if (!StringUtils.isBlank(name)){
            ew.eq("name",name);
        }
        if (!StringUtils.isBlank(url)){
            ew.eq("url",url);
        }
        if (status != -1){
            ew.eq("status",status);
        }
        if (isShow != -1){
            ew.eq("is_show",isShow);
        }
        if (type != -1){
            ew.eq("type",type);
        }
        Page<Resources> paging = new Page<>((page-1)*rows,rows);
        paging.setRecords(baseMapper.getListByPage(paging,ew));
        return paging;
    }

    public boolean insert(Resources resource) {
        resource.setCreateTime(new Date());
        resource.setUpdateTime(new Date());
        return super.insert(resource);
    }

    public boolean modified(Resources resource) {
        resource.setUpdateTime(new Date());
        return super.insertOrUpdate(resource);
    }
}
