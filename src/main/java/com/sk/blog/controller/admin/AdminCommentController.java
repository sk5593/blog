package com.sk.blog.controller.admin;

import com.github.pagehelper.PageInfo;
import com.sk.blog.bean.Comments;
import com.sk.blog.service.admin.CommentService;
import com.sk.blog.utils.Commons;
import com.sk.blog.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class AdminCommentController {
    Commons commons = new Commons();
    @Autowired
    CommentService commentService;
    /**
     * 跳转到评论列表,封装数据库中的评论
     */
    @RequestMapping("/admin/comments")
    public String redirectComments(Model model,@RequestParam(defaultValue = "1") Integer page)
    {

        PageInfo<Comments> allComments = commentService.getAllComments(page);
        model.addAttribute("comments",allComments);
        model.addAttribute("commons",commons);
        return "admin/comment_list";

    }
    /**
     * 删除评论
     */
    @ResponseBody
    @RequestMapping("/admin/comments/delete")
    public Result deleteComments(Integer coid,Integer cid)
    {
        //评论数减1,删除评论
        Result result = commentService.deleteComment(coid,cid);

        return result;
    }
}
