package com.warrior.permissions.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


/**
 * The type User role.
 */
@Table(name="warrior_user_role")
@AllArgsConstructor
@NoArgsConstructor
public class UserRole implements Serializable {

    @Getter @Setter
    @Id
    private long id;
    @Getter @Setter
    @Column(name="userId")
    private long userId;
    @Getter @Setter
    @Column(name="roleId")
    private long roleId;

    public UserRole(long userId) {
        this.userId = userId;
    }

    public UserRole(long userId, long roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }
}