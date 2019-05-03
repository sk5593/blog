package com.sk.blog.service.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sk.blog.bean.Comments;
import com.sk.blog.bean.CommentsExample;
import com.sk.blog.dao.CommentsMapper;
import com.sk.blog.dao.ContentsMapper;
import com.sk.blog.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    CommentsMapper commentsMapper;
    @Autowired
    ContentsMapper contentsMapper;
    public PageInfo<Comments> getAllComments(Integer page)
    {
        PageHelper.startPage(page,10);
        List<Comments> comments = commentsMapper.selectAllComments();
        PageInfo pageInfo = new PageInfo(comments);
        return pageInfo;
    }

    /**
     * 删除评论
     * @return
     */
    public Result deleteComment(Integer coid,Integer cid)
    {
        commentsMapper.deleteByPrimaryKey(coid);
        //对应的文章的评论数应该-1
        contentsMapper.fewComments(cid);
        return Result.ok();
    }
}
