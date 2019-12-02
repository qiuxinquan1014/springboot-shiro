package com.example.exam01.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *  Role 控制类
 */

@Controller
@RequestMapping("/role")
public class RoleController {

    @RequestMapping("role")
    public String role(){
        return "/role/userRole";
    }
}
