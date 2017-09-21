package com.warrior.web.shiro;

import com.warrior.permissions.entity.Resources;
import com.warrior.permissions.entity.Role;
import com.warrior.permissions.service.PermissionService;
import com.warrior.permissions.service.RoleService;
import com.warrior.user.entity.User;
import com.warrior.user.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

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
        Set permissions = new HashSet<>();

        String userName = principalCollection.getPrimaryPrincipal().toString();
        User user = userService.getByUserName(userName);
        for (Role role : roleService.getRoleListByUser(user.getUid())) {
            roles.add(role.getRoleName());
            for (Iterator<Resources> iterator = permissionService.getPermission(role.getRid(), 0).iterator();
                 iterator.hasNext(); ) {
                permissions.add(new WildcardPermission(iterator.next().getPermission()));
            }
        }

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);
        info.setObjectPermissions(permissions);
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