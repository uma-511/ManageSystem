package com.warrior.base.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Setting",description = "系统设置")
@TableName(value = "sys_setting")
public class Setting implements Serializable{

   @TableId
   @Setter @Getter
   private int id;

   @ApiModelProperty(value = "类型")
   @Setter @Getter
   private String sysKey;

   @ApiModelProperty(value = "内容")
   @Setter @Getter
   private String sysValue;

   @ApiModelProperty(value = "创建时间")
   @Setter @Getter
   private Date createTime;

   @ApiModelProperty(value = "修改时间")
   @Setter @Getter
   private Date updateTime;

}