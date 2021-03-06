package com.warrior.base.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import com.warrior.base.dao.RoleDao;
import com.warrior.base.entity.Role;
import com.warrior.base.entity.UserRole;
import com.warrior.base.service.RoleService;
import com.warrior.base.service.UserRoleService;
import com.warrior.base.model.RoleModel;
import com.warrior.common.Contacts;
import com.warrior.common.exception.WarriorException;
import com.warrior.common.service.WarriorBaseServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;


/**
 * The type Role service.
 */
@Service
public class RoleServiceImpl extends WarriorBaseServiceImpl<RoleDao, Role> implements RoleService {

    @Autowired
    private UserRoleService userRoleService;

    public Page<Role> getRoleList(String roleName, int status, int page, int rows) {
        EntityWrapper<Role> ew = new EntityWrapper<>();
        if (!StringUtils.isBlank(roleName)) {
            ew.eq("role_name", roleName);
        }
        if (status != -1) {
            ew.eq("status", status);
        }
        Page<Role> paging = new Page<>(page, rows);
        paging.setRecords(baseMapper.getRoleList(paging, ew));
        return paging;
    }


    public List<RoleModel> getRoleByUser(long uid) {
        EntityWrapper<Role> ew = new EntityWrapper<>();
        ew.eq("status", 0);
        List<Role> roleList = baseMapper.selectList(ew);
        List<RoleModel> modelList = Lists.newArrayList();

        String permission = userRoleService.getRoleByUser(uid);

        for (Role role : roleList) {
            RoleModel model = new RoleModel();
            model.setRid(role.getRid());
            model.setRoleName(role.getRoleName());
            if (!StringUtils.isBlank(permission) && StringUtils.contains(permission, role.getRid() + "$")) {
                model.setChecked(true);
            }
            modelList.add(model);
        }

        return modelList;
    }

    @Transactional
    public boolean updateUserRole(long uid, String permissions) {
        EntityWrapper<UserRole> ew = new EntityWrapper<>();
        ew.eq("user_id", uid);
        userRoleService.delete(ew);

        if (!StringUtils.isBlank(permissions)) {
            String ids[] = permissions.split(",");
            for (String id : ids) {
                userRoleService.insert(new UserRole(uid, Long.parseLong(id)));
            }
        }
        return true;
    }

    @Override
    public List<Role> getRoleListByUser(long uid) {
        EntityWrapper<Role> ew = new EntityWrapper<>();
        ew.eq("status", 0);
        return baseMapper.selectList(ew);
    }

    @Override
    public boolean deleteById(Serializable id) {
        Role role = selectById(id);
        if(role != null && StringUtils.equals("超级管理员",role.getRoleName())){
            throw new WarriorException(Contacts.CODE_FAIL,"超级管理员不能删除！");
        }
        return super.deleteById(id);
    }
}