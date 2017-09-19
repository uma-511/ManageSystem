package com.warrior.permissions.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * The type Dictionary.
 */
@Table(name = "warrior_dictionary")
@NoArgsConstructor
@AllArgsConstructor
public class Dictionary implements Serializable {

    @Getter @Setter
    @Id
    private long id;
    @Getter @Setter
    @Column(name="dicKey")
    private String dicKey;
    @Getter @Setter
    @Column(name="dicValue")
    private String dicValue;
    @Getter @Setter
    private int sort;
    @Getter @Setter
    @Column(name="isShow")
    private int isShow;
    @Getter @Setter
    @Column(name="dicType")
    private int dicType;
    @Getter @Setter
    @Column(name="createTime")
    private Date createTime;
    @Setter @Getter
    private String remark;

    public Dictionary(int dicType) {
        this.dicType = dicType;
    }
}
