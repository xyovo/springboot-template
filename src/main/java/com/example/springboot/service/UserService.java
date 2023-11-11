package com.example.springboot.service;

import com.example.springboot.common.Page;
import com.example.springboot.entity.User;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public void insertUser(User user) {
        userMapper.insert(user);
    }

    public void updateUser(User user) {
        userMapper.update(user);
    }

    public void deleteUser(Integer userId) { userMapper.delete(userId); }

    public List<User> getAllUser() {
        return userMapper.getAllUsers();
    }

    public User getUserById(Integer id) {
        return userMapper.getUserById(id);
    }

    public List<User> getUserByUserName(String name) {
        return userMapper.getUserByName(name);
    }

    public User getUsersByMore(String username, String name) {
        return userMapper.getUsersByMore(username, name);
    }

    public List<User> getUsersByLike(String username, String name) {
        return userMapper.getUsersByLike(username, name);
    }

    public Page<User> getUsersByPage(Integer current, Integer pageSize, String username, String name) {
        Integer skitPage = (current - 1) * pageSize;
        List<User> users =  userMapper.getUsersByPage(skitPage, pageSize, username, name);
        Integer total = userMapper.usersTotal(username, name);
        Page<User> result = new Page<>(current, total, users);
        return result;
    }

    public User login(User user) {
        User dbUser = userMapper.getUserByUserName(user.getUsername());
        if(dbUser == null) {
            throw new ServiceException("401","用户不存在");
        }
        if(!user.getPassword().equals(dbUser.getPassword())) {
            throw new ServiceException("密码错误");
        }
        return dbUser;
    }
}
