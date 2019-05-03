package com.sk.blog.dao;

import com.sk.blog.bean.*;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Size;

@Component
public interface ContentsMapper {
    //查询最新的5篇文章
    List<Contents> selectLastContents();
    //评论数减1
    void fewComments(Integer cid);
    Integer selectCountContents();
    Integer selectCountComments();
    Long selectLastUpdate();
    void updateCategory(@Param("cname") String cname,@Param("kid") Integer kid);
    //删除关系表的某条数据
    void deleteRelationshipByCid(Integer cid);
    //更新关系表
    void updateRelationship(Integer cid,Integer kid);
    //保存分类
    void deleteCategory(Integer kid);
    Integer saveCategory(String cname);
    //根据分类名查询对应的文章数量
    Integer selectCountByCategory(String cname);
    //查询已发布的文章
    List<Contents> selectAllPublishContents();
    //插入分类和文章的关系表
    void insertRelationship(@Param("cid") Integer cid,@Param("kid") Integer kid);
    //根据分类id查询分类名
    String selectCateNameById(Integer id);
    //根据分类name查询id
    Integer selectIdByName(String name);
    //根据categories查询相关文章的数量
    Integer selectCountByCategories(Integer kid);
    //根据类别id查询文章
    List<Contents> selectByCategoryId(Integer id);
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

     void updateCommennums(@Param("cid") Integer cid);

    List<Contents> selectArticlesByKid(Integer kid);

    List<Contents> selectAllContents();
    List<Contents> getLastArticles();
}