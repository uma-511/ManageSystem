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
 * The type Dictionary.
 */
@ApiModel(value = "Dictionary",description = "字典信息")
@TableName(value = "warrior_dictionary")
@NoArgsConstructor
@AllArgsConstructor
public class Dictionary implements Serializable {

    @ApiModelProperty(value = "编号")
    @TableId
    @Getter @Setter
    private long id;

    @ApiModelProperty(value = "字典Key",required = true)
    @Getter @Setter
    private String dicKey;

    @ApiModelProperty(value = "字典Value",required = true)
    @Getter @Setter
    private String dicValue;

    @ApiModelProperty(value = "排序",required = true)
    @Getter @Setter
    private int sort;

    @ApiModelProperty(value = "是否显示",required = true)
    @Getter @Setter
    private int isShow;

    @ApiModelProperty(value = "字典类型",required = true)
    @Getter @Setter
    private int dicType;

    @ApiModelProperty(value = "创建时间")
    @Getter @Setter
    private Date createTime;

    @ApiModelProperty(value = "字典类型名称",required = true)
    @Setter @Getter
    private String typeValue;

    public Dictionary(int dicType) {
        this.dicType = dicType;
    }
}
