package com.warrior.common.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@TableName("warrior_sys_log")
public class SysLog implements Serializable{

    @TableId
    @Getter @Setter
    private long id;

    @Getter @Setter
    private long userId;

    @Getter @Setter
    private String userName;

    @Getter @Setter
    private String operation;

    @Getter @Setter
    private String method;

    @Getter @Setter
    private String params;

    @Getter @Setter
    private long time;

    @Getter @Setter
    private String ip;

    @Getter @Setter
    private Date createTime;

}