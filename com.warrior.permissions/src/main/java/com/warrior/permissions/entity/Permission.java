package com.warrior.permissions.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name="warrior_permissions")
@AllArgsConstructor
@NoArgsConstructor
public class Permission implements Serializable {

    @Getter @Setter
    @Id
    private long id;
    @Getter @Setter
    @Column(name="ownId")
    private long ownId;
    @Getter @Setter
    @Column(name="resId")
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