package com.warrior.gen.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
    private List<QueryParam> query;

    @Setter @Getter
    private String entityName;

    @Setter @Getter
    private List<String> importList = new ArrayList<>();

    @Setter @Getter
    private List<Attribute> attrs = new ArrayList<>();

    public QueryParam getParam(String arg){
        if (query == null || query.size() == 0){
            return null;
        }
        for (QueryParam param : query){
            if (StringUtils.equals(param.getName(),arg)){
                return param;
            }
        }
        return null;
    }
}
