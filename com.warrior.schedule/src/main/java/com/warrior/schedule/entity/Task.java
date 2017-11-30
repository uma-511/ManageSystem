package com.warrior.schedule.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Task",description = "定时作业")
@TableName(value = "sys_task")
public class Task implements Serializable{

   @ApiModelProperty(value = "编号")
   @TableId
   @Setter @Getter
   private int id;

   @ApiModelProperty(value = "任务名称")
   @Setter @Getter
   private String taskName;

   @ApiModelProperty(value = "任务分组")
   @Setter @Getter
   private String taskGroup;

   @ApiModelProperty(value = "任务执行时间表达式")
   @Setter @Getter
   private String cron;

   @ApiModelProperty(value = "任务描述")
   @Setter @Getter
   private String remark;

   @ApiModelProperty(value = "任务执行目标")
   @Setter @Getter
   private String className;

   @ApiModelProperty(value = "任务脚本")
   @Setter @Getter
   private String scriptName;

   @ApiModelProperty(value = "任务状态")
   @Setter @Getter
   private int status;

   @ApiModelProperty(value = "创建时间")
   @Setter @Getter
   private Date createTime;

   @ApiModelProperty(value = "更新时间")
   @Setter @Getter
   private Date updateTime;

}