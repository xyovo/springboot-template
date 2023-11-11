package com.example.springboot.controller;

import cn.hutool.core.util.StrUtil;
import com.example.springboot.common.AuthAccess;
import com.example.springboot.common.Result;
import com.example.springboot.entity.User;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.service.UserService;
import com.example.springboot.utils.TokenUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.rmi.ServerException;

@RestController
public class RootController {

    @Resource
    UserService userService;

    @AuthAccess
    @GetMapping("/")
    public Result root() {
        return Result.success("ok");
    }

    @AuthAccess
    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        if(StrUtil.isBlank(user.getUsername()) || StrUtil.isBlank(user.getPassword())) {
            return Result.error("用户名或密码为空");
        }

        User dbUser =  userService.login(user);

        if(dbUser == null) {
            throw new ServiceException("400", "用户名或密码错误");
        }

        // 生成 token
        String token = TokenUtils.genToken(dbUser.getId().toString(), dbUser.getPassword());
        dbUser.setToken(token);
        return Result.success(dbUser);
    }

}

