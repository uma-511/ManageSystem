package com.warrior.attachment.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import com.warrior.attachment.entity.Attachment;

public interface AttachmentDao extends BaseMapper<Attachment> {

    List<Attachment> getPageList(Pagination page, @Param("ew")Wrapper<Attachment> wrapper);

}