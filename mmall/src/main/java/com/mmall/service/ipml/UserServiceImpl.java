package com.mmall.service.ipml;

import com.mmall.commons.Const;
import com.mmall.commons.ServerResponse;
import com.mmall.commons.TokenCache;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.mmall.utils.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 登入用户
     * @param usernmae
     * @param password
     * @return
     */
    @Override
    public ServerResponse<User> login(String usernmae, String password) {
        /**
         * 检查用户名
         */
        int resultCount = userMapper.checkUserName(usernmae);
        if (resultCount == 0) {
            return ServerResponse.createByErrorMessage("用户名不存在");
        }
        String md5password = MD5Util.MD5EncodeUtf8(password);
        User user = userMapper.selectLoginUser(usernmae, md5password);
        if (user == null) {
            return ServerResponse.createByErrorMessage("密码错误");
        }

        //将 user 用户的密码滞空
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess("登入成功", user);
    }

    /**
     * 注册
     * @param user
     * @return
     */
    public ServerResponse<String> register(User user){
        //检测 用户名
        ServerResponse<String> validResponse = checkValid(user.getUsername(), Const.USERNAME);
        if (!validResponse.isSuccess()){
            return validResponse;
        }
        //检测 Email
        validResponse = checkValid(user.getEmail(), Const.EMAIL);
        if (!validResponse.isSuccess()){
            return validResponse;
        }
        //角色
        user.setRole(Const.Role.ROLE_CUSTOMER);

        //密码 MD5 加密
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        int resultCount = userMapper.insert(user);
        if (resultCount == 0){
            return ServerResponse.createByErrorMessage("注册失败");
        }
        return ServerResponse.createBySuccessMsg("注册成功");
    }

    public ServerResponse<String> checkValid(String str , String type){
        if (StringUtils.isNotBlank(type)){
            //开始校验
            if (Const.USERNAME.equals(type)){
                int resultCount = userMapper.checkUserName(str);
                if (resultCount > 0){
                    return ServerResponse.createByErrorMessage("用户名已存在");
                }
            }
            if (Const.USERNAME.equals(type)){
                int resultCount = userMapper.checkEmail(str);
                if (resultCount > 0){
                    return ServerResponse.createByErrorMessage("email已存在");
                }
            }
        }else {
            return ServerResponse.createByErrorMessage("参数错误");
        }
        return ServerResponse.createBySuccessMsg("校验成功");
    }

    @Override
    public ServerResponse selectUserQuestion(String username) {
        //先对username 进行检测
        ServerResponse<String> valid = checkValid(username, Const.USERNAME);
        if (valid.isSuccess()){
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        String question = userMapper.selectQuestion(username);
        if (question != null){
            return ServerResponse.createBySuccessMsg(question);
        }
        return ServerResponse.createByErrorMessage("用户未设置找回密码问题！");
    }

    @Override
    public ServerResponse checkAnswer(String username, String question, String answer) {
        int count = userMapper.checkAnswer(username, question, answer);
        if (count > 0){
            String forgetToken = UUID.randomUUID().toString();
            TokenCache.setKey(TokenCache.TOKEN_PREFIX+username , forgetToken);
            return ServerResponse.createBySuccess(forgetToken);
        }
        return ServerResponse.createByErrorMessage("问题回答错误");
    }

    public ServerResponse forgetResetPassword(String username , String passwordNew ,String forgetToken){
        //1.判断token
        if (StringUtils.isBlank(forgetToken)){
            return ServerResponse.createByErrorMessage("参数错误，重新传递Token");
        }
        //2.判断用户名
        ServerResponse<String> valid = checkValid(username, Const.USERNAME);
        if (valid.isSuccess()){
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        //3.获取token
        String token = TokenCache.getKey(TokenCache.TOKEN_PREFIX + username);
        if (StringUtils.isBlank(token)){
            return ServerResponse.createByErrorMessage("token无效或过期");
        }
        //4.判断token是否一致
        if (StringUtils.equals(forgetToken,token)){
            String md5PasswordNew = MD5Util.MD5EncodeUtf8(passwordNew);
            int count = userMapper.updatePasswordByUsername(username, md5PasswordNew);
            if (count > 0){
                return ServerResponse.createBySuccessMsg("重置密码成功！");
            }
        }else {
            return ServerResponse.createByErrorMessage("token错误，请重新发起");
        }
        return ServerResponse.createByErrorMessage("修改密码失败！");
    }

    @Override
    public ServerResponse updateInformation(User user) {
        //username 不能够被更新
        //email 也要进行一个校验，校验其他有没有使用到 email
        int count = userMapper.checkEmailByUserId(user.getEmail() , user.getId());
        if (count > 0){
            return ServerResponse.createByErrorMessage("email重复，请更换！");
        }
        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setEmail(user.getEmail());
        updateUser.setPhone(user.getPhone());
        updateUser.setQuestion(user.getQuestion());
        updateUser.setAnswer(user.getAnswer());
        int resultCount = userMapper.updateByPrimaryKeySelective(updateUser);
        if (resultCount > 0){
            return ServerResponse.createBySuccessMsg("更新个人信息成功");
        }
        return  ServerResponse.createByErrorMessage("更新失败");
    }

    @Override
    public ServerResponse<String> resetPassword(String passwordOld, String passwordNew, User user) {
        if (StringUtils.equals(passwordOld,passwordNew)){
            return ServerResponse.createByErrorMessage("新密码不能与旧密码相同！");
        }
        //防止横向越权，要校验一下这个用户的旧密码，一定要指定是这个用户，因为我们会查询一个count如果不指定id
        int count = userMapper.checkPassword(MD5Util.MD5EncodeUtf8(passwordOld),user.getId());
        if (count == 0){
            return ServerResponse.createByErrorMessage("旧密码错误");
        }
        user.setPassword(MD5Util.MD5EncodeUtf8(passwordNew));
        int updatePasswordCount = userMapper.updateByPrimaryKeySelective(user);
        if (updatePasswordCount > 0){
            return ServerResponse.createByErrorMessage("更新密码成功");
        }
        return ServerResponse.createByErrorMessage("更新密码失败");
    }

    @Override
    public ServerResponse<User> getInformation(Integer userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if (user == null){
            return ServerResponse.createByErrorMessage("找不到当前用户");
        }
        //滞空 password
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess(user);
    }

    /**
     * 校验是否为管理员
     * @return
     */
    public ServerResponse checkAdminRole(User user){
        if (user.getRole().intValue() == Const.Role.ROLE_ADMIN){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByErrorMessage("非管理员不能登入！");
    }

}