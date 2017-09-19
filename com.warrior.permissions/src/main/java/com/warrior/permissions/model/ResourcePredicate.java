package com.warrior.permissions.model;


import com.google.common.base.Predicate;
import com.warrior.permissions.entity.Resource;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;

/***
 * 数据过滤器
 */
public class ResourcePredicate implements Predicate<Resource> {

    @Getter @Setter
    private int apply;

    public ResourcePredicate(int apply){
        this.apply = apply;
    }

    @Override
    public boolean apply(@Nullable Resource resource) {
        return resource.getParentId() == apply;
    }
}