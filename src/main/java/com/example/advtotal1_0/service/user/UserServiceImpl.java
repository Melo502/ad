package com.example.advtotal1_0.service.user;


import com.example.advtotal1_0.dao.user.UserDao;
import com.example.advtotal1_0.dao.user.UserDaoImpl;
import com.example.advtotal1_0.pojo.User;

import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();

    public User findUserByUsername(String username) throws Exception {
        return userDao.findUserByUsername(username);
    }

    public boolean addUser(User user) throws Exception {
        User existingUser = userDao.findUserByUsername(user.getUsername());
        if (existingUser != null) {
            return false;
        }
        return userDao.addUser(user);
    }

    @Override
    public List<User> getAllUsers() throws Exception {
        return userDao.getAllUsers();
    }

    @Override
    public User getUserById(int id) throws Exception {
        return userDao.getUserById(id);
    }

    @Override
    public int addUser1(User user) throws Exception {
        return userDao.addUser1(user);
    }

    @Override
    public int updateUser(User user) throws Exception {
        return userDao.updateUser(user);
    }

    @Override
    public int deleteUser(int id) throws Exception {
        return userDao.deleteUser(id);
    }
}

