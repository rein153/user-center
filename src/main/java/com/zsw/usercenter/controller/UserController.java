package com.zsw.usercenter.controller;

import com.zsw.usercenter.model.domain.User;
import com.zsw.usercenter.model.domain.request.UserLoginRequest;
import com.zsw.usercenter.model.domain.request.UserRegisterRequest;
import com.zsw.usercenter.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 用户接口
 *
 * @author zsw
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/register")
    public Long userRegister(@RequestBody UserRegisterRequest userRegisterRequest){

        if(userRegisterRequest == null){
            return -1L;
        }

        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();

        if(StringUtils.isAllBlank(userAccount,userPassword,checkPassword)){
            return null;
        }


        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        return userService.userRegister(userAccount, userPassword, checkPassword);
    }

    @PostMapping("/login")
    public User userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request){

        if(userLoginRequest == null){
            return null;
        }

        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();


        if(StringUtils.isAllBlank(userAccount,userPassword)){
            return null;
        }

        User user = userService.userLogin(userAccount, userPassword, request);
        return userService.userLogin(userAccount, userPassword, request);
    }
}
