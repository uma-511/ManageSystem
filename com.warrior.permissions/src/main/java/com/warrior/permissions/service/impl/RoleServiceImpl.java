package com.warrior.permissions.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import com.warrior.permissions.dao.RoleDao;
import com.warrior.permissions.entity.Role;
import com.warrior.permissions.entity.UserRole;
import com.warrior.permissions.model.RoleModel;
import com.warrior.permissions.service.RoleService;
import com.warrior.permissions.service.UserRoleService;
import com.warrior.util.service.WarriorBaseServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.parser.Entity;
import java.util.List;


/**
 * The type Role service.
 */
@Service
public class RoleServiceImpl extends WarriorBaseServiceImpl<RoleDao,Role> implements RoleService {

    @Autowired
    private UserRoleService userRoleService;

    public Page<Role> getRoleList(String roleName, int status,int page,int rows){
        EntityWrapper<Role> ew = new EntityWrapper<>();
        if (!StringUtils.isBlank(roleName)){
            ew.eq("role_name",roleName);
        }
        if (status != -1){
            ew.eq("status",status);
        }
        Page<Role> paging = new Page<>(page,rows);
        paging.setRecords(baseMapper.getRoleList(paging,ew));
        return paging;
    }


    public List<RoleModel> getRoleByUser(long uid){
        EntityWrapper<Role> ew = new EntityWrapper<>();
        ew.eq("status",0);
        List<Role> roleList = baseMapper.selectList(ew);
        List<RoleModel> modelList = Lists.newArrayList();

        String permission = userRoleService.getRoleByUser(uid);

        for (Role role : roleList) {
            RoleModel model = new RoleModel();
            model.setRid(role.getRid());
            model.setRoleName(role.getRoleName());
            if(!StringUtils.isBlank(permission) && StringUtils.contains(permission,role.getRid()+"$")){
                model.setChecked(true);
            }
            modelList.add(model);
        }

        return modelList;
    }

    @Transactional
    public boolean updateUserRole(long uid, String permissions) {
        EntityWrapper<UserRole> ew = new EntityWrapper<>();
        ew.eq("user_id",uid);
        boolean ret = userRoleService.delete(ew);
        if (ret){
            if (!StringUtils.isBlank(permissions)){
                String ids [] = permissions.split(",");
                for (String id:ids) {
                    userRoleService.insert(new UserRole(uid,Long.parseLong(id)));
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public List<Role> getRoleListByUser(long uid) {
        EntityWrapper<Role> ew = new EntityWrapper<>();
        ew.eq("status",0);
        return baseMapper.selectList(ew);
    }
}