package com.ustcat.ustcatEmergency.ToolKit;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.util.JSONPObject;

import org.springframework.security.core.context.SecurityContextHolder;

public class IOToolKits {
    
    public static JSONPObject myResponseGenerator(HttpServletResponse httpServletResponse, Object responseBody,
            String callbackID) {// 该方法用于标准化回复，默认消去引起CORB的头字段并认为返回函数名为callback
        httpServletResponse.setHeader("X-Content-Type-Options", "");
        JSONPObject jsonpObject = new JSONPObject("callback" + callbackID, responseBody);
        return (jsonpObject);
    }

    public static String getName(){//快速查询本session对应的name
        return(SecurityContextHolder.getContext().getAuthentication().getName());
    }
    
}
