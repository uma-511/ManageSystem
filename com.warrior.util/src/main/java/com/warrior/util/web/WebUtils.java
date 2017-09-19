package com.warrior.util.web;

import com.warrior.util.common.JSONUtils;
import lombok.extern.log4j.Log4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Log4j
public class WebUtils {

    public static boolean isAjaxRequest(HttpServletRequest request) {
        boolean flag = false;
        String requestedWith = request.getHeader("X-Requested-With");
        if (requestedWith != null && requestedWith.equalsIgnoreCase("XMLHttpRequest")) {
            flag = true;
        }else{
            requestedWith = request.getHeader("Access-Control-Request-Headers");
            if (requestedWith != null && requestedWith.equalsIgnoreCase("x-request-with")){
                flag = true;
            }
        }
        return flag;
    }

    public static void responseWrite(HttpServletResponse response,Object obj){
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            out = response.getWriter();
            out.write(JSONUtils.toJson(obj));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}