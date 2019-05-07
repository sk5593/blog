package com.sk.blog.controller;

import com.github.pagehelper.PageInfo;
import com.sk.blog.bean.Contents;
import com.sk.blog.bean.IndexMessages;
import com.sk.blog.bean.Visitors;
import com.sk.blog.service.ArticleService;
import com.sk.blog.service.IndexService;
import com.sk.blog.utils.Commons;
import com.sk.blog.utils.IpAddr;
import com.sk.blog.utils.MapCache;
import com.sk.blog.utils.TaleUtils;
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

    MapCache cache = new MapCache();
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

        long time = new Date().getTime();
        //记录ip和时间
        indexService.insertVisitorsMessages(ip, time);
//        if(StringUtils.isEmpty(cookieValue))
//        {
//            cookie = new Cookie("ip", ip);
//            indexService.updateVisitorsNum();
//            response.addCookie(cookie);
//        }else if (!cookieValue.equals(ip)) {
//                cookie = new Cookie("ip", ip);
//                indexService.updateVisitorsNum();
//                response.addCookie(cookie);
//        }

        //访客数量加1
        //查询缓存中是否有该ip,如果没有，则添加，有，则不添加

       Integer s = cache.get(ip);
       if(s==null)
       {
           //如果该ip为空，则添加
           cache.set(ip,1,3600);
           indexService.updateVisitorsNum();

       }else {
           cache.set(ip,s+1);
       }
//      if(cache.size()>3)
//       {
//           //如果cache中的ip有3个了，更新访客
//           indexService.updateVisitorsNum(cache.size());
//
//       }
        return this.page(1, size, model);

    }

    @GetMapping( "/page/{p}" )
    public String page(@PathVariable Integer p, @RequestParam( defaultValue = "9" ) Integer size, Model model) {
        //查询所有已经发布的文章
        PageInfo<Contents> allContents = indexService.selectAllPublishContents(p, size);
        for (Contents c : allContents.getList()
                ) {
            String content = c.getContent();
                if (content.length() > 100) {
                String substring = content.substring(0, 100);

                c.setContent(substring + "...");
            }
            if (content.startsWith("<iframe") && content.endsWith("</iframe>")) {
                c.setContent("...");
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
