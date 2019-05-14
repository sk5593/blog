package com.sk.blog.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sk.blog.bean.*;
import com.sk.blog.dao.AboutMeMapper;
import com.sk.blog.dao.ContentsMapper;
import com.sk.blog.dao.LogsMapper;
import com.sk.blog.service.AboutService;
import com.sk.blog.service.ArticleService;
import com.sk.blog.service.CategoryService;
import com.sk.blog.utils.Commons;
import com.sk.blog.utils.Result;
import com.sk.blog.utils.TaleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AdminIndexController {
    Commons commons = new Commons();
    @Autowired
    CategoryService categoryService;
    @Autowired
    ArticleService articleService;
    @Autowired
    ContentsMapper contentsMapper;
    @Autowired
    AboutService aboutService;
    @Autowired
    LogsMapper logsMapper;


    @RequestMapping( "/admin/index" )
    /**
     * 跳转到主页
     */
    public String adminIndex(Model model) {
        //准备主页展示的信息
        //最新文章
        List<Contents> lastArticles = articleService.getLastArticles();
        //最新留言
        List<MessagesBoard> lastMessagesBoard = aboutService.getLastMessagesBoard();
        model.addAttribute("comments",lastMessagesBoard);
        //文章数，留言数
        AdminIndexMessages adminMessages = articleService.getAdminMessages();
        model.addAttribute("statistics", adminMessages);

        model.addAttribute("articles", lastArticles);
        model.addAttribute("commons", commons);
        return "admin/index";
    }

    /**
     * 测试页面
     *
     * @param model
     * @return
     */
    @RequestMapping( "/xxx" )
    public String test(Model model) {

        return "index/test";
    }

    /**
     * 发布成功，跳转到文章列表
     *
     * @param model
     * @return
     */
    @GetMapping( "/admin/article" )
    public String successPublish(Model model, @RequestParam( defaultValue = "1" ) Integer page) {
        PageInfo<Contents> allContents = articleService.getAllContents(page);
        model.addAttribute("articles", allContents);
        model.addAttribute("commons", commons);
        return "admin/article_list";
    }

    /**
     * 跳转到留言管理页面，准备数据
     */
    @RequestMapping( "/admin/leaveMessages" )
    public String getMessagesBoard(Model model, @RequestParam( defaultValue = "1" ) Integer page) {
        PageInfo<MessagesBoard> messagesBoard = aboutService.getMessagesBoard(page, 10);
        model.addAttribute("messages",messagesBoard);
        model.addAttribute("commons",commons);
        return "admin/messagesBoard_list";
    }

    /**
     *
     * @return 后台主页显示日志
     */
    @ResponseBody
    @GetMapping("/admin/index/{p}")
        public PageInfo<Logs> adminIndex(@PathVariable("p") Integer p)
        {
            PageHelper.startPage(p,10);
            List<Logs> logs = logsMapper.selectByExample(null);
            for (Logs l:logs
                    ) {
                l.setStringCreated(TaleUtils.formatDate(l.getCreated()));
            }
            PageInfo pageInfo = new PageInfo(logs);
            return pageInfo;

        }

}
