package com.warrior;

import com.warrior.permissions.model.ResourceMap;
import com.warrior.util.common.JSONUtils;

public class Test {
    public static void main(String args[]){
        ResourceMap map = new ResourceMap();
        map.addPath("Test1","/Test1")
           .addPath("Test2","/Test2");
        System.out.println(JSONUtils.toJson(map));
    }
}
