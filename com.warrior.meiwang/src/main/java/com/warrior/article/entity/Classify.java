package com.warrior.article.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "Classify",description = "分类管理")
@TableName(value = "sys_classify")
public class Classify implements Serializable{

   @ApiModelProperty(value = "")
   @TableId
   @Setter @Getter
   private int id;

   @ApiModelProperty(value = "父级id")
   @Setter @Getter
   private int pid;

   @ApiModelProperty(value = "分类名称")
   @Setter @Getter
   private String name;

   @ApiModelProperty(value = "拼音简写")
   @Setter @Getter
   private String idc;

   @ApiModelProperty(value = "首字母")
   @Setter @Getter
   private String letters;

   @ApiModelProperty(value = "拼音")
   @Setter @Getter
   private String yinyin;

   @ApiModelProperty(value = "状态 0：不可用，1：可用")
   @Setter @Getter
   private String state;

   @Setter @Getter
   private List<Classify> children;
}