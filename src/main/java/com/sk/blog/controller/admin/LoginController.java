package com.sk.blog.controller.admin;

import com.sk.blog.service.admin.LoginService;
import com.sk.blog.utils.Commons;
import com.sk.blog.utils.Result;
import com.sk.blog.utils.TaleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.swing.text.View;

@Controller
public class LoginController {
    Commons commons=new Commons();
    @Autowired
    LoginService loginService;
    /**
     *
     * @param model
     * @return 跳转到登录页面
     */
    @RequestMapping("/login")
    public String redirectLogin(Model model)
    {
        model.addAttribute("commons",commons);
        return "admin/login";
    }
    @ResponseBody
    @PostMapping("/admin/login")
    /**
     * 用户登录
     */
    public Result  login(String username, String password, HttpSession session)
    {
        Result result = loginService.login(username, password,session);
        return result;
    }
    /**
     * 注销
     */
    @RequestMapping("/admin/logout")
    public String logout(HttpSession session)
    {

        if(session.getAttribute("msg").equals("admin_login"))
        {
            session.removeAttribute("msg");
        }
        return "redirect:/login";

    }
}
