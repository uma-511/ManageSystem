package com.warrior.base.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Notes",description = "待办事项信息")
@TableName(value = "sys_notes")
public class Notes implements Serializable{

   @ApiModelProperty(value = "编号")
   @TableId
   @Setter @Getter
   private int id;

   @ApiModelProperty(value = "用户编号")
   @Setter @Getter
   private int userId;

   @ApiModelProperty(value = "内容")
   @Setter @Getter
   private String note;

   @ApiModelProperty(value = "状态")
   @Setter @Getter
   private int status;

   @ApiModelProperty(value = "创建时间")
   @Setter @Getter
   private Date createTime;

   @ApiModelProperty(value = "完成时间")
   @Setter @Getter
   private Date overTime;

}