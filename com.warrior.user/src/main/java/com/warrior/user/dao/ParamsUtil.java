package com.warrior.user.dao;

import org.apache.commons.lang.StringUtils;

import java.util.Date;
import java.util.HashMap;

public class ParamsUtil extends HashMap<String,Object> {

    public ParamsUtil addObjct(String key,Object value){
        if (value != null){
            this.put(key,value);
        }
        return this;
    }

    public ParamsUtil addStr(String key,String value){
        if (!StringUtils.isBlank(value)){
            this.put(key,value);
        }
        return this;
    }

    /**
     * 添加数值参数
     * @param key
     * @param value
     * @param all 当 value = all 时不添加该参数
     * @return
     */
    public ParamsUtil addInt(String key,int value,int all){
        if (value != all){
            this.put(key,value);
        }
        return this;
    }

    public ParamsUtil addDouble(String key,double value,double all){
        if (value != all){
            this.put(key,value);
        }
        return this;
    }

    public ParamsUtil addLong(String key,long value,long all){
        if (value != all){
            this.put(key,value);
        }
        return this;
    }

    public ParamsUtil addBoolen(String key,Boolean value){
        if (value != null){
            this.put(key,value);
        }
        return this;
    }

    public ParamsUtil addDate(String key, Date value){
        if (value != null){
            this.put(key,value);
        }
        return this;
    }
}