package com.warrior.base.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


/**
 * The type User role.
 */
@TableName("warrior_user_role")
@AllArgsConstructor
@NoArgsConstructor
public class UserRole implements Serializable {

    @TableId
    @Getter @Setter
    private long id;

    @Getter @Setter
    private long userId;

    @Getter @Setter
    private long roleId;

    public UserRole(long userId) {
        this.userId = userId;
    }

    public UserRole(long userId, long roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }
}