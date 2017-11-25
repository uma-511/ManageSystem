package com.warrior.base.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import com.warrior.base.dao.SysLogDao;
import com.warrior.base.entity.SysLog;
import com.warrior.base.service.SysLogService;
import com.warrior.common.service.WarriorBaseServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysLogServiceImpl extends WarriorBaseServiceImpl<SysLogDao,SysLog> implements SysLogService {


    public Page<SysLog> getListByPage(String keyWord,int page,int rows){
        Page<SysLog> paging = new Page<>(page,rows);
        EntityWrapper<SysLog> entity = new EntityWrapper<>();
        if (!StringUtils.isBlank(keyWord)){
            entity.like("operation",keyWord);
        }
        paging.setRecords(baseMapper.getListByPage(paging,entity));
        return paging;
    }

    public boolean delById(String id){
        if (StringUtils.isEmpty(id)){
            return true;
        }
        String [] array = id.split("-");
        List<Integer> idList = Lists.newArrayList();
        for (String str: array) {
            idList.add(Integer.parseInt(str));
        }
        return super.deleteBatchIds(idList);
    }

    public boolean delAll(){
        return super.delete(new EntityWrapper<SysLog>());
    }

    public SysLog getLastLogin(long uid){
        EntityWrapper<SysLog> ew = new EntityWrapper<>();
        ew.eq("user_id",uid);
        ew.eq("operation","用户登录");
        ew.orderBy("create_time",false);
        ew.last("limit 0,1");
        List<SysLog> list = baseMapper.selectList(ew);
        return list != null && list.size() > 0 ? list.get(0) : null;
    }
}