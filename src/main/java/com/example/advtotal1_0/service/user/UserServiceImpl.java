package com.example.advtotal1_0.service.user;


import com.example.advtotal1_0.dao.user.UserDao;
import com.example.advtotal1_0.dao.user.UserDaoImpl;
import com.example.advtotal1_0.pojo.User;

public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();

    // 根据用户名查找用户
    public User findUserByUsername(String username) throws Exception {
        return userDao.findUserByUsername(username);
    }

    // 增加用户
    public boolean addUser(User user) throws Exception {
        // 先检查用户名是否已存在
        User existingUser = userDao.findUserByUsername(user.getUsername());
        if (existingUser != null) {
            // 如果用户名已经存在，不能添加新用户
            return false;
        }
        // 用户不存在，可以添加新用户
        return userDao.addUser(user);
    }
}

