package com.sk.blog.dao;

import com.sk.blog.bean.Options;
import com.sk.blog.bean.OptionsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OptionsMapper {
    long countByExample(OptionsExample example);

    int deleteByExample(OptionsExample example);

    int deleteByPrimaryKey(String name);

    int insert(Options record);

    int insertSelective(Options record);

    List<Options> selectByExample(OptionsExample example);

    Options selectByPrimaryKey(String name);

    int updateByExampleSelective(@Param("record") Options record, @Param("example") OptionsExample example);

    int updateByExample(@Param("record") Options record, @Param("example") OptionsExample example);

    int updateByPrimaryKeySelective(Options record);

    int updateByPrimaryKey(Options record);
}