package com.example.exam01.shiro.realm;

import com.example.exam01.entity.User;
import com.example.exam01.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * realm类
 */
public class MyRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;
    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 获取当前登录用户
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();

        // 获取 SimpleAuthorizationInfo 对象写入授权规则
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        // 创建一个 set 集合用来保存当前用户的授权信息
        Set<String> stringSet = new HashSet<>();
        stringSet.add(user.getPrems());

        // 将授权信息写入 SimpleAuthorizationInfo 对象中
        info.setStringPermissions(stringSet);
        return info;
    }

    /**
     * 认证
     * @param auToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auToken) throws AuthenticationException {
        // AuthenticationToken 强转 UsernamePasswordToken
        UsernamePasswordToken token = (UsernamePasswordToken) auToken;
        // 从数据库获取用户信息
        User user = userService.findByName(token.getUsername());
        if (user == null){
            System.out.println("账号错误");
            return null;
        }
        return new SimpleAuthenticationInfo(user, user.getPassword(),getName());
    }
}
