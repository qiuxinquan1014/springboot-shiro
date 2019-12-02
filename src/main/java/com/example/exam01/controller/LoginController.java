package com.example.exam01.controller;

import com.example.exam01.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *  Login 控制类
 */

@Controller
public class LoginController {

    // 跳转登录页面
    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }

    // 执行登录方法
    @RequestMapping("/login")
    public String login(User user, Model model){
        // 执行加密算法
        SimpleHash md5 = new SimpleHash("MD5",user.getPassword(),null,1);
        String password = md5.toString();

        // 获取 subject 对象
        Subject subject = SecurityUtils.getSubject();

        // 准备 token 令牌
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(),password);

        // 定义一个返回提示信息容器
        String msg = null;
        // 执行认证登录
        try{
            subject.login(token);
        } catch (UnknownAccountException uae) {
            msg = "未知账户";
        } catch (IncorrectCredentialsException ice) {
            msg = "密码不正确";
        } catch (LockedAccountException lae) {
            msg = "账户已锁定";
        } catch (ExcessiveAttemptsException eae) {
            msg = "用户名或密码错误次数过多";
        } catch (AuthenticationException ae) {
            msg = "用户名或密码不正确";
        }

        // 判断登录是否成功
        if (subject.isAuthenticated()) {
            return "main";
        } else {
            token.clear();
            // 写入返回 tips
            model.addAttribute("msg",msg);
            return "login";
        }
    }

    // 执行登出方法
    @RequestMapping("/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "login";
    }

    // 跳转错误页面
    @RequestMapping("/noAuth")
    public String noAuth(){
        return "noAuth";
    }
}
