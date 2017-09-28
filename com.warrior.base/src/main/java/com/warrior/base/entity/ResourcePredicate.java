package com.warrior.base.entity;

import com.google.common.base.Predicate;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;

public class ResourcePredicate implements Predicate<Resources> {
    @Getter
    @Setter
    private int apply;

    public ResourcePredicate(int apply){
        this.apply = apply;
    }

    @Override
    public boolean apply(@Nullable Resources resource) {
        return resource.getParentId() == apply;
    }

    @Override
    public boolean test(@Nullable Resources input) {
        return input == null ? false : true;
    }
}
