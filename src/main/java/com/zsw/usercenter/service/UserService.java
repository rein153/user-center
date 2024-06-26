package com.zsw.usercenter.service;

import com.zsw.usercenter.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

/**
* @author zsw
*
*/
public interface UserService extends IService<User> {


    /**
     * 用户注册
     * @param userAccount 用户账户
     * @param userPassword 用户密码
     * @param checkPassword 校验密码
     * @param plantCode 编号
     * @return 新用户 id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword, String plantCode);

    /**
     * 用户登录
     * @param userAccount
     * @param userPassword
     * @param request
     * @return user对象
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户脱敏
     * @param originUser
     * @return
     */
    User getSafetyUser(User originUser);

    /**
     * 清求用户注销
     * @param request
     * @return
     */
    int userLogout(HttpServletRequest request);
}
