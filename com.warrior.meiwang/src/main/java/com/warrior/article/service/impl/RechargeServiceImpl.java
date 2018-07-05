package com.warrior.article.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.warrior.common.service.WarriorBaseServiceImpl;
import org.springframework.stereotype.Service;
import com.warrior.article.service.RechargeService;
import com.warrior.article.dao.RechargeDao;
import com.warrior.article.entity.Recharge;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

@Service
public class RechargeServiceImpl extends WarriorBaseServiceImpl<RechargeDao, Recharge> implements RechargeService {

    @Override
    public Page<Recharge> getPageList(Page<Recharge> page , Date time_start, Date time_end, String uid, String channel,String status, String outtradeno) {
        EntityWrapper<Recharge> ew = new EntityWrapper<>();
        if(time_start != null){
            ew.ge("sr.time",time_start);
        }
        if(time_end != null){
            ew.le("sr.time",time_end);
        }
        if(StringUtils.isNotEmpty(uid)){
            ew.eq("sr.uid",uid);
        }
        if(!channel.equals("all")){
            ew.eq("sr.channel",channel);
        }
        if(!status.equals("all")){
            ew.eq("sr.status",status);
        }
        if(!StringUtils.isBlank(outtradeno)){
            ew.eq("sr.outtradeno",outtradeno);
        }
        page.setRecords(baseMapper.getPageList(page, ew));
        return page;
   }

}