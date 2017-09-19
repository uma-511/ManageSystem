package com.warrior.permissions.entity;

import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "warrior_resources")
@AllArgsConstructor
@NoArgsConstructor
public class Resource implements Serializable {

    @Getter @Setter
    @Id
    @Column(name="resId")
    private long resId;
    @Getter @Setter
    @Column(name="resName")
    private String resName;
    @Getter @Setter
    @Column(name="parentId")
    private int parentId;
    @Getter @Setter
    private String url;
    @Getter @Setter
    private int sort;
    @Getter @Setter
    @Column(name="isShow")
    private int isShow;
    @Getter @Setter
    private String remark;
    @Getter @Setter
    private int status;
    @Getter @Setter
    @Column(name="createTime")
    private Date createTime;
    @Getter @Setter
    @Column(name="updateTime")
    private Date updateTime;
    @Getter @Setter
    private String permission;
    @Getter @Setter
    private String icon;
    @Getter @Setter
    private int type;

    public Resource(int parentId) {
        this.parentId = parentId;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj !=null && obj instanceof Resource){
            return ((Resource)obj).getResId() == this.resId;
        }else{
            return false;
        }
    }

    public static Ordering<Resource> parentIdOrder = new Ordering<Resource>() {
        @Override
        public int compare(@Nullable Resource resource, @Nullable Resource t1) {
            return Ints.compare(resource.getParentId(),t1.getParentId());
        }
    };
}
