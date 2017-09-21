package com.warrior.permissions.model;


import com.google.common.base.Predicate;
import com.warrior.permissions.entity.Resources;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;

/***
 * 数据过滤器
 */
public class ResourcePredicate implements Predicate<Resources> {

    @Getter @Setter
    private int apply;

    public ResourcePredicate(int apply){
        this.apply = apply;
    }

    @Override
    public boolean apply(@Nullable Resources resource) {
        return resource.getParentId() == apply;
    }
}