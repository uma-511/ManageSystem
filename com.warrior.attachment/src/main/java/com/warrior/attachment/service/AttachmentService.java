package com.warrior.attachment.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.warrior.attachment.FileTypes;
import com.warrior.attachment.entity.Attachment;
import com.warrior.util.poi.ExcelWorkBook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AttachmentService extends IService<Attachment>{

    Page<Attachment> getPageList(Page<Attachment> page);

    String uploadFile(HttpServletRequest request, int type);

    void downloadFile(String id, HttpServletResponse response);

    ExcelWorkBook importExcel(HttpServletRequest request, String paramName);

}
