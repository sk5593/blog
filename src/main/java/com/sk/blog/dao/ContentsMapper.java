package com.sk.blog.dao;

import com.sk.blog.bean.Archives;
import com.sk.blog.bean.Categories;
import com.sk.blog.bean.Contents;
import com.sk.blog.bean.ContentsExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Size;

@Component
public interface ContentsMapper {
    //首页展示
    List<Contents> index();
    //根据id获取种类的名
    String getCategoryName(Integer id);
    //获取种类id和种类名

    List<Categories> getAllCategories();
    //根据月份查找对应的文章
    List<Contents> getContentsByMonths(String month);
    //分组查询月份
    List<Archives> selectUnixTime();

    long countByExample(ContentsExample example);

    int deleteByExample(ContentsExample example);

    int deleteByPrimaryKey(Integer cid);

    int insert(Contents record);

    int insertSelective(Contents record);

    List<Contents> selectByExampleWithBLOBs(ContentsExample example);

    List<Contents> selectByExample(ContentsExample example);

    Contents selectByPrimaryKey(Integer cid);

    int updateByExampleSelective(@Param("record") Contents record, @Param("example") ContentsExample example);

    int updateByExampleWithBLOBs(@Param("record") Contents record, @Param("example") ContentsExample example);

    int updateByExample(@Param("record") Contents record, @Param("example") ContentsExample example);

    int updateByPrimaryKeySelective(Contents record);

    int updateByPrimaryKeyWithBLOBs(Contents record);

    int updateByPrimaryKey(Contents record);

    void updateHits(@Param("cid") Integer cid,@Param("hits") Integer hits);
}