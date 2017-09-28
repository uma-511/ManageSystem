package com.warrior.common.model;

import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
import java.io.Serializable;

public class ResourceModel implements Serializable{

    @Getter @Setter
    private long resId;
    @Getter @Setter
    private String resName;
    @Getter @Setter
    private int parentId;
    @Getter @Setter
    private String url;
    @Getter @Setter
    private String icon;
    @Getter @Setter
    private int sort;
    @Getter @Setter
    private int type;
    @Getter @Setter
    private boolean checked;
    @Setter @Getter
    private ResourceChild child = new ResourceChild();
    @Setter @Getter
    private ResourceMap resMap = new ResourceMap();

    public ResourceModel(){}

    public ResourceModel(long resId,String resName,int parentId,String url,String icon,int sort,int type){
        this.resId = resId;
        this.resName = resName;
        this.parentId = parentId;
        this.url = url;
        this.icon = icon;
        this.sort = sort;
        this.type = type;
    }

    public static Ordering<ResourceModel> sortOrder = new Ordering<ResourceModel>() {
        @Override
        public int compare(@Nullable ResourceModel resources, @Nullable ResourceModel t1) {
            return Ints.compare(resources.getSort(),t1.getSort());
        }
    };
}