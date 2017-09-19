package com.warrior.user.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

public class UserEntity implements Serializable {

    @Getter
    @Setter
    private long uid;

    @Getter
    @Setter
    private String userName;

    @Getter
    @Setter
    private String gender;

    @Getter
    @Setter
    private int age;

    @Getter @Setter
    @Column(name="createTime")
    private Date createTime;
    @Getter @Setter
    @Column(name="updateTime")
    private Date updateTime;
}
