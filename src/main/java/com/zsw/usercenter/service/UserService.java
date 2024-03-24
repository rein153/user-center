package com.zsw.usercenter.service;

import com.zsw.usercenter.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

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
     * @return 新用户 id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     *
     */

}
