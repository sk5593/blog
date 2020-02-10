package com.sk.blog.controller.admin;

import com.sk.blog.bean.Categories;
import com.sk.blog.bean.Contents;
import com.sk.blog.service.ArticleService;
import com.sk.blog.service.CategoryService;
import com.sk.blog.utils.AdminCommons;
import com.sk.blog.utils.Commons;
import com.sk.blog.utils.Result;
import org.apache.tomcat.util.http.fileupload.FileUploadException;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

@Controller
public class AdminArticleController {
    @Value("${web.upload-path}")
    String uploadPath;
    @Value("${web.upload-url}")
    String uploadUrl;
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
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    @PostMapping("/admin/article/publish")
    public Result saveArticle(Contents content)
    {
        try {
            articleService.insertArticles(content);
            return Result.ok();
        }catch (Exception e){
              throw e;
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
    /**
     * kindeditor上传图片
     */
    @RequestMapping( "/upload" )
    public void upload(@RequestParam( "imgFile" ) MultipartFile file, HttpServletResponse response, HttpServletRequest request) throws IOException, FileUploadException {
        PrintWriter out = response.getWriter();

        String ext = file.getOriginalFilename();
        ext = ext.substring(ext.lastIndexOf("."));
//        文件名
        String fileName = UUID.randomUUID() + ext;
//        //文件保存目录URL
        String saveUrl = "/images/" + fileName;
        try {
            file.transferTo(new File(uploadPath, fileName));
        } catch (Exception e) {
            out.println(getError("上传文件失败。"));
            return;
        }
        response.setContentType("text/html; charset=UTF-8");
        JSONObject obj = new JSONObject();
        obj.put("error", 0);
//        https://sknebula.top
//        http://localhost:8080
        obj.put("url", uploadUrl + saveUrl);
        response.getWriter().println(obj.toJSONString());
        return;
    }
    private String getError(String message) {
        JSONObject obj = new JSONObject();
        obj.put("error", 1);
        obj.put("message", message);
        return obj.toJSONString();
    }
}
