package com.warrior.permissions.service.impl;

import com.google.common.collect.Collections2;
import com.warrior.permissions.dao.PermissionDao;
import com.warrior.permissions.dao.UserRoleDao;
import com.warrior.permissions.entity.Permission;
import com.warrior.permissions.entity.Resource;
import com.warrior.permissions.entity.UserRole;
import com.warrior.permissions.model.ResourceModel;
import com.warrior.permissions.model.ResourcePredicate;
import com.warrior.permissions.service.PermissionService;
import com.warrior.user.entity.UserEntity;
import com.warrior.util.common.Contacts;
import com.warrior.util.dao.WarriorBaseMapper;
import com.warrior.util.exception.WarriorException;
import com.warrior.util.service.WarriorBaseServiceImpl;
import com.warrior.util.web.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@Service
public class PermissionServiceImpl extends WarriorBaseServiceImpl<Permission> implements PermissionService {

    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Override
    public WarriorBaseMapper<Permission> getBaseMapper() {
        return (WarriorBaseMapper<Permission>)permissionDao;
    }

    /**
     * 添加用户角色
     * @param userId
     * @param roleId
     * @return
     */
    public UserRole addUserRole(long userId,long roleId){
        UserRole userRole = new UserRole(userId,roleId);
        int ret = userRoleDao.insert(userRole);
        if (ret <=0 ){
            throw new WarriorException("数据新增失败！");
        }
        return userRole;
    }

    /**
     * 删除用户角色
     * @param id
     * @return
     */
    public boolean delUserRole(long id){
        int ret = userRoleDao.deleteByPrimaryKey(new UserRole(id));
        if (ret <=0 ){
            throw new WarriorException("数据删除失败！");
        }
        return true;
    }

    /**
     * 新增用户权限
     * @param userId
     * @param resId
     * @return
     */
    public Permission addUserPermission(long userId,long resId){
        Permission permission = new Permission(userId,resId,1);
        int ret = permissionDao.insert(permission);
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
    public boolean delUserPermission(long id){
        int ret = permissionDao.deleteByPrimaryKey(new Permission(id));
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
        int ret = permissionDao.insert(permission);
        if (ret <= 0){
            throw new WarriorException("新增角色权限成功！");
        }
        return permission;
    }

    /**
     * 删除角色权限
     * @param id
     * @return
     */
    public boolean delRolePermission(long id){
        int ret = permissionDao.deleteByPrimaryKey(new Permission(id));
        if (ret <= 0){
            throw new WarriorException("删除角色权限失败！");
        }
        return true;
    }

    /**
     * 获取用户或角色的相关权限信息
     * @param ownId
     * @param type   0、角色 1、用户
     * @return
     */
    public List<Resource> getPermission(long ownId, int type){
        return permissionDao.getResourcess(new Permission(ownId, type));
    }

    /**
     * 根据用户ID获取用户权限集合
     * @return
     */
    public LinkedList<ResourceModel> getUserPermission(){
        UserEntity entity = SessionUtil.getValue(Contacts.SESSION_USER);
        List<Resource> roleResources = permissionDao.getResourcesByRole(entity.getUid());
        List<Resource> userResources = permissionDao.getResourcesByUser(entity.getUid());

        //删除A集合中存在的B集合元素
        roleResources.removeAll(userResources);
        //将A集合添加到B集合
        roleResources.addAll(userResources);

        Collection<Resource> parentList = Collections2.filter(roleResources,new ResourcePredicate(0));

        Iterator<Resource> iterator = parentList.iterator();

        LinkedList<ResourceModel> modelList = new LinkedList<>();
        while (iterator.hasNext()){
            Resource res = iterator.next();
            ResourceModel model = new ResourceModel(res.getResId(),res.getResName(),res.getParentId(),res.getUrl(),res.getIcon(),res.getSort(),res.getType());
            model.getResMap().addPath(model.getResName(),model.getUrl());
            addChild(model,roleResources);
            modelList.add(model);
        }
        modelList.sort(ResourceModel.sortOrder);
        return modelList;
    }

    private void addChild(ResourceModel model,List<Resource> roleResources){
        Collection<Resource> subList = Collections2.filter(roleResources,new ResourcePredicate((int)model.getResId())); //过滤数据
        if (subList != null && subList.size() > 0){
            Iterator<Resource> iterator = subList.iterator();
            while (iterator.hasNext()){
                Resource rs = iterator.next();
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