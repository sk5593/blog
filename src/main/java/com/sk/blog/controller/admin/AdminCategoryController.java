package com.sk.blog.controller.admin;

import com.sk.blog.bean.Categories;
import com.sk.blog.service.CategoryService;
import com.sk.blog.utils.AdminCommons;
import com.sk.blog.utils.Commons;
import com.sk.blog.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
public class AdminCategoryController {
    AdminCommons adminCommons = new AdminCommons();
    Commons commons = new Commons();
    @Autowired
    CategoryService categoryService;
    @GetMapping("admin/category")
    /**
     * 跳转到分类页面
     */
    public String redirectCategory(Model model)
    {
        List<Categories> allCategories = categoryService.getAllCategories();
        model.addAttribute("categories",allCategories);
        model.addAttribute("commons",commons);
        model.addAttribute("adminCommons",adminCommons);
        return "admin/category";
    }
    /**
     * 保存分类
     */
    @PostMapping("/admin/category")
    @ResponseBody
    public Result saveCategory(String cname,Integer kid)
    {

        if(kid !=null)
        {
            //执行更新操作
            Result result = categoryService.updateCategory(cname, kid);
            return result;
        }else {
            //插入新分类
            Result result = categoryService.saveCategory(cname);
            return result;
        }
    }
    /**
     * 分类删除
     */
    @DeleteMapping("/admin/category")
    @ResponseBody
    public Result deleteCategory(Integer kid)
    {
        Result result = categoryService.deleteCategory(kid);
        return result;
    }
    /**
     * 分类修改
     */
//    @PutMapping("/admin/category")
//    @ResponseBody
//    public Result putCategory(String cname,Integer kid)
//    {
//        System.out.println(kid+"1111111111111111111111111111"+cname);
//        Result result = categoryService.updateCategory(cname, kid);
//        return result;
//    }
}
