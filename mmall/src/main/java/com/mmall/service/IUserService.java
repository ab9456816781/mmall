package com.mmall.service;

import com.mmall.commons.ServerResponse;
import com.mmall.pojo.User;

public interface IUserService {

    //登入
    ServerResponse<User> login(String usernmae, String password);

    //注册
    ServerResponse<String> register(User user);

    //检验参数
    ServerResponse<String> checkValid(String str , String type);

    ServerResponse selectUserQuestion(String username);

    ServerResponse checkAnswer(String username, String question, String answer);

    ServerResponse forgetResetPassword(String username , String passwordNew ,String forgetToken);

    ServerResponse updateInformation(User user);

    ServerResponse<String> resetPassword(String passwordOld, String passwordNew, User user);

    ServerResponse<User> getInformation(Integer id);

    ServerResponse checkAdminRole(User user);
}
