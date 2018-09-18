package com.mmall.controller.portal;

import com.mmall.commons.Const;
import com.mmall.commons.ResponseEnum;
import com.mmall.commons.ServerResponse;
import com.mmall.dao.CartMapper;
import com.mmall.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartMapper cartMapper;

    @RequestMapping("/list.do")
    public ServerResponse list(HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseEnum.NEED_LOGIN.getCode() , ResponseEnum.NEED_LOGIN.getMsg());
        }


        //yonghui66898
        return ServerResponse.createBySuccess();
    }
}
