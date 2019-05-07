package com.sk.blog.config;

import com.sk.blog.utils.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class MyExceptionHandler {

    @Autowired
    ExceptionUtils utils;
    @ExceptionHandler(Exception.class)
    public String exception500(Exception e, Model model,HttpServletRequest request) throws SQLException, ClassNotFoundException {
        model.addAttribute("javax.servlet.error.status_code",+500);
        utils.errorToDB(e);
        return "forward:/error";
    }
}



