package com.warrior.base.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.warrior.base.entity.Resources;
import com.warrior.common.model.ResourceModel;

import java.util.LinkedList;
import java.util.List;

public interface ResourceService extends IService<Resources>{

    List<Resources> getListByParentId(int parentId);

    Page<Resources> getListByPage(String name, String url, int status, int isShow, int type, int page, int rows);

    boolean modified(Resources resource);

    LinkedList<ResourceModel> getResourceModelList(List<Resources> resList,String permission);

}
