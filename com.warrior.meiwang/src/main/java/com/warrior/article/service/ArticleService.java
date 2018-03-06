package com.warrior.article.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.warrior.article.entity.Article;
import java.util.Date;

public interface ArticleService extends IService<Article>{

    Page<Article> getPageList(Page<Article> page,Date createTime_start,Date createTime_end,int typeId,String isHot);

}
