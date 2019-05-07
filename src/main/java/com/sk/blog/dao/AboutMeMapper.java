package com.sk.blog.dao;

import com.sk.blog.bean.AboutMe;
import com.sk.blog.bean.MessagesBoard;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface AboutMeMapper {
    void delectMessagesBoard(Integer mid);
    public List<AboutMe>  getAllAbout();
    //保存
    void insert(AboutMe aboutMe);
    //删除
    void delete(Integer aid);
    void update(AboutMe aboutMe);
    //提交留言
    void insertMessagesBoard(MessagesBoard messagesBoard);
    //分页获取留言
    List<MessagesBoard> getMessagesBoard();
    //获取留言数
    Integer getCountMessagesBoard();
    //获取最新留言
    List<MessagesBoard> getLastMessagesBoard();
    //查询十分钟之内某个ip 的留言数
    Integer getCountByLimitTenMinute(@Param("created") Long created,@Param("ip") String ip);
    Integer getCountByLimitTenMinuteFromComments(@Param("created") Long created,@Param("ip") String ip);
}
