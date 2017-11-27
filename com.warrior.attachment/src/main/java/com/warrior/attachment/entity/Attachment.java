package com.warrior.attachment.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "Attachment",description = "附件信息")
@TableName(value = "sys_attachment")
public class Attachment implements Serializable{

   @ApiModelProperty(value = "编号")
   @TableId(value = "aid",type = IdType.NONE)
   @Setter @Getter
   private String aid;

   @ApiModelProperty(value = "文件名")
   @Setter @Getter
   private String fileName;

   @ApiModelProperty(value = "文件大小")
   @Setter @Getter
   private long size;

   @ApiModelProperty(value = "文件路径")
   @Setter @Getter
   private String filePath;

   @ApiModelProperty(value = "上传时间")
   @Setter @Getter
   private Date createTime;

    @ApiModelProperty(value = "文件类型")
   @Setter @Getter
   private int fileType;

}