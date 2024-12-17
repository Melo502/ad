package com.example.advtotal1_0.service.user;

import com.example.advtotal1_0.pojo.User;

public interface UserService {
    User findUserByUsername(String username) throws Exception;
    boolean addUser(User user) throws Exception;
}
