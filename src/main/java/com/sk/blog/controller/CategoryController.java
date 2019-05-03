package com.sk.blog.controller;

import com.sk.blog.bean.Categories;
import com.sk.blog.bean.Contents;
import com.sk.blog.bean.ContentsExample;
import com.sk.blog.dao.ContentsMapper;
import com.sk.blog.service.ArchivesService;
import com.sk.blog.service.ArticleService;
import com.sk.blog.service.CategoryService;
import com.sk.blog.utils.Commons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CategoryController {
    Commons commons=new Commons();
    @Autowired
    CategoryService categoryService;
    @Autowired
    ContentsMapper contentsMapper;
    @Autowired
    ArticleService articleService;
    ContentsExample example=new ContentsExample();
    @RequestMapping("/categories")
    public String show(Model model)
    {
//        获取分类id和分类名
        List<Categories> allCategories = categoryService.getAllCategories();
        for (Categories c:allCategories
                ) {
//           设置每个分类下的文章列表和对应的数量
            Integer kid = c.getKid();

            List<Contents> contents = contentsMapper.selectArticlesByKid(kid);

//            根据分类名查询对应的文章数量
            String cname = c.getCname();
            Integer count = categoryService.selectCountByCategory(cname);
            c.setCount(count);
            c.setList(contents);
        }
        model.addAttribute("commons",commons);
        model.addAttribute("archives",allCategories);
        return "index/archives";
    }
    @ResponseBody
    @GetMapping("/indexCategories")
    /**
     * 主页分类显示
     */
    public List<Categories> indexCategories()
    {
       //获取所有的分类id和分类名
        List<Categories> allCategories = categoryService.getAllCategories();
        for (Categories c:allCategories
                ) {
            //根据分类名查询对应的文章数量
            String cname = c.getCname();
            Integer count = categoryService.selectCountByCategory(cname);
            c.setCount(count);
        }
        return allCategories;
    }

    /**
     * 根据主页上的分类查询相关文章
     * @param id  kid
     * @return
     */
    @RequestMapping("/categories/{id}")
    public String getContentsByCategoryId(@PathVariable Integer id,Model model)
    {
        //获取文章列表
        List<Contents> contents = contentsMapper.selectArticlesByKid(id);
        //获取每个文章的分类
        String s = contentsMapper.selectCateNameById(id);
        for (Contents c:contents
             ) {
            c.setCategories(s);
        }
        for (Contents c:contents
                ) {
            String content = c.getContent();
            if(content.length()>20)
            {
                String substring = content.substring(0, 100);
                c.setContent(substring+"...");
            }
            if(content.startsWith("<iframe")&&content.endsWith("</iframe>"))
            {
                c.setContent("...");
            }
        }
        model.addAttribute("articles",contents);
        model.addAttribute("commons",commons);
        model.addAttribute("temp","temp");
        return "index/index";
    }


}
