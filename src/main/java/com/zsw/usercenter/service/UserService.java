package com.zsw.usercenter.service;

import com.zsw.usercenter.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author zsw
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2024-03-11 23:30:35
*/
public interface UserService extends IService<User> {

    /**
     * 用户登录
     */
    long UserRegist()
}
