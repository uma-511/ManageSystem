package com.warrior.util.common;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.warrior.util.exception.UtilException;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/***
 * 配置文件工具类
 *
 */
@Log4j
public class PropUtils {

    private static List<String> propFileList = Lists.newArrayList();
    private static Map<String,Object> propValueMap = Maps.newHashMap();

    public static void loadProp(String propFilePath){
        try {
            if(!propFileList.contains(propFilePath)){
                propFileList.add(propFilePath);
                Properties properties = new Properties();
                if(StringUtils.startsWith(propFilePath,"classpath:")){
                    propFilePath = propFilePath.replace("classpath:","");
                    properties.load(PropUtils.class.getClassLoader().getResourceAsStream(propFilePath));
                } else {
                    properties.load(new FileInputStream(new File(propFilePath)));
                }
                Enumeration names = properties.propertyNames();
                String name = null;
                while (names.hasMoreElements()){
                    name = names.nextElement().toString();
                    propValueMap.put(name,properties.get(name));
                }
                log.info("===="+propFilePath+" is loaded!");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new UtilException(propFilePath+":配置文件加载失败！");
        }
    }

    public static String getPropValue(String key){
        return String.valueOf(propValueMap.get(key));
    }

}