package com.warrior.web.shiro;

import com.warrior.util.shiro.MD5;
import com.warrior.web.filter.LoginFilter;
import com.warrior.web.filter.PermissionsFilter;
import com.warrior.web.filter.RolesFilter;
import com.warrior.web.filter.ShiroAjaxSessionFilter;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean("ehCacheManager")
    public EhCacheManager ehCacheCacheManager(){
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
        return ehCacheManager;
    }

    @Bean
    @DependsOn(value={"lifecycleBeanPostProcessor","ehCacheManager"})
    public CustomSecurityRealm customSecurityRealm(){
        CustomSecurityRealm realm = new CustomSecurityRealm(ehCacheCacheManager());
        realm.setAuthenticationCacheName("authenticationCache");
        realm.setAuthorizationCacheName("authorizationCache");
        realm.setCachingEnabled(true);
        RetryLimitHashedCredentialsMatcher matcher = new RetryLimitHashedCredentialsMatcher(ehCacheCacheManager());
        matcher.setHashAlgorithmName(MD5.ALGORITHM_NAME);
        matcher.setHashIterations(MD5.HASHITERATIONS);
        matcher.setStoredCredentialsHexEncoded(true);
        realm.setCredentialsMatcher(matcher);
        realm.setAuthenticationCachingEnabled(false);
        realm.setAuthorizationCachingEnabled(true);
        return realm;
    }

    @Bean
    public WebSecurityManager securityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(customSecurityRealm());

        securityManager.setSessionManager(sessionManager());

        securityManager.setCacheManager(ehCacheCacheManager());
        return securityManager;
    }

    @Bean
    public ShiroAjaxSessionFilter shiroAjaxSessionFilter(){
        return new ShiroAjaxSessionFilter();
    }

    @Bean
    public EnterpriseCacheSessionDAO enterpriseCacheSessionDAO(){
        EnterpriseCacheSessionDAO enterpriseCacheSessionDAO = new EnterpriseCacheSessionDAO();
        enterpriseCacheSessionDAO.setActiveSessionsCacheName("activeSessionCache");
        enterpriseCacheSessionDAO.setCacheManager(ehCacheCacheManager());
        return enterpriseCacheSessionDAO;
    }

    @Bean
    public DefaultWebSessionManager sessionManager(){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setGlobalSessionTimeout(1000 * 60 * 5);
        sessionManager.setSessionValidationInterval(1000*60*5);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setSessionIdCookieEnabled(true);
//        sessionManager.setSessionIdCookie(simpleCookie());
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        sessionManager.setSessionDAO(enterpriseCacheSessionDAO());
        sessionManager.getSessionListeners().add(new ShiroSessionListener());
        return sessionManager;
    }

    @Bean
    public SimpleCookie simpleCookie(){
        SimpleCookie cookie = new SimpleCookie();
        cookie.setName("warrior.session.id");
        cookie.setMaxAge(1000*60*5);
        return cookie;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public MethodInvokingFactoryBean methodInvokingFactoryBean(){
        MethodInvokingFactoryBean factoryBean = new MethodInvokingFactoryBean();
        factoryBean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
        factoryBean.setArguments(new Object[]{securityManager()});
        return factoryBean;
    }

    @Bean
    @DependsOn(value="lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
        proxyCreator.setProxyTargetClass(true);
        return proxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }

    @Bean(name="shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());

        //默认登录url
        shiroFilterFactoryBean.setLoginUrl("/");
        //登录成功调整url
        shiroFilterFactoryBean.setSuccessUrl("/index");
        //没有权限跳转url
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauth");

        Map<String,String> fcdMap = new HashMap<>();
        fcdMap.put("/doLogin","anon");
        fcdMap.put("/doLogOut","logout");
        fcdMap.put("/unauth","anon");
//        fcdMap.put("/dist","anon");

        fcdMap.put("/index","authc");
        fcdMap.put("/**","authc,perms,roles");

//        fcdMap.put("/role/*","authc");
//        fcdMap.put("/user/*","authc");
//        fcdMap.put("/permission/*","authc");
//        fcdMap.put("/dictionary/*","authc");
//        fcdMap.put("/resource/*","authc");

        //anon 不需要认证 authc 需要认证 user 验证通过或RememberMe登录的都可以
        shiroFilterFactoryBean.setFilterChainDefinitionMap(fcdMap);

        Map<String,Filter> filterMap = new HashMap<>();
        filterMap.put("authc",loginFilter());
        filterMap.put("perms",permissionsFilter());
        filterMap.put("roles",rolesFilter());
        filterMap.put("logout",logoutFilter());
        filterMap.put("user",shiroAjaxSessionFilter());
        shiroFilterFactoryBean.setFilters(filterMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public RolesFilter rolesFilter(){
        return new RolesFilter();
    }

    @Bean
    public PermissionsFilter permissionsFilter(){
        return new PermissionsFilter();
    }

    @Bean
    public LoginFilter loginFilter(){
        return new LoginFilter();
    }

    @Bean
    public LogoutFilter logoutFilter(){
        LogoutFilter filter = new LogoutFilter();
        filter.setRedirectUrl("/login");
        return filter;
    }
}