package com.sk.blog.controller.admin;

import com.sk.blog.bean.Categories;
import com.sk.blog.bean.Contents;
import com.sk.blog.service.ArticleService;
import com.sk.blog.service.CategoryService;
import com.sk.blog.utils.AdminCommons;
import com.sk.blog.utils.Commons;
import com.sk.blog.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AdminArticleController {
    Commons commons=new Commons();
    @Autowired
    CategoryService categoryService;
    @Autowired
    ArticleService articleService;
    /**
     * 跳转到发布文章页面
     */

    @GetMapping("/admin/article/publish")
    public String indexPublish(Model model)
    {
        //获得类别
        List<Categories> allCategories = categoryService.getAllCategories();
        model.addAttribute("categories",allCategories);
        //
        model.addAttribute("commons",commons);
        return "admin/article_edit";
    }

    /**
     private String title;
     private Long created;
     private Integer modified;
     private Integer authorId;
     private String type;
     private String status;
     private String tags;
     private Integer categories;
     private Integer hits;
     private Integer commentsNum;
     private Boolean allowComment;
     private Boolean allowStick;
     private Boolean allowFeed;
     private String content;
     * 文章发布
     *  获取文章信息
     * @return
     */
    @Transactional
    @ResponseBody
    @PostMapping("/admin/article/publish")
    public Result saveArticle(Contents content)
    {
        try {

            articleService.insertArticles(content);
            return Result.ok();
        }
        catch (Exception e)
        {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.msg("添加文章失败！");
        }

    }

    /**
     * 删除文章
     * @param cid 前端传来的文章 id
     * @return
     */
    @ResponseBody
    @PostMapping("/admin/article/delete")
   public Result deleteArticle(Integer cid)
   {
       Result result = articleService.deleteArticle(cid);
       return result;
   }
    /**
     * 编辑文章，跳转到编辑页面
     */
    @RequestMapping("/admin/article/{cid}")
    public String editArticle(@PathVariable Integer cid,Model model)
    {
        //根据id查找对应的文章
        Contents contents = articleService.selectArticleById(cid);
        model.addAttribute("contents",contents);
        model.addAttribute("commons",commons);
        //获得类别
        List<Categories> allCategories = categoryService.getAllCategories();
        model.addAttribute("categories",allCategories);
        AdminCommons adminCommons = new AdminCommons();
        model.addAttribute("adminCommons",adminCommons);
        return "admin/article_edit";
    }
    /**
     * 修改文章 ， 提交编辑后的文章
     */
    @ResponseBody
    @PostMapping("/admin/article/modify")
    public Result updateArticle(Contents contents)
    {

        Result result = articleService.updateArticle(contents);
        return result;
    }
}
