package com.warrior.article.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.warrior.article.entity.AppUser;

public interface AppUserService extends IService<AppUser>{

    Page<AppUser> getPageList(Page<AppUser> page,String nickName,String status,String phone,String gender);

    boolean cancelUserById(int id);

    boolean disableUserById(int id);

    boolean enableUserById(int id);
}
