package com.warrior.user.entity;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "warrior_user")
public class User implements Serializable{

    @Getter @Setter
    @Id
    private long uid;
    @Getter @Setter
    @Column(name = "userName")
    private String userName;
    @Getter @Setter
    @Column(name="passWord")
    private String passWord;
    @Getter @Setter
    private String salt;
    @Getter @Setter
    private String gender;
    @Getter @Setter
    private int age;
    @Getter @Setter
    @Column(name="userType")
    private int userType;
    @Getter @Setter
    private int status;
    @Getter @Setter
    @Column(name="createTime")
    private Date createTime;
    @Getter @Setter
    @Column(name="updateTime")
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