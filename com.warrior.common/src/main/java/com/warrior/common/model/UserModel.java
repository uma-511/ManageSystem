package com.warrior.common.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

public class UserModel implements Serializable {

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
    private Date createTime;

    @Getter @Setter
    private Date updateTime;
}
