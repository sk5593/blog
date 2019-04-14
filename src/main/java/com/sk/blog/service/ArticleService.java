package com.sk.blog.service;

import com.sk.blog.bean.Comments;
import com.sk.blog.bean.CommentsExample;
import com.sk.blog.bean.Contents;
import com.sk.blog.bean.ContentsExample;
import com.sk.blog.dao.CommentsMapper;
import com.sk.blog.dao.ContentsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.enterprise.inject.New;
import java.util.List;

@Service


public class ArticleService {
    @Autowired
    ContentsMapper contentsMapper;
    @Autowired
    CommentsMapper commentsMapper;

    /**
     * 根据id查看文章的具体内容和评论
     * @return
     */
    public Contents getArticle(Integer id)
    {

        //查询文章详情
        ContentsExample example=new ContentsExample();
        example.createCriteria().andCidEqualTo(id);
        Contents contents = contentsMapper.selectByPrimaryKey(id);


        return contents;
    }
    public List<Comments> getComments(Integer cid)
    {
        //查询评论
        CommentsExample commentsExample=new CommentsExample();
        CommentsExample.Criteria criteria = commentsExample.createCriteria();
        criteria.andCidEqualTo(cid);
        List<Comments> comments = commentsMapper.selectByExampleWithBLOBs(commentsExample);
        return comments;
    }

    /**
     * 点击数+1
     */
    public void addHits(Integer hits,Integer cid)
    {
        contentsMapper.updateHits(cid,hits);
    }

    /**
     * 添加评论
     */
    public void insertComment(Comments comments)
    {
        commentsMapper.insert(comments);
    }
}


