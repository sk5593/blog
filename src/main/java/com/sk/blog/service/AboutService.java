package com.sk.blog.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sk.blog.bean.AboutMe;
import com.sk.blog.bean.MessagesBoard;
import com.sk.blog.dao.AboutMeMapper;
import com.sk.blog.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Service
public class AboutService {
    @Autowired
    AboutMeMapper aboutMeMapper;
    public List<AboutMe> getAllAbout(){
        List<AboutMe> allAbout = aboutMeMapper.getAllAbout();
        return allAbout;
    }
    /**
     * 保存
     */
    @Transactional
    public Result insertAbout(AboutMe aboutMe)
    {
        long time = new Date().getTime();
        aboutMe.setAcreated(time);
        try {
            aboutMeMapper.insert(aboutMe);
            return Result.ok();
        }
        catch (Exception ex)
        {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.msg("添加失败!");
        }


    }

    /**
     * 删除
     * @param aid
     * @return
     */
    @Transactional
    public Result deleteAbout(Integer aid)
    {
        try {
            aboutMeMapper.delete(aid);
            return Result.ok();
        }
        catch (Exception ex)
        {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.msg("删除失败!");
        }
    }
    /**
     * 更新关于
     */
    public Result updateAbout(AboutMe aboutMe)
    {
        try {
            aboutMeMapper.update(aboutMe);
            return Result.ok();
        }
        catch (Exception ex)
        {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.msg("更新失败!");
        }
    }
    /**
     * 提交留言
     * @return
     */
    public Result messagesBoard(MessagesBoard messagesBoard)
    {
        try {
            //需要判断同一ip不能重复多次留言
            String ip = messagesBoard.getIp();
            Long created = messagesBoard.getCreated();
            //十分钟之前
            Long limitCreated = created - 600000;

            Integer countByLimitTenMinute = aboutMeMapper.getCountByLimitTenMinute(limitCreated,ip);

            if (countByLimitTenMinute > 10) {
                return Result.msg("您操作太频繁啦，请稍后再试");
            } else {
                aboutMeMapper.insertMessagesBoard(messagesBoard);
                return Result.ok();
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return Result.msg("留言失败");
        }

    }
    /**
     * 分页获取留言内容
     */
    public PageInfo<MessagesBoard> getMessagesBoard(Integer page,Integer pageSize)
    {
        PageHelper.startPage(page,pageSize);
        List<MessagesBoard> messagesBoard = aboutMeMapper.getMessagesBoard();
        PageInfo<MessagesBoard> pageInfo = new PageInfo<>(messagesBoard);
        return pageInfo;

    }
    /**
     * 获取留言数
     */
    public Integer getCountMessagesBoard()
    {
        return aboutMeMapper.getCountMessagesBoard();
    }

    /**
     * 获取最新留言
     */
    public List<MessagesBoard> getLastMessagesBoard()
    {
        List<MessagesBoard> lastMessagesBoard = aboutMeMapper.getLastMessagesBoard();
        return lastMessagesBoard;

    }

}
