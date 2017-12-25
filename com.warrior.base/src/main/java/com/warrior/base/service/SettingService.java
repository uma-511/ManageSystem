package com.warrior.base.service;

import com.baomidou.mybatisplus.service.IService;
import com.warrior.base.entity.Setting;

public interface SettingService extends IService<Setting>{

    Setting getSettingByKey(String key);

    boolean updateSetting(String key,String value);
}
