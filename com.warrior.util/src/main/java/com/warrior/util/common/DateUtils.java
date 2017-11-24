package com.warrior.util.common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static String formartDate(Date date,String formart){
        SimpleDateFormat dateFormat = new SimpleDateFormat(formart);
        return dateFormat.format(date);
    }
}