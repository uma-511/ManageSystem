package com.warrior.article.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.warrior.article.entity.Classify;

import java.util.List;

public interface ClassifyService extends IService<Classify>{

    Page<Classify> getPageList(Page<Classify> page,String name,String state);

    List<Classify> getTree(String name,String state);
}
