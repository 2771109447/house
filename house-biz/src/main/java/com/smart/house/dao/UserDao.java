package com.smart.house.dao;

import com.smart.house.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @mapper  Spring框架扫描Dao接口与mapper映射文件形成代理类，储存再IOC容器中
 */
@Mapper
public interface UserDao {
    //多属性查找用户
     List<User> selectUsers(User user);
     //添加用户
     Integer addUser(User user);
     //删除用户
     Integer deleteUser(String email);
     //更新用户
     Integer updateUser(User user);
}
