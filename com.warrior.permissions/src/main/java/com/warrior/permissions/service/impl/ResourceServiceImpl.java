package com.warrior.permissions.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Collections2;
import com.warrior.permissions.dao.ResourcesDao;
import com.warrior.permissions.entity.Resources;
import com.warrior.permissions.model.ResourceModel;
import com.warrior.permissions.model.ResourcePredicate;
import com.warrior.permissions.service.ResourceService;
import com.warrior.util.service.WarriorBaseServiceImpl;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Log4j
@Service
public class ResourceServiceImpl extends WarriorBaseServiceImpl<ResourcesDao,Resources> implements ResourceService {

    /**
     * 根据资源父ID查找信息
     * @param parentId
     * @return
     */
    public List<Resources> getListByParentId(int parentId){
        return baseMapper.getListByParentId(parentId);
    }


    public Page<Resources> getListByPage(String name, String url, int status, int isShow, int type, int page, int rows) {
        EntityWrapper<Resources> ew = new EntityWrapper<>();
        if (!StringUtils.isBlank(name)){
            ew.eq("name",name);
        }
        if (!StringUtils.isBlank(url)){
            ew.eq("url",url);
        }
        if (status != -1){
            ew.eq("status",status);
        }
        if (isShow != -1){
            ew.eq("is_show",isShow);
        }
        if (type != -1){
            ew.eq("type",type);
        }
        Page<Resources> paging = new Page<>(page,rows);
        paging.setRecords(baseMapper.getListByPage(paging,ew));
        return paging;
    }

    public boolean insert(Resources resource) {
        resource.setCreateTime(new Date());
        resource.setUpdateTime(new Date());
        return super.insert(resource);
    }

    public boolean modified(Resources resource) {
        resource.setUpdateTime(new Date());
        return super.insertOrUpdate(resource);
    }

    /**
     * 将资源集合转换成Model
     * @param resList
     * @return
     */
    public LinkedList<ResourceModel> getResourceModelList(List<Resources> resList,String permission){
        Collection<Resources> parentList = Collections2.filter(resList,new ResourcePredicate(0));

        Iterator<Resources> iterator = parentList.iterator();

        LinkedList<ResourceModel> modelList = new LinkedList<>();
        while (iterator.hasNext()){
            Resources res = iterator.next();
            ResourceModel model = new ResourceModel(res.getResId(),res.getResName(),res.getParentId(),res.getUrl(),res.getIcon(),res.getSort(),res.getType());
            if (!StringUtils.isBlank(permission) && StringUtils.contains(permission,res.getResId()+"$")){
                model.setChecked(true);
            }
            model.getResMap().addPath(model.getResName(),model.getUrl());
            addChild(model,resList,permission);
            modelList.add(model);
        }
        modelList.sort(ResourceModel.sortOrder);
        return modelList;
    }

    private void addChild(ResourceModel model, List<Resources> roleResources,String permission){
        Collection<Resources> subList = Collections2.filter(roleResources,new ResourcePredicate((int)model.getResId())); //过滤数据
        if (subList != null && subList.size() > 0){
            Iterator<Resources> iterator = subList.iterator();
            while (iterator.hasNext()){
                Resources rs = iterator.next();
                ResourceModel subModel = new ResourceModel(rs.getResId(),rs.getResName(),rs.getParentId(),rs.getUrl(),rs.getIcon(),rs.getSort(),rs.getType());
                if (!StringUtils.isBlank(permission) && StringUtils.contains(permission,rs.getResId()+"$")){
                    subModel.setChecked(true);
                }
                subModel.getResMap().addAll(model.getResMap());
                subModel.getResMap().addPath(rs.getResName(),rs.getUrl());
                model.getChild().add(subModel);
                addChild(subModel,roleResources,permission);
            }
            model.getChild().sort(ResourceModel.sortOrder);
        }
    }
}
