package com.warrior.base.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@TableName(value="warrior_permissions")
@AllArgsConstructor
@NoArgsConstructor
public class Permission implements Serializable {

    @TableId
    @Getter @Setter
    private long id;

    @Getter @Setter
    private long ownId;

    @Getter @Setter
    private long resId;

    @Getter @Setter
    private int type;

    public Permission(long id) {
        this.id = id;
    }

    public Permission(long ownId, int type) {
        this.ownId = ownId;
        this.type = type;
    }

    public Permission(long ownId, long resId, int type) {
        this.ownId = ownId;
        this.resId = resId;
        this.type = type;
    }
}