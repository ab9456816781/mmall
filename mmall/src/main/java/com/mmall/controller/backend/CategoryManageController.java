package com.mmall.controller.backend;


import com.mmall.commons.Const;
import com.mmall.commons.ResponseEnum;
import com.mmall.commons.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.ICategoryService;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/manage/category")
public class CategoryManageController {

    @Autowired
    private ICategoryService iCategoryService;

    @Autowired
    private IUserService iUserService;

    /**
     * 添加分类功能
     * @param session
     * @param categoryName
     * @param parentId
     * @return
     */
    @RequestMapping(value = "add_category.do")
    @ResponseBody
    public ServerResponse addCategory(HttpSession session , String categoryName ,  @RequestParam(value = "parentId" ,defaultValue = "0") Integer parentId ){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseEnum.NEED_LOGIN.getCode(), "用户未登入，请登入！");
        }
        //校验是否为 管理员
        if (iUserService.checkAdminRole(user).isSuccess()){
            return iCategoryService.addCategory(categoryName, parentId);
        }
        return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
    }

    /**
     * 修改 分类名称
     * @return
     */
    @RequestMapping(value = "set_category_name.do")
    @ResponseBody
    public ServerResponse updateCategoryName(HttpSession session ,Integer categoryId,String categoryName ){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseEnum.NEED_LOGIN.getCode(), "用户未登入，请登入！");
        }
        //校验是否为 管理员
        if (iUserService.checkAdminRole(user).isSuccess()){
            return iCategoryService.setCategoryName(categoryId,categoryName);
        }
        return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
    }

    /**
     * 获取平级  无递归
     * @param categoryId
     * @return
     */
    @RequestMapping(value = "get_category.do")
    @ResponseBody
    public ServerResponse getChildrenParallelCategory(HttpSession session ,@RequestParam(value = "categoryId" ,defaultValue = "0") Integer categoryId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseEnum.NEED_LOGIN.getCode(), "用户未登入，请登入！");
        }
        //校验是否为 管理员
        if (iUserService.checkAdminRole(user).isSuccess()){
            //查询子节点 的 category信息，并且不递归，保持平级
            return iCategoryService.getChildrenParallelCategory(categoryId);
        }
        return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
    }

    @RequestMapping(value = "get_deep_category.do")
    @ResponseBody
    public ServerResponse findChildrenCategory(HttpSession session ,@RequestParam(value = "categoryId" ,defaultValue = "0") Integer categoryId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseEnum.NEED_LOGIN.getCode(), "用户未登入，请登入！");
        }
        //校验是否为 管理员
        if (iUserService.checkAdminRole(user).isSuccess()){
            //查找当前节点id 和 子节点id
            return iCategoryService.selectCategoryAndChildrenById(categoryId);
        }
        return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
    }

}
