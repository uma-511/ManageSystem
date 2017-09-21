package com.warrior.permissions.controller;

import com.warrior.permissions.entity.UserRole;
import com.warrior.permissions.service.PermissionService;
import com.warrior.permissions.service.UserRoleService;
import com.warrior.util.common.JSONMsg;
import com.warrior.util.web.WarriorBaseController;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/permission")
@Log4j
public class PermissionController extends WarriorBaseController {

    @Autowired
    private PermissionService permissionService;
    @Autowired
    private UserRoleService userRoleService;

    /**
     * 新增用户角色关系
     * @return
     */
    @RequestMapping(value = "/userRole",method = RequestMethod.POST)
    public JSONMsg addUserRole(UserRole userRole){
        return buildMsg(userRoleService.insert(userRole));
    }

    /**
     * 删除用户角色关系
     * @param id
     * @return
     */
    @RequestMapping(value="/userRole/{id}",method = RequestMethod.DELETE)
    public JSONMsg delUserRole(@PathVariable(value = "id") long id){
        return buildMsg(userRoleService.deleteById(id));
    }

    /**
     * 新增用户权限
     * @param userId
     * @param resId
     * @return
     */
    @RequestMapping(value="/userPermission",method = RequestMethod.POST)
    public JSONMsg addUserPermission(long userId,long resId){
        return buildMsg(permissionService.addUserPermission(userId, resId));
    }

    /**
     * 删除用户权限信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/userPermission/{id}",method = RequestMethod.DELETE)
    public JSONMsg delUserPermission(@PathVariable(value = "id") long id){
        return buildMsg(permissionService.delPermission(id));
    }

    /**
     * 新增角色权限
     * @param roleId
     * @param resId
     * @return
     */
    @RequestMapping(value="/rolePermission",method = RequestMethod.POST)
    public JSONMsg addRolePermission(long roleId,long resId){
        return buildMsg(permissionService.addRolePermission(roleId, resId));
    }

    /**
     * 删除角色权限
     * @param id
     * @return
     */
    @RequestMapping(value="/rolePermission/{id}",method = RequestMethod.DELETE)
    public JSONMsg delRolePermission(long id){
        return buildMsg(permissionService.delPermission(id));
    }

    /**
     * 根据类型获取相关权限信息
     * @param ownId 权限拥有者ID
     * @param type  权限类型
     * @return
     */
    @RequestMapping(value="/userPermission/{type}/{ownId}",method = RequestMethod.GET)
    public JSONMsg getPermission(@PathVariable(value = "ownId") long ownId,@PathVariable(value = "type") int type){
        return buildMsg(permissionService.getPermission(ownId,type));
    }

    /**
     * 获取用户权限
     * @return
     */
    @RequestMapping(value = "/userPermission",method = RequestMethod.GET)
    public JSONMsg getUserPermission(){
        return buildMsg(permissionService.getUserPermission());
    }

}