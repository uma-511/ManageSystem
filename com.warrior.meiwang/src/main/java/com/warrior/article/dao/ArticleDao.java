package com.warrior.article.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import com.warrior.article.entity.Article;

public interface ArticleDao extends BaseMapper<Article> {

    List<Article> getPageList(Pagination page, @Param("ew")Wrapper<Article> wrapper);

}