package com.warrior.permissions.model;

import java.util.LinkedList;

public class ResourceChild extends LinkedList<ResourceModel>{

    public ResourceChild addChild(ResourceModel model){
        this.add(model);
        return this;
    }
}