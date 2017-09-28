package com.warrior.common.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class RoleModel implements Serializable{

    @Getter @Setter
    private long rid;

    @Getter @Setter
    private String roleName;

    @Getter @Setter
    private boolean checked;

    public RoleModel() {
    }

    public RoleModel(long rid, String roleName, boolean checked) {
        this.rid = rid;
        this.roleName = roleName;
        this.checked = checked;
    }
}