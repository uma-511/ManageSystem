package com.warrior.user.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@TableName(value = "warrior_user")
public class User implements Serializable{

    @TableId
    @Getter @Setter
    private long uid;

    @Getter @Setter
    private String userName;

    @Getter @Setter
    private String passWord;

    @Getter @Setter
    private String salt;

    @Getter @Setter
    private String gender;

    @Getter @Setter
    private int age;

    @Getter @Setter
    private int userType;

    @Getter @Setter
    private int status;

    @Getter @Setter
    private Date createTime;

    @Getter @Setter
    private Date updateTime;


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