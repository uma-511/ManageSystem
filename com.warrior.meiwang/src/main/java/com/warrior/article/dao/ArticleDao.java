package com.warrior.article.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.warrior.article.entity.Article;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface ArticleDao extends BaseMapper<Article> {

    List<Article> getPageList(Pagination page, @Param("ew")Wrapper<Article> wrapper);
    List<HashMap> getArtUserPageList(Pagination page, @Param("ew")Wrapper<Article> wrapper);

}