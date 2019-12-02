package com.example.exam01.shiro.config;

import com.example.exam01.shiro.realm.MyRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro 配置类
 */
@Configuration
public class ShiroConfig {
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl("/toLogin");
        shiroFilterFactoryBean.setUnauthorizedUrl("/noAuth");

        // 定义一个map集合用来存放访问规则
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        /*
                Shiro内置过滤器, 可以实现权限相关的拦截器
                常用的过滤器:
                anon: 无需认证(登录)可以访问
                authc: 必须认证才可以访问
                user: 使用 rememberMe 的功能可以直接访问
                perms: 该资源必须得到资源权限才可以访问
                role: 该资源必须得到角色权限才可以访问
         */
        // 注意配置顺序
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/", "anon");
        filterChainDefinitionMap.put("/admin/**", "perms[user:admin]");
        filterChainDefinitionMap.put("/user/**", "authc");
        filterChainDefinitionMap.put("/logout", "authc");
        //主要这行代码必须放在所有权限设置的最后，不然会导致所有 url 都被拦截 剩余的都需要认证
        filterChainDefinitionMap.put("/**", "authc");
        // 将规则写入 shiroFilterFactoryBean 中
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;

    }

    /**
     * 获取 SecurityManager
     * @return
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager defaultSecurityManager = new DefaultWebSecurityManager();
        defaultSecurityManager.setRealm(myRealm());
        return defaultSecurityManager;
    }

    /**
     * 获取 MyRealm
     * @return
     */
    @Bean
    public MyRealm myRealm() {
        MyRealm myRealm = new MyRealm();
        return myRealm;
    }
}
