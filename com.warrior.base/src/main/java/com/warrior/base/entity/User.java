package com.warrior.base.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "User",description = "用户信息")
@TableName(value = "sys_user")
public class User implements Serializable{

    @ApiModelProperty(value = "用户编号")
    @TableId
    @Getter @Setter
    private long uid;

    @ApiModelProperty(value = "用户名")
    @Getter @Setter
    private String userName;

    @ApiModelProperty(value = "用户密码")
    @Getter @Setter
    private String passWord;

    @ApiModelProperty(value = "盐(加密因子)")
    @Getter @Setter
    private String salt;

    @ApiModelProperty(value = "性别")
    @Getter @Setter
    private String gender;

    @ApiModelProperty(value = "年龄")
    @Getter @Setter
    private int age;

    @ApiModelProperty(value = "用户类型")
    @Getter @Setter
    private int userType;

    @ApiModelProperty(value = "用户状态")
    @Getter @Setter
    private int status;

    @ApiModelProperty(value = "创建时间")
    @Getter @Setter
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @Getter @Setter
    private Date updateTime;

    @Getter @Setter
    private String token;

    @Getter @Setter
    private String img;

    public User() {
    }

    public User(long uid) {
        this.uid = uid;
    }

    public User(String userName) {
        this.userName = userName;
    }

    public String genCredentialsSalt(){
        return userName+salt;
    }
}