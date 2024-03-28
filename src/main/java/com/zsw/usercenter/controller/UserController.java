package com.zsw.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zsw.usercenter.constant.UserConstant;
import com.zsw.usercenter.model.domain.User;
import com.zsw.usercenter.model.domain.request.UserLoginRequest;
import com.zsw.usercenter.model.domain.request.UserRegisterRequest;
import com.zsw.usercenter.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.zsw.usercenter.constant.UserConstant.ADMIN_ROLE;

//import static com.zsw.usercenter.constant.UserConstant.ADMIN_ROLE;

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

    @GetMapping("/search")
    public List<User> searchUsers(String username, HttpServletRequest request){
        // 用户角色鉴权,仅管理员可查询
        if(!isAdmin(request)){
            return new ArrayList<>();
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(username)){
            queryWrapper.like("username",username);
        }

        // 全部返回信息太多了，过滤一下
//        return userService.list(queryWrapper);

        List<User> userList = userService.list(queryWrapper);
        return userList.stream().map(user -> {
            user.setUserPassword(null);
            return userService.getSafetyUser(user);
        }).collect(Collectors.toList());
    }

    @PostMapping("/delete")
    public boolean deleteUsers(@RequestBody long id, HttpServletRequest request){
        // 用户角色鉴权,仅管理员可删除
        if(!isAdmin(request)){
            return false;
        }

        if(id <= 0) {
            return false;
        }
        return userService.removeById(id);
    }


    /**
     * 是否为管理员
     *
     * @param request
     * @return
     */

    //查询和删除是同样的逻辑
    private boolean isAdmin(HttpServletRequest request){
        Object userObj = request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        User user = (User) userObj;
        if(user == null || user.getUserRole() != ADMIN_ROLE){
            return false;
        }
        return true;
    }
}
