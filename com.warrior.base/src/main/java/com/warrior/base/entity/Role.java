package com.warrior.base.entity;

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


/**
 * The type Role.
 */
@ApiModel(value = "Role",description = "角色信息")
@TableName("warrior_role")
@AllArgsConstructor
@NoArgsConstructor
public class Role implements Serializable{

    @ApiModelProperty(value = "角色ID")
    @TableId
    @Getter @Setter
    private long rid;

    @ApiModelProperty(value = "角色名称",required = true)
    @Getter @Setter
    private String roleName;

    @ApiModelProperty(value = "备注",required = true)
    @Getter @Setter
    private String remark;

    @ApiModelProperty(value = "状态",required = true)
    @Getter @Setter
    private int status;

    @ApiModelProperty(value = "创建时间")
    @Getter @Setter
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @Getter @Setter
    private Date updateTime;

    public Role(String roleName, int status) {
        this.roleName = roleName;
        this.status = status;
    }
}
