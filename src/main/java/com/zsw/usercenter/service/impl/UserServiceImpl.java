package com.zsw.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsw.usercenter.model.domain.User;
import com.zsw.usercenter.service.UserService;
import com.zsw.usercenter.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author zsw
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2024-03-11 23:30:35
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




