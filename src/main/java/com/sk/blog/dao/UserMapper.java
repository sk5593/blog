package com.sk.blog.dao;

import com.sk.blog.bean.User;
import com.sk.blog.bean.UserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
public interface UserMapper {
    Long selectLastLogin();
    Integer selectActivated();

    Integer selectErrorNum();

    void updateErrorsAddOne();

    void  updatelastLoginErrorsActivated(@Param("lastLogin") long lastLogin,@Param("errors") Integer errors,@Param("activated") Integer activated);

    Integer selectByUsernameAndPassword(@Param("username") String username,@Param("password") String password);

    long countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(Integer uid);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Integer uid);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    void updatePasswordByname(@Param("username") String username,@Param("password") String password);
}