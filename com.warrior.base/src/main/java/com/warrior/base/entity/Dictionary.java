package com.warrior.base.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;


/**
 * The type Dictionary.
 */
@TableName(value = "w")
@NoArgsConstructor
@AllArgsConstructor
public class Dictionary implements Serializable {

    @TableId
    @Getter @Setter
    private long id;

    @Getter @Setter
    private String dicKey;

    @Getter @Setter
    private String dicValue;

    @Getter @Setter
    private int sort;

    @Getter @Setter
    private int isShow;

    @Getter @Setter
    private int dicType;

    @Getter @Setter
    private Date createTime;

    @Setter @Getter
    private String typeValue;

    public Dictionary(int dicType) {
        this.dicType = dicType;
    }
}
