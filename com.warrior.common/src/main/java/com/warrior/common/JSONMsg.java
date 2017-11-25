package com.warrior.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class JSONMsg implements Serializable{

    @ApiModelProperty(value = "接口状态码",required = true)
    @Getter @Setter
    private int code;           //是否成功

    @ApiModelProperty(value = "接口返回数据")
    @Getter @Setter
    private Object data;        //返回数据

    @ApiModelProperty(value = "接口错误信息")
    @Getter @Setter
    private String msg;         //错误信息

    public JSONMsg() {
    }

    public JSONMsg(int code) {
        this.code = code;
    }

    public JSONMsg(int code, Object data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public JSONMsg(int code,String msg){
        this.code = code;
        this.msg = msg;
    }
//    public JSONMsg(int code, Page pageInfo, String msg) {
//        this.code = code;
//        this.data = pageInfo;
//        this.msg = msg;
//    }
//
//    public void setPageInfo(Page pageInfo){
//        this.data = pageInfo;
//    }
}