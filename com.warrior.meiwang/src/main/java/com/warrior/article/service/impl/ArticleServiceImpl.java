package com.warrior.article.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.warrior.article.dao.ArticleDao;
import com.warrior.article.entity.Article;
import com.warrior.article.service.ArticleService;
import com.warrior.common.service.WarriorBaseServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Date;

@Service
public class ArticleServiceImpl extends WarriorBaseServiceImpl<ArticleDao, Article> implements ArticleService {

    public Page<Article> getPageList(Page<Article> page ,Date createTime_start,Date createTime_end,int typeId,String isHot) {
        EntityWrapper<Article> ew = new EntityWrapper<>();
        if(createTime_start != null){
            ew.ge("create_time",createTime_start);
        }
        if(createTime_end != null){
            ew.le("create_time",createTime_end);
        }
        if(typeId > 0){
            ew.eq("type_id",typeId);
        }
        if(!StringUtils.isBlank(isHot)){
            ew.eq("is_hot",isHot);
        }
        ew.eq("is_deleted",false);
        page.setRecords(baseMapper.getPageList(page, ew));
        return page;
   }

    @Override
    public boolean deleteById(Serializable id) {
        Article article = selectById(id);
        article.setDeleted(true);
        return super.updateById(article);
    }
}