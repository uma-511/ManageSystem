package com.warrior.permissions.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@TableName("warrior_resources")
@AllArgsConstructor
@NoArgsConstructor
public class Resources implements Serializable {

    @TableId
    @Getter @Setter
    private long resId;

    @Getter @Setter
    private String resName;

    @Getter @Setter
    private int parentId;

    @Getter @Setter
    private String url;

    @Getter @Setter
    private int sort;

    @Getter @Setter
    private int isShow;

    @Getter @Setter
    private String remark;

    @Getter @Setter
    private int status;

    @Getter @Setter
    private Date createTime;

    @Getter @Setter
    private Date updateTime;

    @Getter @Setter
    private String permission;

    @Getter @Setter
    private String icon;

    @Getter @Setter
    private int type;

    @TableField(exist=false)
    @Getter @Setter
    private String parentName;

    public Resources(int parentId) {
        this.parentId = parentId;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj !=null && obj instanceof Resources){
            return ((Resources)obj).getResId() == this.resId;
        }else{
            return false;
        }
    }
}
