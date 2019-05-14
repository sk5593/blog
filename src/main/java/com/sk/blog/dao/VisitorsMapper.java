package com.sk.blog.dao;

import com.sk.blog.bean.Visitor;
import com.sk.blog.bean.Visitors;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface VisitorsMapper {
    //访客数量+1
    public void updateVisitorsNum(Integer size);
    //记录访客信息
    public void insertVisitorsMessages(List<Visitor> list);
    //获取访问人数
    public Visitors getVisitorsNum();
}
