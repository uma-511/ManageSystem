package com.warrior.base.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.warrior.base.dao.SettingDao;
import com.warrior.base.entity.Setting;
import com.warrior.base.service.SettingService;
import com.warrior.common.service.WarriorBaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SettingServiceImpl extends WarriorBaseServiceImpl<SettingDao, Setting> implements SettingService {

    @Override
    public Setting getSettingByKey(String key) {
        Wrapper wrapper = new EntityWrapper();
        wrapper.eq("sys_key",key);
        return selectOne(wrapper);
    }

    @Override
    public boolean updateSetting(String key, String value) {
        Setting setting = getSettingByKey(key);
        if(setting != null){
            setting.setSysValue(value);
            setting.setUpdateTime(new Date());
            return updateById(setting);
        }
        return false;
    }
}