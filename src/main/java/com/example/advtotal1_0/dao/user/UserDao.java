package com.example.advtotal1_0.dao.user;

import com.example.advtotal1_0.pojo.User;

import java.util.List;

public interface UserDao {
     User findUserByUsername(String username) throws Exception;
    boolean addUser(User user) throws Exception;
    List<User> getAllUsers() throws Exception;
    User getUserById(int id) throws Exception;
    int addUser1(User user) throws Exception;
    int updateUser(User user) throws Exception;
    int deleteUser(int id) throws Exception;
}
