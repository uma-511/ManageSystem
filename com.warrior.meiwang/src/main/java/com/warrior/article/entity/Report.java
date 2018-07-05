package com.warrior.article.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "Report",description = "投诉管理")
@TableName(value = "sys_report")
public class Report implements Serializable{

   @ApiModelProperty(value = "id")
   @TableId
   @Setter @Getter
   private int id;

   @ApiModelProperty(value = "举报人id")
   @Setter @Getter
   private int reporter;

   @ApiModelProperty(value = "被举报人")
   @Setter @Getter
   private int defendant;

   @ApiModelProperty(value = "举报内容")
   @Setter @Getter
   private String content;

   @ApiModelProperty(value = "举报时间")
   @Setter @Getter
   private Date reportTime;

   @ApiModelProperty(value = "消息id")
   @Setter @Getter
   private int artid;

   @ApiModelProperty(value = "处理状态")
   @Setter @Getter
   private int status;

   @ApiModelProperty(value = "处理方式")
   @Setter @Getter
   private String processing_mode;

   @ApiModelProperty(value = "处理结果")
   @Setter @Getter
   private String result;
}