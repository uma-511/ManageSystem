package com.warrior.util.common;

import com.google.common.collect.Lists;
import com.google.common.hash.Hashing;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

@Log4j
public class TokenUtil {

    /**
     * 获取Token
     * @return
     */
    public static String getToken(){
        return UUID.randomUUID().toString().replace("-","");
    }

    public static String getSign(Map<String,String> paraMap,String salt,boolean urlEncode){
        String sign = "";
        try {
            List<Map.Entry<String,String>> infoIds = Lists.newArrayList(paraMap.entrySet());
            Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
                @Override
                public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                    return o1.getKey().toString().compareTo(o2.getKey());
                }
            });
            StringBuilder buf = new StringBuilder();
            String key,val="";
            for (Map.Entry<String,String> item : infoIds){
                if (StringUtils.isBlank(item.getKey())){
                    continue;
                }
                key = item.getKey();
                val = item.getValue();
                if (urlEncode){
                    val = URLEncoder.encode(val,"utf-8");
                }
                buf.append(key).append("=").append(val);
                buf.append("&");
            }
            if (!buf.toString().isEmpty()){
                sign = buf.deleteCharAt(buf.length()-1).toString();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return getMd5Str(sign,salt);
    }

    private static String getMd5Str(String sign,String salt){
       return Hashing.hmacMd5(salt.getBytes()).hashBytes(sign.getBytes()).toString();
    }

    public static void main(String args []){
        System.out.println(getMd5Str("0202040103","aaaaaa"));
    }
}