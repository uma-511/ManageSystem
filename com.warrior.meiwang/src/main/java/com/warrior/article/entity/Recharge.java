package com.warrior.article.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "Recharge",description = "充值管理")
@TableName(value = "sys_recharge")
public class Recharge implements Serializable{

   @ApiModelProperty(value = "id")
   @TableId
   @Setter @Getter
   private int id;

   @ApiModelProperty(value = "用户id")
   @Setter @Getter
   private int uid;

   @ApiModelProperty(value = "充值金额")
   @Setter @Getter
   private double money;

   @ApiModelProperty(value = "充值时间")
   @Setter @Getter
   private Date time;

   @ApiModelProperty(value = "充值渠道:Alipay=支付宝,Wechat=微信,UnionPay=银联,BossWallet=老板钱包")
   @Setter @Getter
   private String  channel;

   @ApiModelProperty(value = "订单号")
   @Setter @Getter
   private String outtradeno;

   @ApiModelProperty(value = "订单状态 0：提交，1：完成")
   @Setter @Getter
   private int status;

   @ApiModelProperty(value="充值类型")
   @Setter @Getter
   private  String chargeType;

   @ApiModelProperty(value="目标用户id")
   @Setter @Getter
   private int tuid;

   @ApiModelProperty(value="充值流向，支出/收入")
   @Setter @Getter
   private int direction;

   @ApiModelProperty(value="回调成功")
   @Setter @Getter
   private int handled;

}