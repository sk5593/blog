package com.sk.blog.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sk.blog.bean.Comments;
import com.sk.blog.bean.Contents;
import com.sk.blog.service.ArticleService;
import com.sk.blog.utils.Commons;
import com.sk.blog.utils.IpAddr;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.enterprise.inject.Default;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
public class ArticleController {

    @Autowired
    ArticleService articleService;
    @RequestMapping("article/{cid}")
    /**
     * 第一次访问该文章 ，显示第一页评论
     * 根据点击的id 查看文章详情和评论
     * 问题是 common每次都要送到域中
     */
  public String article(@PathVariable Integer cid, Model model, HttpSession session,Integer p)
  {

      Contents article = articleService.getArticle(cid);
      //点击数加1
      Integer hits = article.getHits();
      hits=hits+1;

      articleService.addHits(hits,cid);

      model.addAttribute("article",article);
      Commons commons=new Commons();
      model.addAttribute("commons",commons);

      //将cid传到session域中，供分页使用
      session.setAttribute("ArticleCid",article.getCid());

      //第一次查看页面时候，访问的是此mapping
      //当点击的是下一页时候，访问的是comments
      getComments(session,model,1,3);
      return "index/page";
  }

    /**点击下一页评论，再次访问该文章页，显示下一页评论
     * 评论分页
     * @param session
     * @param model
     * @param p
     * @param size
     * @return
     */
  @RequestMapping("comments/{p}")
  public String getComments(HttpSession session, Model model, @PathVariable Integer p,@RequestParam(defaultValue = "3") Integer size)
  {

      //获取第一次访问时传入的cid
      Integer cid = (Integer) session.getAttribute("ArticleCid");
      //再次获取此article
      Contents article = articleService.getArticle(cid);
      model.addAttribute("article",article);
      //分页，从url中获取开始的下标
      PageHelper.startPage(p,size);
      //获取分页评论
      List<Comments> list = articleService.getComments(cid);
      //封装成pageinfo
      PageInfo<Comments> comments=new PageInfo<>(list);
      model.addAttribute("comments",comments);
      Commons commons=new Commons();
      model.addAttribute("commons",commons);
      return "index/page";
  }

    /**
     *
     * @param author 提交人
     * @param mail 提交的邮箱
     * @param textarea 提交的内容
     * @param session 文章id
     * @return
     */
    @PostMapping("/comments")
    public String subComment(String author, String mail, String textarea, HttpSession session, HttpServletRequest request)
    {
        //获取提交人的ip地址
        IpAddr ia=new IpAddr();
        String ip = ia.getIp(request);
        //如果没有填写名字，给一个默认名字
        if(StringUtils.isEmpty(author))
        {
            author="匿名用户";
        }
        //该文章的评论
        Integer articleCid = (Integer) session.getAttribute("ArticleCid");
        //获取当前时间戳
        long time =  new Date().getTime();
        //封装评论数据
        Comments comments=new Comments();
        comments.setIp(ip);
        comments.setCreated(time);
        comments.setAuthor(author);
        comments.setMail(mail);
        comments.setContent(textarea);
        comments.setCid(articleCid);
        articleService.insertComment(comments);
        return "redirect:article/"+articleCid;
    }

}
