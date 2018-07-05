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

@ApiModel(value = "AppUser",description = "用户管理")
@TableName(value = "sys_app_user")
public class AppUser implements Serializable{

   @ApiModelProperty(value = "")
   @TableId
   @Setter @Getter
   private int id;

   @ApiModelProperty(value = "昵称")
   @Setter @Getter
   private String nickName;

   @ApiModelProperty(value = "性别:man=男,woman=女")
   @Setter @Getter
   private String gender;

   @ApiModelProperty(value = "性别")
   @Setter @Getter
   private int age;

   @ApiModelProperty(value = "密码")
   @Setter @Getter
   private String password;

   @ApiModelProperty(value = "密码盐")
   @Setter @Getter
   private String salt;

   @ApiModelProperty(value = "头像")
   @Setter @Getter
   private String avatar;

   @ApiModelProperty(value = "电话号码")
   @Setter @Getter
   private String phone;

   @ApiModelProperty(value = "Session标识")
   @Setter @Getter
   private String token;

   @ApiModelProperty(value = "是否实名认证")
   @Setter @Getter
   private boolean certified;

   @ApiModelProperty(value = "企业认证")
   @Setter @Getter
   private boolean bossCertified;

   @ApiModelProperty(value = "用户状态:normal=正常,disable=停用,cancel=注销")
   @Setter @Getter
   private String status;

   @ApiModelProperty(value = "注册时间")
   @Setter @Getter
   private Date createTime;

   @ApiModelProperty(value = "最后登录时间")
   @Setter @Getter
   private Date lastLoginTime;

   @ApiModelProperty(value = "登录失败次数")
   @Setter @Getter
   private int loginfailure;

   @ApiModelProperty(value = "推荐码")
   @Setter @Getter
   private String refereeCode;

   @ApiModelProperty(value = "账号")
   @Setter @Getter
   private String account;

   @ApiModelProperty(value = "token过期时间")
   @Setter @Getter
   private Date tokenExpire;

   @ApiModelProperty(value = "地址")
   @Setter @Getter
   private String address;

   @ApiModelProperty(value = "网址")
   @Setter @Getter
   private String website;

   @ApiModelProperty(value = "个性签名")
   @Setter @Getter
   private String sign;

   @ApiModelProperty(value = "")
   @TableField(value = "deviceToken")
   @Setter @Getter
   private String deviceToken;

   @ApiModelProperty(value = "用户等级")
   @Setter @Getter
   private int level;

}