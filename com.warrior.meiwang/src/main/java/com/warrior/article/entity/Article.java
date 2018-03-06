package com.warrior.article.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "Article",description = "文章管理")
@TableName(value = "sys_article")
public class Article implements Serializable{

   @ApiModelProperty(value = "")
   @TableId
   @Setter @Getter
   private int id;

   @ApiModelProperty(value = "用户id")
   @Setter @Getter
   private int uid;

   @ApiModelProperty(value = "")
   @Setter @Getter
   private String content;

   @ApiModelProperty(value = "图片1")
   @TableField(value = "img_1")
   @Setter @Getter
   private String img1;

   @ApiModelProperty(value = "图片2")
   @TableField(value = "img_2")
   @Setter @Getter
   private String img2;

   @ApiModelProperty(value = "图片3")
   @TableField(value = "img_3")
   @Setter @Getter
   private String img3;

   @ApiModelProperty(value = "图片4")
   @TableField(value = "img_4")
   @Setter @Getter
   private String img4;

   @ApiModelProperty(value = "图片5")
   @TableField(value = "img_5")
   @Setter @Getter
   private String img5;

   @ApiModelProperty(value = "图片6")
   @TableField(value = "img_6")
   @Setter @Getter
   private String img6;

   @ApiModelProperty(value = "图片7")
   @TableField(value = "img_7")
   @Setter @Getter
   private String img7;

   @ApiModelProperty(value = "图片8")
   @TableField(value = "img_8")
   @Setter @Getter
   private String img8;

   @ApiModelProperty(value = "图片9")
   @TableField(value = "img_9")
   @Setter @Getter
   private String img9;

   @ApiModelProperty(value = "纬度")
   @Setter @Getter
   private String lat;

   @ApiModelProperty(value = "经度")
   @Setter @Getter
   private String lng;

   @ApiModelProperty(value = "价格")
   @Setter @Getter
   private double price;

   @ApiModelProperty(value = "创建时间")
   @TableField(value = "create_time")
   @Setter @Getter
   private Date createTime;

   @ApiModelProperty(value = "点赞数")
   @Setter @Getter
   private int likes;

   @ApiModelProperty(value = "浏览次数")
   @Setter @Getter
   private int reads;

   @ApiModelProperty(value = "视频地址")
   @Setter @Getter
   private String video;

   @ApiModelProperty(value = "类别id")
   @TableField(value = "type_id")
   @Setter @Getter
   private int typeId;

   @ApiModelProperty(value = "是否热门：热门：1，非热门：0")
   @TableField(value = "is_hot")
   @Setter @Getter
   private String isHot;

   @ApiModelProperty(value = "是否删除")
   @TableField(value = "is_deleted")
   @Setter @Getter
   private boolean isDeleted;

   @ApiModelProperty(value = "是否屏蔽")
   @TableField(value = "is_hidden")
   @Setter @Getter
   private boolean isHidden;

}