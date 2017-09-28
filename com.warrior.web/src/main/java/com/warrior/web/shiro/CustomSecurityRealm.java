package com.warrior.web.shiro;

import com.google.common.collect.Sets;
import com.warrior.base.entity.Role;
import com.warrior.base.service.PermissionService;
import com.warrior.base.service.RoleService;
import com.warrior.base.entity.User;
import com.warrior.base.service.UserService;
import lombok.extern.log4j.Log4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.CacheManager;
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

    public CustomSecurityRealm(CacheManager cacheManager) {
        super(cacheManager);
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
        SimpleAuthenticationInfo ai = new SimpleAuthenticationInfo(user.getUserName(), user.getPassWord(), ByteSource.Util.bytes(user.genCredentialsSalt()), getName());
        return ai;
    }
}