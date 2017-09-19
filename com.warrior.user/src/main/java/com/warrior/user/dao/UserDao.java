package com.warrior.user.dao;

import com.warrior.user.entity.User;
import com.warrior.util.dao.WarriorBaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserDao extends WarriorBaseMapper<User> {

    List<User> getUserList(Map<String,Object> params);

    User getByUserName(String userName);

}
