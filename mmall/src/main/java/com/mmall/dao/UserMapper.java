package com.mmall.dao;

import com.mmall.commons.ServerResponse;
import com.mmall.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /**检查用户名**/
    int checkUserName(String username);

    /**检查用户名**/
    int checkEmail(String eamil);

    /**验证用户名密码**/
    User selectLoginUser(@Param("username")String username , @Param("password")String password);

    /***找回密码问题**/
    String selectQuestion(String username);

    int checkAnswer(@Param("username")String username, @Param("question")String question, @Param("answer")String answer);

    int updatePasswordByUsername(@Param("username")String username, @Param("password")String password);

    int checkPassword(@Param("passwordOld")String passwordOld,@Param("userId")Integer userId);

    int checkEmailByUserId(@Param("email")String email, @Param("id")Integer id);
}