package com.sk.blog.dao;

import com.sk.blog.bean.Metas;
import com.sk.blog.bean.MetasExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MetasMapper {
    long countByExample(MetasExample example);

    int deleteByExample(MetasExample example);

    int deleteByPrimaryKey(Integer mid);

    int insert(Metas record);

    int insertSelective(Metas record);

    List<Metas> selectByExample(MetasExample example);

    Metas selectByPrimaryKey(Integer mid);

    int updateByExampleSelective(@Param("record") Metas record, @Param("example") MetasExample example);

    int updateByExample(@Param("record") Metas record, @Param("example") MetasExample example);

    int updateByPrimaryKeySelective(Metas record);

    int updateByPrimaryKey(Metas record);
}