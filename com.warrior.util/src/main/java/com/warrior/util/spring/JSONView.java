package com.warrior.util.spring;


import com.warrior.util.common.JSONUtils;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

public class JSONView extends AbstractView {

    @Override
    protected void renderMergedOutputModel(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/json; charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(JSONUtils.toJson(map));
        writer.flush();
        writer.close();
    }

}