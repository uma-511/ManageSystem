package com.warrior.attachment.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.warrior.attachment.dao.AttachmentDao;
import com.warrior.attachment.entity.Attachment;
import com.warrior.attachment.service.AttachmentService;
import com.warrior.common.Contacts;
import com.warrior.common.exception.WarriorException;
import com.warrior.common.service.WarriorBaseServiceImpl;
import com.warrior.util.common.DateUtils;
import com.warrior.util.common.PropUtils;
import com.warrior.util.common.RandomCode;
import com.warrior.util.poi.ExcelWorkBook;
import lombok.extern.log4j.Log4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;

@Log4j
@Service
public class AttachmentServiceImpl extends WarriorBaseServiceImpl<AttachmentDao, Attachment> implements AttachmentService {

    public Page<Attachment> getPageList(Page<Attachment> page ) {
        EntityWrapper<Attachment> ew = new EntityWrapper<>();
        page.setRecords(baseMapper.getPageList(page, ew));
        return page;
   }

    public ExcelWorkBook importExcel(HttpServletRequest request,String paramName){
        ExcelWorkBook wb = new ExcelWorkBook();
        try {
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getServletContext());
            if (multipartResolver.isMultipart(request)){
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
                MultipartFile file = multiRequest.getFile(paramName);
                wb.readExcel(file.getInputStream());
            }
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wb;
    }

    @Transactional
    public String uploadFile(HttpServletRequest request, int type) {
        String ids = "";
        try {
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getServletContext());
            if(multipartResolver.isMultipart(request)){

                File filePath = new File(getUploadPath());
                if(!filePath.exists()){
                    filePath.mkdir();
                }
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
                Iterator<String> iterator = multiRequest.getFileNames();
                Attachment attachment;
                while(iterator.hasNext()){
                    MultipartFile file = multiRequest.getFile(iterator.next());
                    if (file != null){
                        attachment = new Attachment();
                        attachment.setCreateTime(new Date());
                        attachment.setFileName("file_"+DateUtils.formartDate(new Date(),"yyyyMMdd_hhmmss")+"_"+ RandomCode.genIntCode(4)+file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."),file.getOriginalFilename().length()));
                        attachment.setSize(file.getSize());
                        attachment.setFilePath(filePath.getPath());
                        attachment.setFileType(type);
                        baseMapper.insert(attachment);
                        file.transferTo(new File(filePath.getPath()+File.separator+attachment.getFileName()));
                        ids +=attachment.getAid()+",";
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(ids.length()>1){
            ids = ids.substring(0,ids.length()-1);
        }
        return ids;
    }

    public void downloadFile(String id, HttpServletResponse response){
        Attachment attachment = baseMapper.selectById(id);
        if(attachment == null){
            throw new WarriorException(Contacts.CODE_FAIL,"文件编号不存在！");
        }
        File file = new File(attachment.getFilePath()+File.separator+attachment.getFileName());
        if (!file.exists()){
            throw new WarriorException(Contacts.CODE_FAIL,"文件不存在！");
        }
        OutputStream stream = null;
        try {
            response.addHeader("Content-Disposi","attachment;filename="+attachment.getFileName());
            response.addHeader("Content-Length",String.valueOf(attachment.getSize()));
            response.setContentType("application/octet-stream");
            stream = response.getOutputStream();
            stream.write(FileUtils.readFileToByteArray(file));
            stream.flush();
            stream.close();
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    @Override
    public boolean deleteById(Serializable id) {
        Attachment att = baseMapper.selectById(id);
        File file = new File(att.getFilePath()+File.separator+att.getFileName());
        if(file.exists()){
            file.delete();
        }
        return super.deleteById(id);
    }

    private String getUploadPath(){
        String path = PropUtils.getPropValue("upload.path");
        String dir = PropUtils.getPropValue("upload.dir");
        if (StringUtils.isEmpty(path)){
            path = System.getProperty("user.dir");
        }
        dir = path.endsWith("/") ? path + dir : path + File.separator + dir;
        return dir;
    }

}