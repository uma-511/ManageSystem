package com.warrior.permissions.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Lists;
import com.warrior.permissions.dao.PermissionDao;
import com.warrior.permissions.entity.Permission;
import com.warrior.permissions.entity.Resources;
import com.warrior.permissions.model.ResourceModel;
import com.warrior.permissions.service.PermissionService;
import com.warrior.permissions.service.ResourceService;
import com.warrior.user.model.UserModel;
import com.warrior.util.common.Contacts;
import com.warrior.util.exception.WarriorException;
import com.warrior.util.service.WarriorBaseServiceImpl;
import com.warrior.util.web.SessionUtil;
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
        List<Integer> list = baseMapper.getPermission(new Permission(ownId, type));
        String permission = "";
        for (Integer num: list) {
            permission += num+"$";
        }
        return resourceService.getResourceModelList(resourceService.selectList(new EntityWrapper<>()),permission);
    }

    /**
     * 根据用户ID获取用户权限集合
     * @return
     */
    public LinkedList<ResourceModel> getUserPermission(){
        UserModel entity = SessionUtil.getValue(Contacts.SESSION_USER);
        List<Resources> roleResources = baseMapper.getResourcesByRole(entity.getUid());
        List<Resources> userResources = baseMapper.getResourcesByUser(entity.getUid());

        //删除A集合中存在的B集合元素
        roleResources.removeAll(userResources);
        //将A集合添加到B集合
        roleResources.addAll(userResources);

        return resourceService.getResourceModelList(roleResources,null);
    }


    @Transactional
    public boolean updatePermission(long ownId,int type,String permission) {
        int ret = baseMapper.delPermission(ownId,type);
        if (ret >= 0 && !StringUtils.isBlank(permission)){
            String [] ids = permission.split(",");
            for (String id:ids) {
                baseMapper.insert(new Permission(ownId,Integer.parseInt(id),type));
            }
            return true;
        }
        return false;
    }
}