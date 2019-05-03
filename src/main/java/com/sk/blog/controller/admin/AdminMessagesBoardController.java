package com.sk.blog.controller.admin;

import com.sk.blog.dao.AboutMeMapper;
import com.sk.blog.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminMessagesBoardController {
    @Autowired
    AboutMeMapper aboutMeMapper;
   @RequestMapping("/admin/messages/delete")
   @ResponseBody
    public Result delete(Integer mid)
    {
        aboutMeMapper.delectMessagesBoard(mid);
        return Result.ok();

    }
}
