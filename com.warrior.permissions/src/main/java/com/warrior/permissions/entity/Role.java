package com.warrior.permissions.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * The type Role.
 */
@Table(name = "warrior_role")
@AllArgsConstructor
@NoArgsConstructor
public class Role implements Serializable{

    @Getter @Setter
    @Id
    private long rid;
    @Getter @Setter
    @Column(name="roleName")
    private String roleName;
    @Getter @Setter
    private String remark;
    @Getter @Setter
    private int status;
    @Getter @Setter
    @Column(name="createTime")
    private Date createTime;
    @Getter @Setter
    @Column(name="updateTime")
    private Date updateTime;

    public Role(String roleName, int status) {
        this.roleName = roleName;
        this.status = status;
    }
}
