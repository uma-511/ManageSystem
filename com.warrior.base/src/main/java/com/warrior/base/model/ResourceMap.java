package com.warrior.base.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedList;

public class ResourceMap extends LinkedList<ResourceMap.MapInfo> {

    public ResourceMap addPath(String name,String url){
        this.add(new MapInfo(name,url));
        return this;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    class MapInfo{
        @Getter @Setter
        private String name;

        @Getter @Setter
        private String url;
    }
}