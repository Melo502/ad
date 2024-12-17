package com.example.advtotal1_0.dao.user;

import com.example.advtotal1_0.pojo.User;

public interface UserDao {
    public User findUserByUsername(String username) throws Exception;
    public boolean addUser(User user) throws Exception;
}
