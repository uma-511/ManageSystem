package com.warrior.util.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.pagehelper.PageInfo;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class JSONMsg implements Serializable{

    //成功
    public final static boolean FLAG_SUCCESS = true;
    //失败
    public final static boolean FLAG_FAIL = false;

    @Getter @Setter
    private boolean success;     //是否成功

    @Getter @Setter
    private Object data;        //返回数据

    @Getter @Setter
    private String msg;         //错误信息

    @Getter @Setter
    private boolean isLogin = true;

    public JSONMsg() {
    }

    public JSONMsg(boolean success) {
        this.success = success;
    }

    public JSONMsg(boolean success, Object data, String msg) {
        this.success = success;
        this.data = data;
        this.msg = msg;
    }

    public JSONMsg(boolean success, PageInfo pageInfo, String msg) {
        this.success = success;
        this.data = pageInfo;
        this.msg = msg;
    }

    public void setPageInfo(PageInfo pageInfo){
        this.data = pageInfo;
    }
}