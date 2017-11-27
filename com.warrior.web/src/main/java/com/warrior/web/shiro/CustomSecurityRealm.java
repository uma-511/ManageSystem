package com.warrior.web.shiro;

import com.google.common.collect.Sets;
import com.warrior.base.entity.Role;
import com.warrior.base.entity.User;
import com.warrior.base.service.PermissionService;
import com.warrior.base.service.RoleService;
import com.warrior.base.service.UserService;
import com.warrior.common.Contacts;
import com.warrior.common.JSONMsg;
import com.warrior.common.push.EventType;
import com.warrior.common.push.PushService;
import com.warrior.common.web.WarriorSession;
import com.warrior.util.common.JSONUtils;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

@Log4j
public class CustomSecurityRealm extends AuthorizingRealm {

    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Set roles = new HashSet<>();

        String userName = principalCollection.getPrimaryPrincipal().toString();
        User user = userService.getByUserName(userName);
        for (Role role : roleService.getRoleListByUser(user.getUid())) {
            roles.add(role.getRoleName());
        }
        String permissionStr = permissionService.getPermissionStr(user.getUid());
        permissionStr = StringUtils.isEmpty(permissionStr) ? "" : permissionStr;
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);
        info.setStringPermissions(Sets.newHashSet(permissionStr.split(",")));

        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upat = (UsernamePasswordToken) token;
        User user = userService.getByUserName(upat.getUsername());
        if (user == null) {
            throw new UnknownAccountException();
        }
        if (user.getStatus() == 1 || user.getStatus() == 2) {
            throw new LockedAccountException();
        }
        if(!StringUtils.isEmpty(user.getToken())){
            if(WarriorSession.getItem(user.getToken()) != null){
                JSONMsg msg = new JSONMsg(Contacts.CODE_OTHER_LOGIN,null,"当前用户已在其他设备登录！");
                PushService.sendMessageToOneClient(user.getToken(), EventType.NOTICE_INFO, JSONUtils.toJson(msg));
            }
            WarriorSession.removeItem(user.getToken());
            user.setToken("");
            userService.updateById(user);
        }

        SimpleAuthenticationInfo ai = new SimpleAuthenticationInfo(user.getUserName(), user.getPassWord(), ByteSource.Util.bytes(user.genCredentialsSalt()), getName());
        return ai;
    }
}