package com.warrior.article.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.warrior.article.entity.Recharge;

import java.util.Date;

public interface RechargeService extends IService<Recharge>{

    Page<Recharge> getPageList(Page<Recharge> page, Date time_start, Date time_end, String uid, String channel,String status, String outtradeno);

}
