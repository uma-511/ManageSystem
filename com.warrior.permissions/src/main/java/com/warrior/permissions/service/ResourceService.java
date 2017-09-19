package com.warrior.permissions.service;

import com.github.pagehelper.PageInfo;
import com.warrior.permissions.entity.Resource;
import com.warrior.util.service.WarriorBaseService;

import java.util.List;

public interface ResourceService extends WarriorBaseService<Resource> {

    List<Resource> getListByParentId(int parentId);

    PageInfo<Resource> getListByPage(String name,String url,int status,int isShow,int type,int page,int rows);

}
