package com.warrior.common.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import com.warrior.common.dao.SysLogDao;
import com.warrior.common.entity.SysLog;
import com.warrior.common.service.SysLogService;
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
}