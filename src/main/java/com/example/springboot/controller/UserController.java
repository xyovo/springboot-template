package com.example.springboot.controller;

import com.example.springboot.common.Page;
import com.example.springboot.common.Result;
import com.example.springboot.entity.User;
import com.example.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 新增用户
     *
     * @param user
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody User user) {
        userService.insertUser(user);
        return Result.success();
    }

    /**
     * 修改用户信息
     *
     * @param user
     * @return
     */
    @PutMapping("/update")
    public Result update(@RequestBody User user) {
        userService.updateUser(user);
        return Result.success();
    }

    /**
     * 删除用户通过ID
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        userService.deleteUser(id);
        return Result.success();
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        for (Integer id : ids) {
            userService.deleteUser(id);
        }
        return Result.success();
    }

    /**
     * 查询全部用户
     * @return
     */
    @GetMapping("/all")
    public Result getAll() {
        List<User> users = userService.getAllUser();
        return Result.success(users);
    }

    /**
     * 查询指定ID用户
     * @return
     */
    @GetMapping("/{id}")
    public Result getUserById(@PathVariable Integer id) {
        User user = userService.getUserById(id);
        return Result.success(user);
    }

    /**
     * 查询用户通过name
     * @param name
     * @return
     */
    @GetMapping("/byName")
    public Result getUsersByName(@RequestParam String name) {
        return Result.success(userService.getUserByUserName(name));
    }

    /**
     * 查询用户通过用户名与姓名
     * @param username
     * @param name
     * @return
     */
    @GetMapping("/byMore")
    public Result gerUsersByMore(@RequestParam String username, @RequestParam String name) {
        return Result.success(userService.getUsersByMore(username, name));
    }

    /**
     * 模糊查询用户通过用户名与姓名
     * @param username
     * @param name
     * @return
     */
    @GetMapping("/byLike")
    public Result gerUsersByLike(@RequestParam String username, @RequestParam String name) {
        return Result.success(userService.getUsersByLike(username, name));
    }


    @GetMapping("/byPage")
    public Result gerUsersByPage(@RequestParam Integer current, @RequestParam Integer pageSize, @RequestParam String username, @RequestParam String name) {
        Page<User> result = userService.getUsersByPage(current, pageSize, username, name);
        return Result.success(result);
    }

}
