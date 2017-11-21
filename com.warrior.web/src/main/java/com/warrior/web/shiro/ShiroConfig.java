package com.warrior.web.shiro;

import com.warrior.util.shiro.MD5;
import com.warrior.web.filter.NoSessionHttpMethodPermissionFilter;
import com.warrior.web.filter.ShiroAjaxSessionFilter;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;
import org.apache.shiro.web.mgt.WebSecurityManager;
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
    @DependsOn(value={"lifecycleBeanPostProcessor"})
    public CustomSecurityRealm customSecurityRealm(){
        CustomSecurityRealm realm = new CustomSecurityRealm();
        realm.setAuthenticationCacheName("authenticationCache");
        realm.setAuthorizationCacheName("authorizationCache");
        realm.setCachingEnabled(true);
        RetryLimitHashedCredentialsMatcher matcher = new RetryLimitHashedCredentialsMatcher(ehCacheCacheManager());
        matcher.setHashAlgorithmName(MD5.ALGORITHM_NAME);
        matcher.setHashIterations(MD5.HASHITERATIONS);
        matcher.setStoredCredentialsHexEncoded(true);
        realm.setCredentialsMatcher(matcher);
        return realm;
    }

    @Bean
    public WebSecurityManager securityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(customSecurityRealm());
        securityManager.setCacheManager(ehCacheCacheManager());
        securityManager.setSubjectFactory(defaultWebSubjectFactory());
        securityManager.setSessionManager(defaultSessionManager());
        return securityManager;
    }

    @Bean
    public DefaultSessionManager defaultSessionManager(){
        DefaultSessionManager manager = new DefaultSessionManager();
        manager.setSessionValidationSchedulerEnabled(false);
        return manager;
    }

    @Bean
    public DefaultWebSubjectFactory defaultWebSubjectFactory(){
        CustomSubjectFactory factory = new CustomSubjectFactory();
        return factory;
    }

    @Bean
    public ShiroAjaxSessionFilter shiroAjaxSessionFilter(){
        return new ShiroAjaxSessionFilter();
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

    @Bean(name="shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());

        Map<String,String> fcdMap = new HashMap<>();
        fcdMap.put("/admin/**","noSessionCreation,rest");
        //anon 不需要认证 authc 需要认证 user 验证通过或RememberMe登录的都可以
        shiroFilterFactoryBean.setFilterChainDefinitionMap(fcdMap);

        Map<String,Filter> filterMap = new HashMap<>();
        filterMap.put("user",shiroAjaxSessionFilter());
        filterMap.put("rest",noSessionHttpMethodPermissionFilter());
        shiroFilterFactoryBean.setFilters(filterMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public NoSessionHttpMethodPermissionFilter noSessionHttpMethodPermissionFilter(){
        NoSessionHttpMethodPermissionFilter noSessionHttpMethodPermissionFilter = new NoSessionHttpMethodPermissionFilter();
        return noSessionHttpMethodPermissionFilter;
    }
}