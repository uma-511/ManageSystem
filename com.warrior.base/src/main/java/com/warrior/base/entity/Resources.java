package com.warrior.base.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "Resources",description = "资源信息")
@TableName("sys_resources")
@AllArgsConstructor
@NoArgsConstructor
public class Resources implements Serializable {

    @ApiModelProperty(value = "资源ID")
    @TableId
    @Getter @Setter
    private long resId;

    @ApiModelProperty(value = "资源名称",required = true)
    @Getter @Setter
    private String resName;

    @ApiModelProperty(value = "父级ID",required = true)
    @Getter @Setter
    private int parentId;

    @ApiModelProperty(value = "URL",required = true)
    @Getter @Setter
    private String url;

    @ApiModelProperty(value = "排序",required = true)
    @Getter @Setter
    private int sort;

    @ApiModelProperty(value = "是否显示",required = true)
    @Getter @Setter
    private int isShow;

    @ApiModelProperty(value = "备注",required = true)
    @Getter @Setter
    private String remark;

    @ApiModelProperty(value = "资源状态",required = true)
    @Getter @Setter
    private int status;

    @ApiModelProperty(value = "创建时间")
    @Getter @Setter
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @Getter @Setter
    private Date updateTime;

    @ApiModelProperty(value = "权限值")
    @Getter @Setter
    private String permission;

    @ApiModelProperty(value = "图标")
    @Getter @Setter
    private String icon;

    @ApiModelProperty(value = "资源类型",required = true)
    @Getter @Setter
    private int type;

    @ApiModelProperty(value = "父级名称")
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
