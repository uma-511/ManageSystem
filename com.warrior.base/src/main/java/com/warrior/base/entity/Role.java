package com.warrior.base.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;


/**
 * The type Role.
 */
@TableName("warrior_role")
@AllArgsConstructor
@NoArgsConstructor
public class Role implements Serializable{

    @TableId
    @Getter @Setter
    private long rid;

    @Getter @Setter
    private String roleName;

    @Getter @Setter
    private String remark;

    @Getter @Setter
    private int status;

    @Getter @Setter
    private Date createTime;

    @Getter @Setter
    private Date updateTime;

    public Role(String roleName, int status) {
        this.roleName = roleName;
        this.status = status;
    }
}
