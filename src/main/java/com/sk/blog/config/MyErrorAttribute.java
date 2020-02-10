package com.sk.blog.config;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

//@Component
//public class MyErrorAttribute extends DefaultErrorAttributes {
//    @Override
//    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
//        Map<String,Object> map=super.getErrorAttributes(webRequest, includeStackTrace);
//        String msg = (String) webRequest.getAttribute("msg", 0);
//        map.put("messages",msg);
//        return map;
//    }
//}
