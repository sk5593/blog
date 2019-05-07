package com.sk.blog.service.admin;

import com.sk.blog.bean.User;
import com.sk.blog.bean.UserExample;
import com.sk.blog.dao.UserMapper;
import com.sk.blog.utils.Result;
import com.sk.blog.utils.TaleUtils;
import io.lettuce.core.ScriptOutputType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Service
public class LoginService {
    @Autowired
    UserMapper userMapper;

    public synchronized Result login(String username, String password, HttpSession session) {
        String s = TaleUtils.MD5encode(password);
        //账号密码正确并且activated为1，如果账号密码正确，但是activated为0，则计算与上次登录禁用的时间差，
        // 达到半小时则将activate置为0，正确登录
        Integer s1 = userMapper.selectByUsernameAndPassword(username, s);

        Integer activated = userMapper.selectActivated();

        //帐号密码正确
        if (s1 !=null) {
            //如果没被禁用
            if (activated == 1) {
                //设置这次登录成功的时间，错误次数为0，是否禁用为可用
                userMapper.updatelastLoginErrorsActivated(new Date().getTime(), 0, 1);
                session.setAttribute("msg","admin_login");
                session.setAttribute("username", username);
                return Result.ok();
            } else {
                //被禁用了,查询被禁用的时间，与现在的时间相比
                Long lastLogin = userMapper.selectLastLogin();
                long now = new Date().getTime();
                //被禁时间大于半小时
                if ((now - lastLogin) / 1000 > 1800) {
                    userMapper.updatelastLoginErrorsActivated(new Date().getTime(), 0, 1);
                    session.setAttribute("msg", "admin_login");
                    session.setAttribute("username", username);
                    return Result.ok();
                } else {
                    return Result.msg("错误次数超过三次，请稍后重试！");
                }
            }

        } else {
            //错误次数加1，
            userMapper.updateErrorsAddOne();
            //密码错误但是没被禁用
            if(activated==1){
                //查询错误次数,如果错误次数为3，是否禁用为禁用，并更新时间
                Integer errorNum = userMapper.selectErrorNum();
                if (errorNum == 3) {
                    userMapper.updatelastLoginErrorsActivated(new Date().getTime(), 0, 0);
                    return Result.msg("错误次数超过三次，请稍后重试！");

                } else {
                    return Result.msg("用户名或密码错误！");
                }
            }else
            {
                //密码错误而且被禁用
                return Result.msg("错误次数超过三次，请稍后重试！");
            }


        }
    }
}
