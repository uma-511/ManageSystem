package com.warrior.gen.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class TableInfo implements Serializable {

    @Setter @Getter
    private String tableName;

    @Setter @Getter
    private String modulePath;

    @Setter @Getter
    private String packageName;

    @Setter @Getter
    private String remark;

    @Setter @Getter
    private Boolean swagger = true;

    @Setter @Getter
    private String entityName;
}
