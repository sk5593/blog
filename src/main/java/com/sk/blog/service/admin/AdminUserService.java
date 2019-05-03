package com.sk.blog.service.admin;

import com.sk.blog.bean.User;
import com.sk.blog.dao.UserMapper;
import com.sk.blog.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class AdminUserService {
    @Autowired
    UserMapper userMapper;

    /**
     * 修改密码
     * @param password
     * @param session
     * @return
     */
    public Result editPassword(String password,HttpSession session)
    {
        try{   String username = (String) session.getAttribute("username");
           userMapper.updatePasswordByname(username,password);
            return Result.ok();
        }catch (Exception e)
        {
            return Result.msg("修改密码失败");

        }


    }

    /**
     * 确认旧密码
     * @param oldPassword
     * @param session
     * @return
     */
    public  Result confirmPassword(String oldPassword, HttpSession session)
    {
        String username = (String) session.getAttribute("username");
        Integer s1 = userMapper.selectByUsernameAndPassword(username,oldPassword);
        if(s1==1)
        {
            return Result.ok();
        }else return Result.msg("旧密码错误");
    }
}
