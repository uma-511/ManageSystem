package com.warrior.util.common;

import org.apache.commons.lang.StringUtils;

import java.util.Date;
import java.util.HashMap;


/**
 * 用于构建mybatis查询参数
 * The type Query params.
 */

public class QueryParams extends HashMap<String,Object> {

    /**
     * 添加 String 参数
     * @param key
     * @param value
     * @return
     */
    public QueryParams addStrParam(String key,String value){
        if(!StringUtils.isBlank(value)){
            this.put(key,value);
        }
        return this;
    }

    /**
     * 添加数值类型参数 value == valve 不会添加参数
     * @param key
     * @param value
     * @param valve 阈值
     * @return
     */
    public QueryParams addNumParam(String key,Integer value,Integer valve){
        if (value != null && value != valve){
            this.put(key,value);
        }
        return this;
    }

    /**
     * 添加数值类型参数 默认等于 -1 不添参数
     * @param key
     * @param value
     * @return
     */
    public QueryParams addNumParam(String key,Integer value){
        return addNumParam(key, value,-1);
    }

    /**
     * 添加时间类型参数
     * @param key
     * @param value
     * @return
     */
    public QueryParams addDateParam(String key,Date value){
        if (value != null){
            this.put(key,value);
        }
        return this;
    }

}