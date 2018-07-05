package com.warrior.article.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.warrior.article.dao.ClassifyDao;
import com.warrior.article.entity.Classify;
import com.warrior.article.service.ClassifyService;
import com.warrior.common.service.WarriorBaseServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClassifyServiceImpl extends WarriorBaseServiceImpl<ClassifyDao, Classify> implements ClassifyService {

    public Page<Classify> getPageList(Page<Classify> page ,String name,String state) {
        EntityWrapper<Classify> ew = new EntityWrapper<>();
        if(!StringUtils.isBlank(name)){
            ew.eq("name",name);
        }
        if(!StringUtils.isBlank(state)){
            ew.eq("state",state);
        }
        page.setRecords(baseMapper.getPageList(page, ew));
        return page;
   }

    @Override
    public List<Classify> getTree(String name,String state) {
        EntityWrapper<Classify> ew = new EntityWrapper<>();
        if(!StringUtils.isBlank(name)){
            ew.eq("name",name);
        }
        if(!"all".equals(state)){
            ew.eq("state",state);
        }
        List<Classify> list=baseMapper.getList(ew);

        List<Classify> root=genRoot(list);
        genTree(list,root);

        return root;
    }

    private List<Classify> genRoot(List<Classify> list){
        List<Classify> root=new ArrayList<>();
        for (Classify item:list) {
            if(item.getPid()==0){
                root.add(item);
            }
        }
        return root;
    }

    private void genTree(List<Classify> list,List<Classify> currLevel){
        List<Classify> tempCurrLevel=new ArrayList<>();

        for (Classify item:list) {
            for(Classify currNode:currLevel) {
                if (item.getPid() == currNode.getId()) {
                    if(currNode.getChildren()==null){
                        currNode.setChildren(new ArrayList<Classify>());
                    }
                    currNode.getChildren().add(item);
                    tempCurrLevel.add(item);
                }
            }
        }
        if(tempCurrLevel.size()>0)
        genTree(list,tempCurrLevel);
    }

}