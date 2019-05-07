package com.sk.blog.controller.admin;

import com.sk.blog.service.admin.AdminUserService;
import com.sk.blog.utils.Commons;
import com.sk.blog.utils.Result;
import com.sk.blog.utils.TaleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class AdminUserController {
    @Autowired
    AdminUserService adminUserService;
    Commons commons = new Commons();
    @RequestMapping("/admin/profile")
    public String redirect(Model model)
    {
        model.addAttribute("commons",commons);
        return "admin/profile";
    }
    /**
     * 修改密码
     */
    @RequestMapping("/admin/password")
    @ResponseBody
    public Result editPassword(String oldPassword, String password, HttpSession session)
    {
        String s = TaleUtils.MD5encode(oldPassword);
        String s1 = TaleUtils.MD5encode(password);
        Result result = adminUserService.confirmPassword(s, session);
        if(result.isSuccess())
        {
            Result result1 = adminUserService.editPassword(s1, session);
            return result1;
        }
        else
            return result;

    }
}
