package com.sk.blog.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sk.blog.bean.*;
import com.sk.blog.dao.ContentsMapper;
import com.sk.blog.dao.VisitorsMapper;
import com.sk.blog.utils.TaleUtils;
import jdk.nashorn.api.scripting.ScriptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class IndexService {
    private  Integer tag=0;

    List list = new ArrayList();
    @Autowired
    ContentsMapper contentsMapper;
    @Autowired
    VisitorsMapper visitorsMapper;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    ExecutorService pool;
//    @Autowired
//    List list;
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
    public void updateVisitorsNum(String ip) {


        SetOperations<String, String> op = stringRedisTemplate.opsForSet();

        Long visitors = op.add("visitors", ip);

        //成功为1，失败为0

        pool.execute(() -> {
            synchronized (this) {
                if (visitors == 1) {
                    tag++;
                    Visitor visitor = new Visitor();
                    visitor.setIp(ip);
                    visitor.setTime(new Date().getTime());
                    list.add(visitor);
                }
                if (op.size("visitors") >= 5 && tag >= 5) {
                    try {
                        visitorsMapper.updateVisitorsNum(tag);
                        //批量插入
                        insertVisitorsMessages(list);

                    } finally {
                        list.clear();
                        tag = 0;
                    }
                }
            }

        });

    }



    /**
     * 定时清空缓存
     */
    @Scheduled( cron = "0 0 0 * * ?" )
//    @Scheduled(fixedRate = 5000)
    public void scheduled() {
        Set<String> visitors = stringRedisTemplate.keys("visitors");
        Long delete = stringRedisTemplate.delete(visitors);
    }
    /**
     *
     */
    public void insertVisitorsMessages(List<Visitor> list) {
        visitorsMapper.insertVisitorsMessages(list);
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
        if(last==null)
        {
            last=contentsMapper.selectLastCreated();
        }
        //        //将时间戳转化为时间
        String format = TaleUtils.formatDate(last);
        indexMessages.setArticlesNum(contents);
        indexMessages.setLastUpdate(format);
        indexMessages.setCommentsNum(comments);
        Visitors visitorsNum = this.getVisitorsNum();
        indexMessages.setVisitors(visitorsNum);
        return indexMessages;
    }

}
