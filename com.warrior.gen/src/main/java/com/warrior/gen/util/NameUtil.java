package com.warrior.gen.util;

public class NameUtil {

    public static String getCamelCaseName(String name,boolean isField){
        String [] names = name.split("_");
        String className = "";
        int i = 0;
        for(String temp : names){
            if (i == 0 && isField){
                className += temp;
            }else{
                className += temp.substring(0,1).toUpperCase()+temp.substring(1,temp.length());
            }
        }
        return className;
    }
}
