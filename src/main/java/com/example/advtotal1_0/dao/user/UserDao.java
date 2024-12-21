package com.example.advtotal1_0.dao.user;

import com.example.advtotal1_0.pojo.User;

import java.util.List;

public interface UserDao {
     User findUserByUsername(String username) throws Exception;
     boolean addUser(User user) throws Exception;
    /**
     * 获取所有用户
     *
     * @return 用户列表
     * @throws Exception 数据库异常
     */
    List<User> getAllUsers() throws Exception;

    /**
     * 根据ID获取用户
     *
     * @param id 用户ID
     * @return 用户对象
     * @throws Exception 数据库异常
     */
    User getUserById(int id) throws Exception;

    /**
     * 添加用户
     *
     * @param user 用户对象
     * @return 受影响的行数
     * @throws Exception 数据库异常
     */
    int addUser1(User user) throws Exception;

    /**
     * 更新用户
     *
     * @param user 用户对象
     * @return 受影响的行数
     * @throws Exception 数据库异常
     */
    int updateUser(User user) throws Exception;

    /**
     * 删除用户
     *
     * @param id 用户ID
     * @return 受影响的行数
     * @throws Exception 数据库异常
     */
    int deleteUser(int id) throws Exception;
}
