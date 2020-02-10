package com.sk.blog.controller;

import com.github.pagehelper.PageInfo;
import com.sk.blog.bean.Contents;
import com.sk.blog.bean.IndexMessages;
import com.sk.blog.bean.Visitor;
import com.sk.blog.bean.Visitors;
import com.sk.blog.service.ArticleService;
import com.sk.blog.service.IndexService;
import com.sk.blog.utils.Commons;
import com.sk.blog.utils.IpAddr;
import com.sk.blog.utils.MapCache;
import com.sk.blog.utils.TaleUtils;

import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;
import java.util.Date;
import java.util.List;

@Controller
public class IndexController {

    Commons commons = new Commons();
    @Autowired
    IndexService indexService;
    @Autowired
    ArticleService articleService;

    @RequestMapping( "/" )
    /**
     * 登录首页记录登录时间，登录ip，和登录次数
     * @param model
     * @param page 当前页
     * @param size 每页显示的文档数量
     * @return
     * 2019/4/8
     */
    public String index( HttpServletRequest request,
                        Model model, Integer page, @RequestParam( defaultValue = "9" ) Integer size) {

        String ip = IpAddr.getIp(request);

           indexService.updateVisitorsNum(ip);

        return this.page(1, size, model);

    }

    @GetMapping( "/page/{p}" )
    public String page(@PathVariable Integer p, @RequestParam( defaultValue = "9" ) Integer size, Model model) {
        //查询所有已经发布的文章
        PageInfo<Contents> allContents = indexService.selectAllPublishContents(p, size);
        for (Contents c : allContents.getList()
                ) {
            String content = c.getContent();
            String txtcontent = content.replaceAll("</?[^>]+>", ""); //剔出<html>的标签
            txtcontent = txtcontent.replaceAll("<a>\\s*|\t|\r|\n</a>", "");//去除字符串中的空格,回车,换行符,制表符


            if(!StringUtils.isEmpty(txtcontent)){
                if (txtcontent.length() > 100) {

                    String substring = txtcontent.substring(0, 100);

                    c.setContent(substring + "...");
                }
                else {
                    c.setContent(txtcontent);
                }
            }else {
                c.setContent("无摘要内容");
            }

        }
        model.addAttribute("articles", allContents);
        model.addAttribute("commons", commons);

        return "index/index";
    }

    //访客统计
    @RequestMapping( "/visitors" )
    @ResponseBody
    public Visitors visitors() {
        Visitors visitorsNum = indexService.getVisitorsNum();
        return visitorsNum;
    }

    //网站主页信息
    @RequestMapping( "/indexMessages" )
    @ResponseBody
    public IndexMessages getIndexMessages() {
        IndexMessages indexMessages = indexService.getIndexMessages();
        return indexMessages;

    }

    /**
     * 主页显示最新文章
     */
    @RequestMapping( "/lastArticles" )
    @ResponseBody
    public List<Contents> selectLastContents() {
        List<Contents> list = articleService.selectLastContents();
        for (Contents c : list
                ) {
            Long created = c.getCreated();
            String s = TaleUtils.formatDate(created);
            c.setStringTime(s);
        }
        return list;
    }


}
