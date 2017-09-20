package com.warrior.user.service;

import com.github.pagehelper.PageInfo;
import com.warrior.user.entity.User;
import com.warrior.user.entity.UserEntity;
import com.warrior.util.service.WarriorBaseService;

import java.util.Date;
import java.util.List;

public interface UserService extends WarriorBaseService<User> {

    UserEntity getCurrentUser();

    UserEntity login(String userName, String pwd);

    void logOut();

    User getById(long id);

    User getByUserName(String userName);

    PageInfo<User> getUserList(String userName, Integer userType, Integer status, Date startTime, Date endTime, Integer page, Integer rows);
}