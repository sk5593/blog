package com.sk.blog.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sk.blog.bean.Contents;
import com.sk.blog.bean.ContentsExample;
import com.sk.blog.bean.IndexMessages;
import com.sk.blog.bean.Visitors;
import com.sk.blog.dao.ContentsMapper;
import com.sk.blog.dao.VisitorsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Service
public class IndexService {
    @Autowired
    ContentsMapper contentsMapper;
    @Autowired
    VisitorsMapper visitorsMapper;

    /**
     * 分页显示所有文章
     *
     * @param page
     * @param size
     * @return
     */
    public PageInfo<Contents> getAllContents(Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<Contents> list = contentsMapper.selectAllContents();

        PageInfo<Contents> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 查询已发布文章
     */
    public PageInfo<Contents> selectAllPublishContents(Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<Contents> list = contentsMapper.selectAllPublishContents();

        PageInfo<Contents> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 更新访客数量
     */
    public void updateVisitorsNum() {
        visitorsMapper.updateVisitorsNum();
    }

    /**
     * @param ip   访客ip
     * @param time 访问时间
     */
    public void insertVisitorsMessages(String ip, Long time) {
        visitorsMapper.insertVisitorsMessages(ip, time);
    }

    /**
     * @return 获取访客数量
     */
    public Visitors getVisitorsNum() {
        Visitors visitorsNum = visitorsMapper.getVisitorsNum();
        return visitorsNum;
    }

    /**
     * 获取主页信息
     * private Integer archivesNum:文章数;
     * private Integer commentsNum ： 评论数;
     * private Long lastUpdate :最后更新的文章的时间点;
     * private Visitors visitors ：总浏览量和每日浏览量;
     */
    public IndexMessages getIndexMessages() {
        IndexMessages indexMessages = new IndexMessages();
        Integer contents = contentsMapper.selectCountContents();
        Integer comments = contentsMapper.selectCountComments();
        Long last = contentsMapper.selectLastUpdate();
        //将时间戳转化为时间
        Date date = new Date();
        date.setTime(last);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(date);
        indexMessages.setArticlesNum(contents);
        indexMessages.setLastUpdate(format);
        indexMessages.setCommentsNum(comments);
        Visitors visitorsNum = this.getVisitorsNum();
        indexMessages.setVisitors(visitorsNum);
        return indexMessages;
    }

}
