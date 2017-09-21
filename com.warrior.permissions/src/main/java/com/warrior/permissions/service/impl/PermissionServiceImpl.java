package com.warrior.permissions.service.impl;

import com.google.common.collect.Collections2;
import com.warrior.permissions.dao.PermissionDao;
import com.warrior.permissions.entity.Permission;
import com.warrior.permissions.entity.Resources;
import com.warrior.permissions.model.ResourceModel;
import com.warrior.permissions.model.ResourcePredicate;
import com.warrior.permissions.service.PermissionService;
import com.warrior.user.model.UserModel;
import com.warrior.util.common.Contacts;
import com.warrior.util.exception.WarriorException;
import com.warrior.util.service.WarriorBaseServiceImpl;
import com.warrior.util.web.SessionUtil;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@Service
public class PermissionServiceImpl extends WarriorBaseServiceImpl<PermissionDao,Permission> implements PermissionService {

    /**
     * 新增用户权限
     * @param userId
     * @param resId
     * @return
     */
    public Permission addUserPermission(long userId,long resId){
        Permission permission = new Permission(userId,resId,1);
        int ret = baseMapper.insert(permission);
        if (ret <= 0){
            throw new WarriorException("新增用户权限成功！");
        }
        return permission;
    }

    /**
     * 删除用户权限
     * @param id
     * @return
     */
    public boolean delPermission(long id){
        int ret = baseMapper.deleteById(id);
        if (ret <= 0){
            throw new WarriorException("删除用户权限失败！");
        }
        return true;
    }

    /**
     * 新增角色权限
     * @param roleId
     * @param resId
     * @return
     */
    public Permission addRolePermission(long roleId,long resId){
        Permission permission = new Permission(roleId,resId,1);
        int ret = baseMapper.insert(permission);
        if (ret <= 0){
            throw new WarriorException("新增角色权限成功！");
        }
        return permission;
    }

    /**
     * 获取用户或角色的相关权限信息
     * @param ownId
     * @param type   0、角色 1、用户
     * @return
     */
    public List<Resources> getPermission(long ownId, int type){
        return baseMapper.getResourcess(new Permission(ownId, type));
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

        Collection<Resources> parentList = Collections2.filter(roleResources,new ResourcePredicate(0));

        Iterator<Resources> iterator = parentList.iterator();

        LinkedList<ResourceModel> modelList = new LinkedList<>();
        while (iterator.hasNext()){
            Resources res = iterator.next();
            ResourceModel model = new ResourceModel(res.getResId(),res.getResName(),res.getParentId(),res.getUrl(),res.getIcon(),res.getSort(),res.getType());
            model.getResMap().addPath(model.getResName(),model.getUrl());
            addChild(model,roleResources);
            modelList.add(model);
        }
        modelList.sort(ResourceModel.sortOrder);
        return modelList;
    }

    private void addChild(ResourceModel model,List<Resources> roleResources){
        Collection<Resources> subList = Collections2.filter(roleResources,new ResourcePredicate((int)model.getResId())); //过滤数据
        if (subList != null && subList.size() > 0){
            Iterator<Resources> iterator = subList.iterator();
            while (iterator.hasNext()){
                Resources rs = iterator.next();
                ResourceModel subModel = new ResourceModel(rs.getResId(),rs.getResName(),rs.getParentId(),rs.getUrl(),rs.getIcon(),rs.getSort(),rs.getType());
                subModel.getResMap().addAll(model.getResMap());
                subModel.getResMap().addPath(rs.getResName(),rs.getUrl());
                model.getChild().add(subModel);
                addChild(subModel,roleResources);
            }
            model.getChild().sort(ResourceModel.sortOrder);
        }
    }

}