package com.mmall.controller.portal;

import com.mmall.commons.Const;
import com.mmall.commons.ResponseEnum;
import com.mmall.commons.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService iUserService;

    /**
     * 登入
     * @return
     */
    @RequestMapping(value = "login.do" , method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String username , String password , HttpSession session){
        ServerResponse<User> login = iUserService.login(username, password);
        if (login.isSuccess()){//登入成功
            //放入session
            session.setAttribute(Const.CURRENT_USER , login.getData());
        }
        return login;
    }
    /**
     * 登出
     */
    @RequestMapping(value = "logout.do" , method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> logout(HttpSession session){
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess();
    }

    /**
     * 注册   springMVC数据绑定功能
     */
    @RequestMapping(value = "register.do" , method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> register(User user){
        return iUserService.register(user);
    }

    /**
     * 检验参数
     * @param str
     * @param type
     * @return
     */
    @RequestMapping(value = "check_vaild.do" , method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> checkVaild(String str,String type){
        return iUserService.checkValid(str ,type);
    }

    /**
     * 获取登入用户信息
     * @param session
     * @return
     */
    @RequestMapping(value = "get_user_info.do" , method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> getUserInfo(HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user != null){
            return ServerResponse.createBySuccess(user);
        }
        return ServerResponse.createByErrorMessage("用户未登入，无法获取当前用户信息");
    }

    /**
     * 找回密码问题
     * @param username
     * @return
     */
    @RequestMapping(value = "forget_get_question.do" , method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetGetQuestion(String username){
        return iUserService.selectUserQuestion(username);
    }

    /**
     * 提交问题答案
     * @param username
     * @param question
     * @param answer
     * @return
     */
    @RequestMapping(value = "forget_check_answer.do" , method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetCheckAnswer(String username,String question , String answer){
        return iUserService.checkAnswer(username,question,answer);
    }

    /**
     * 重设密码
     */
    @RequestMapping(value = "forget_reset_password.do" , method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetResetPassword(String username , String passwordNew ,String forgetToken){
        return iUserService.forgetResetPassword(username,passwordNew,forgetToken);
    }

    /**
     * 登入状态重置密码
     */
    @RequestMapping(value = "reset_password.do" , method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> resetPassword(String passwordOld , String passwordNew , HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user==null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return iUserService.resetPassword(passwordOld,passwordNew,user);
    }

    /**
     * 登入状态更新 个人信息
     */
    @RequestMapping(value = "update_information.do" , method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> updateInformation(HttpSession session , User user){
        User sessionUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (sessionUser == null){
            return ServerResponse.createByErrorMessage("用户未登入");
        }
        user.setId(sessionUser.getId());
        ServerResponse response = iUserService.updateInformation(user);
        if (response.isSuccess()){
            return ServerResponse.createBySuccessMsg("更新信息成功");
        }
        return response;
    }

    /**
     * 获取当前登入用户的详细信息，并强制登入
     */
    @RequestMapping(value = "get_information.do" , method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> getInformation(HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseEnum.NEED_LOGIN.getCode() , "未登入！");
        }
        return iUserService.getInformation(user.getId());
    }




}
