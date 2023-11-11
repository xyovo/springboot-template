package com.example.springboot.mapper;

import com.example.springboot.common.Page;
import com.example.springboot.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO `user` (username, password, name, phone, email, address, avatar) " +
            "VALUES (#{username}, #{password}, #{name}, #{phone}, #{email}, #{address}, #{avatar})")
    void insert(User user);

    @Select("SELECT * FROM `user`")
    void get();

    @Update("UPDATE `user` SET username=#{username}, name=#{name}, phone=#{phone}, " +
            "email=#{email}, address=#{address}, avatar=#{avatar} where id=#{id}")
    void update(User user);

    @Delete("DELETE FROM `user` WHERE id=#{id}")
    void delete(Integer userId);

    @Select("select * from `user` order by id desc")
    List<User> getAllUsers();

    @Select("select * from `user` where id = #{id}")
    User getUserById(Integer id);

    @Select("select * from `user` where name = #{name} order by id desc")
    List<User> getUserByName(String name);

    @Select("select * from `user` where name = #{name} and username = #{username}")
    User getUsersByMore(@Param("username") String username, @Param("name") String name);

    @Select("select * from `user` where name like concat('%', #{name}, '%') and username like concat('%', #{username}, '%')")
    List<User> getUsersByLike(@Param("username") String username, @Param("name") String name);

    @Select("select * from `user` where name like concat('%', #{name}, '%') and username like concat('%', #{username}, '%') limit #{skipPage}, #{pageSize}")
    List<User> getUsersByPage(@Param("skipPage") Integer skipPage, @Param("pageSize") Integer pageSize, @Param("username") String username, @Param("name") String name);

    @Select("select count(`id`) from `user`  where name like concat('%', #{name}, '%') and username like concat('%', #{username}, '%') ")
    Integer usersTotal( @Param("username") String username, @Param("name") String name);

    @Select("select * from `user` where username = #{username}")
    User getUserByUserName(String username);
}
