package com.sk.blog.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sk.blog.bean.*;
import com.sk.blog.dao.AboutMeMapper;
import com.sk.blog.dao.CommentsMapper;
import com.sk.blog.dao.ContentsMapper;
import com.sk.blog.utils.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.enterprise.inject.New;
import java.util.Date;
import java.util.List;

@Service


public class ArticleService {
    @Autowired
    ContentsMapper contentsMapper;
    @Autowired
    CommentsMapper commentsMapper;
    @Autowired
    AboutMeMapper aboutMeMapper;


    /**
     * 根据id查看文章的具体内容和评论
     *
     * @return
     */
    public Contents getArticle(Integer id) {

        //查询文章详情
        ContentsExample example = new ContentsExample();
        example.createCriteria().andCidEqualTo(id);
        Contents contents = contentsMapper.selectByPrimaryKey(id);


        return contents;
    }

    public List<Comments> getComments(Integer cid) {
        //查询评论
        CommentsExample commentsExample = new CommentsExample();
        CommentsExample.Criteria criteria = commentsExample.createCriteria();
        criteria.andCidEqualTo(cid);
        List<Comments> comments = commentsMapper.selectByExampleWithBLOBs(commentsExample);
        return comments;
    }

    /**
     * 点击数+1
     */
    public void addHits(Integer hits, Integer cid) {
        contentsMapper.updateHits(cid, hits);
    }

    /**
     * 添加评论
     */
    public void insertComment(Comments comments) {
        commentsMapper.insert(comments);
        //评论数加1
        Integer cid = comments.getCid();
        contentsMapper.updateCommennums(cid);
    }

    /**
     * 根据categories查询文章列表
     */
    public List<Contents> getContentsByCategoryId(Integer id) {
        List<Contents> contents = contentsMapper.selectByCategoryId(id);
        return contents;
    }

    /**
     * 创建文章
     */
    public void insertArticles(Contents contents) {

        if(StringUtils.isEmpty(contents.getCategories()))
        {
            contents.setCategories("默认分类");
        }
        contents.setHits(0);
        contents.setCommentsNum(0);
//        contents.setAllowComment(true);
//        contents.setAllowFeed(true);
//        contents.setCategories(1);
        contents.setCreated(new Date().getTime());
        contentsMapper.insert(contents);
        //此时还需要在关系表中插入对应的cid和kid
        String categories = contents.getCategories();
        Integer cid = contents.getCid();
        String[] category = categories.split(",");
        for (String name:category
             ) {
            Integer kid = contentsMapper.selectIdByName(name);
            contentsMapper.insertRelationship(cid,kid);
        }

    }
    /**
     * 查看文章列表
     */
    public PageInfo<Contents> getAllContents(Integer page)
    {
        PageHelper.startPage(page,15);
        List<Contents> list = contentsMapper.selectAllContents();
        PageInfo pageInfo=new PageInfo(list);
        return pageInfo;
    }
    public Result deleteArticle(Integer cid)
    {
        int i = contentsMapper.deleteByPrimaryKey(cid);
        if(i>0)
        {
            return Result.ok();
        }
        else
            return Result.msg("删除失败！");
    }
    /**
     * 根据id查询对应的文章
     */
    public Contents selectArticleById(Integer cid)
    {
        Contents contents = contentsMapper.selectByPrimaryKey(cid);
        return contents;
    }
    /**
     * 修改文章
     */
    public Result updateArticle(Contents contents)
    {
        //如果修改了文章的类型，对应的关系表也应该发生变化
        //先获取原来的类型
        Integer cid = contents.getCid();
        Contents contents1 = contentsMapper.selectByPrimaryKey(cid);
        //类型发生了改变
        if(!contents1.getCategories().equals(contents.getCategories()))
        {
            //将原来的数据删除，然后加入新数据
            contentsMapper.deleteRelationshipByCid(cid);
            //加入新数据
            String categories = contents.getCategories();
            String[] split = categories.split(",");
            for(int x=0;x<split.length;x++)
            {
                //根据分类名获取id
                Integer kid = contentsMapper.selectIdByName(split[x]);
                contentsMapper.insertRelationship(cid,kid);
            }

        }
        int i = contentsMapper.updateByPrimaryKey(contents);
        if(i>0)
            return Result.ok();
        else return Result.msg("更新失败！");
    }
    /**
     * 展示最新的五篇文章
     *
     */
    public List<Contents> getLastArticles()
    {
        List<Contents> lastArticles = contentsMapper.getLastArticles();
        return lastArticles;
    }
    /**
     * 后台主页信息展示
     */
    public AdminIndexMessages getAdminMessages()
    {
        AdminIndexMessages index = new AdminIndexMessages();
        Integer articles = contentsMapper.selectCountContents();
        Integer comments = aboutMeMapper.getCountMessagesBoard();
        index.setArticles(articles);
        index.setAttachs(0);
        index.setLinks(0);
        index.setComments(comments);
        return index;
    }
    /**
     * 主页显示的最新的5篇文章
     */

    public List<Contents> selectLastContents()
    {
        List<Contents> list = contentsMapper.selectLastContents();
        return list;
    }
}


