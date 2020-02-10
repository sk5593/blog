package com.sk.blog.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sk.blog.bean.Comments;
import com.sk.blog.bean.Contents;
import com.sk.blog.service.ArticleService;
import com.sk.blog.utils.Commons;
import com.sk.blog.utils.IpAddr;
import com.sk.blog.utils.Result;
import com.sk.blog.utils.TaleUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.enterprise.inject.Default;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
public class ArticleController {
    Commons commons = new Commons();
    @Autowired
    ArticleService articleService;

    @RequestMapping( "article/{cid}" )
    /**
     * 第一次访问该文章 ，显示第一页评论
     * 根据点击的id 查看文章详情和评论
     *
     */
    public String article(@PathVariable Integer cid, Model model, HttpSession session, Integer p) {

        Contents article = articleService.getArticle(cid);
        //点击数加1
        Integer hits = article.getHits();
        hits = hits + 1;
        articleService.addHits(hits, cid);

        String content = article.getTags();
        String[] split = content.split(",");

        model.addAttribute("tags",split);

        model.addAttribute("article", article);

        model.addAttribute("commons", commons);

        //将cid传到session域中，供分页使用
        session.setAttribute("ArticleCid", article.getCid());

        return "index/page";
    }

    /**
     * 点击下一页评论，再次访问该文章页，显示下一页评论
     * 评论分页
     *
     * @param session
     * @param model
     * @param p
     * @param size
     * @return
     */
    @RequestMapping( "comments/{p}" )
    @ResponseBody
    public PageInfo<Comments> getComments(HttpSession session, Model model, @PathVariable Integer p, @RequestParam( defaultValue = "3" ) Integer size) {

        //获取第一次访问时传入的cid
        Integer cid = (Integer) session.getAttribute("ArticleCid");

        //分页，从url中获取开始的下标
        PageHelper.startPage(p, size);
        //获取分页评论
        List<Comments> list = articleService.getComments(cid);
        //封装成pageinfo
        PageInfo<Comments> comments = new PageInfo<>(list);
        for(int x = 0 ; x<comments.getList().size();x++)
        {
            Long created = comments.getList().get(x).getCreated();
            String s = TaleUtils.formatDate(created);
            comments.getList().get(x).setStringCreated(s);
        }
          return comments;
    }

    /**
     * @param author   提交人
     * @param mail     提交的邮箱
     * @param session  文章id
     * @return
     */
    @ResponseBody
    @PostMapping( "/comments" )
    public Result subComment(Integer cid,String author, String mail, String mytextarea, HttpSession session, HttpServletRequest request) {
        //获取提交人的ip地址
        IpAddr ia = new IpAddr();
        String ip = ia.getIp(request);
        //如果没有填写名字，给一个默认名字
        if (StringUtils.isEmpty(author)) {
            author = "匿名用户";
        }
        //获取当前时间戳
        long time = new Date().getTime();
        //封装评论数据
        Comments comments = new Comments();
        comments.setIp(ip);
        comments.setCreated(time);
        comments.setAuthor(author);
        comments.setMail(mail);
        comments.setContent(mytextarea);
        comments.setCid(cid);
        //还需要让对应文章的评论数+1
        Result result = articleService.insertComment(comments);
        return result;

    }
    @ResponseBody
    @RequestMapping("/checout")
    public String checkout(@RequestParam String data) {
        return data;
    }

}
