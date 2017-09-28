package com.warrior.common.spring;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomDateConverter implements Converter<String,Date> {

    @Override
    public Date convert(String s) {
        try {
            if(!StringUtils.isBlank(s)){
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(s);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}