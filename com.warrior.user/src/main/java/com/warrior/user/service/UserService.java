package com.warrior.user.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.warrior.user.entity.User;
import com.warrior.user.model.UserModel;

import java.util.Date;

public interface UserService extends IService<User> {

    UserModel getCurrentUser();

    UserModel login(String userName, String pwd);

    void logOut();

    User getByUserName(String userName);

    Page<User> getUserList(String userName, int userType, int status, Date startTime, Date endTime, int page, int rows);

    boolean delete(long id);

    boolean modified(User user);
}