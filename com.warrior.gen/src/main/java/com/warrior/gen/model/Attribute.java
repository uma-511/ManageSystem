package com.warrior.gen.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
public class Attribute implements Serializable {

    @Setter @Getter
    private boolean primaryKey = false;

    @Setter @Getter
    private String type;

    @Setter @Getter
    private String name;

    @Setter @Getter
    private String remark;

    public Attribute(boolean primaryKey, String type, String name, String remark) {
        this.primaryKey = primaryKey;
        this.type = type;
        this.name = name;
        this.remark = remark;
    }
}
