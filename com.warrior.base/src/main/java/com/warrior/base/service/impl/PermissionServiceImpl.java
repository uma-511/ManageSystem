package com.warrior.base.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Lists;
import com.warrior.base.dao.PermissionDao;
import com.warrior.base.entity.Permission;
import com.warrior.base.entity.Resources;
import com.warrior.common.model.ResourceModel;
import com.warrior.base.service.PermissionService;
import com.warrior.base.service.ResourceService;
import com.warrior.common.service.impl.WarriorBaseServiceImpl;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Log4j
@Service
public class PermissionServiceImpl extends WarriorBaseServiceImpl<PermissionDao,Permission> implements PermissionService {

    @Autowired
    private ResourceService resourceService;

    /**
     * 获取用户或角色的相关权限信息
     * @param ownId
     * @param type   0、角色 1、用户
     * @return
     */
    public List<ResourceModel> getPermission(long ownId, int type){
        List<Integer> list = baseMapper.getPermission(ownId, type);
        String permission = "$";
        for (Integer num: list) {
            permission += num+"$";
        }
        return resourceService.getResourceModelList(resourceService.selectList(new EntityWrapper<Resources>()),permission);
    }

    public List<Resources> getPermissionByType(long ownId,int type){
        if (type == 0){
            return baseMapper.getResourcesByRole(ownId,-1);
        }else{
            return baseMapper.getResourcesByUser(ownId,-1);
        }
    }

    public String getPermissionStr(long uid){
        return baseMapper.getPermissionStr(uid);
    }
    /**
     * 根据用户ID获取用户权限集合
     * @return
     */
    public LinkedList<ResourceModel> getUserPermission(long uid){
        List<Resources> roleResources = baseMapper.getResourcesByRole(uid,0);
        List<Resources> userResources = baseMapper.getResourcesByUser(uid,0);

        //删除A集合中存在的B集合元素
        roleResources.removeAll(userResources);
        //将A集合添加到B集合
        roleResources.addAll(userResources);

        return resourceService.getResourceModelList(roleResources,null);
    }


    @Transactional
    public boolean updatePermission(long ownId,int type,String permission) {
        int ret = baseMapper.delPermission(ownId,type);
        if (ret >= 0){
            if(!StringUtils.isBlank(permission)){
                String [] ids = permission.split(",");
                List<Permission> list = Lists.newArrayList();
                for (String id:ids) {
                    list.add(new Permission(ownId,Integer.parseInt(id),type));
                }
                super.insertBatch(list);
            }
            return true;
        }
        return false;
    }
}