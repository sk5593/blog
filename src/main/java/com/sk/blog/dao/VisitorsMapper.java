package com.sk.blog.dao;

import com.sk.blog.bean.Visitors;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface VisitorsMapper {
    //访客数量+1
    public void updateVisitorsNum();
    //记录访客信息
    public void insertVisitorsMessages(@Param("ip") String ip, @Param("time") Long time);
    //获取访问人数
    public Visitors getVisitorsNum();
}
