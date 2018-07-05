package com.warrior.article.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.warrior.common.service.WarriorBaseServiceImpl;
import org.springframework.stereotype.Service;
import com.warrior.article.service.AppUserService;
import com.warrior.article.dao.AppUserDao;
import com.warrior.article.entity.AppUser;
import org.apache.commons.lang.StringUtils;

@Service
public class AppUserServiceImpl extends WarriorBaseServiceImpl<AppUserDao, AppUser> implements AppUserService {

    @Override
    public Page<AppUser> getPageList(Page<AppUser> page , String nickName, String status, String phone, String gender) {
        EntityWrapper<AppUser> ew = new EntityWrapper<>();
        if(!StringUtils.isBlank(nickName)){
            ew.eq("nick_name",nickName);
        }
        if(!status.equals("all")){
            ew.eq("status",status);
        }
        if(!StringUtils.isBlank(phone)){
            ew.eq("phone",phone);
        }
        if(!gender.equals("all")){
            ew.eq("gender",gender);
        }
        page.setRecords(baseMapper.getPageList(page, ew));
        return page;
   }

    @Override
    public boolean cancelUserById(int id) {
        return baseMapper.cancelUserById(id);
    }

    @Override
    public boolean disableUserById(int id) {
        return baseMapper.disableUserById(id);
    }

    @Override
    public boolean enableUserById(int id) {
        return baseMapper.enableUserById(id);
    }

}