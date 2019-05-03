package com.sk.blog.controller;

import com.github.pagehelper.PageInfo;
import com.sk.blog.bean.AboutMe;
import com.sk.blog.bean.MessagesBoard;
import com.sk.blog.service.AboutService;
import com.sk.blog.utils.Commons;
import com.sk.blog.utils.IpAddr;
import com.sk.blog.utils.Result;
import com.sk.blog.utils.TaleUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class AboutController {
    Commons commons=new Commons();
    /**
     *
     * @return 关于我页面跳转
     */
    @Autowired
    AboutService aboutService;
    @RequestMapping("/aboutMe")
    public String about(Model model)
    {

        //获取数据库关于我的信息
        List<AboutMe> allAbouts = aboutService.getAllAbout();
        model.addAttribute("allAbouts",allAbouts);

        model.addAttribute("commons",commons);
        return "index/aboutme";
    }
    /**
     * 关于我的留言功能
     * 提交留言
     */
    @RequestMapping("/messagesBoard")
    @ResponseBody
    public Result messagesBoard(Integer mid, String author, String mail, String mytextarea, HttpServletRequest request)
    {

         MessagesBoard messagesBoard = new MessagesBoard();
         messagesBoard.setContent(mytextarea);
         if(!StringUtils.isEmpty(mail))
         messagesBoard.setMail(mail);
         if(!StringUtils.isEmpty(author))
         {
             messagesBoard.setAuthor(author);
         }else {
             messagesBoard.setAuthor("匿名用户");
         }
         messagesBoard.setCreated(new Date().getTime());
         //获得ip
         messagesBoard.setIp(IpAddr.getIp(request));
         //
         Result result = aboutService.messagesBoard(messagesBoard);
         return result;
    }
    @ResponseBody
    @RequestMapping("/messagesBoard/{p}")
    public PageInfo<MessagesBoard> pageMessagesBoard(@PathVariable Integer p,Model model)
    {
        PageInfo<MessagesBoard> messagesBoard = aboutService.getMessagesBoard(p,3);
        for(int x = 0 ; x<messagesBoard.getList().size();x++)
        {
            Long created = messagesBoard.getList().get(x).getCreated();
            String s = TaleUtils.formatDate(created);
            messagesBoard.getList().get(x).setStringCreated(s);
        }
        return messagesBoard;
    }
}
