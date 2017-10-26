package com.warrior.common;

import com.baomidou.mybatisplus.plugins.Page;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class JSONMsg implements Serializable{

    //成功
    public final static boolean FLAG_SUCCESS = true;
    //失败
    public final static boolean FLAG_FAIL = false;

    @ApiModelProperty(value = "接口状态",required = true)
    @Getter @Setter
    private boolean success;     //是否成功

    @ApiModelProperty(value = "接口返回数据")
    @Getter @Setter
    private Object data;        //返回数据

    @ApiModelProperty(value = "接口错误信息")
    @Getter @Setter
    private String msg;         //错误信息

    @ApiModelProperty(value = "用户当前登录状态")
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

    public JSONMsg(boolean success, Page pageInfo, String msg) {
        this.success = success;
        this.data = pageInfo;
        this.msg = msg;
    }

    public void setPageInfo(Page pageInfo){
        this.data = pageInfo;
    }
}